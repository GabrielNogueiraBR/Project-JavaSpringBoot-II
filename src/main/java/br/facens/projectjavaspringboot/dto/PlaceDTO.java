package br.facens.projectjavaspringboot.dto;

import br.facens.projectjavaspringboot.entities.Place;

public class PlaceDTO {
    private Long id;
    private String name;
    private String address;

    public PlaceDTO(){
        
    }

    public PlaceDTO(Long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public PlaceDTO(Place place) {
        this.id = place.getId();
        this.name = place.getName();
        this.address = place.getAddress();
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    
}
