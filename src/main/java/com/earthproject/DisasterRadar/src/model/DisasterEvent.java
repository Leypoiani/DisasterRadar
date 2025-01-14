package com.earthproject.DisasterRadar.src.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DisasterEvent {
    private String id;
    private String title;
    private String description;
    private String link;
    private List<Category> categories;
    private List<Source> sources;
    private List<Geometry> geometry;

    // Getters e setters
}
