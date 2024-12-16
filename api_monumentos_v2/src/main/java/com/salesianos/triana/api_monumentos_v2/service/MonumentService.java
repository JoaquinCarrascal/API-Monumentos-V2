package com.salesianos.triana.api_monumentos_v2.service;

import com.salesianos.triana.api_monumentos_v2.exception.MonumentNotFoundException;
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

    public Monument findMonumentById(Long id){
        Optional<Monument> mon = repository.findById(id);

        if(mon.isEmpty())
            throw new MonumentNotFoundException();
        else
            return mon.get();

    }

    public List<Monument> findByCityName(String cityName) {

        List<Monument> result = repository.findByCityName(cityName);

        if(result.isEmpty())
            throw new MonumentNotFoundException();

        return result;

    }

    public List<Monument> query(String sortDirection) {
        List<Monument> result = switch (sortDirection) {
            case "asc" -> repository.findAllByOrderByNameAsc();
            case "desc" -> repository.findAllByOrderByNameDesc();
            default -> repository.findAll();
        };

        if(result.isEmpty())
            throw new MonumentNotFoundException();
        else
            return result;

    }

    public Monument edit(Long id , Monument newMonument){

        Optional<Monument> editable = repository.findById(id);

        if(editable.isEmpty())
            throw new MonumentNotFoundException(id);

        editable.map(oldMonument -> {
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

        return newMonument;

    }

}