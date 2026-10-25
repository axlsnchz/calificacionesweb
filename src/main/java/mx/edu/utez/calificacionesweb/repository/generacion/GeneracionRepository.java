package mx.edu.utez.calificacionesweb.repository.generacion;

import mx.edu.utez.calificacionesweb.model.generacion.Generacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneracionRepository extends JpaRepository <Generacion, Long> {
}
