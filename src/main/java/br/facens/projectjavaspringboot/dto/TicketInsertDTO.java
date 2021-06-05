package br.facens.projectjavaspringboot.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import br.facens.projectjavaspringboot.entities.TicketType;

public class TicketInsertDTO {
    
    @Positive(message = "O ID do participante deve ser maior do que zero.")
    private Long idAttend;

    @Size(min = 0, max = 1, message = "The TicketType must be in range of 0-1.")
    @Enumerated(EnumType.ORDINAL)
    private TicketType type;

    public TicketInsertDTO() {
    }

    public TicketInsertDTO(Long idAttend, TicketType type) {
        this.idAttend = idAttend;
        this.type = type;
    }

    public Long getIdAttend() {
        return idAttend;
    }

    public void setIdAttend(Long idAttend) {
        this.idAttend = idAttend;
    }

    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }
}
