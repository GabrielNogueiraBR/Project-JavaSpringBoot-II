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
    @Query("SELECT e FROM Event e " +
           "WHERE " +
           " ( LOWER(e.name)      LIKE   LOWER(CONCAT('%', :name,    '%')))  AND " +
           " ( LOWER(e.place)   LIKE   LOWER(CONCAT('%', :place, '%')))      AND" +
           " ( LOWER(e.description) LIKE LOWER(CONCAT('%',:description,'%'))) AND" +
           " e.startDate >= :startDate"
    )
    public Page <Event> findEventPageable(Pageable pageRequest, String name, String place, String description, LocalDate startDate);


}
