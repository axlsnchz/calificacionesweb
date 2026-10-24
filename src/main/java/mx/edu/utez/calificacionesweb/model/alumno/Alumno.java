package mx.edu.utez.calificacionesweb.model.alumno;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.calificacionesweb.model.usuario.Usuario;
import mx.edu.utez.calificacionesweb.model.grupo.Grupo;

@Entity
@Table(name = "alumno")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_alumno;

    @Column(length = 20)
    private String matricula;

    @Column(length = 20)
    private String telefono;

    private String estado;

    @OneToOne
    @JoinColumn(name = "FK_id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "FK_id_grupo")
    private Grupo grupo;
}