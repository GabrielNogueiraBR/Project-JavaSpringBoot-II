package br.facens.projectjavaspringboot.controllers;

import java.net.URI;
import java.util.List;

import javax.servlet.Servlet;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ResponseEntity<List<PlaceDTO>> getAllPlaces(){
        List<PlaceDTO> placesDTO = placeService.getAllPlaces();
        return ResponseEntity.ok(placesDTO);
    }

    @GetMapping("{id}")
    public ResponseEntity<PlaceDTO> getPlaceById(@PathVariable Long id){
        PlaceDTO dto = placeService.getPlaceById(id);
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
