package com.example.RestApiProject.utilTest;

import com.example.RestApiProject.models.Sensor;
import com.example.RestApiProject.repositories.SensorRepository;
import com.example.RestApiProject.service.SensorService;
import com.example.RestApiProject.util.SensorValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SensorValidatorTest {
    private SensorValidator sensorValidator;
    private SensorService sensorService;
    @Autowired
    private SensorRepository sensorRepository;

    @BeforeEach
    public void setUp(){
        sensorService = new SensorService(sensorRepository);
        sensorValidator = new SensorValidator(sensorService);
    }

    @Test
    public void testValidateButNameTaken(){
        Sensor sensor = new Sensor("Sensor");
        sensorService.save(sensor);

        Sensor newSensor = new Sensor("Sensor");
        Errors errors = new BeanPropertyBindingResult(newSensor, "newSensor");
        sensorValidator.validate(newSensor, errors);

        assertTrue(errors.hasErrors());
        assertEquals(1, errors.getFieldErrors().size());
        assertEquals("The name of sensor is already taken", errors.getFieldError("name").getDefaultMessage());
    }

    @Test
    public void testValidateNameIsNotTaken(){
        Sensor sensor = new Sensor("Sensor");
        Errors errors = new BeanPropertyBindingResult(sensor, "sensor");
        sensorValidator.validate(sensor, errors);

        assertEquals(0, errors.getGlobalErrors().size());
    }
}
