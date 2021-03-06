package com.workshopLab.workshopLab.controller;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RegistrationRequest {

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;
    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;

}
