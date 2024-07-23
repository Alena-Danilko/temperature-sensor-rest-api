package com.example.RestApiProject.controllersTest;

import com.example.RestApiProject.dto.SensorDTO;
import com.example.RestApiProject.models.Sensor;
import com.example.RestApiProject.service.SensorService;
import com.example.RestApiProject.util.SensorNotCreatedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class SensorControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private SensorService sensorService;

    private MockMvc mockMvc;

    private SensorDTO sensorDTO;

    @BeforeEach
    public void setUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void createSensorSuccessfully() throws Exception {
        sensorDTO = new SensorDTO();
        sensorDTO.setName("Test sensor");

        mockMvc.perform(MockMvcRequestBuilders.post("/sensors/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sensorDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void createSensorBadRequest() throws Exception {
        sensorDTO = new SensorDTO();

        mockMvc.perform(MockMvcRequestBuilders.post("/sensors/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sensorDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void handleResponseSensorNotCreated() throws Exception {
        sensorDTO = new SensorDTO();
        sensorDTO.setName("Test sensor");

        doThrow(new SensorNotCreatedException("Sensor not created")).when(sensorService).save(any(Sensor.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/sensors/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sensorDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Sensor not created"));

    }
}
