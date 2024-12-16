package com.salesianos.triana.api_monumentos_v2.dto;

import com.salesianos.triana.api_monumentos_v2.model.Monument;
import lombok.Builder;

@Builder
public record GetMonumentDto (
        Long id,
        String countryCode,
        String countryName,
        String cityName,
        String monumentName,
        String description,
        String photoUrl,
        double latitude,
        double longitude
) {

    public static GetMonumentDto ofGetMonumentDto(Monument m){

        return GetMonumentDto.builder()
                .id(m.getId())
                .countryCode(m.getCountryCode())
                .countryName(m.getCountryName())
                .cityName(m.getCityName())
                .monumentName(m.getMonumentName())
                .description(m.getDescription())
                .photoUrl(m.getPhotoUrl())
                .latitude(m.getLatitude())
                .longitude(m.getLongitude()).build();

    }

}
