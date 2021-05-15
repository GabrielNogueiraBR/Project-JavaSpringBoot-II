package br.facens.projectjavaspringboot.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import br.facens.projectjavaspringboot.entities.Event;

public class EventDTO {
    
    private Long id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String emailContact;
    private Long amountFreeTickets;
    private Long amountPayedTickets;
    private Long freeTicketsSelled;
    private Long payedTicketsSelled;
    private Double priceTicket;

    public EventDTO(){

    }

    public EventDTO(Long id, String name, String description, LocalDate startDate, LocalDate endDate,
            LocalTime startTime, LocalTime endTime, String emailContact, Long amountFreeTickets,
            Long amountPayedTickets, Long freeTicketsSelled, Long payedTicketsSelled, Double priceTicket) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.emailContact = emailContact;
        this.amountFreeTickets = amountFreeTickets;
        this.amountPayedTickets = amountPayedTickets;
        this.freeTicketsSelled = freeTicketsSelled;
        this.payedTicketsSelled = payedTicketsSelled;
        this.priceTicket = priceTicket;
    }

    public EventDTO(Event event, Long freeTicketsSelled, Long payedTicketsSelled ){
        this.id = event.getId();
        this.name = event.getName();
        this.description = event.getDescription();
        this.startDate = event.getStartDate();
        this.endDate = event.getEndDate();
        this.startTime = event.getStartTime();
        this.endTime = event.getEndTime();
        this.emailContact = event.getEmailContact();
        this.amountFreeTickets = event.getAmountFreeTickets();
        this.amountPayedTickets = event.getAmountPayedTickets();
        this.freeTicketsSelled = freeTicketsSelled;
        this.payedTicketsSelled = payedTicketsSelled;
        this.priceTicket = event.getPriceTicket();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getFreeTicketsSelled() {
        return freeTicketsSelled;
    }

    public void setFreeTicketsSelled(Long freeTicketsSelled) {
        this.freeTicketsSelled = freeTicketsSelled;
    }

    public Long getPayedTicketsSelled() {
        return payedTicketsSelled;
    }

    public void setPayedTicketsSelled(Long payedTicketsSelled) {
        this.payedTicketsSelled = payedTicketsSelled;
    }

    public Double getPriceTicket() {
        return priceTicket;
    }

    public void setPriceTicket(Double priceTicket) {
        this.priceTicket = priceTicket;
    }

    

    
}
