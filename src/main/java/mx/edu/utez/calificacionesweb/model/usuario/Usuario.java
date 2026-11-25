package mx.edu.utez.calificacionesweb.model.usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "Usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuario;

    @Column(length = 80)
    private String nombre;

    @Column(length = 100)
    private String apellidos;

    @Column(length = 120)
    private String correo;

    @Column(length = 12)
    private String contraseña;

    @Enumerated(EnumType.STRING)
    private Roles rol;

    private boolean activo;

    private LocalDate fecha_registro;
}
