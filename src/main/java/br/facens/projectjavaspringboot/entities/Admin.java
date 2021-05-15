package br.facens.projectjavaspringboot.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.Valid;

import br.facens.projectjavaspringboot.dto.AdminInsertDTO;
import br.facens.projectjavaspringboot.dto.AdminUpdateDTO;

@Entity
@Table(name = "TB_ADMIN")
@PrimaryKeyJoinColumn(name = "USER_ID")
public class Admin extends BaseUser{
    
    private String phoneNumber;

    @OneToMany(mappedBy = "admin")
    private List<Event> events = new ArrayList<>();

    public Admin(){
        
    }

    public Admin(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Admin(AdminInsertDTO insertDTO){
        this.setName(insertDTO.getName());
        this.setEmail(insertDTO.getEmail());
        this.setPhoneNumber(insertDTO.getPhoneNumber());
    }

    public Admin(Long id, String name, String email, String phoneNumber) {
        super(id, name, email);
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void addEvent(Event event) {
        this.events.add(event);
    }

    public void updateAdmin(AdminUpdateDTO updateDTO) {
        this.setName(updateDTO.getName());
        this.setEmail(updateDTO.getEmail());
        this.setPhoneNumber(updateDTO.getPhoneNumber());
    }

    
    
}
