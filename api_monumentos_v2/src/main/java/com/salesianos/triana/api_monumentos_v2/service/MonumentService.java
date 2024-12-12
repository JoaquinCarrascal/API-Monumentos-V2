package com.salesianos.triana.api_monumentos_v2.service;

import com.salesianos.triana.api_monumentos_v2.model.Monument;
import com.salesianos.triana.api_monumentos_v2.repository.MonumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MonumentService extends BaseServiceImpl <Monument, Long , MonumentRepository> {

    private final MonumentRepository repository;

    public List<Monument> findByCityName(String cityName) {
        return repository.findByCityName(cityName);
    }

    public List<Monument> query(String sortDirection) {
        if (sortDirection.equalsIgnoreCase("asc")) {
            return repository.findAllByOrderByNameAsc();
        } else if (sortDirection.equalsIgnoreCase("desc")) {
            return repository.findAllByOrderByNameDesc();
        } else {
            return repository.findAll();
        }
    }

    public Optional<Monument> edit(Long id , Monument newMonument){

        return repository.findById(id).map(oldMonument -> {
            oldMonument.setCountryCode(newMonument.getCountryCode());
            oldMonument.setCountryName(newMonument.getCountryName());
            oldMonument.setCityName(newMonument.getCityName());
            oldMonument.setLatitude(newMonument.getLatitude());
            oldMonument.setLongitude(newMonument.getLongitude());
            oldMonument.setMonumentName(newMonument.getMonumentName());
            oldMonument.setDescription(newMonument.getDescription());
            oldMonument.setPhotoUrl(newMonument.getPhotoUrl());
            return repository.save(oldMonument);
        });

    }

}