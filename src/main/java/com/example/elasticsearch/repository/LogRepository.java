package com.example.elasticsearch.repository;

import com.example.elasticsearch.domain.Log;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface LogRepository extends ElasticsearchRepository<Log, String> {
}
