package br.facens.projectjavaspringboot.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

public class AttendUpdateDTO {
    
    @NotBlank(message = "O nome de um participante não pode estar em branco.")
    @Length(min = 5, max = 100, message = "O nome de um participante deve estar entre 5 a 100 caracteres.")
    private String name;

    @NotBlank(message = "O email de um participante não pode estar em branco.")
    @Email(message = "Digite um email valido para o participante.")
    private String email;

    public AttendUpdateDTO(){

    }

    public AttendUpdateDTO(
            String name,
            String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
