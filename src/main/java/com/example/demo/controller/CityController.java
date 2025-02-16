package com.example.demo.controller;

import com.example.demo.dto.CityListResponseDTO;
import com.example.demo.service.CityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cities")
@Slf4j
public class CityController {

    @Autowired
    private CityService cityService;

    @RequestMapping("/search")
    public ResponseEntity<CityListResponseDTO> getAllCities(@RequestParam String type) {
        log.debug("[getAllCities][START][search type: {}]", type);
        CityListResponseDTO listResponseDTO = null;
        if (type.equals("all")) {
            listResponseDTO = cityService.retrieveAllCities();
            log.debug("[getAllCities][size all: {}]", listResponseDTO.getCities().size());
        } else if (type.equals("mostPopulated")) {
            listResponseDTO = cityService.retrieveTenMostPopulatedCities();
            log.debug("[getAllCities][size most populated: {}]", listResponseDTO.getCities().size());
        }

        log.debug("[getAllCities][END]");
        return ResponseEntity.ok(listResponseDTO);
    }




}
