package com.salesianos.triana.api_monumentos_v2.exception;

public class MonumentNotFoundException extends RuntimeException{

    public MonumentNotFoundException() {
        super("Monumento no encontrado");
    }

    public MonumentNotFoundException(Long id) {super("El Monumento con id " + id + " no se encuentra.");}

}
