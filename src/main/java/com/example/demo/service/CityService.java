package com.example.demo.service;

import com.example.demo.dto.CityListResponseDTO;

public interface CityService {

    CityListResponseDTO retrieveAllCities();

    CityListResponseDTO retrieveTenMostPopulatedCities();

}
