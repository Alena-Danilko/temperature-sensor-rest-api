import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;


public class Consumer {
    public static void main(String[] args) {
        String sensorName= "ClientSensor1";
        Random random = new Random();
        registerNewSensor(sensorName);
        for(int i =0; i< 20; i++){
            addNewMeasurement(-100 + ( 200 * random.nextDouble()), random.nextBoolean(), sensorName );
        }
    }

    public static void createPostRequest(String url, Map<String, Object> jsonData){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> request = new HttpEntity<>(jsonData, headers);
        try{
            restTemplate.postForObject(url, request, String.class);
            System.out.println("Sensor was create successfully");
        }catch (HttpClientErrorException e){
            System.out.println("An error occur when creating new sensor");
            System.out.println(e.getMessage());
        }
    }

    public static void registerNewSensor(String sensorName){
        String registrationUrl = "http://localhost:8080/sensors/registration";
        Map<String, Object> sensor = new HashMap<>();
        sensor.put("name", sensorName);
        createPostRequest(registrationUrl, sensor);
    }

    public static void addNewMeasurement(double value, boolean raining, String sensorName){
        String addingMeasurementUrl = "http://localhost:8080/measurements/add";
        Map<String, Object> measurement = new HashMap<>();
        measurement.put("value", value);
        measurement.put("raining", raining);
        measurement.put("sensor", Map.of("name", sensorName));
        createPostRequest(addingMeasurementUrl, measurement);
    }
}
