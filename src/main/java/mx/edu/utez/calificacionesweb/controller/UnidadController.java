package mx.edu.utez.calificacionesweb.controller;

import mx.edu.utez.calificacionesweb.model.unidad.Unidad;
import mx.edu.utez.calificacionesweb.service.UnidadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/materias/{materiaId}/unidades")
public class UnidadController {

    private final UnidadService unidadService;

    public UnidadController(UnidadService unidadService) {
        this.unidadService = unidadService;
    }

    @PostMapping
    public ResponseEntity<Unidad> crear(
            @PathVariable Long materiaId,
            @RequestBody Unidad unidad) {
        Unidad nuevaUnidad = unidadService.crear(materiaId, unidad);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaUnidad);
    }

    @GetMapping
    public ResponseEntity<List<Unidad>> listarPorMateria(@PathVariable Long materiaId) {
        List<Unidad> unidades = unidadService.listarPorMateria(materiaId);
        return ResponseEntity.ok(unidades);
    }
}