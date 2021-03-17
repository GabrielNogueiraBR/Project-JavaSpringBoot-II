package br.facens.projectjavaspringboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.facens.projectjavaspringboot.services.EventService;

@RestController
@RequestMapping("/events")
public class EventController {
    
    @Autowired
    private EventService service;
}
