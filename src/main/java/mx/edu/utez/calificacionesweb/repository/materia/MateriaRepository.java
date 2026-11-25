package mx.edu.utez.calificacionesweb.repository.materia;

import mx.edu.utez.calificacionesweb.model.profesor.Profesor;
import mx.edu.utez.calificacionesweb.model.materia.Materia;
import mx.edu.utez.calificacionesweb.model.carrera.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MateriaRepository extends JpaRepository<Materia,Long> {

    Optional<Materia> findByClave(String clave);

    boolean existsByClave(String clave);

    List<Materia> findByCarrera(Carrera carrera);

    List<Materia> findByProfesor(Profesor profesor);
}
