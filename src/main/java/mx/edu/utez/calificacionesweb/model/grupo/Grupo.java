package mx.edu.utez.calificacionesweb.model.grupo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.calificacionesweb.model.generacion.Generacion;

@Entity
@Table(name = "grupo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_grupo;

    private String letra;

    private Integer cuatrimestre_actual;

    @Column(length = 30)
    private String estado;

    @ManyToOne
    @JoinColumn(name = "FK_id_generacion")
    private Generacion generacion;
}