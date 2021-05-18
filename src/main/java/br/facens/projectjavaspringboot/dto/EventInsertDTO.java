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
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.validator.constraints.Length;

public class EventInsertDTO {
    
    @NotBlank(message = "O campo 'name' precisa ser preenchido.")
    @Length(min = 3, max = 100, message = "o campo 'name' precisa estar entre 3 ate 100 caracteres.")
    private String name;

    @NotBlank(message = "O campo 'description' deve ser preenchido.")
    @Length(max = 255, message = "O campo 'description' excedeu o numero limite de 255 caractes.")
    private String description;

    @NotNull(message = "O campo 'startDate' deve ser preenchido")
    @FutureOrPresent(message = "A data inicial deve ser igual a data atual ou uma data futura.")
    private LocalDate startDate;

    @NotNull(message = "O campo 'enDate' deve ser preenchido")
    @Future(message = "O campo 'endDate' deve ser uma data futura")
    private LocalDate endDate;

    @JsonIgnore
    @AssertTrue(message = "O campo 'endDate' deve ser maior, ou seja, depois do campo 'startDate'.")
    public boolean isEndDateValid(){
        var dias = Period.between(startDate, endDate).getDays();
        // Validacao para nao permitir data final do evento antes da data inicial
        if(dias < 0)
            return false;
        return true;
    }

    @NotNull(message = "O campo 'startTime' deve ser preenchido")
    private LocalTime startTime;

    @NotNull(message = "O campo 'endTime' deve ser preenchido")
    private LocalTime endTime;

    @NotBlank
    @Email(message = "O campo 'emailContact' deve conter um email valido.")
    private String emailContact;

    @PositiveOrZero(message = "A quantidade de tickets gratuitos deve ser zero ou mais.")
    private Long amountFreeTickets;

    @PositiveOrZero(message = "A quantidade de tickets pagos deve ser zero ou mais.")
    private Long amountPayedTickets;

    @PositiveOrZero(message = "O valor dos tickets deve ser maior ou igual a zero.")
    private Double priceTicket;

    @PositiveOrZero(message = "O número identificador do administrador deve ser positivo.")
    private Long idAdmin;

    public EventInsertDTO() {
    
    }

    public EventInsertDTO(
            String name,
            String description,
            LocalDate startDate,
            LocalDate endDate,
            LocalTime startTime,
            LocalTime endTime,
            String emailContact,
            Long amountFreeTickets,
            Long amountPayedTickets,
            Double priceTicket,
            Long idAdmin) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.emailContact = emailContact;
        this.amountFreeTickets = amountFreeTickets;
        this.amountPayedTickets = amountPayedTickets;
        this.priceTicket = priceTicket;
        this.idAdmin = idAdmin;
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

    public Long getAmountFreeTickets() {
        return amountFreeTickets;
    }

    public void setAmountFreeTickets(Long amountFreeTickets) {
        this.amountFreeTickets = amountFreeTickets;
    }

    public Long getAmountPayedTickets() {
        return amountPayedTickets;
    }

    public void setAmountPayedTickets(Long amountPayedTickets) {
        this.amountPayedTickets = amountPayedTickets;
    }

    public Double getPriceTicket() {
        return priceTicket;
    }

    public void setPriceTicket(Double priceTicket) {
        this.priceTicket = priceTicket;
    }

    public Long getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(Long idAdmin) {
        this.idAdmin = idAdmin;
    }

    
    
}
