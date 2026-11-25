package com.escuela.calificaciones.controller;

import com.escuela.calificaciones.dto.MateriaRequest;
import com.escuela.calificaciones.dto.MateriaResponse;
import com.escuela.calificaciones.dto.UnidadResponse;
import com.escuela.calificaciones.service.MateriaService;
import com.escuela.calificaciones.service.UnidadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materias")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MateriaController {

    private final MateriaService materiaService;
    private final UnidadService unidadService;

    @PostMapping
    public ResponseEntity<MateriaResponse> crear(@Valid @RequestBody MateriaRequest request) {
        MateriaResponse creada = materiaService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @GetMapping
    public ResponseEntity<List<MateriaResponse>> listar() {
        return ResponseEntity.ok(materiaService.listar());
    }

    @GetMapping("/carrera/{carreraId}")
    public ResponseEntity<List<MateriaResponse>> porCarrera(@PathVariable Integer carreraId) {
        return ResponseEntity.ok(materiaService.listarPorCarrera(carreraId));
    }

    @GetMapping("/{id}/unidades")
    public ResponseEntity<List<UnidadResponse>> unidades(@PathVariable Integer id) {
        return ResponseEntity.ok(unidadService.listarPorMateria(id));
    }
}