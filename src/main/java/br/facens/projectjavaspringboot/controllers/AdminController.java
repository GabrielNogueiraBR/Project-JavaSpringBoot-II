
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

import br.facens.projectjavaspringboot.dto.AdminDTO;
import br.facens.projectjavaspringboot.dto.AdminInsertDTO;
import br.facens.projectjavaspringboot.dto.AdminUpdateDTO;
import br.facens.projectjavaspringboot.services.AdminService;

@RestController
@RequestMapping("/admins")
public class AdminController {
    
    @Autowired
    private AdminService adminService;

    @GetMapping
    public ResponseEntity<Page<AdminDTO>> getAllAdmins(
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "linesPerPage", defaultValue = "6") Integer linesPerPage,
        @RequestParam(value = "direction", defaultValue = "ASC") String direction,
        @RequestParam(value = "orderBy", defaultValue = "id") String orderBy
    ){
        
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction.toUpperCase()),orderBy);

        Page<AdminDTO> admins = adminService.getPageAdmins(pageRequest);
        return ResponseEntity.ok(admins);
    }

    @GetMapping("{id}")
    public ResponseEntity<AdminDTO> getAdminById(@PathVariable Long id){
        AdminDTO aDto = adminService.getAdminDTOById(id);
        return ResponseEntity.ok(aDto);
    }

    @PostMapping()
    public ResponseEntity<AdminDTO> insertAdmin(@Valid @RequestBody AdminInsertDTO insertDTO){
        AdminDTO adminDTO = adminService.insert(insertDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(adminDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(adminDTO);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAdminById(@PathVariable Long id){
        adminService.deleteAdminById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<AdminDTO> updateAdmin(@PathVariable Long id, @Valid @RequestBody AdminUpdateDTO updateDTO){
        AdminDTO adminDTO = adminService.updateAdmin(id, updateDTO);
        return ResponseEntity.ok().body(adminDTO);

    }
}
