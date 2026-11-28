package mx.edu.utez.calificacionesweb.controller;

import jakarta.persistence.EntityNotFoundException;
import mx.edu.utez.calificacionesweb.model.carrera.Carrera;
import mx.edu.utez.calificacionesweb.model.materia.Materia;
import mx.edu.utez.calificacionesweb.model.profesor.Profesor;
import mx.edu.utez.calificacionesweb.repository.carrera.CarreraRepository;
import mx.edu.utez.calificacionesweb.repository.materia.MateriaRepository;
import mx.edu.utez.calificacionesweb.repository.profesor.ProfesorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materias")
@CrossOrigin(origins = "*")
public class MateriaController {

    private final MateriaRepository materiaRepository;
    private final CarreraRepository carreraRepository;
    private final ProfesorRepository profesorRepository;

    public MateriaController(
            MateriaRepository materiaRepository,
            CarreraRepository carreraRepository,
            ProfesorRepository profesorRepository) {
        this.materiaRepository = materiaRepository;
        this.carreraRepository = carreraRepository;
        this.profesorRepository = profesorRepository;
    }

    @PostMapping
    public ResponseEntity<Materia> crear(@RequestBody Materia materia) {
        if (materia.getCarrera() == null || materia.getCarrera().getId() == null) {
            throw new IllegalStateException("La carrera es obligatoria");
        }

        Carrera carrera = carreraRepository.findById(materia.getCarrera().getId())
                .orElseThrow(() -> new EntityNotFoundException("La carrera no existe"));
        materia.setCarrera(carrera);

        if (materia.getProfesor() == null || materia.getProfesor().getId_profesor() == null) {
            throw new IllegalStateException("El profesor es obligatorio");
        }
        Profesor profesor = profesorRepository.findById(materia.getProfesor().getId_profesor())
                .orElseThrow(() -> new EntityNotFoundException("El profesor no existe"));
        materia.setProfesor(profesor);

        validarCuatrimestre(materia.getCuatrimestre(), carrera);

        Materia creada = materiaRepository.save(materia);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Materia> actualizar(@PathVariable Long id, @RequestBody Materia datos) {
        Materia materia = materiaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("La materia no existe"));

        validarCuatrimestre(datos.getCuatrimestre(), materia.getCarrera());

        materia.setNombre(datos.getNombre());
        materia.setCreditos(datos.getCreditos());
        materia.setCuatrimestre(datos.getCuatrimestre());

        if (datos.getProfesor() != null && datos.getProfesor().getId_profesor() != null) {
            Profesor profesor = profesorRepository.findById(datos.getProfesor().getId_profesor())
                    .orElseThrow(() -> new EntityNotFoundException("El profesor no existe"));
            materia.setProfesor(profesor);
        }

        return ResponseEntity.ok(materiaRepository.save(materia));
    }

    private void validarCuatrimestre(int cuatrimestre, Carrera carrera) {
        if (cuatrimestre < 1 || cuatrimestre > carrera.getNum_cuatrimestres()) {
            throw new IllegalStateException(
                    "El cuatrimestre debe estar entre 1 y " + carrera.getNum_cuatrimestres());
        }
    }

    @GetMapping
    public ResponseEntity<List<Materia>> listar() {
        return ResponseEntity.ok(materiaRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Materia> obtenerPorId(@PathVariable Long id) {
        Materia materia = materiaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("La materia no existe"));
        return ResponseEntity.ok(materia);
    }

    @GetMapping("/carrera/{carreraId}")
    public ResponseEntity<List<Materia>> porCarrera(@PathVariable Long carreraId) {
        Carrera carrera = carreraRepository.findById(carreraId)
                .orElseThrow(() -> new EntityNotFoundException("La carrera no existe"));
        return ResponseEntity.ok(materiaRepository.findByCarrera(carrera));
    }
}
