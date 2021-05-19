package br.facens.projectjavaspringboot.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.facens.projectjavaspringboot.dto.PlaceDTO;
import br.facens.projectjavaspringboot.dto.PlaceInsertDTO;
import br.facens.projectjavaspringboot.dto.PlaceUpdateDTO;
import br.facens.projectjavaspringboot.entities.Place;
import br.facens.projectjavaspringboot.repositories.PlaceRepository;

@Service
public class PlaceService {
    
    @Autowired
    private PlaceRepository placeRepository;

    public List<PlaceDTO> getAllPlaces() {
        List<Place> places = placeRepository.findAll();
        return toDTOList(places);
    }

    public List<PlaceDTO> toDTOList(List<Place> places){
        List<PlaceDTO> placeDTOs = new ArrayList<>();

        for(Place place: places){
            PlaceDTO dto = new PlaceDTO(place);
            placeDTOs.add(dto);
        }

        return placeDTOs;
    }

    public PlaceDTO getPlaceById(Long id) {
        Optional<Place> opt = placeRepository.findById(id);
        Place place = opt.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Place not found"));
        
        return new PlaceDTO(place);
    }

    public PlaceDTO insertPlace(PlaceInsertDTO insertDTO) {
        Place place = new Place(insertDTO);
        place = placeRepository.save(place);

        return new PlaceDTO(place);
    }

    @Transactional
    public void deletePlace(Long id) {
        try {
            // Obter o Place
            Place place = placeRepository.getOne(id);
            // Verificar se possui algum evento ja utilizando o Place
            if(place.getEvents().isEmpty()){
                // Caso nao tenha, podemos excluir o Place
                placeRepository.deleteById(id);
            }
            else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Could not delte Place with foreign key");
            }
        } 
        catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Place not found");
        }
        catch(DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Could not delete Place with foreign key");
        }
        catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found");
        }
    }

    public PlaceDTO updatePlace(Long id, @Valid PlaceUpdateDTO updateDTO) {
        try{
            Place place = placeRepository.getOne(id);
            place.updatePlace(updateDTO);
            place = placeRepository.save(place);

            return new PlaceDTO(place);
        }
        catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found");
        }
    }

    public Page<PlaceDTO> getPagePlaces(PageRequest pageRequest) {
        Page<Place> list = placeRepository.findPlacePageable(pageRequest);
        return list.map(place -> new PlaceDTO(place));
    }
}
