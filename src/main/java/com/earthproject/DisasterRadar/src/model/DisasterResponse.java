package com.earthproject.DisasterRadar.src.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class DisasterResponse {
    private String title;
    private String description;
    private String link;
    private List<DisasterEvent> events;

}
