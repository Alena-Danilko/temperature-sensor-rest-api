package com.example.RestApiProject.utilTest;

import com.example.RestApiProject.models.Measurement;
import com.example.RestApiProject.models.Sensor;
import com.example.RestApiProject.repositories.SensorRepository;
import com.example.RestApiProject.service.SensorService;
import com.example.RestApiProject.util.MeasurementValidator;
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
public class MeasurementValidatorTest {
    private MeasurementValidator measurementValidator;
    private SensorService sensorService;
    @Autowired
    private SensorRepository sensorRepository;

    @BeforeEach
    public void setUp(){
        sensorService = new SensorService(sensorRepository);
        measurementValidator = new MeasurementValidator(sensorService);
    }

    @Test
    public void testValidateMeasurementIsNormal(){
        Sensor sensor = new Sensor("Sensor Test");
        Measurement measurement = new Measurement(-40.0, false, sensor);

        Errors errors = new BeanPropertyBindingResult(measurement, "measurement");
        measurementValidator.validate(measurement, errors);

        assertFalse(errors.hasErrors());
        assertEquals(0, errors.getFieldErrors().size());
    }

    @Test
    public void testValidateWhenSensorNotFound(){
        Sensor sensor = new Sensor("Sensor");
        Measurement measurement = new Measurement(-40.0, false, sensor);

        Errors errors = new BeanPropertyBindingResult(measurement, "measurement");
        measurementValidator.validate(measurement, errors);

        assertTrue(errors.hasErrors());
        assertEquals(1, errors.getFieldErrors().size());
        assertEquals("There is not sensor with this name", errors.getFieldError("sensor").getDefaultMessage());
    }
}
