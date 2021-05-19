package br.facens.projectjavaspringboot.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.facens.projectjavaspringboot.entities.Place;

@Repository
public interface PlaceRepository extends JpaRepository<Place,Long> {

    @Query("SELECT p FROM Place p")
    public Page<Place> findPlacePageable(PageRequest pageRequest);
    
}
