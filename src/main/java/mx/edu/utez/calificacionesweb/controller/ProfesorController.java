package mx.edu.utez.calificacionesweb.controller;

import jakarta.persistence.EntityNotFoundException;
import mx.edu.utez.calificacionesweb.model.profesor.Profesor;
import mx.edu.utez.calificacionesweb.model.usuario.Usuario;
import mx.edu.utez.calificacionesweb.repository.profesor.ProfesorRepository;
import mx.edu.utez.calificacionesweb.repository.usuario.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profesores")
@CrossOrigin(origins = "*")
public class ProfesorController {

    private final ProfesorRepository profesorRepository;
    private final UsuarioRepository usuarioRepository;

    public ProfesorController(ProfesorRepository profesorRepository, UsuarioRepository usuarioRepository) {
        this.profesorRepository = profesorRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    public ResponseEntity<Profesor> crear(@RequestBody Profesor profesor) {
        if (profesor.getUsuario() == null || profesor.getUsuario().getId_usuario() == null) {
            throw new IllegalStateException("El usuario es obligatorio");
        }

        Usuario usuario = usuarioRepository.findById(profesor.getUsuario().getId_usuario())
                .orElseThrow(() -> new EntityNotFoundException("El usuario no existe"));
        profesor.setUsuario(usuario);

        Profesor creado = profesorRepository.save(profesor);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping
    public ResponseEntity<List<Profesor>> listar() {
        return ResponseEntity.ok(profesorRepository.findAll());
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Profesor>> activos() {
        List<Profesor> activos = profesorRepository.findAll().stream()
                .filter(p -> p.getUsuario() != null && p.getUsuario().isActivo())
                .toList();
        return ResponseEntity.ok(activos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profesor> obtenerPorId(@PathVariable Long id) {
        Profesor profesor = profesorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El profesor no existe"));
        return ResponseEntity.ok(profesor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Profesor> actualizar(@PathVariable Long id, @RequestBody Profesor profesor) {
        Profesor existente = profesorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El profesor no existe"));

        existente.setNum_empleado(profesor.getNum_empleado());
        existente.setEspecialidad(profesor.getEspecialidad());

        if (profesor.getUsuario() != null && profesor.getUsuario().getId_usuario() != null) {
            Usuario usuario = usuarioRepository.findById(profesor.getUsuario().getId_usuario())
                    .orElseThrow(() -> new EntityNotFoundException("El usuario no existe"));
            existente.setUsuario(usuario);
        }

        return ResponseEntity.ok(profesorRepository.save(existente));
    }

    @PatchMapping("/{id}/baja")
    public ResponseEntity<Profesor> darDeBaja(@PathVariable Long id) {
        Profesor profesor = profesorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El profesor no existe"));

        if (profesor.getUsuario() != null) {
            profesor.getUsuario().setActivo(false);
            usuarioRepository.save(profesor.getUsuario());
        }

        return ResponseEntity.ok(profesorRepository.save(profesor));
    }
}
