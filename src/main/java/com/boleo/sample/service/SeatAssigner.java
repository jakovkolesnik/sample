package com.boleo.sample.service;

import com.boleo.sample.model.SeatAssignmentRequest;
import com.boleo.sample.model.Seats;
import org.springframework.stereotype.Component;

import java.util.Iterator;

@Component
public class SeatAssigner {

    public int assign(SeatAssignmentRequest request, Seats seats) {
        if (seats.getUnassigned().isEmpty()) {
            throw new IllegalStateException("No more free seats for passenger " + request.getPassenger());
        }
        if (seats.getUnassigned().remove(request.getDesiredSeat())) {
            seats.getSeatMap().put(request.getDesiredSeat(), request.getPassenger());
            return request.getDesiredSeat();
        }

        final int randomSeatNumber = getRandomSeat(seats);
        return assignSeat(randomSeatNumber, request.getPassenger(), seats);

    }

    private int getRandomSeat(Seats seats) {

        final long randomIndex = Math.round((Math.random() * (seats.getUnassigned().size() - 1)));
        if (randomIndex == 0) {
            return seats.getUnassigned().first();
        }
        if (randomIndex == (seats.getUnassigned().size() - 1)) {
            return seats.getUnassigned().last();
        }
        Iterator<Integer> it = seats.getUnassigned().iterator();
        int i = 0;
        while (i < randomIndex) {
            it.next();
            i++;
        }
        return it.next();
    }

    private int assignSeat(int seatNumber, String passenger, Seats seats) {
        seats.getSeatMap().put(seatNumber, passenger);
        seats.getUnassigned().remove(seatNumber);
        return seatNumber;
    }
}
