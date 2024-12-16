package com.salesianos.triana.api_monumentos_v2.dto;

import com.salesianos.triana.api_monumentos_v2.model.Monument;
import lombok.Builder;

import java.util.List;

@Builder
public record GetAllMonumentDto(

        List<Monument> list,
        Long count

        /*Long id,
        String countryCode,
        String countryName,
        String cityName,
        String monumentName,
        String description,
        String photoUrl,
        double latitude,
        double longitude*/
) {


    public static GetAllMonumentDto ofGetAllMonumentDto(List<Monument> monumentList){

        return new GetAllMonumentDto(monumentList, (long) monumentList.size());

    }

    public static GetAllMonumentDto ofGetSpecificCityMonumentDto(List<Monument> monumentList){

        return new GetAllMonumentDto(monumentList, (long) monumentList.size());

    }

}
