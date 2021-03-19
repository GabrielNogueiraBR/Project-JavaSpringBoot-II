package br.facens.projectjavaspringboot.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.validator.constraints.Length;

public class EventInsertDTO {
    
    @NotBlank(message = "O campo 'name' precisa ser preenchido.")
    @Length(min = 3, max = 100, message = "o campo 'name' precisa estar entre 3 ate 100 caracteres.")
    private String name;

    @NotBlank(message = "O campo 'description' deve ser preenchido.")
    @Length(max = 255, message = "O campo 'description' excedeu o numero limite de 255 caractes.")
    private String description;

    @NotBlank(message = "O campo 'place' deve ser preenchido.")
    @Length(max = 255, message = "O campo 'place' excedeu o numero limite de 255 caracteres.")
    private String place;

    @NotNull(message = "O campo 'startDate' deve ser preenchido")
    @FutureOrPresent(message = "A data inicial deve ser igual a data atual ou uma data futura.")
    private LocalDate startDate;

    @NotNull
    @Future(message = "O campo 'endDate' deve ser uma data futura")
    private LocalDate endDate;

    @JsonIgnore
    @AssertTrue(message = "O campo 'endDate' deve ser maior, ou seja, depois do campo 'startDate'.")
    public boolean isEndDateValid(){
        var dias = Period.between(startDate, endDate).getDays();
        if(dias < 0)
            return false;
        return true;
    }

    @NotNull
    @FutureOrPresent(message = "O campo 'startTime' deve ser um valor valido.")
    private LocalTime startTime;

    @NotNull
    @Future(message = "O campo 'endTime' deve ser um valor no futuro.")
    private LocalTime endTime;

    @NotBlank
    @Email(message = "O campo 'emailContact' deve conter um email valido.")
    private String emailContact;
    
    public EventInsertDTO(String name, String description, String place, LocalDate startDate, LocalDate endDate,
            LocalTime startTime, LocalTime endTime, String emailContact) {
        this.name = name;
        this.description = description;
        this.place = place;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.emailContact = emailContact;
    }

    public EventInsertDTO() {
    }
    
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getPlace() {
        return place;
    }
    public void setPlace(String place) {
        this.place = place;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public LocalTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
    public LocalTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
    public String getEmailContact() {
        return emailContact;
    }
    public void setEmailContact(String emailContact) {
        this.emailContact = emailContact;
    }
}
