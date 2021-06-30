package com.boleo.sample.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public ExecutorService getExecutorService(@Value("${thread.number:10}") Integer nThreads) {
        return Executors.newFixedThreadPool(nThreads);
    }
}
