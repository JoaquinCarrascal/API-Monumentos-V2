package com.salesianos.triana.api_monumentos_v2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "monument")
public class Monument {

    /*Su ID (un número entero)
El código de país (según el código ISO 3166-1 alfa 2).
El nombre del país.
El nombre de la ciudad.
Su localización (latitud, longitud).
El nombre del monumento
Un descripción del mismo
La URL de una foto.*/

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String countryCode;
    private String countryName;
    private String cityName;
    private double latitude;
    private double longitude;
    private String monumentName;
    private String description;
    private String photoUrl;

}