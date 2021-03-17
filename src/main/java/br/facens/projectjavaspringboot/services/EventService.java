package br.facens.projectjavaspringboot.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.facens.projectjavaspringboot.dto.EventDTO;
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
            EventDTO dto = new EventDTO(event.getId(), event.getName(), event.getDescription(), event.getPlace(), event.getContact());
            listDTO.add(dto);
        }

        return listDTO;
    }
}
