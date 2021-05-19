package br.facens.projectjavaspringboot.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.facens.projectjavaspringboot.entities.Attend;

@Repository
public interface AttendRepository extends JpaRepository<Attend,Long>{

    @Query("SELECT a FROM Attend a")
    public Page<Attend> findAttendeesPageable(PageRequest pageRequest);
    
}
