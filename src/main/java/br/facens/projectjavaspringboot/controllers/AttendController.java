package br.facens.projectjavaspringboot.controllers;

import java.net.URI;
import java.util.List;

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

import br.facens.projectjavaspringboot.dto.AttendDTO;
import br.facens.projectjavaspringboot.dto.AttendInsertDTO;
import br.facens.projectjavaspringboot.dto.AttendUpdateDTO;
import br.facens.projectjavaspringboot.services.AttendService;

@RestController
@RequestMapping("/attendees")
public class AttendController {
    
    @Autowired
    private AttendService attendService;

    @GetMapping
    public ResponseEntity<Page<AttendDTO>> getAllAttendees(
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "linesPerPage", defaultValue = "6") Integer linesPerPage,
        @RequestParam(value = "direction", defaultValue = "ASC") String direction,
        @RequestParam(value = "orderBy", defaultValue = "id") String orderBy
    ){

        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction.toUpperCase()),orderBy);

        Page<AttendDTO> attendees = attendService.getPageAttendees(pageRequest);
        return ResponseEntity.ok(attendees);
    }

    @GetMapping("{id}")
    public ResponseEntity<AttendDTO> getAttendById(@PathVariable Long id){
        AttendDTO aDto = attendService.getAttendById(id);
        return ResponseEntity.ok(aDto);
    }

    @PostMapping()
    public ResponseEntity<AttendDTO> insertAttend(@Valid @RequestBody AttendInsertDTO insertDTO){
        AttendDTO attendDTO = attendService.insert(insertDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(attendDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(attendDTO);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAttendById(@PathVariable Long id){
        attendService.deleteAttendById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<AttendDTO> updateAttend(@PathVariable Long id, @Valid @RequestBody AttendUpdateDTO updateDTO){
        AttendDTO attendDTO = attendService.updateAttend(id, updateDTO);
        return ResponseEntity.ok().body(attendDTO);

    }
}
