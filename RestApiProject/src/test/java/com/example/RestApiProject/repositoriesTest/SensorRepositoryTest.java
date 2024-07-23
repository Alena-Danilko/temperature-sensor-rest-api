package com.example.RestApiProject.repositoriesTest;

import com.example.RestApiProject.models.Sensor;
import com.example.RestApiProject.repositories.SensorRepository;
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
public class SensorRepositoryTest {
    @Autowired
    private SensorRepository sensorRepository;

    @Test
    public void testFindByName(){
        Sensor sensor = new Sensor();
        sensor.setName("Sensor name");
        sensorRepository.save(sensor);

        Optional<Sensor> foundSensor = sensorRepository.findByName("Sensor name");
        assertTrue(foundSensor.isPresent());
        assertEquals("Sensor name", foundSensor.get().getName());

        foundSensor = sensorRepository.findByName("Test Sensor");
        assertFalse(foundSensor.isPresent());
    }
}
