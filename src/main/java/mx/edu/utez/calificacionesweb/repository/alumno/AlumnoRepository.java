package mx.edu.utez.calificacionesweb.repository.alumno;

import mx.edu.utez.calificacionesweb.model.alumno.Alumno;
import mx.edu.utez.calificacionesweb.model.grupo.Grupo;
import mx.edu.utez.calificacionesweb.model.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

    Optional<Alumno> findByMatricula(String matricula);

    boolean existsByMatricula(String matricula);

    Optional<Alumno> findByUsuario(Usuario usuario);

    List<Alumno> findByGrupo(Grupo grupo);
}