package br.facens.projectjavaspringboot.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.facens.projectjavaspringboot.dto.AdminDTO;
import br.facens.projectjavaspringboot.dto.AdminInsertDTO;
import br.facens.projectjavaspringboot.dto.AdminUpdateDTO;
import br.facens.projectjavaspringboot.entities.Admin;
import br.facens.projectjavaspringboot.repositories.AdminRepository;

@Service
public class AdminService {
    
    @Autowired
    private AdminRepository adminRepository;

    public List<AdminDTO> getAllAdmins() {
        List<Admin> admins = adminRepository.findAll();
        return toDTOList(admins);
    }

    public AdminDTO getAdminDTOById(Long id) {
        Optional<Admin> opt = adminRepository.findById(id);
        Admin admin = opt.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found"));

        return new AdminDTO(admin);
    }

    public Admin getAdminById(Long id){
        Optional<Admin> opt = adminRepository.findById(id);
        return opt.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found"));
    }

    public AdminDTO insert(AdminInsertDTO insertDTO) {
        Admin admin = new Admin(insertDTO);
        admin = adminRepository.save(admin);
        return new AdminDTO(admin);
    }

    public void deleteAdminById(Long id) {
        try{
            adminRepository.deleteById(id);
        }
        catch(EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found");
        }
        catch(DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Could not delete admin with foreign key.");
        }
    }

    public AdminDTO updateAdmin(Long id, @Valid AdminUpdateDTO updateDTO) {
        try{
            Admin admin = adminRepository.getOne(id);
            admin.updateAdmin(updateDTO);
            admin = adminRepository.save(admin);
            return new AdminDTO(admin);
        }
        catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found");
        }
    }

    public List<AdminDTO> toDTOList(List<Admin> admins){
        List<AdminDTO> listDTO = new ArrayList<>();

        for (Admin admin: admins) {
            AdminDTO dto = new AdminDTO(admin);
            listDTO.add(dto);
        }

        return listDTO;
    }

    public Page<AdminDTO> getPageAdmins(PageRequest pageRequest) {
        Page<Admin> list = adminRepository.findAdminPageable(pageRequest);
        return list.map(admin -> new AdminDTO(admin));
    }
}
