package mx.edu.utez.calificacionesweb.repository.unidad;

import mx.edu.utez.calificacionesweb.model.materia.Materia;
import mx.edu.utez.calificacionesweb.model.unidad.Unidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnidadRepository extends JpaRepository<Unidad,Long> {
    List<Unidad> findByMateriaOrderByNumeroAsc(Materia materia);

    boolean existsByMateriaAndNumero(Materia materia, Integer numero);

    @Query("SELECT COUNT(u) > 0 FROM Unidad u WHERE u.materia.id_materia = :materiaId AND u.numero = :numero")
    boolean existsByMateriaIdAndNumero(@Param("materiaId") Long materiaId, @Param("numero") Integer numero);

    @Query("SELECT u FROM Unidad u WHERE u.materia.id_materia = :materiaId ORDER BY u.numero ASC")
    List<Unidad> findByMateriaIdOrderByNumeroAsc(@Param("materiaId") Long materiaId);

}
