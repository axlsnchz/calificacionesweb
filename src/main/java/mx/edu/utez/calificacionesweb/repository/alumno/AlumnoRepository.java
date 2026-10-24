package mx.edu.utez.calificacionesweb.repository.alumno;

import mx.edu.utez.calificacionesweb.model.alumno.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
public interface AlumnoRepository extends JpaRepository<Alumno,Long> {


}
