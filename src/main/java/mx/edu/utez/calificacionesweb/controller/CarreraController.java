package mx.edu.utez.calificacionesweb.controller;

import jakarta.persistence.EntityNotFoundException;
import mx.edu.utez.calificacionesweb.model.carrera.Carrera;
import mx.edu.utez.calificacionesweb.model.materia.Materia;
import mx.edu.utez.calificacionesweb.repository.carrera.CarreraRepository;
import mx.edu.utez.calificacionesweb.repository.materia.MateriaRepository;
import mx.edu.utez.calificacionesweb.repository.unidad.UnidadRepository;
import mx.edu.utez.calificacionesweb.service.CarreraService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping("/api/carreras")
@CrossOrigin(origins = "*")
public class CarreraController {

    private final CarreraRepository carreraRepository;
    private final MateriaRepository materiaRepository;
    private final UnidadRepository unidadRepository;
    private final CarreraService carreraService;

    public CarreraController(
            CarreraRepository carreraRepository,
            MateriaRepository materiaRepository,
            UnidadRepository unidadRepository,
            CarreraService carreraService) {
        this.carreraRepository = carreraRepository;
        this.materiaRepository = materiaRepository;
        this.unidadRepository = unidadRepository;
        this.carreraService = carreraService;
    }

    @PostMapping
    public ResponseEntity<Carrera> crear(@RequestBody Carrera carrera) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carreraService.crear(carrera));
    }

    @GetMapping
    public ResponseEntity<List<Carrera>> listar() {
        return ResponseEntity.ok(carreraRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carrera> obtenerPorId(@PathVariable Long id) {
        Carrera carrera = carreraRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("La carrera no existe"));
        return ResponseEntity.ok(carrera);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Carrera> actualizar(@PathVariable Long id, @RequestBody Carrera datos) {
        Carrera carrera = carreraRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("La carrera no existe"));
        validar(datos);
        carrera.setNombre(datos.getNombre());
        carrera.setNumCuatrimestres(datos.getNumCuatrimestres());
        return ResponseEntity.ok(carreraRepository.save(carrera));
    }

    @GetMapping("/{id}/plan")
    public ResponseEntity<Map<Integer, List<Map<String, Object>>>> plan(@PathVariable Long id) {
        Carrera carrera = carreraRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("La carrera no existe"));

        Map<Integer, List<Map<String, Object>>> plan = new TreeMap<>();
        for (Materia materia : materiaRepository.findByCarrera(carrera)) {
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("id_materia", materia.getId_materia());
            item.put("nombre", materia.getNombre());
            item.put("creditos", materia.getCreditos());
            item.put("id_profesor", materia.getProfesor() != null ? materia.getProfesor().getId_profesor() : null);
            item.put("unidades", unidadRepository.findByMateriaIdOrderByNumeroAsc(materia.getId_materia()).size());
            plan.computeIfAbsent(materia.getCuatrimestre(), k -> new ArrayList<>()).add(item);
        }
        return ResponseEntity.ok(plan);
    }

    private void validar(Carrera carrera) {
        if (carrera.getNombre() == null || carrera.getNombre().isBlank()) {
            throw new IllegalStateException("El nombre de la carrera es obligatorio");
        }
        if (carrera.getNumCuatrimestres() <= 0) {
            throw new IllegalStateException("El numero de cuatrimestres debe ser mayor a 0");
        }
    }
}
