package br.facens.projectjavaspringboot.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.facens.projectjavaspringboot.dto.EventDTO;
import br.facens.projectjavaspringboot.dto.EventInsertDTO;
import br.facens.projectjavaspringboot.dto.EventUpdateDTO;
import br.facens.projectjavaspringboot.entities.Event;
import br.facens.projectjavaspringboot.repositories.EventRepository;

@Service
public class EventService {
    
    @Autowired
    private EventRepository repository;

    public Page<EventDTO> getEvents(
            PageRequest pageRequest, 
            String name, 
            String place,
            String description,
            LocalDate startDate
    ) 
    {
            Page<Event> list = repository.findEventPageable(pageRequest, name, place,description,startDate);
            return list.map(event -> new EventDTO(event));
    }

    public List<EventDTO> toDTOList(List<Event> list) {

        List<EventDTO> listDTO = new ArrayList<>();

        for (Event event : list) {
            EventDTO dto = new EventDTO(event.getId(), event.getName(), event.getDescription(), event.getPlace(), event.getEmailContact());
            listDTO.add(dto);
        }

        return listDTO;
    }

    public EventDTO getEventById(Long id) {
        Optional<Event> opt = repository.findById(id);
        Event event = opt.orElseThrow( ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found") );

        return new EventDTO(event);
    }

    public EventDTO insert(EventInsertDTO insertDto) {
        Event event = new Event(insertDto);
        event = repository.save(event);
        return new EventDTO(event);
    }

    public void delete(Long id){
        try{
            repository.deleteById(id);
        }
        catch(EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }
    }

    public EventDTO update(Long id, EventUpdateDTO updateDTO) {
        try{
            Event event = repository.getOne(id);
            event.setName(updateDTO.getName());
            event.setDescription(updateDTO.getDescription());
            event = repository.save(event);
            return new EventDTO(event);
        }
        catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }
    }


}
