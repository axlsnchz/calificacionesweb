package mx.edu.utez.calificacionesweb.model.generacion;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.calificacionesweb.model.carrera.Carrera;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Generacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id_generacion;

    @Column(length = 50)
    private  String nombre;

    private LocalDate fecha_inicio;

    @ManyToOne
    @JoinColumn(name = "FK_id_carrera")
    private Carrera carrera;
}
