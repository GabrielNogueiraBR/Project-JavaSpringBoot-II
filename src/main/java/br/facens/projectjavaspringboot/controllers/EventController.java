package br.facens.projectjavaspringboot.controllers;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.facens.projectjavaspringboot.dto.EventDTO;
import br.facens.projectjavaspringboot.dto.EventInsertDTO;
import br.facens.projectjavaspringboot.services.EventService;


@RestController
@RequestMapping("/events")
public class EventController {
    
    @Autowired
    private EventService service;

    @GetMapping
    public ResponseEntity<Page<EventDTO>> getEvents(

        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "linesPerPage", defaultValue = "6") Integer linesPerPage,
        @RequestParam(value = "direction", defaultValue = "ASC") String direction,
        @RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
        @RequestParam(value = "name", defaultValue = "") String name,
        @RequestParam(value = "place", defaultValue = "") String place,
        @RequestParam(value = "description", defaultValue = "") String description

    ){
        
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),orderBy);

        Page<EventDTO> list = service.getEvents(pageRequest, name, place,description);
        return ResponseEntity.ok(list);
    }

    @GetMapping("{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long id) {
        EventDTO eDto = service.getEventById(id);
        return ResponseEntity.ok(eDto);
    }

    @PostMapping
    public ResponseEntity<EventDTO> insert(@Valid @RequestBody EventInsertDTO insertDto){
        EventDTO eventDTO = service.insert(insertDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(eventDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(eventDTO);
    }
    
}
