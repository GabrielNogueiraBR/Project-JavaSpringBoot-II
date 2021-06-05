package br.facens.projectjavaspringboot.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.facens.projectjavaspringboot.dto.PlaceDTO;
import br.facens.projectjavaspringboot.dto.PlaceInsertDTO;
import br.facens.projectjavaspringboot.dto.PlaceUpdateDTO;
import br.facens.projectjavaspringboot.services.PlaceService;

@RestController
@RequestMapping("/places")
public class PlaceController {
    
    @Autowired
    private PlaceService placeService;

    @GetMapping
    public ResponseEntity<Page<PlaceDTO>> getAllPlaces(
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "linesPerPage", defaultValue = "6") Integer linesPerPage,
        @RequestParam(value = "direction", defaultValue = "ASC") String direction,
        @RequestParam(value = "orderBy", defaultValue = "id") String orderBy
    ){

        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction.toUpperCase()),orderBy);

        Page<PlaceDTO> placesDTO = placeService.getPagePlaces(pageRequest);
        return ResponseEntity.ok(placesDTO);
    }

    @GetMapping("{id}")
    public ResponseEntity<PlaceDTO> getPlaceDTOById(@PathVariable Long id){
        PlaceDTO dto = placeService.getPlaceDTOById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<PlaceDTO> insertPlace(@Valid @RequestBody PlaceInsertDTO insertDTO){
        PlaceDTO dto = placeService.insertPlace(insertDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePlace(@PathVariable Long id){
        placeService.deletePlace(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<PlaceDTO> updatePlace(@PathVariable Long id, @Valid @RequestBody PlaceUpdateDTO updateDTO){
        PlaceDTO dto = placeService.updatePlace(id,updateDTO);
        return ResponseEntity.ok().body(dto);
    }
}
