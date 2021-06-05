package br.facens.projectjavaspringboot.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.facens.projectjavaspringboot.dto.EventDTO;
import br.facens.projectjavaspringboot.dto.EventInsertDTO;
import br.facens.projectjavaspringboot.dto.EventUpdateDTO;
import br.facens.projectjavaspringboot.dto.TicketDTO;
import br.facens.projectjavaspringboot.dto.TicketInsertDTO;
import br.facens.projectjavaspringboot.dto.TicketsDTO;
import br.facens.projectjavaspringboot.entities.Admin;
import br.facens.projectjavaspringboot.entities.Attend;
import br.facens.projectjavaspringboot.entities.Event;
import br.facens.projectjavaspringboot.entities.Place;
import br.facens.projectjavaspringboot.entities.Ticket;
import br.facens.projectjavaspringboot.entities.TicketType;
import br.facens.projectjavaspringboot.repositories.AttendRepository;
import br.facens.projectjavaspringboot.repositories.EventRepository;
import br.facens.projectjavaspringboot.repositories.TicketRepository;

@Service
public class EventService {
    
    @Autowired
    private EventRepository repository;

    @Autowired
    private AdminService adminService;

    @Autowired
    private PlaceService placeService;

    @Autowired
    private AttendService attendService;

    @Autowired
    private AttendRepository attendRepository;

    @Autowired
    private TicketRepository ticketRepository;

    public Page<EventDTO> getEvents(
            PageRequest pageRequest, 
            String name, 
            String description,
            LocalDate startDate
    ) 
    {
            Page<Event> list = repository.findEventPageable(pageRequest, name, description,startDate);
            return list.map(event -> getEventDTOById(event.getId()));
    }

    @Transactional
    public EventDTO getEventDTOById(Long id) {
        Optional<Event> opt = repository.findById(id);
        Event event = opt.orElseThrow( ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found") );

        return new EventDTO(event,event.freeTicketsSelled(),event.payedTicketsSelled());
    }

    public Event getEventById(Long id){
        Optional<Event> opt = repository.findById(id);
        Event event = opt.orElseThrow( ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found") );

        return event;
    }
    
    public EventDTO insertEvent(EventInsertDTO insertDto) {
        
        // Obtendo o administrador que criou o evento
        Admin admin = adminService.getAdminById(insertDto.getIdAdmin());
        
        // Criando o evento corretamente
        Event event = new Event(insertDto, admin);
        event = repository.save(event);

        // Retornando um EventDTO com os valores corretos de ingressos vendidos
        return getEventDTOById(event.getId());
    }

    @Transactional
    public void deleteEvent(Long id){
        try{
            // Um evento que ja tenha ingressos vendidos nao podera ser removido
            
            // Obtendo o evento
            Event event = repository.getOne(id);

            // Verificando a quantia de ingresso vedidos
            if(event.getTickets().isEmpty()){
                repository.deleteById(id);
            }
            else{
                // Caso nao possa excluir o evento, lancar uma excessao
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"You cannot delete an event with tickets sold.");
            }
        }
        catch(EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }
        catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }
        catch(DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Could not delete Event with foreign key.");
        }
    }
    
