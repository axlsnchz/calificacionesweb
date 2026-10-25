package mx.edu.utez.calificacionesweb.repository.profesor;

import mx.edu.utez.calificacionesweb.model.profesor.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfesorRepository extends JpaRepository<Profesor,Long> {
    Optional<Profesor> findByNumEmpleado(String numEmpleado);

    boolean existsByNumEmpleado(String numEmpleado);
}
