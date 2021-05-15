package br.facens.projectjavaspringboot.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

public class AdminInsertDTO {
    
    @NotBlank
    @Length(min = 5, max = 100, message = "O nome de um administrador deve estar entre 5 a 100 caracteres.")
    private String name;

    @NotBlank
    @Email(message = "Digite um email valido para o administrador.")
    private String email;

    @NotBlank
    @Length(min = 5, max = 20, message = "Por favor, digite um número de telefone válido (de 5 a 20 caracteres).")
    private String phoneNumber;

    public AdminInsertDTO() {
    }

    public AdminInsertDTO(
            @NotBlank @Length(min = 5, max = 100, message = "O nome de um administrador deve estar entre 5 a 100 caracteres.") String name,
            @NotBlank @Email(message = "Digite um email valido para o administrador.") String email,
            @NotBlank @Length(min = 5, max = 20, message = "Por favor, digite um número de telefone válido.") String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    
}
