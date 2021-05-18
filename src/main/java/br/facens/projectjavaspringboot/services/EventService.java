package br.facens.projectjavaspringboot.services;

import java.time.LocalDate;
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
import br.facens.projectjavaspringboot.entities.Admin;
import br.facens.projectjavaspringboot.entities.Event;
import br.facens.projectjavaspringboot.entities.Ticket;
import br.facens.projectjavaspringboot.entities.TicketType;
import br.facens.projectjavaspringboot.repositories.EventRepository;

@Service
public class EventService {
    
    @Autowired
    private EventRepository repository;

    @Autowired
    private AdminService adminService;

    public Page<EventDTO> getEvents(
            PageRequest pageRequest, 
            String name, 
            String description,
            LocalDate startDate
    ) 
    {
            Page<Event> list = repository.findEventPageable(pageRequest, name, description,startDate);
            return list.map(event -> getEventById(event.getId()));
    }

    @Transactional
    public EventDTO getEventById(Long id) {
        Optional<Event> opt = repository.findById(id);
        Event event = opt.orElseThrow( ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found") );

        // Contabilizando a quantidade de ingressos gratituitos e pagos vendidos
        int freeTicketsSelled = 0;
        int payedTicketsSelled = 0;

        for(Ticket ticket: event.getTickets()){
            if(ticket.getType() == TicketType.FREE){
                freeTicketsSelled++;
            }
            else{
                payedTicketsSelled++;
            }
        }

        return new EventDTO(event,Long.valueOf(freeTicketsSelled),Long.valueOf(payedTicketsSelled));
    }

    
    public EventDTO insertEvent(EventInsertDTO insertDto) {
        
        // Obtendo o administrador que criou o evento
        Admin admin = adminService.getAdminById(insertDto.getIdAdmin());
        
        // Criando o evento corretamente
        Event event = new Event(insertDto, admin);
        event = repository.save(event);

        // Retornando um EventDTO com os valores corretos de ingressos vendidos
        return getEventById(event.getId());
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
    
                event = repository.save(event);
                return getEventById(event.getId());
            }
            else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You cannot change an event after its start date.");
            }

        }
        catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }
    }
}
