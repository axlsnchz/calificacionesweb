package mx.edu.utez.calificacionesweb.controller;


import mx.edu.utez.calificacionesweb.model.calificacion.Calificacion;
import mx.edu.utez.calificacionesweb.service.CalificacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/calificaciones")
public class CalificacionController {

    private final CalificacionService calificacionService;

    public CalificacionController(CalificacionService calificacionService) {
        this.calificacionService = calificacionService;
    }

    @PostMapping
    public ResponseEntity<Calificacion> registrar(
            @RequestParam Long alumnoId,
            @RequestParam Long unidadId,
            @RequestParam Long profesorId,
            @RequestParam double calificacion) {
        Calificacion creada = calificacionService.registrar(alumnoId, unidadId, profesorId, calificacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Calificacion> modificar(
            @PathVariable Long id,
            @RequestParam Long profesorId,
            @RequestParam double calificacion) {
        Calificacion actualizada = calificacionService.modificar(id, profesorId, calificacion);
        return ResponseEntity.ok(actualizada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Calificacion> detalle(@PathVariable Long id) {
        return ResponseEntity.ok(calificacionService.detalle(id));
    }

    @GetMapping("/alumno/{alumnoId}")
    public ResponseEntity<List<Calificacion>> listarPorAlumno(@PathVariable Long alumnoId) {
        return ResponseEntity.ok(calificacionService.listarPorAlumno(alumnoId));
    }

    @GetMapping("/unidad/{unidadId}")
    public ResponseEntity<List<Calificacion>> listarPorUnidad(@PathVariable Long unidadId) {
        return ResponseEntity.ok(calificacionService.listarPorUnidad(unidadId));
    }

    @GetMapping("/resultado/{resultado}")
    public ResponseEntity<List<Calificacion>> listarPorResultado(@PathVariable String resultado) {
        return ResponseEntity.ok(calificacionService.listarPorResultado(resultado));
    }
}