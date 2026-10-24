package mx.edu.utez.calificacionesweb.repository.carrera;

import mx.edu.utez.calificacionesweb.model.carrera.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarreraRepository extends JpaRepository<Carrera, Long> {
}
