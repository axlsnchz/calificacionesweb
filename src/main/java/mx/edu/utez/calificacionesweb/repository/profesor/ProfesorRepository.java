package mx.edu.utez.calificacionesweb.repository.profesor;

import mx.edu.utez.calificacionesweb.model.profesor.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfesorRepository extends JpaRepository<Profesor,Long> {
}
