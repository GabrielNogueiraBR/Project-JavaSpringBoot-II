package br.facens.projectjavaspringboot.repositories;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.facens.projectjavaspringboot.entities.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>{
    
    // Montando uma consulta paginada com uma consulta JPQL
    @Query("SELECT DISTINCT e FROM Event e " +
           "INNER JOIN e.places p " +
           "WHERE " +
           " ( LOWER(e.name)      LIKE   LOWER(CONCAT('%',:name,'%')))  AND " +
           " ( LOWER(e.description) LIKE LOWER(CONCAT('%',:description,'%'))) AND" +
           " e.startDate >= :startDate AND " +
           " (LOWER(p.address) LIKE LOWER(CONCAT('%',:address,'%'))) "
    )
    public Page <Event> findEventPageable(Pageable pageRequest, String name, String address, String description, LocalDate startDate);


}
