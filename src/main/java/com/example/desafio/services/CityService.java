package com.example.desafio.services;

import com.example.desafio.models.City;
import com.example.desafio.repository.CityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {
    @Autowired
    private CityRepository cityRepository;

    public Iterable<City> findAll() {
        return cityRepository.findAll();
    }
}
