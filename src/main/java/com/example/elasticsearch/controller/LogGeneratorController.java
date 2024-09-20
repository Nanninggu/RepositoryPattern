package com.example.elasticsearch.controller;

import com.example.elasticsearch.domain.Log;
import com.example.elasticsearch.generator.LogGenerator;
import com.example.elasticsearch.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/generate-log")
public class LogGeneratorController {

    private static final Logger log = LoggerFactory.getLogger(LogGeneratorController.class);
    private final LogGenerator logGenerator;
    private final LogService logService;

    @Autowired
    public LogGeneratorController(LogService logService) {
        this.logGenerator = new LogGenerator();
        this.logService = logService;
    }

    @GetMapping
    public Log generateLog() {
        Log log = logGenerator.generateLog();
        logService.createLog(log.getMessage());
        return log;
    }

    /*
    10개의 스레드를 활용하여 에러를 발생 시킨다.
    발생된 에러는 ElasticSearch에 저장된다.
     */
    @GetMapping("/generate-error-logs")
    public ResponseEntity<String> generateErrorLogs() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            executorService.submit(() -> {
                try {
                    logGenerator.generateErrorLog();
                } catch (RuntimeException e) {
                    Log errorLog = new Log();
                    errorLog.setId(UUID.randomUUID().toString());
                    errorLog.setMessage(e.getMessage());
                    errorLog.setTimestamp(System.currentTimeMillis());
                    logService.createLog(errorLog.getMessage());
                }
            });
        }

        executorService.shutdown();
        log.error("Error logs generated");
        return ResponseEntity.ok("Error logs generated successfully");
    }
}