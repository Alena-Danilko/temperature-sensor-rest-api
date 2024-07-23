package dto;

import java.util.List;

public class MeasurementResponse {
    private List<MeasurementDTO> measurements;

    public List<MeasurementDTO> getMeasurement() {
        return measurements;
    }

    public void setMeasurement(List<MeasurementDTO> measurement) {
        this.measurements = measurement;
    }
}
