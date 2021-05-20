package com.workshopLab.workshopLab.controller;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class PriceRequest {
    @NotNull
    @Max(value = 100000,message = "Price should be less than 100000")
    private Double price ;
    @NotEmpty
    private String name;

    private String description;
}
