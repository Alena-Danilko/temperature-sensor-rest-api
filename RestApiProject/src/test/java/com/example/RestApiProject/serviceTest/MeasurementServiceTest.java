package com.example.RestApiProject.serviceTest;

import com.example.RestApiProject.models.Measurement;
import com.example.RestApiProject.models.Sensor;
import com.example.RestApiProject.repositories.MeasurementRepository;
import com.example.RestApiProject.repositories.SensorRepository;
import com.example.RestApiProject.service.MeasurementService;
import com.example.RestApiProject.service.SensorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MeasurementServiceTest {
    @Autowired
    private MeasurementRepository measurementRepository;

    @Autowired
    private SensorRepository sensorRepository;

    private SensorService sensorService;

    private MeasurementService measurementService;

    @BeforeEach
    public void setUp(){
        sensorService = new SensorService(sensorRepository);
        measurementService = new MeasurementService(measurementRepository, sensorService);
    }

    @Test
    public void getAllMeasurements(){
        Sensor sensor = new Sensor("Sensor");
        sensorService.save(sensor);

        Measurement measurement1 = new Measurement(-40.0, false, sensor);
        Measurement measurement2 = new Measurement(-50.0, true, sensor);
        measurementService.save(measurement1);
        measurementService.save(measurement2);

        List<Measurement> measurements = measurementService.findAll();

        assertFalse(measurements.isEmpty());
        assertEquals(2, measurements.size());
    }

    @Test
    public void testSaveMeasurement(){
        Sensor sensor = new Sensor("Sensor");
        sensorService.save(sensor);

        Measurement newMeasurement = new Measurement(-20.0, false, sensor);
        measurementService.save(newMeasurement);
        int id = newMeasurement.getId();
        Measurement result = measurementRepository.findById(id).orElse(null);

        assertNotNull(newMeasurement.getId());
        assertNotNull(result);
        assertEquals(-20.0, result.getValue(), 0.001);
        assertNotNull(result.getCreatedAt());
        assertTrue(result.getCreatedAt().isBefore(LocalDateTime.now()));
    }
}
