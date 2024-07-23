package com.example.RestApiProject.controllersTest;

import com.example.RestApiProject.controllers.MeasurementController;
import com.example.RestApiProject.dto.MeasurementDTO;
import com.example.RestApiProject.dto.SensorDTO;
import com.example.RestApiProject.models.Measurement;
import com.example.RestApiProject.models.Sensor;
import com.example.RestApiProject.service.MeasurementService;
import com.example.RestApiProject.util.MeasurementValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MeasurementController.class)
public class MeasurementControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private MeasurementService measurementService;

    @MockBean
    private MeasurementValidator measurementValidator;

    @MockBean
    private ModelMapper modelMapper;

    private SensorDTO sensorDTO;
    private Measurement measurement1;
    private Measurement measurement2;
    private MeasurementDTO measurementDTO1;
    private MeasurementDTO measurementDTO2;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        sensorDTO = new SensorDTO();
        sensorDTO.setName("Sensor name");

        measurementDTO1 = new MeasurementDTO();
        measurementDTO1.setSensor(sensorDTO);
        measurementDTO1.setRaining(true);
        measurementDTO1.setValue(-33.3);

        measurementDTO2 = new MeasurementDTO();
        measurementDTO2.setSensor(sensorDTO);
        measurementDTO2.setRaining(false);
        measurementDTO2.setValue(-44.4);

        Sensor sensor = new Sensor("Sensor name");

        measurement1 = new Measurement(-33.3, true, sensor);
        measurement2 = new Measurement(-44.4, false, sensor);

        when(modelMapper.map(measurement1, MeasurementDTO.class)).thenReturn(measurementDTO1);
        when(modelMapper.map(measurement2, MeasurementDTO.class)).thenReturn(measurementDTO2);
    }

    @Test
    public void testGetMeasurements() throws Exception {
        List<Measurement> measurements = Arrays.asList(measurement1, measurement2);

        when(measurementService.findAll()).thenReturn(measurements);

        mockMvc.perform(get("/measurements"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.measurement").isArray())
                .andExpect(jsonPath("$.measurement[0].value").value(measurement1.getValue()))
                .andExpect(jsonPath("$.measurement[1].value").value(measurement2.getValue()));
    }

    @Test
    public void testGetCountOfRainyDay() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/measurements/rainyDaysCount"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isNumber());
    }

    @Test
    public void createMeasurementSuccessfully() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/measurements/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(measurementDTO1)))
                .andExpect(status().isOk());
    }

    @Test
    public void createMeasurementBadRequest() throws Exception {
        measurementDTO1.setSensor(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/measurements/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(measurementDTO1)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void handleResponseMeasurementNotCreated() throws Exception {
        doAnswer(invocation -> {
            BindingResult bindingResult = invocation.getArgument(1);
            bindingResult.rejectValue("sensor", "", "There is not sensor with this name");
            return null;
        }).when(measurementValidator).validate(any(), any(BindingResult.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/measurements/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(measurementDTO1)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("sensor - There is not sensor with this name;"));

    }
}
