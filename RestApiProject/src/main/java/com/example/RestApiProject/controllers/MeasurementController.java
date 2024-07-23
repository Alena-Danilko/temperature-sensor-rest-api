package com.example.RestApiProject.controllers;

import com.example.RestApiProject.dto.MeasurementDTO;
import com.example.RestApiProject.dto.MeasurementResponse;
import com.example.RestApiProject.models.Measurement;
import com.example.RestApiProject.service.MeasurementService;
import com.example.RestApiProject.util.MeasurementNotCreatedException;
import com.example.RestApiProject.util.ErrorResponse;
import com.example.RestApiProject.util.MeasurementValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {

    private final MeasurementService measurementService;
    private final MeasurementValidator measurementValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementController(MeasurementService measurementService, MeasurementValidator measurementValidator, ModelMapper modelMapper) {
        this.measurementService = measurementService;
        this.measurementValidator = measurementValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public MeasurementResponse getMeasurements(){
        return new MeasurementResponse(measurementService.findAll().stream().map(this::convertToMeasurementDTO).
                collect(Collectors.toList()));
    }

    @GetMapping("/rainyDaysCount")
    public Long getCountOfRainyDay(){
        return measurementService.findAll().stream().filter(Measurement::getRaining).count();
    }


    @PostMapping("/add")
    public ResponseEntity<HttpStatus> createMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO,
                                                        BindingResult bindingResult){
        measurementValidator.validate(convertToMeasurement(measurementDTO), bindingResult);
        if(bindingResult.hasErrors()){
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error: errors){
                errorMessage.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(";");
            }
            throw new MeasurementNotCreatedException(errorMessage.toString());
        }
        measurementService.save(convertToMeasurement(measurementDTO));
        return  ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleResponse(MeasurementNotCreatedException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }

}
