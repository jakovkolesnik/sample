package com.boleo.sample.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CalculationResult {
    private final BigDecimal probability;
    private final Integer numberOfSeats;
    private final Integer iterations;
}
