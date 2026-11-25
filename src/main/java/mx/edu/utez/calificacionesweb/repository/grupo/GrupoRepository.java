package mx.edu.utez.calificacionesweb.repository.grupo;

import mx.edu.utez.calificacionesweb.model.generacion.Generacion;
import mx.edu.utez.calificacionesweb.model.grupo.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {

    List<Grupo> findByGeneracion(Generacion generacion);

    List<Grupo> findByEstado(String estado);

    Optional<Grupo> findByGeneracionAndLetra(Generacion generacion, String letra);

    boolean existsByGeneracionAndLetra(Generacion generacion, String letra);
}