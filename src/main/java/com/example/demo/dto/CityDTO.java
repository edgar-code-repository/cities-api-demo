package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CityDTO {

    private String id;

    private String name;

    private String country;

    @JsonIgnore
    private String strPopulation;

    private Long population;

}
