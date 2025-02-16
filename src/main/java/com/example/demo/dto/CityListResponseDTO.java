package com.example.demo.dto;

import java.util.List;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CityListResponseDTO
{

    private List<CityDTO> cities;

}
