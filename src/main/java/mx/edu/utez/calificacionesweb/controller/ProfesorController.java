package com.escuela.calificaciones.controller;

import com.escuela.calificaciones.dto.ProfesorRequest;
import com.escuela.calificaciones.dto.ProfesorResponse;
import com.escuela.calificaciones.service.ProfesorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profesores")
@RequiredArgsConstructor
public class ProfesorController {

    private final ProfesorService profesorService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProfesorResponse crear(@Valid @RequestBody ProfesorRequest request) {
        return profesorService.crear(request);
    }

    @GetMapping
    public List<ProfesorResponse> listar() {
        return profesorService.listar();
    }

    @GetMapping("/activos")
    public List<ProfesorResponse> activos() {
        return profesorService.listarActivos();
    }

    @GetMapping("/{id}")
    public ProfesorResponse obtenerPorId(@PathVariable Integer id) {
        return profesorService.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    public ProfesorResponse actualizar(@PathVariable Integer id, @Valid @RequestBody ProfesorRequest request) {
        return profesorService.actualizar(id, request);
    }

    @PatchMapping("/{id}/baja")
    public ProfesorResponse darDeBaja(@PathVariable Integer id) {
        return profesorService.darDeBaja(id);
    }
}