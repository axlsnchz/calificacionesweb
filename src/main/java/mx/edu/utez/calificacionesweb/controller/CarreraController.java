package mx.edu.utez.calificacionesweb.controller;

import mx.edu.utez.calificacionesweb.model.carrera.Carrera;
import mx.edu.utez.calificacionesweb.service.CarreraService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carreras")
@CrossOrigin(origins = "*")
public class CarreraController {

    private final CarreraService carreraService;

    public CarreraController(CarreraService carreraService) {
        this.carreraService = carreraService;
    }

    @PostMapping
    public ResponseEntity<Carrera> crear(@RequestBody Carrera carrera) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carreraService.crear(carrera));
    }

    @GetMapping
    public ResponseEntity<List<Carrera>> listar() {
        return ResponseEntity.ok(carreraService.listar());
    }
}