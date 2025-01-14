package com.earthproject.DisasterRadar.src.controllers;

import com.earthproject.DisasterRadar.src.model.Category;
import com.earthproject.DisasterRadar.src.services.DisasterService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;


@Slf4j
@RestController
public class DisasterController {

    private static final Logger logger = LoggerFactory.getLogger(DisasterController.class);
    private static final String LOG_REQUEST = "REQUEST - ";
    private static final String LOG_RESPONSE_SUCESS = "SUCCESS";
    private static final String LOG_ERROR = "ERROR - ";

    private final DisasterService disasterService;
    private final ObjectMapper objectMapper;

    @Autowired
    public DisasterController(DisasterService disasterService, ObjectMapper objectMapper) {
        this.disasterService = disasterService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/api/disasters")
    public Mono<String> getDisastersByCategory(@RequestParam(required = false) String categoryId) {
        logger.info(LOG_REQUEST + "/api/disasters categoryId: " + categoryId);

        return disasterService.getDisasterEvents(categoryId)
                .flatMap(events -> {
                    try {
                        return Mono.just(objectMapper.writeValueAsString(events));
                    } catch (JsonProcessingException e) {
                        logger.error(LOG_ERROR + e.getMessage());
                        return Mono.error(e);
                    }
                })
                .doOnSuccess(response -> logger.info(LOG_RESPONSE_SUCESS))
                .doOnError(error -> logger.error(LOG_ERROR + error.getMessage()));
    }

    @GetMapping("/api/disasters/categories")
    public Map<String, Object> getCategories() {
        logger.info(LOG_REQUEST + "/api/disasters/categories");
        return disasterService.getCategories();
    }
}