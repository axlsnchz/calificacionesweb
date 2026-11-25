package mx.edu.utez.calificacionesweb.model.calificacion;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.calificacionesweb.model.alumno.Alumno;
import mx.edu.utez.calificacionesweb.model.profesor.Profesor;
import mx.edu.utez.calificacionesweb.model.unidad.Unidad;

import java.time.LocalDateTime;

@Entity
@Table(name = "calificacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_calificacion;

    @Column(nullable = false)
    private double calificacion;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fecha_registro;

    private LocalDateTime fecha_modificacion;

    @ManyToOne
    @JoinColumn(name = "FK_id_alumno", nullable = false)
    private Alumno alumno;

    @ManyToOne
    @JoinColumn(name = "FK_id_unidad", nullable = false)
    private Unidad unidad;

    @ManyToOne
    @JoinColumn(name = "FK_id_profesor", nullable = false)
    private Profesor profesor;

    @PrePersist
    protected void alRegistrar() {
        fecha_registro = LocalDateTime.now();
    }

    @PreUpdate
    protected void alModificar() {
        fecha_modificacion = LocalDateTime.now();
    }
}