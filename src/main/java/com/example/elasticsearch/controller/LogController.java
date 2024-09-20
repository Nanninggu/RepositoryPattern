package com.example.elasticsearch.controller;

import com.example.elasticsearch.domain.Log;
import com.example.elasticsearch.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/logs")
public class LogController {

    @Autowired
    private LogService logService;

    /*
    POST
    http://localhost:8080/logs

    {
    "message": "This is a log message"
    }
     */
    @PostMapping
    public Log createLog(@RequestBody String message) {
        return logService.createLog(message);
    }

    @GetMapping
    public Iterable<Log> getAllLogs() {
        return logService.findAllLogs();
    }

    @DeleteMapping("/{id}")
    public void deleteLog(@PathVariable String id) {
        logService.deleteLog(id);
    }
}
