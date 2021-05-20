package com.workshopLab.workshopLab.controller;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}
