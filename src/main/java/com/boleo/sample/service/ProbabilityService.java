package com.boleo.sample.service;

import com.boleo.sample.model.CalculationResult;
import com.boleo.sample.model.Seats;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Service
@Slf4j
public class ProbabilityService {

    private static final int PRECISION = 6;
    private final ExecutorService executorService;
    private final SeatingArrangementSimulator simulator;

    public ProbabilityService(ExecutorService executorService, SeatingArrangementSimulator simulator) {
        this.executorService = executorService;
        this.simulator = simulator;
    }

    public CalculationResult calculateProbability(int iterations, int numberOfSeats) {
        log.info("Calculating probability for {} seats using {} iterations", numberOfSeats, iterations);
        final List<Future<Boolean>> futures = new ArrayList<>();
        for (int i = 0; i < iterations; i++) {
            log.debug("adding simulator future {}", i + 1);
            futures.add(executorService.submit(() -> simulate(numberOfSeats)));
        }
        final AtomicInteger success = new AtomicInteger(0);
        futures.forEach(f -> {
            try {
                if (f.get()) {
                    success.incrementAndGet();
                }
            } catch (Exception e) {
                log.error("Failed ot get Future result", e);
                throw new RuntimeException("Task failed", e);
            }
        });
        final BigDecimal probability = BigDecimal.valueOf(success.get()).divide(BigDecimal.valueOf(iterations), PRECISION, RoundingMode.UP);
        final CalculationResult result = CalculationResult.builder()
                .probability(probability)
                .iterations(iterations)
                .numberOfSeats(numberOfSeats)
                .build();
        log.info("Calculated probability {}", result);
        return result;
    }

    private Boolean simulate(int numberOfSeats) {
        final TreeSet<Integer> unassigned = new TreeSet<>();
        IntStream.range(1, numberOfSeats + 1).forEach(unassigned::add);
        final Seats seats = Seats.builder()
                .total(numberOfSeats)
                .seatMap(new HashMap<>())
                .unassigned(unassigned)
                .build();
        return simulator.simulate(seats);
    }
}
