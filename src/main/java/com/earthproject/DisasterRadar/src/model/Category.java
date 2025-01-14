package com.earthproject.DisasterRadar.src.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class CategoryResponse {
    private String title;
    private String description;
    private String link;
    private List<Category> categories;

    @AllArgsConstructor
    @Getter
    @Setter
    public class Category {
        private String id;
        private String title;
        private String link;
        private String description;
        private String layers;

    }
}
