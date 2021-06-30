package com.boleo.sample.controller;

import com.boleo.sample.model.CalculationResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class ProbabilityControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    public void testProbability() throws Exception {
        final int numberOfSeats = 100;
        final int iterations = 1000;

        final MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(ProbabilityController.BASE_PATH + "/calculate")
                .param("iterations", String.valueOf(iterations))
                .param("seats", String.valueOf(numberOfSeats)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        final CalculationResult actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CalculationResult.class);

        assertThat(actual).isNotNull();
        assertThat(actual.getIterations()).isEqualTo(iterations);
        assertThat(actual.getNumberOfSeats()).isEqualTo(numberOfSeats);
        assertThat(actual.getProbability()).isGreaterThan(BigDecimal.ZERO);
        assertThat(actual.getProbability()).isLessThan(BigDecimal.ONE);

        System.out.println(actual);
    }
}