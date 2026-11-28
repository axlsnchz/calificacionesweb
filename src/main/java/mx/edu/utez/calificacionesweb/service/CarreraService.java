package mx.edu.utez.calificacionesweb.service;

import mx.edu.utez.calificacionesweb.model.carrera.Carrera;
import mx.edu.utez.calificacionesweb.repository.carrera.CarreraRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
public class CarreraService {

    private final CarreraRepository carreraRepository;

    public CarreraService(CarreraRepository carreraRepository) {
        this.carreraRepository = carreraRepository;
    }

    @Transactional
    public Carrera crear(Carrera carrera) {
        if (carrera.getNombre() == null || carrera.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre de la carrera es obligatorio");
        }
        if (carrera.getClave() == null || carrera.getClave().isBlank()) {
            throw new IllegalArgumentException("La clave de la carrera es obligatoria");
        }
        if (carrera.getNumCuatrimestres() <= 0) {
            throw new IllegalArgumentException("El número de cuatrimestres debe ser mayor a 0");
        }

        String clave = normalizarClave(carrera.getClave());
        if (carreraRepository.existsByClave(clave)) {
            throw new IllegalStateException("Ya existe una carrera con la clave " + clave);
        }

        Carrera nueva = new Carrera();
        nueva.setNombre(carrera.getNombre().trim());
        nueva.setClave(clave);
        nueva.setNumCuatrimestres(carrera.getNumCuatrimestres());
        return carreraRepository.save(nueva);
    }

    @Transactional(readOnly = true)
    public List<Carrera> listar() {
        return carreraRepository.findAll();
    }

    private String normalizarClave(String clave) {
        return clave.replaceAll("\\s+", "").toUpperCase(Locale.ROOT);
    }
}