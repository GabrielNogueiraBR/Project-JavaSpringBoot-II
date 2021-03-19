package br.facens.projectjavaspringboot.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.facens.projectjavaspringboot.dto.EventDTO;
import br.facens.projectjavaspringboot.dto.EventInsertDTO;
import br.facens.projectjavaspringboot.entities.Event;
import br.facens.projectjavaspringboot.repositories.EventRepository;

@Service
public class EventService {
    
    @Autowired
    private EventRepository repository;

    public List<EventDTO> getEvents() {
       List<Event> list = repository.findAll();
       return toDTOList(list);
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
}
