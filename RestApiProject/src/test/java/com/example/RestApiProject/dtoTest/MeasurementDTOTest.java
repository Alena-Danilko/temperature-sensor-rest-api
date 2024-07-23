package com.example.RestApiProject.dtoTest;

import com.example.RestApiProject.dto.MeasurementDTO;
import com.example.RestApiProject.dto.SensorDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class MeasurementDTOTest {
    private Validator validator;

    @BeforeEach
    public void setUp(){
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void testValueValidation(){
        MeasurementDTO measurementDTO = new MeasurementDTO();
        SensorDTO sensorDTO = new SensorDTO();
        sensorDTO.setName("Sensor name");

        measurementDTO.setValue(null);
        measurementDTO.setRaining(true);
        measurementDTO.setSensor(sensorDTO);
        Set<ConstraintViolation<MeasurementDTO>> violations = validator.validate(measurementDTO);
        assertFalse(violations.isEmpty());
        assertEquals("Value shouldn't be empty", violations.iterator().next().getMessage());

        measurementDTO.setValue(-111.0);
        violations = validator.validate(measurementDTO);
        assertFalse(violations.isEmpty());
        assertEquals("The value should be greater than -100", violations.iterator().next().getMessage());

        measurementDTO.setValue(-100.0);
        violations = validator.validate(measurementDTO);
        assertTrue(violations.isEmpty());

        measurementDTO.setValue(0.0);
        violations = validator.validate(measurementDTO);
        assertTrue(violations.isEmpty());

        measurementDTO.setValue(100.0);
        violations = validator.validate(measurementDTO);
        assertTrue(violations.isEmpty());

        measurementDTO.setValue(101.0);
        violations = validator.validate(measurementDTO);
        assertFalse(violations.isEmpty());
        assertEquals("The value should be less than 100", violations.iterator().next().getMessage());
    }

    @Test
    public void testRainingValidation(){
        MeasurementDTO measurementDTO = new MeasurementDTO();
        SensorDTO sensorDTO = new SensorDTO();
        sensorDTO.setName("Sensor name");

        measurementDTO.setValue(-100.0);
        measurementDTO.setRaining(null);
        measurementDTO.setSensor(sensorDTO);

        Set<ConstraintViolation<MeasurementDTO>> violations = validator.validate(measurementDTO);
        assertFalse(violations.isEmpty());
        assertEquals("The field raining shouldn't be empty", violations.iterator().next().getMessage());
    }

    @Test
    public void testSensorValidation(){
        MeasurementDTO measurementDTO = new MeasurementDTO();

        measurementDTO.setValue(-100.0);
        measurementDTO.setRaining(true);
        measurementDTO.setSensor(null);

        Set<ConstraintViolation<MeasurementDTO>> violations = validator.validate(measurementDTO);
        assertFalse(violations.isEmpty());
        assertEquals("Sensor shouldn't be empty", violations.iterator().next().getMessage());
    }
}
