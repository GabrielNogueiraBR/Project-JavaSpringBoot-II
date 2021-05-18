package br.facens.projectjavaspringboot.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

public class PlaceInsertDTO {
    
    @NotBlank
    @Length(min = 5, max = 100, message = "O nome do local deve estar entre 5 a 100 caracteres")
    private String name;

    @NotBlank
    @Length(min = 5, max = 255, message = "O endereço do local deve ter no mínimo 5 e no máximo 255 caracteres")
    private String address;

    public PlaceInsertDTO(){
        
    }

    public PlaceInsertDTO(String name, String address) {
        this.name = name;
        this.address = address;
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
