package mx.edu.utez.calificacionesweb.controller;

import jakarta.persistence.EntityNotFoundException;
import mx.edu.utez.calificacionesweb.model.alumno.Alumno;
import mx.edu.utez.calificacionesweb.model.calificacion.Calificacion;
import mx.edu.utez.calificacionesweb.model.grupo.Grupo;
import mx.edu.utez.calificacionesweb.model.usuario.Usuario;
import mx.edu.utez.calificacionesweb.repository.alumno.AlumnoRepository;
import mx.edu.utez.calificacionesweb.repository.calificacion.CalificacionRepository;
import mx.edu.utez.calificacionesweb.repository.grupo.GrupoRepository;
import mx.edu.utez.calificacionesweb.repository.usuario.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/alumnos")
@CrossOrigin(origins = "*")
public class AlumnoController {

    private final AlumnoRepository alumnoRepository;
    private final UsuarioRepository usuarioRepository;
    private final GrupoRepository grupoRepository;
    private final CalificacionRepository calificacionRepository;

    public AlumnoController(
            AlumnoRepository alumnoRepository,
            UsuarioRepository usuarioRepository,
            GrupoRepository grupoRepository,
            CalificacionRepository calificacionRepository) {
        this.alumnoRepository = alumnoRepository;
        this.usuarioRepository = usuarioRepository;
        this.grupoRepository = grupoRepository;
        this.calificacionRepository = calificacionRepository;
    }

    @PostMapping
    public ResponseEntity<Alumno> crear(@RequestBody Alumno alumno) {
        if (alumno.getUsuario() != null && alumno.getUsuario().getId_usuario() != null) {
            Usuario usuario = usuarioRepository.findById(alumno.getUsuario().getId_usuario())
                    .orElseThrow(() -> new EntityNotFoundException("El usuario no existe"));
            alumno.setUsuario(usuario);
        }

        if (alumno.getGrupo() != null && alumno.getGrupo().getId_grupo() != null) {
            Grupo grupo = grupoRepository.findById(alumno.getGrupo().getId_grupo())
                    .orElseThrow(() -> new EntityNotFoundException("El grupo no existe"));
            alumno.setGrupo(grupo);
        }

        if (alumno.getEstado() == null || alumno.getEstado().isBlank()) {
            alumno.setEstado("ACTIVO");
        }

        Alumno creado = alumnoRepository.save(alumno);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping
    public ResponseEntity<List<Alumno>> listar() {
        return ResponseEntity.ok(alumnoRepository.findAll());
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Alumno>> activos() {
        List<Alumno> activos = alumnoRepository.findAll().stream()
                .filter(a -> "ACTIVO".equalsIgnoreCase(a.getEstado()))
                .toList();
        return ResponseEntity.ok(activos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alumno> obtenerPorId(@PathVariable Long id) {
        Alumno alumno = alumnoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El alumno no existe"));
        return ResponseEntity.ok(alumno);
    }

    @GetMapping("/{id}/promedio")
    public ResponseEntity<Map<String, Object>> promedio(@PathVariable Long id) {
        if (!alumnoRepository.existsById(id)) {
            throw new EntityNotFoundException("El alumno no existe");
        }

        List<Calificacion> calificaciones = calificacionRepository.findByAlumnoId(id);
        double promedio = calificaciones.stream()
                .mapToDouble(Calificacion::getCalificacion)
                .average()
                .orElse(0.0);

        return ResponseEntity.ok(Map.of(
                "alumnoId", id,
                "promedio", promedio,
                "totalCalificaciones", calificaciones.size()
        ));
    }

    @PatchMapping("/{id}/baja")
    public ResponseEntity<Alumno> darDeBaja(@PathVariable Long id) {
        Alumno alumno = alumnoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El alumno no existe"));
        alumno.setEstado("BAJA");
        return ResponseEntity.ok(alumnoRepository.save(alumno));
    }
}
