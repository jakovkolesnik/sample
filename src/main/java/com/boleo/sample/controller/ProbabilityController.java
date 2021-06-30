package com.boleo.sample.controller;

import com.boleo.sample.model.CalculationResult;
import com.boleo.sample.service.ProbabilityService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(ProbabilityController.BASE_PATH)
@RestController
public class ProbabilityController {
    public static final String BASE_PATH = "/v1/probability";

    private final ProbabilityService probabilityService;

    public ProbabilityController(ProbabilityService probabilityService) {
        this.probabilityService = probabilityService;
    }

    @GetMapping(value = "/calculate", produces = MediaType.APPLICATION_JSON_VALUE)
    public CalculationResult calculateProbability(@RequestParam(name = "iterations", defaultValue = "100") Integer iterations,
                                                  @RequestParam(name = "seats", defaultValue = "100") Integer numberOfSeats) {
        return probabilityService.calculateProbability(iterations, numberOfSeats);
    }

}