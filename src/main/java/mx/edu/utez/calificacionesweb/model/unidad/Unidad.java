package mx.edu.utez.calificacionesweb.model.unidad;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.calificacionesweb.model.materia.Materia;

@Entity
@Table(name = "unidad")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Unidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_unidad;

    @Column(nullable = false)
    private Integer numero;

    @Column(length = 120, nullable = false)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "id_materia", nullable = false)
    private Materia materia;
}