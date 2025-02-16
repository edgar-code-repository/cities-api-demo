package com.example.demo.service;

import com.example.demo.dto.CityDTO;
import com.example.demo.dto.CityListResponseDTO;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CityServiceImpl implements CityService {

    @Value("${cities.filePath}")
    private String filePath;

    public CityListResponseDTO retrieveAllCities() {
        log.debug("[retrieveAllCities][START]");
        CityListResponseDTO cityListResponse = new CityListResponseDTO();
        cityListResponse.setCities(this.getAllCitiesFromFile());
        log.debug("[retrieveAllCities][END]");
        return cityListResponse;
    }

    public CityListResponseDTO retrieveTenMostPopulatedCities() {
        log.debug("[retrieveTenMostPopulatedCities][START]");
        CityListResponseDTO cityListResponse = new CityListResponseDTO();
        List<CityDTO> list = this.getAllCitiesFromFile();
        list = list.stream()
                .sorted(Comparator.comparingLong(CityDTO::getPopulation).reversed()).limit(10)
                .collect(Collectors.toList());

        cityListResponse.setCities(list);
        log.debug("[retrieveTenMostPopulatedCities][END]");
        return cityListResponse;
    }

    private List<CityDTO> getAllCitiesFromFile() {
        log.debug("[getAllCitiesFromFile][START]");
        List<CityDTO> list = new ArrayList<>();
        try {
            list = Files.lines(
                            Paths.get(filePath))
                    .skip(1) // Skip header
                    .map(line -> {
                        String[] data = line.split(",");
                        //log.debug("[getAllCitiesFromFile][city: {}][population: {}]", data[0], data[9]);
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
        } catch (Exception e) {
            log.debug("[getAllCitiesFromFile][Error: {}]", e.toString());
        }
        log.debug("[getAllCitiesFromFile][END]");
        return list;
    }

}
