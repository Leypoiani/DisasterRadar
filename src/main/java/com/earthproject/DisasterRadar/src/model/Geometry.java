package com.earthproject.DisasterRadar.src.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class Geometry {
    private Double magnitudeValue;
    private String magnitudeUnit;
    private String date;
    private String type;
    private List<Double> coordinates;

}