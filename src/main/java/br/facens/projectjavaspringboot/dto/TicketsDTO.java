package br.facens.projectjavaspringboot.dto;

import java.util.ArrayList;
import java.util.List;

public class TicketsDTO {
    private Long idEvent;
    private Long amountPayedTickets;
    private Long amountFreeTickets;
    private Long payedTicketsSelled;
    private Long freeTicketsSelled;
    private List<TicketDTO> tickets = new ArrayList<>();
    
    public TicketsDTO() {
        
    }

    public TicketsDTO(Long idEvent, Long amountPayedTickets, Long amountFreeTickets, Long payedTicketsSelled,
            Long freeTicketsSelled, List<TicketDTO> tickets) {
        this.idEvent = idEvent;
        this.amountPayedTickets = amountPayedTickets;
        this.amountFreeTickets = amountFreeTickets;
        this.payedTicketsSelled = payedTicketsSelled;
        this.freeTicketsSelled = freeTicketsSelled;
        this.tickets = tickets;
    }

    public Long getAmountPayedTickets() {
        return amountPayedTickets;
    }

    public void setAmountPayedTickets(Long amountPayedTickets) {
        this.amountPayedTickets = amountPayedTickets;
    }

    public Long getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(Long idEvent) {
        this.idEvent = idEvent;
    }

    public Long getAmountFreeTickets() {
        return amountFreeTickets;
    }

    public void setAmountFreeTickets(Long amountFreeTickets) {
        this.amountFreeTickets = amountFreeTickets;
    }

    public Long getPayedTicketsSelled() {
        return payedTicketsSelled;
    }

    public void setPayedTicketsSelled(Long payedTicketsSelled) {
        this.payedTicketsSelled = payedTicketsSelled;
    }

    public Long getFreeTicketsSelled() {
        return freeTicketsSelled;
    }

    public void setFreeTicketsSelled(Long freeTicketsSelled) {
        this.freeTicketsSelled = freeTicketsSelled;
    }

    public List<TicketDTO> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketDTO> tickets) {
        this.tickets = tickets;
    }

    
}
