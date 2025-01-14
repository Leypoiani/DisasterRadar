package com.earthproject.DisasterRadar.src.services;

import com.earthproject.DisasterRadar.src.model.Category;
import com.earthproject.DisasterRadar.src.model.DisasterEvent;
import com.earthproject.DisasterRadar.src.model.DisasterResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Node;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class DisasterService {

    @Value("${eonet.api.url}")
    private String eonetApiUrl;

    private final RestTemplate restTemplate;

    public DisasterService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Mono<List<DisasterEvent>> getDisasterEvents(String categoryFilter) {
        String url = eonetApiUrl + "/events";

        DisasterResponse disasterResponse = restTemplate.getForObject(url, DisasterResponse.class);

        if (categoryFilter != null) {
            return Mono.just(disasterResponse.getEvents().stream()
                    .filter(event -> event.getCategories().stream()
                            .anyMatch(category -> category.getId().equalsIgnoreCase(categoryFilter)))
                    .collect(Collectors.toList()));
        }

        return Mono.just(disasterResponse.getEvents());
    }


    public Map<String, Object> getCategories() {
        String url = eonetApiUrl + "/categories";

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                String.class
        );

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.getBody());

            JsonNode categoriesNode = rootNode.get("categories");

            List<Category> categories = objectMapper.convertValue(
                    categoriesNode,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Category.class)
            );

            return Map.of("categories", categories);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar o JSON", e);
        }
    }
}