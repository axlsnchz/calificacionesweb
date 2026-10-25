package mx.edu.utez.calificacionesweb.model.profesor;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.calificacionesweb.model.usuario.Usuario;

@Entity
@Table(name = "Profesor")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Profesor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_profesor;

    @Column(name = "num_empleado", length = 20, nullable = false, unique = true)
    private String numEmpleado;

    @Column(length = 100)
    private String especialidad;

    @OneToOne
    @JoinColumn(name = "id_usuario", nullable = false, unique = true)
    private Usuario usuario;
}
