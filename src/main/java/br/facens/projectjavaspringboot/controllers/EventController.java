package br.facens.projectjavaspringboot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.facens.projectjavaspringboot.dto.EventDTO;
import br.facens.projectjavaspringboot.services.EventService;

@RestController
@RequestMapping("/events")
public class EventController {
    
    @Autowired
    private EventService service;


    public ResponseEntity<List<EventDTO>> getEvents(){
        List<EventDTO> list = service.getEvents();
        return ResponseEntity.ok(list);
    }
}