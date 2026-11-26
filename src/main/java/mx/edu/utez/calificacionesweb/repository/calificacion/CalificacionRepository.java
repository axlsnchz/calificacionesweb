package mx.edu.utez.calificacionesweb.repository.calificacion;

import mx.edu.utez.calificacionesweb.model.alumno.Alumno;
import mx.edu.utez.calificacionesweb.model.calificacion.Calificacion;
import mx.edu.utez.calificacionesweb.model.profesor.Profesor;
import mx.edu.utez.calificacionesweb.model.unidad.Unidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query("SELECT COUNT(c) > 0 FROM Calificacion c WHERE c.alumno.id_alumno = :alumnoId AND c.unidad.id_unidad = :unidadId")
    boolean existsByAlumnoIdAndUnidadId(@Param("alumnoId") Long alumnoId, @Param("unidadId") Long unidadId);

    @Query("SELECT c FROM Calificacion c WHERE c.alumno.id_alumno = :alumnoId")
    List<Calificacion> findByAlumnoId(@Param("alumnoId") Long alumnoId);

    @Query("SELECT c FROM Calificacion c WHERE c.unidad.id_unidad = :unidadId")
    List<Calificacion> findByUnidadId(@Param("unidadId") Long unidadId);

    List<Calificacion> findByCalificacionGreaterThanEqual(double calificacion);

    List<Calificacion> findByCalificacionLessThan(double calificacion);
}