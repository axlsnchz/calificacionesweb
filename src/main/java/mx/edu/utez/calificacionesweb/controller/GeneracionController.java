package mx.edu.utez.calificacionesweb.controller;

import mx.edu.utez.calificacionesweb.model.generacion.Generacion;
import mx.edu.utez.calificacionesweb.service.GeneracionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/generaciones")
@CrossOrigin(origins = "*")
public class GeneracionController {

    private final GeneracionService generacionService;

    public GeneracionController(GeneracionService generacionService) {
        this.generacionService = generacionService;
    }

    @PostMapping
    public ResponseEntity<Generacion> crear(@RequestBody Generacion generacion) {
        return ResponseEntity.status(HttpStatus.CREATED).body(generacionService.crear(generacion));
    }

    @GetMapping
    public ResponseEntity<List<Generacion>> listar() {
        return ResponseEntity.ok(generacionService.listar());
    }
}