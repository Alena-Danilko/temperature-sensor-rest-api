package com.example.RestApiProject.modelsTest;

import com.example.RestApiProject.dto.SensorDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SensorTest {
    private Validator validator;

    @BeforeEach
    public void setUp(){
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void testSensorValidation(){
        SensorDTO sensorDTO = new SensorDTO();

        Set<ConstraintViolation<SensorDTO>> violations = validator.validate(sensorDTO);
        assertFalse(violations.isEmpty());
        assertEquals("Name shouldn't be empty", violations.iterator().next().getMessage());

        sensorDTO.setName("E");
        violations = validator.validate(sensorDTO);
        assertFalse(violations.isEmpty());
        assertEquals("Name should be between 3 to 30 char", violations.iterator().next().getMessage());

        sensorDTO.setName("EEE");
        violations = validator.validate(sensorDTO);
        assertTrue(violations.isEmpty());

        sensorDTO.setName("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
        violations = validator.validate(sensorDTO);
        assertTrue(violations.isEmpty());

        sensorDTO.setName("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
        violations = validator.validate(sensorDTO);
        assertFalse(violations.isEmpty());
        assertEquals("Name should be between 3 to 30 char", violations.iterator().next().getMessage());
    }
}
