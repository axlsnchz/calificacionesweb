package mx.edu.utez.calificacionesweb.repository.calificacion;

import mx.edu.utez.calificacionesweb.model.alumno.Alumno;
import mx.edu.utez.calificacionesweb.model.calificacion.Calificacion;
import mx.edu.utez.calificacionesweb.model.profesor.Profesor;
import mx.edu.utez.calificacionesweb.model.unidad.Unidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {

    List<Calificacion> findByAlumno(Alumno alumno);

    List<Calificacion> findByUnidad(Unidad unidad);

    List<Calificacion> findByProfesor(Profesor profesor);

    Optional<Calificacion> findByAlumnoAndUnidad(Alumno alumno, Unidad unidad);

    boolean existsByAlumnoAndUnidad(Alumno alumno, Unidad unidad);
}