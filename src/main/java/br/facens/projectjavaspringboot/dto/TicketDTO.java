package br.facens.projectjavaspringboot.dto;

import br.facens.projectjavaspringboot.entities.Ticket;
import br.facens.projectjavaspringboot.entities.TicketType;

public class TicketDTO {
    private TicketType type;
    private String nameAttend;
    
    public TicketDTO() {
    }

    public TicketDTO(TicketType type, String nameAttend) {
        this.type = type;
        this.nameAttend = nameAttend;
    }

    public TicketDTO(Ticket ticket){
        this.type = ticket.getType();
        this.nameAttend = ticket.getAttend().getName();
    }

    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    public String getNameAttend() {
        return nameAttend;
    }

    public void setNameAttend(String nameAttend) {
        this.nameAttend = nameAttend;
    }
}
