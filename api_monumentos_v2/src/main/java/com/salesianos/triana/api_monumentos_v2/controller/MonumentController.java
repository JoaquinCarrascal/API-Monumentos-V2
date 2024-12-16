package com.salesianos.triana.api_monumentos_v2.controller;

import com.salesianos.triana.api_monumentos_v2.model.Monument;
import com.salesianos.triana.api_monumentos_v2.repository.MonumentRepository;
import com.salesianos.triana.api_monumentos_v2.service.MonumentService;

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

    @Operation(summary = "Obtener todos los monumentos de una ciudad")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Devuelve una lista de monumentos",
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
    public List<Monument> getAll(
            @RequestParam(required = false,
                    value = "sort", defaultValue = "no") String sortDirection){

        return monumentService.query(sortDirection);

    }


    @GetMapping("of/{cityName}")
    public List<Monument> findByCityName(@PathVariable String cityName) {

        List<Monument> result = monumentService.findByCityName(cityName);

        return !result.isEmpty() ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<Monument> getOne(@PathVariable Long id) {
        return ResponseEntity.of(monumentService.findById(id));
    }

   @PutMapping("{id}")
    public ResponseEntity<Monument> edit(@PathVariable Long id, @RequestBody Monument newMonument) {
        Optional<Monument> result = monumentService.edit(id, newMonument);
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Monument> create(@RequestBody Monument monument) {
        return ResponseEntity.status(201).body(monumentService.save(monument));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        monumentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
