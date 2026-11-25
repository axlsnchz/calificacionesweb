package mx.edu.utez.calificacionesweb.model.materia;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.calificacionesweb.model.carrera.Carrera;
import mx.edu.utez.calificacionesweb.model.profesor.Profesor;

@Entity
@Table(name = "materia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Materia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_materia;

    @Column(length = 120, nullable = false)
    private String nombre;

    @Column(nullable = false)
    private int cuatrimestre;

    @Column(nullable = false)
    private int creditos;

    @ManyToOne
    @JoinColumn(name = "id_carrera", nullable = false)
    private Carrera carrera;

    @ManyToOne
    @JoinColumn(name = "id_profesor")
    private Profesor profesor;
}

