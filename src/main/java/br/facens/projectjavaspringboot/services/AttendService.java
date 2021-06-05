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

    public AttendDTO getAttendDTOById(Long id) {
        Optional<Attend> opt = attendRepository.findById(id);
        Attend attend = opt.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Attend not found"));

        return new AttendDTO(attend);
    }
    
    public Attend getAttendById(Long id) {
        Optional<Attend> opt = attendRepository.findById(id);
        Attend attend = opt.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Attend not found"));

        return attend;
    }

    public AttendDTO insert(AttendInsertDTO insertDTO) {
        Attend attend = new Attend(insertDTO);
        try{
            attend = attendRepository.save(attend);
            return new AttendDTO(attend);
        }
        catch(DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Could not insert user with email duplicated.");
        }
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
            Attend attend = attendRepository.getOne(id);
            attend.updateAttend(updateDTO);
            attend = attendRepository.save(attend);
            return new AttendDTO(attend);
        }
        catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attend not found");
        }
        catch(DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Could not update user with email duplicated.");
        }
    }

    public List<AttendDTO> toDTOList(List<Attend> attendees){
        List<AttendDTO> listDTO = new ArrayList<>();

        for (Attend attend: attendees) {
            AttendDTO dto = new AttendDTO(attend);
            listDTO.add(dto);
        }

        return listDTO;
    }

    public Page<AttendDTO> getPageAttendees(PageRequest pageRequest) {
        Page<Attend> list = attendRepository.findAttendeesPageable(pageRequest);
        return list.map(attend -> new AttendDTO(attend));
    }
    
}
