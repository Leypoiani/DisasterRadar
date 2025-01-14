package com.earthproject.DisasterRadar.src.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
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
