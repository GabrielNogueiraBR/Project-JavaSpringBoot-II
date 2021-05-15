package br.facens.projectjavaspringboot.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.facens.projectjavaspringboot.dto.AttendDTO;
import br.facens.projectjavaspringboot.dto.AttendInsertDTO;
import br.facens.projectjavaspringboot.dto.AttendUpdateDTO;
import br.facens.projectjavaspringboot.entities.Attend;
import br.facens.projectjavaspringboot.repositories.AttendRepository;

@Service
public class AttendService {
    
    @Autowired
    private AttendRepository attendRepository;

    public List<AttendDTO> getAllAttendees() {
        List<Attend> attendees = attendRepository.findAll();
        return toDTOList(attendees);
    }

    public AttendDTO getAttendById(Long id) {
        Optional<Attend> opt = attendRepository.findById(id);
        Attend Attend = opt.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Attend not found"));

        return new AttendDTO(Attend);
    }

    public AttendDTO insert(AttendInsertDTO insertDTO) {
        Attend Attend = new Attend(insertDTO);
        Attend = attendRepository.save(Attend);
        return new AttendDTO(Attend);
    }

    public void deleteAttendById(Long id) {
        try{
            attendRepository.deleteById(id);
        }
        catch(EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attend not found");
        }
        catch(DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Could not delete Attend with foreign key.");
        }
    }

    public AttendDTO updateAttend(Long id, @Valid AttendUpdateDTO updateDTO) {
        try{
            Attend Attend = attendRepository.getOne(id);
            Attend.updateAttend(updateDTO);
            Attend = attendRepository.save(Attend);
            return new AttendDTO(Attend);
        }
        catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attend not found");
        }
    }

    public List<AttendDTO> toDTOList(List<Attend> Attendees){
        List<AttendDTO> listDTO = new ArrayList<>();

        for (Attend Attend: Attendees) {
            AttendDTO dto = new AttendDTO(Attend);
            listDTO.add(dto);
        }

        return listDTO;
    }
    
}
