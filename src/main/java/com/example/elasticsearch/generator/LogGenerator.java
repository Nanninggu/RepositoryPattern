package com.example.elasticsearch.generator;

import com.example.elasticsearch.domain.Log;

import java.util.UUID;

public class LogGenerator {

    public Log generateLog() {
        Log log = new Log();
        log.setId(UUID.randomUUID().toString());
        log.setMessage("Sample log message " + UUID.randomUUID().toString());
        log.setTimestamp(System.currentTimeMillis());
        return log;
    }

    public Log generateErrorLog() {
        Log log = new Log();
        log.setId(UUID.randomUUID().toString());
        log.setMessage("Error log message " + UUID.randomUUID().toString());
        log.setTimestamp(System.currentTimeMillis());
        throw new RuntimeException("Intentional error for log: " + log.getId());
    }
}
