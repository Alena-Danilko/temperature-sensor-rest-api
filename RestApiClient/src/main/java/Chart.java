import dto.MeasurementDTO;
import dto.MeasurementResponse;
import org.knowm.xchart.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Chart{
    public static void main(String[] args) {
        double[] xData = IntStream.range(0, getValuesFromServer().size()).asDoubleStream().toArray();
        double[] yData = getValuesFromServer().stream().mapToDouble(x -> x).toArray();

        XYChart chart = QuickChart.getChart("Chart for REST API project",
                "X", "Y", "temperature", xData, yData);

        new SwingWrapper(chart).displayChart();
    }

    public static List<Double> getValuesFromServer(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/measurements";
        MeasurementResponse measurement = restTemplate.getForObject(url, MeasurementResponse.class);
        if(measurement.getMeasurement() == null){
            return Collections.emptyList();
        }
        return measurement.getMeasurement().stream().map(MeasurementDTO::getValue).collect(Collectors.toList());
    }
}