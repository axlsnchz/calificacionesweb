package mx.edu.utez.calificacionesweb.service;

import jakarta.persistence.EntityNotFoundException;
import mx.edu.utez.calificacionesweb.excepcion.GlobalExceptionHandler;
import mx.edu.utez.calificacionesweb.model.materia.Materia;
import mx.edu.utez.calificacionesweb.model.unidad.Unidad;
import mx.edu.utez.calificacionesweb.repository.materia.MateriaRepository;
import mx.edu.utez.calificacionesweb.repository.unidad.UnidadRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UnidadService {

    private final UnidadRepository unidadRepository;
    private final MateriaRepository materiaRepository;

    public UnidadService(UnidadRepository unidadRepository, MateriaRepository materiaRepository) {
        this.unidadRepository = unidadRepository;
        this.materiaRepository = materiaRepository;
    }

    @Transactional
    public Unidad crear(Long materiaId, Unidad unidad) {
        Materia materia = materiaRepository.findById(materiaId)
                .orElseThrow(() -> new IllegalArgumentException("La materia con ID " + materiaId + " no existe"));
        if (unidadRepository.existsByMateriaIdAndNumero(materiaId, unidad.getNumero())) {
            throw new EntityNotFoundException("Ya existe una unidad con el número " + unidad.getNumero() + " en esta materia");
        }

        unidad.setMateria(materia);
        return unidadRepository.save(unidad);
    }

    @Transactional(readOnly = true)
    public List<Unidad> listarPorMateria(Long materiaId) {
        if (!materiaRepository.existsById(materiaId)) {
            throw new EntityNotFoundException("La materia con ID " + materiaId + " no existe");
        }

        return unidadRepository.findByMateriaIdOrderByNumeroAsc(materiaId);
    }
}