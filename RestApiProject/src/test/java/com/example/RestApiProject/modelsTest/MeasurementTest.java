package com.example.RestApiProject.modelsTest;

import com.example.RestApiProject.models.Measurement;
import com.example.RestApiProject.models.Sensor;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MeasurementTest {
    private Validator validator;

    @BeforeEach
    public void setUp(){
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void testValueValidation(){
        Measurement measurement = new Measurement();
        Sensor sensor = new Sensor("Sensor name");

        measurement.setValue(null);
        measurement.setRaining(true);
        measurement.setSensor(sensor);
        measurement.setCreatedAt(LocalDateTime.now());
        Set<ConstraintViolation<Measurement>> violations = validator.validate(measurement);
        assertFalse(violations.isEmpty());
        assertEquals("Value shouldn't be empty", violations.iterator().next().getMessage());

        measurement.setValue(-111.0);
        violations = validator.validate(measurement);
        assertFalse(violations.isEmpty());
        assertEquals("The value should be greater than -100", violations.iterator().next().getMessage());

        measurement.setValue(-100.0);
        violations = validator.validate(measurement);
        assertTrue(violations.isEmpty());

        measurement.setValue(0.0);
        violations = validator.validate(measurement);
        assertTrue(violations.isEmpty());

        measurement.setValue(100.0);
        violations = validator.validate(measurement);
        assertTrue(violations.isEmpty());

        measurement.setValue(101.0);
        violations = validator.validate(measurement);
        assertFalse(violations.isEmpty());
        assertEquals("The value should be less than 100", violations.iterator().next().getMessage());
    }

    @Test
    public void testRainingValidation(){
        Measurement measurement = new Measurement();
        Sensor sensor = new Sensor("Sensor name");

        measurement.setValue(-100.0);
        measurement.setRaining(null);
        measurement.setSensor(sensor);
        measurement.setCreatedAt(LocalDateTime.now());

        Set<ConstraintViolation<Measurement>> violations = validator.validate(measurement);
        assertFalse(violations.isEmpty());
        assertEquals("The field raining shouldn't be empty", violations.iterator().next().getMessage());
    }

    @Test
    public void testSensorValidation(){
        Measurement measurement = new Measurement();

        measurement.setValue(-100.0);
        measurement.setRaining(true);
        measurement.setSensor(null);
        measurement.setCreatedAt(LocalDateTime.now());

        Set<ConstraintViolation<Measurement>> violations = validator.validate(measurement);
        assertFalse(violations.isEmpty());
        assertEquals("Sensor shouldn't be empty", violations.iterator().next().getMessage());
    }

    @Test
    public void testCreatedAtValidation(){
        Measurement measurement = new Measurement();
        Sensor sensor = new Sensor("Sensor name");

        measurement.setValue(-100.0);
        measurement.setRaining(true);
        measurement.setSensor(sensor);
        measurement.setCreatedAt(null);

        Set<ConstraintViolation<Measurement>> violations = validator.validate(measurement);
        assertFalse(violations.isEmpty());
        assertEquals("The field createdAt shouldn't be empty", violations.iterator().next().getMessage());
    }
}
