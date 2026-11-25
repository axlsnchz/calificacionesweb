package com.escuela.calificaciones.controller;

import com.escuela.calificaciones.dto.AlumnoRequest;
import com.escuela.calificaciones.dto.AlumnoResponse;
import com.escuela.calificaciones.dto.PromedioResponse;
import com.escuela.calificaciones.service.AlumnoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alumnos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AlumnoController {

    private final AlumnoService alumnoService;

    @PostMapping
    public ResponseEntity<AlumnoResponse> crear(@Valid @RequestBody AlumnoRequest request) {
        AlumnoResponse creado = alumnoService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping
    public ResponseEntity<List<AlumnoResponse>> listar() {
        return ResponseEntity.ok(alumnoService.listar());
    }

    @GetMapping("/activos")
    public ResponseEntity<List<AlumnoResponse>> activos() {
        return ResponseEntity.ok(alumnoService.listarActivos());
    }

    @GetMapping("/{id}/promedio")
    public ResponseEntity<PromedioResponse> promedio(@PathVariable Integer id) {
        return ResponseEntity.ok(alumnoService.promedio(id));
    }

    @PatchMapping("/{id}/baja")
    public ResponseEntity<AlumnoResponse> darDeBaja(@PathVariable Integer id) {
        return ResponseEntity.ok(alumnoService.darDeBaja(id));
    }
}