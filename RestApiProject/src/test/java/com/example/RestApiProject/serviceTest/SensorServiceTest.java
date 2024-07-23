package com.example.RestApiProject.serviceTest;

import com.example.RestApiProject.models.Sensor;
import com.example.RestApiProject.repositories.SensorRepository;
import com.example.RestApiProject.service.SensorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SensorServiceTest {
    @Autowired
    private SensorRepository sensorRepository;
    private SensorService sensorService;

    @BeforeEach
    public void setUp(){
        sensorService = new SensorService(sensorRepository);
    }

    @Test
    public void testSaveSensor(){
        Sensor sensor = new Sensor("Sensor");
        sensorService.save(sensor);
        int id = sensor.getId();
        Sensor result = sensorRepository.findById(id).orElse(null);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("Sensor", result.getName());
    }

    @Test
    public void testShouldReturnSensorByName(){
        Sensor sensor = new Sensor("Sensor");
        sensorService.save(sensor);
        Optional<Sensor> result = sensorRepository.findByName("Sensor");

        assertNotNull(result);
        assertEquals("Sensor", result.get().getName());
    }

    @Test
    public void testShouldShouldFailBecauseSensorDoesntExist(){
        Optional<Sensor> sensor = sensorRepository.findByName("Sensor");

        assertFalse(sensor.isPresent());
    }
}
