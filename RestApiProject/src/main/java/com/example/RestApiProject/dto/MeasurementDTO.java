package com.example.RestApiProject.dto;

import jakarta.validation.constraints.*;

public class MeasurementDTO {
    @Min(value = -100, message = "The value should be greater than -100")
    @Max(value = 100, message = "The value should be less than 100")
    @NotNull(message = "Value shouldn't be empty")
    private Double value;

    @NotNull(message = "The field raining shouldn't be empty")
    private Boolean raining;

    @NotNull(message = "Sensor shouldn't be empty")
    private SensorDTO sensor;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Boolean getRaining() {
        return raining;
    }

    public void setRaining(Boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }
}
