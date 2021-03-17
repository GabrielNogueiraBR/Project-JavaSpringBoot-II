package br.facens.projectjavaspringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.facens.projectjavaspringboot.entities.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>{
    
}
