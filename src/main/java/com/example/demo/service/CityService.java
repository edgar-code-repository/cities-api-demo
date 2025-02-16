package com.example.demo.service;

import com.example.demo.dto.CityDTO;
import com.example.demo.dto.CityListResponseDTO;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CityService {

    @Value("${cities.filePath}")
    private String filePath;

    public CityListResponseDTO retrieveAllCities() {
        log.debug("[retrieveAllCities][START]");
        CityListResponseDTO cityListResponse = new CityListResponseDTO();
        try {
            List<CityDTO> list = Files.lines(
                Paths.get(filePath))
                    .skip(1) // Skip header
                    .map(line -> {
                        String[] data = line.split(",");
                        log.debug("[retrieveAllCities][city: {}][population: {}]", data[0], data[9]);
                        //if (data.length != 11) {
                        //    log.debug("[retrieveAllCities][city: {}][length: {}]", data[0], data.length);
                        //}
                        return new CityDTO(data[10], data[0], data[4], data[9], 0L);
                    })
                    .collect(Collectors.toList());

            list = list.stream().map(
                    city -> {
                        if (city.getStrPopulation().trim().equals("")) {
                            city.setPopulation(0L);
                        } else {
                            city.setPopulation(Long.parseLong(city.getStrPopulation()));
                        }
                        return city;
                    }).collect(Collectors.toList());

            cityListResponse.setCities(list);
        } catch (Exception e) {
            log.debug("[retrieveAllCities][Error: {}]", e.toString());
        }
        log.debug("[retrieveAllCities][END]");
        return cityListResponse;
    }

}
