package mx.edu.utez.calificacionesweb.repository.unidad;

import mx.edu.utez.calificacionesweb.model.materia.Materia;
import mx.edu.utez.calificacionesweb.model.unidad.Unidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnidadRepository extends JpaRepository<Unidad,Long> {
    List<Unidad> findByMateriaOrderByNumeroAsc(Materia materia);

    boolean existsByMateriaAndNumero(Materia materia, Integer numero);
}
