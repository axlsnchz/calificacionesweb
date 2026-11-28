package mx.edu.utez.calificacionesweb.service;

import jakarta.persistence.EntityNotFoundException;
import mx.edu.utez.calificacionesweb.model.carrera.Carrera;
import mx.edu.utez.calificacionesweb.model.generacion.Generacion;
import mx.edu.utez.calificacionesweb.repository.carrera.CarreraRepository;
import mx.edu.utez.calificacionesweb.repository.generacion.GeneracionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GeneracionService {

    private final GeneracionRepository generacionRepository;
    private final CarreraRepository carreraRepository;

    public GeneracionService(GeneracionRepository generacionRepository,
                             CarreraRepository carreraRepository) {
        this.generacionRepository = generacionRepository;
        this.carreraRepository = carreraRepository;
    }


    @Transactional
    public Generacion crear(Generacion generacion) {
        if (generacion.getCarrera() == null || generacion.getCarrera().getId() == null) {
            throw new IllegalArgumentException("La carrera es obligatoria");
        }
        if (generacion.getNombre() == null || generacion.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre de la generación es obligatorio");
        }
        if (generacion.getFechaInicio() == null) {
            throw new IllegalArgumentException("La fecha de inicio es obligatoria");
        }

        Long carreraId = generacion.getCarrera().getId();
        Carrera carrera = carreraRepository.findById(carreraId)
                .orElseThrow(() -> new EntityNotFoundException("La carrera con ID " + carreraId + " no existe"));

        Generacion nueva = new Generacion();
        nueva.setNombre(generacion.getNombre().trim());
        nueva.setFechaInicio(generacion.getFechaInicio());
        nueva.setCarrera(carrera);
        return generacionRepository.save(nueva);
    }

    @Transactional(readOnly = true)
    public List<Generacion> listar() {
        return generacionRepository.findAll();
    }
}