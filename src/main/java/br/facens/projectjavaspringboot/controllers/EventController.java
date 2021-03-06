package br.facens.projectjavaspringboot.controllers;

import java.net.URI;
import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.facens.projectjavaspringboot.dto.EventDTO;
import br.facens.projectjavaspringboot.dto.EventInsertDTO;
import br.facens.projectjavaspringboot.dto.EventUpdateDTO;
import br.facens.projectjavaspringboot.dto.TicketDTO;
import br.facens.projectjavaspringboot.dto.TicketInsertDTO;
import br.facens.projectjavaspringboot.dto.TicketsDTO;
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
        @RequestParam(value = "address", defaultValue = "") String address,
        @RequestParam(value = "description", defaultValue = "") String description,
        @RequestParam(value = "startDate", defaultValue = "#{T(java.time.LocalDate).now()}") @DateTimeFormat(iso = ISO.DATE) LocalDate startDate
        
    ){
        
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction.toUpperCase()),orderBy);

        Page<EventDTO> list = service.getEvents(pageRequest, name, address, description,startDate);
        return ResponseEntity.ok(list);
    }

    @GetMapping("{id}")
    public ResponseEntity<EventDTO> getEventDTOById(@PathVariable Long id) {
        EventDTO eDto = service.getEventDTOById(id);
        return ResponseEntity.ok(eDto);
    }

    @PostMapping
    public ResponseEntity<EventDTO> insertEvent(@Valid @RequestBody EventInsertDTO insertDto){
        EventDTO eventDTO = service.insertEvent(insertDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(eventDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(eventDTO);
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id){
        service.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<EventDTO> update(@PathVariable Long id, @Valid @RequestBody EventUpdateDTO updateDTO){
        EventDTO dto = service.update(id, updateDTO);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping("{idEvent}/places/{idPlace}")
    public ResponseEntity<EventDTO> addPlace(@PathVariable Long idEvent, @PathVariable Long idPlace){
        EventDTO dto = service.addPlace(idEvent,idPlace);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("{idEvent}/places/{idPlace}")
    public ResponseEntity<EventDTO> removePlace(@PathVariable Long idEvent, @PathVariable Long idPlace){
        EventDTO dto = service.removePlace(idEvent,idPlace);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/{id}/tickets")
    public ResponseEntity<TicketsDTO> getTicketsList(@PathVariable Long id){
        TicketsDTO dto = service.getTicketsList(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping("/{id}/tickets")
    public ResponseEntity<TicketDTO> sellTicket(@PathVariable Long id, @RequestBody TicketInsertDTO insertDTO){
        TicketDTO dto = service.sellTIcket(id,insertDTO);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{id}/tickets")
    public ResponseEntity<Void> giveBackTicket(@PathVariable Long id, @RequestBody TicketInsertDTO insertDTO){
        service.giveBackTicket(id,insertDTO);
        return ResponseEntity.noContent().build();
    }
}
