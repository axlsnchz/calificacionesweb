package mx.edu.utez.calificacionesweb.repository.usuario;

import mx.edu.utez.calificacionesweb.model.usuario.Roles;
import mx.edu.utez.calificacionesweb.model.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByCorreo(String correo);

    boolean existsByCorreo(String correo);

    List<Usuario> findByRol(Roles rol);
}
