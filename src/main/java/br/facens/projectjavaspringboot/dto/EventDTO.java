package br.facens.projectjavaspringboot.dto;

import br.facens.projectjavaspringboot.entities.Event;

public class EventDTO {
    
    private Long id;
    private String name;
    private String description;
    private String place;
    private String emailContact;
    
    public EventDTO() {
    }

    public EventDTO(Long id, String name, String description, String place, String emailContact) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.place = place;
        this.emailContact = emailContact;
    }

    public EventDTO(Event event) {
        this.id = event.getId();
        this.name = event.getName();
        this.description = event.getDescription();
        this.place = event.getPlace();
        this.emailContact = event.getEmailContact();
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getEmailContact() {
        return emailContact;
    }

    public void setEmailContact(String emailContact) {
        this.emailContact = emailContact;
    }
}
