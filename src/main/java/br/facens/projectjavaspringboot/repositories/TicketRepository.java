package br.facens.projectjavaspringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.facens.projectjavaspringboot.entities.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long>{

}
