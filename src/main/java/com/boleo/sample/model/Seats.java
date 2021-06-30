package com.boleo.sample.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.TreeSet;


@Data
@Builder
public class Seats {

    private final int total;
    private final Map<Integer, String> seatMap;
    private final TreeSet<Integer> unassigned;

}
