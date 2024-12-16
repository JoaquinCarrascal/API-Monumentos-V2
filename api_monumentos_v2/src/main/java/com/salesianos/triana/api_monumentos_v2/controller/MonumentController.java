package com.salesianos.triana.api_monumentos_v2.controller;

import com.salesianos.triana.api_monumentos_v2.dto.GetAllMonumentDto;
import com.salesianos.triana.api_monumentos_v2.dto.GetMonumentDto;
import com.salesianos.triana.api_monumentos_v2.model.Monument;
import com.salesianos.triana.api_monumentos_v2.repository.MonumentRepository;
import com.salesianos.triana.api_monumentos_v2.service.MonumentService;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/monument/")
@Tag(name = "Controlador de monumentos" , description = "El controlador de monumentos")
public class MonumentController {

    private final MonumentService monumentService;

    @Operation(summary = "Obtener todos los monumentos")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Devuelve una lista de monumentos y la cantidad de monumentos que hay",
                    content = {@Content (mediaType = "application/json",
                            array = @ArraySchema(schema=@Schema(implementation = Monument.class)),
                            examples = {@ExampleObject(value = """
                [
                {"id": 1 , "name": "Monumento 1", "cityName": "Sevilla", "description": "Descripción del monumento 1", "photoUrl": "https://www.google.com"},
                {"id": 2 , "name": "Monumento 2", "cityName": "Sevilla", "description": "Descripción del monumento 2", "photoUrl": "https://www.google.com"}
                ]
                """)})}),
            @ApiResponse(responseCode = "404", description = "No se encuentra ningún Monumento" ,
                    content = @Content)
    })
    @GetMapping
    public GetAllMonumentDto getAll(
            @RequestParam(required = false,
                    value = "sort", defaultValue = "no") @Parameter(required = false, description = "Ordenación alfabética de los nombres de monumentos de la respuesta") String sortDirection){

        return GetAllMonumentDto.ofGetAllMonumentDto(monumentService.query(sortDirection));

    }

    @Operation(summary = "Obtener todos los monumentos de una ciudad")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Devuelve una lista de monumentos de una ciudad en concreto y la cantidad que hay en la lista",
                    content = {@Content (mediaType = "application/json",
                            array = @ArraySchema(schema=@Schema(implementation = Monument.class)),
                            examples = {@ExampleObject(value = """
                [
                {"id": 1 , "name": "Monumento 1", "cityName": "Sevilla", "description": "Descripción del monumento 1", "photoUrl": "https://www.google.com"},
                {"id": 2 , "name": "Monumento 2", "cityName": "Sevilla", "description": "Descripción del monumento 2", "photoUrl": "https://www.google.com"}
                ]
                """)})}),
            @ApiResponse(responseCode = "404", description = "No se encuentra ningún Monumento" ,
                    content = @Content)
    })
    @GetMapping("of/{cityName}")
    public GetAllMonumentDto findByCityName(
            @Parameter(name = "cityName", description = "Nombre de la ciudad", required = true, in = ParameterIn.PATH)
            @PathVariable String cityName) {

        return GetAllMonumentDto.ofGetSpecificCityMonumentDto(monumentService.findByCityName(cityName));

    }

    @Operation(summary = "Obtener un monumento en concreto por id")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Devuelve un monumento en concreto",
                    content = {@Content (mediaType = "application/json",
                            array = @ArraySchema(schema=@Schema(implementation = Monument.class)),
                            examples = {@ExampleObject(value = """
                {"id": 1 , "name": "Monumento 1", "cityName": "Sevilla", "description": "Descripción del monumento 1", "photoUrl": "https://www.google.com"}
                """)})}),
            @ApiResponse(responseCode = "404", description = "No se encuentra ningún Monumento" ,
                    content = @Content)
    })
    @GetMapping("{id}")
    public GetMonumentDto getOne(
            @PathVariable
            @Parameter(required = true,in = ParameterIn.PATH,description = "Id del monumento en concreto") Long id) {
        return GetMonumentDto.ofGetMonumentDto(monumentService.findMonumentById(id));
    }

    @Operation(summary = "Editar un monumento en concreto")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Devuelve un monumento editado",
                    content = {@Content (mediaType = "application/json",
                            array = @ArraySchema(schema=@Schema(implementation = Monument.class)),
                            examples = {@ExampleObject(value = """
                {"id": 1 , "name": "Monumento 1", "cityName": "Sevilla", "description": "Descripción del monumento 1", "photoUrl": "https://www.google.com"}
                """)})}),
            @ApiResponse(responseCode = "404", description = "No se encuentra ningún Monumento" ,
                    content = @Content)
    })
   @PutMapping("{id}")
    public GetMonumentDto edit(
            @PathVariable @Parameter(required = true,in = ParameterIn.PATH,description = "Id del monumento que se quiera editar") Long id,
            @RequestBody @Parameter(required = true, description = "El monumento editado") Monument newMonument) {
        return GetMonumentDto.ofGetMonumentDto(monumentService.edit(id,newMonument));
    }

    @Operation(summary = "Crear un monumento nuevo y agregarlo a la lista de monumentos")
    @ApiResponse(responseCode = "201" , description = "Devuelve el monumento creado",
                content = {@Content(mediaType = "application/json",
                                    array = @ArraySchema(schema=@Schema(implementation = Monument.class)),
                examples = {@ExampleObject(value= """
                        {"id": 1 , "name": "Monumento 1", "cityName": "Sevilla", "description": "Descripción del monumento 1", "photoUrl": "https://www.google.com"}
                        """)})})
    @PostMapping
    public ResponseEntity<GetMonumentDto> create(
            @RequestBody Monument monument) {
        return ResponseEntity.status(201).body(GetMonumentDto.ofGetMonumentDto(monumentService.save(monument)));
    }

    @Operation(summary = "Borrar un monumento del listado de monumentos")
    @ApiResponse(responseCode = "204" , description = "Devuelve una entidad de respuesta sin contenido",
            content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema=@Schema(implementation = Monument.class))
                    )})
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        monumentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
