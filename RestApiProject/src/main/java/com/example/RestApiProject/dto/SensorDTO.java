package com.example.RestApiProject.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class SensorDTO {
    @NotEmpty(message = "Name shouldn't be empty")
    @Size(min =3, max = 30, message = "Name should be between 3 to 30 char")
    private String name;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
