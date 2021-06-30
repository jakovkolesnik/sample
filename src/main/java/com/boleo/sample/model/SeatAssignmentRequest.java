package com.boleo.sample.model;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class SeatAssignmentRequest {

    private final String passenger;
    private final Integer desiredSeat;
}
