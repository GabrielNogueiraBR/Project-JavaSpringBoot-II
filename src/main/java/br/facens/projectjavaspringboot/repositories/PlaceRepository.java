package br.facens.projectjavaspringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.facens.projectjavaspringboot.entities.Place;

@Repository
public interface PlaceRepository extends JpaRepository<Place,Long> {
    
}
