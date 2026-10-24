package mx.edu.utez.calificacionesweb.repository.usuario;

import mx.edu.utez.calificacionesweb.model.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
