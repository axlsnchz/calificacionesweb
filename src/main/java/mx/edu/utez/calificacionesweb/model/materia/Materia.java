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
    Long id_Materia;

    @Column(length = 120, nullable = false)
    private String nombre;

    @Column(length = 20, nullable = false, unique = true)
    private String clave;

    @Column(nullable = false)
    private Integer cuatrimestre;

    @Column(nullable = false)
    private Integer creditos;

    @ManyToOne
    @JoinColumn(name = "id_carrera", nullable = false)
    private Carrera carrera;

    @ManyToOne
    @JoinColumn(name = "id_profesor")
    private Profesor profesor;
}