    public EventDTO update(Long id, EventUpdateDTO updateDTO) {
        try{
            
            Event event = repository.getOne(id);

            // Nao sera possivel alterar as informacoes do evento apos a sua realizacao
            if(event.getStartDate().isAfter(LocalDate.now())){
                
                Admin admin = adminService.getAdminById(updateDTO.getIdAdmin());
                event.update(updateDTO,admin);
                
                // Verificando se eh possivel alterar de acordo com Place
                
                // Percorrer a lista de Places do evento
                for(Place place : event.getPlaces()){
                    // Caso algum Place nao tenha disponibilidade
                    if(placeService.availability(place, event) == false){
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"You cannot update this Event because the Place "+place.getId() + " does not have availability on the new Dates.");
                    }
                }
    
                event = repository.save(event);
                return getEventDTOById(event.getId());
            }
            else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You cannot change an event after its start date.");
            }

        }
        catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }
    }

    @Transactional
    public EventDTO addPlace(Long idEvent, Long idPlace) {
        
        // Verificar existencia do Event
        Event event = getEventById(idEvent);

        // Verificar existencia do Place
        Place place = placeService.getPlaceById(idPlace);

        // Verificar se o evento já aconteceu
        LocalDateTime startEvent = event.getStartDate().atTime(event.getStartTime());
        if(startEvent.isBefore(LocalDateTime.now())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"You cannot change an event after its start date.");
        }
        else{
            // Validar disponibilidade
            if(placeService.availability(place,event)){
                // Caso o evento nao possua uma relacao com o place, adicionamos essa relacao entre os dois
                if(place.getEvents().contains(event) == false){
                    event.addPlace(place);
                    repository.save(event);
                }
                return getEventDTOById(event.getId());
            }
            else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"This place has no availability for the Event.");
            }
        }
    }

    @Transactional
    public EventDTO removePlace(Long idEvent, Long idPlace) {
        
        // Verificar existencia do Event
        Event event = getEventById(idEvent);

        // Verificar existencia do Place
        Place place = placeService.getPlaceById(idPlace);

        // Verificar se o evento já aconteceu
        LocalDateTime startEvent = event.getStartDate().atTime(event.getStartTime());
        if(startEvent.isBefore(LocalDateTime.now())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"You cannot change an event after its start date.");
        }
        else{
            // Remove o relacionamento de Place e Evento se existir
            if(event.removePlace(place)){
                event = repository.save(event);
                return getEventDTOById(event.getId());
            }
            else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"This Event does not have the Place informed.");
            }
        }
    }

    @Transactional
    public TicketsDTO getTicketsList(Long id) {
        
        // Verificando a existencia do evento
        Event event = getEventById(id);

        // Array com a lista de TicketsDTO
        List<TicketDTO> list = new ArrayList<>();

        for(Ticket ticket: event.getTickets()){
            list.add(new TicketDTO(ticket));
        }

        return new TicketsDTO(id, event.getAmountPayedTickets(), event.getAmountFreeTickets(), event.payedTicketsSelled(), event.freeTicketsSelled(), list);
    }

    @Transactional
    public TicketDTO sellTIcket(Long id, TicketInsertDTO insertDTO) {
        
        // Validar se o Event existe
        Event event = getEventById(id);

        // Validar se o Attend existe
        Attend attend = attendService.getAttendById(insertDTO.getIdAttend());

        // Obtendo o TicketType
        TicketType type = insertDTO.getType();

        // Validar se eh possivel realizar a venda (Nao comprar eventos que ja aconteceram e quantidade de ingressos por tipo)
        if(event.isEventInPast()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"You could not buy any tickets from event who already ocurrence.");
        }
        else{
            if(event.isTicketsAvailable(type)){
                Ticket ticket = new Ticket();
                ticket.setType(type);
                ticket.setDate(Instant.now());
                if(type == TicketType.PAYED){
                    ticket.setPrice(event.getPriceTicket());
                }
                else{
                    ticket.setPrice(0.0);
                }
                ticket.setAttend(attend);
                ticket.setEvent(event);
                
                event.addTicket(ticket);
                repository.save(event);

                return new TicketDTO(ticket);
            }
            else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"All tickets " + type.name() + " already selled.");
            }
        }
    }

    @Transactional
    public void giveBackTicket(Long id, TicketInsertDTO insertDTO) {
        
        // Validar se o Event existe
        Event event = getEventById(id);

        // Validar se o Attend existe
        Attend attend = attendService.getAttendById(insertDTO.getIdAttend());

        // Validar se o Attend possui um Ticket para o Event - Obter o primeiro caso tenha
        Ticket ticketAttend = new Ticket();
        for(Ticket ticket : event.getTickets()){
            if(ticket.getAttend().equals(attend) && ticket.getType() == insertDTO.getType()){
                ticketAttend = ticket;
                break;
            }
        }
        if(event.getTickets().contains(ticketAttend)){
            // Validar se o Event ja comecou
            if(event.isEventBegin()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"You could not give back ticket because this event already begin.");
            }
            else{
                // Devolver o Ticket
                event.removeTicket(ticketAttend);
                // Incrementar o saldo do Attend
                attend.addBalance(ticketAttend.getPrice());

                // Salvar as entidades modificadas
                repository.save(event);
                attendRepository.save(attend);
                ticketRepository.delete(ticketAttend);
            }
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"This attend does not have a ticket " + insertDTO.getType() + " for Event.");
        }
    }
}