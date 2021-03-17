package br.facens.projectjavaspringboot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.facens.projectjavaspringboot.repositories.EventRepository;

@Service
public class EventService {
    
    @Autowired
    private EventRepository repository;
    
}
