package com.example.demo

import com.example.demo.controller.CityController
import com.example.demo.dto.CityDTO
import com.example.demo.dto.CityListResponseDTO
import com.example.demo.service.CityService
import org.springframework.http.HttpStatus
import spock.lang.Specification

class CityControllerSpec extends Specification {

    CityService cityService = Mock(CityService)
    CityController cityController = new CityController()

    def setup() {
        cityController.cityService = cityService
    }

    def "Should return all cities when type is 'all'"() {
        given:
            def type = "all"
            def cityDTO1 = new CityDTO(id: 1, name: "City1", population: 100000)
            def cityDTO2 = new CityDTO(id: 2, name: "City2", population: 200000)
            def cityList = [cityDTO1, cityDTO2]
            def responseDTO = new CityListResponseDTO(cities: cityList)

        when:
            cityService.retrieveAllCities() >> responseDTO
            def response = cityController.getAllCities(type)

        then:
            response.statusCode == HttpStatus.OK
            response.body == responseDTO
            response.body.cities.size() == 2
    }

    def "Should return most populated cities when type is 'mostPopulated'"() {
        given:
            def type = "mostPopulated"
            def cityDTO1 = new CityDTO(id: 1, name: "City1", population: 1000000)
            def cityDTO2 = new CityDTO(id: 2, name: "City2", population: 900000)
            def cityList = [cityDTO1, cityDTO2]
            def responseDTO = new CityListResponseDTO(cities: cityList)

        when:
            cityService.retrieveTenMostPopulatedCities() >> responseDTO
            def response = cityController.getAllCities(type)

        then:
            response.statusCode == HttpStatus.OK
            response.body == responseDTO
            response.body.cities.size() == 2
    }

    def "Should handle empty city lists"() {
        given:
            def type = "all"
            def emptyList = new CityListResponseDTO(cities: [])

        when:
            cityService.retrieveAllCities() >> emptyList
            def response = cityController.getAllCities(type)

        then:
            response.statusCode == HttpStatus.OK
            response.body == emptyList
            response.body.cities.isEmpty()
    }

    def "Should handle empty mostPopulated city lists"() {
        given:
            def type = "mostPopulated"
            def emptyList = new CityListResponseDTO(cities: [])

        when:
            cityService.retrieveTenMostPopulatedCities() >> emptyList
            def response = cityController.getAllCities(type)

        then:
            response.statusCode == HttpStatus.OK
            response.body == emptyList
            response.body.cities.isEmpty()
    }


}
