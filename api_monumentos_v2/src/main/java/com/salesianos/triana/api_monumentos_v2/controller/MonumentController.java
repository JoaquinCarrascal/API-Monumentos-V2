package com.salesianos.triana.api_monumentos_v2.controller;

import com.salesianos.triana.api_monumentos_v2.model.Monument;
import com.salesianos.triana.api_monumentos_v2.repository.MonumentRepository;
import com.salesianos.triana.api_monumentos_v2.service.MonumentService;
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
public class MonumentController {

    private final MonumentService monumentService;

    @GetMapping
    public ResponseEntity<List<Monument>> getAll(
            @RequestParam(required = false,
                    value = "sort", defaultValue = "no") String sortDirection){

        List<Monument> result = monumentService.query(sortDirection);

        if (result.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(result);

    }

    @GetMapping("of/{cityName}")
    public ResponseEntity<List<Monument>> findByCityName(@PathVariable String cityName) {

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
