package com.example.elasticsearch.service;

import com.example.elasticsearch.domain.Log;
import com.example.elasticsearch.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;

    public Log createLog(String message) {
        Log log = new Log();
        log.setId(UUID.randomUUID().toString());
        log.setMessage(message);
        log.setTimestamp(System.currentTimeMillis());
        return logRepository.save(log);
    }

    public Iterable<Log> findAllLogs() {
        return logRepository.findAll();
    }

    public void deleteLog(String id) {
        logRepository.deleteById(id);
    }
}
