package com.example.demo.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CityDTO {

    // "city","city_ascii","lat","lng","country","iso2","iso3","admin_name","capital","population","id"

    private String id;

    private String name;

    private String country;

    private String strPopulation;

    private Long population;

}
