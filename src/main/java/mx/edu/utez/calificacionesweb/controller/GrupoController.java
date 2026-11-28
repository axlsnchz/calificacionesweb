package mx.edu.utez.calificacionesweb.controller;

import jakarta.persistence.EntityNotFoundException;
import mx.edu.utez.calificacionesweb.model.alumno.Alumno;
import mx.edu.utez.calificacionesweb.model.generacion.Generacion;
import mx.edu.utez.calificacionesweb.model.grupo.Grupo;
import mx.edu.utez.calificacionesweb.repository.alumno.AlumnoRepository;
import mx.edu.utez.calificacionesweb.repository.generacion.GeneracionRepository;
import mx.edu.utez.calificacionesweb.repository.grupo.GrupoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grupos")
@CrossOrigin(origins = "*")
public class GrupoController {

    private final GrupoRepository grupoRepository;
    private final GeneracionRepository generacionRepository;
    private final AlumnoRepository alumnoRepository;

    public GrupoController(GrupoRepository grupoRepository,
                           GeneracionRepository generacionRepository,
                           AlumnoRepository alumnoRepository) {
        this.grupoRepository = grupoRepository;
        this.generacionRepository = generacionRepository;
        this.alumnoRepository = alumnoRepository;
    }

    @PostMapping
    public ResponseEntity<Grupo> crear(@RequestBody Grupo grupo) {
        if (grupo.getLetra() == null || grupo.getLetra().isBlank()) {
            throw new IllegalStateException("La letra del grupo es obligatoria");
        }
        if (grupo.getGeneracion() == null || grupo.getGeneracion().getId_generacion() == null) {
            throw new IllegalStateException("La generación es obligatoria");
        }

        Generacion generacion = generacionRepository.findById(grupo.getGeneracion().getId_generacion())
                .orElseThrow(() -> new EntityNotFoundException("La generación no existe"));

        String letra = grupo.getLetra().trim().toUpperCase();
        if (grupoRepository.existsByGeneracionAndLetra(generacion, letra)) {
            throw new IllegalStateException("Ya existe el grupo " + letra + " en esta generación");
        }

        grupo.setId_grupo(null);
        grupo.setLetra(letra);
        grupo.setGeneracion(generacion);
        grupo.setCuatrimestre_actual(1);
        grupo.setEstado("ACTIVO");

        Grupo creado = grupoRepository.save(grupo);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping
    public ResponseEntity<List<Grupo>> listar() {
        return ResponseEntity.ok(grupoRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Grupo> obtenerPorId(@PathVariable Long id) {
        Grupo grupo = grupoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El grupo no existe"));
        return ResponseEntity.ok(grupo);
    }

    @GetMapping("/generacion/{generacionId}")
    public ResponseEntity<List<Grupo>> porGeneracion(@PathVariable Long generacionId) {
        Generacion generacion = generacionRepository.findById(generacionId)
                .orElseThrow(() -> new EntityNotFoundException("La generación no existe"));
        return ResponseEntity.ok(grupoRepository.findByGeneracion(generacion));
    }

    @GetMapping("/{id}/alumnos")
    public ResponseEntity<List<Alumno>> alumnos(@PathVariable Long id) {
        Grupo grupo = grupoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El grupo no existe"));
        return ResponseEntity.ok(alumnoRepository.findByGrupo(grupo));
    }
}