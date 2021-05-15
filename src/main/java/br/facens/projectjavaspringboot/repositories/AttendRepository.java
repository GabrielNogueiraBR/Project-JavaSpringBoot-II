package br.facens.projectjavaspringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.facens.projectjavaspringboot.entities.Attend;

@Repository
public interface AttendRepository extends JpaRepository<Attend,Long>{
    
}
