package com.boleo.sample.service;

import com.boleo.sample.model.SeatAssignmentRequest;
import com.boleo.sample.model.Seats;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SeatingArrangementSimulator {
    private final SeatAssigner seatAssigner;

    public SeatingArrangementSimulator(SeatAssigner seatAssigner) {
        this.seatAssigner = seatAssigner;
    }

    public boolean simulate(Seats seats) {
        assignCrazy(seats);
        for (int i = 1; i < seats.getTotal(); i++) {
            seatAssigner.assign(SeatAssignmentRequest.builder()
                    .desiredSeat(i + 1)
                    .passenger(String.valueOf(i + 1))
                    .build(), seats);
        }
        final boolean result = seats.getSeatMap().get(seats.getTotal()).equals(String.valueOf(seats.getTotal()));
        return result;
    }

    private void assignCrazy(Seats seats) {
        final SeatAssignmentRequest crazyPassenger = SeatAssignmentRequest.builder()
                .desiredSeat(seats.getTotal() + 1)
                .passenger("1")
                .build();
        final int assigned = seatAssigner.assign(crazyPassenger, seats);
        log.debug("Passenger 1 was assigned seat {}", assigned);
    }
}
