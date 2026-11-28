package mx.edu.utez.calificacionesweb.service;


import jakarta.persistence.EntityNotFoundException;
import mx.edu.utez.calificacionesweb.model.alumno.Alumno;
import mx.edu.utez.calificacionesweb.model.calificacion.Calificacion;
import mx.edu.utez.calificacionesweb.model.materia.Materia;
import mx.edu.utez.calificacionesweb.model.profesor.Profesor;
import mx.edu.utez.calificacionesweb.model.unidad.Unidad;
import mx.edu.utez.calificacionesweb.repository.alumno.AlumnoRepository;
import mx.edu.utez.calificacionesweb.repository.calificacion.CalificacionRepository;
import mx.edu.utez.calificacionesweb.repository.profesor.ProfesorRepository;
import mx.edu.utez.calificacionesweb.repository.unidad.UnidadRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CalificacionService {

    private final CalificacionRepository calificacionRepository;
    private final AlumnoRepository alumnoRepository;
    private final UnidadRepository unidadRepository;
    private final ProfesorRepository profesorRepository;

    public CalificacionService(
            CalificacionRepository calificacionRepository,
            AlumnoRepository alumnoRepository,
            UnidadRepository unidadRepository,
            ProfesorRepository profesorRepository) {
        this.calificacionRepository = calificacionRepository;
        this.alumnoRepository = alumnoRepository;
        this.unidadRepository = unidadRepository;
        this.profesorRepository = profesorRepository;
    }

    @Transactional
    public Calificacion registrar(Long alumnoId, Long unidadId, Long profesorId, double nota) {
        Alumno alumno = alumnoRepository.findById(alumnoId).orElse(null);
        if (alumno == null) {
            throw new EntityNotFoundException("El alumno no existe");
        }

        Unidad unidad = unidadRepository.findById(unidadId).orElse(null);
        if (unidad == null) {
            throw new EntityNotFoundException("La unidad no existe");
        }

        Profesor profesor = profesorRepository.findById(profesorId).orElse(null);
        if (profesor == null) {
            throw new EntityNotFoundException("El profesor no existe");
        }

        if (!"ACTIVO".equalsIgnoreCase(alumno.getEstado())) {
            throw new IllegalStateException("El alumno no está activo");
        }

        if (profesor.getUsuario() == null || !profesor.getUsuario().isActivo()) {
            throw new IllegalStateException("El profesor no está activo");
        }

        Materia materia = unidad.getMateria();
        if (materia.getProfesor() == null || !materia.getProfesor().getId_profesor().equals(profesor.getId_profesor())) {
            throw new IllegalStateException("El profesor no es el titular asignado a la materia");
        }

        if (alumno.getGrupo() == null
                || alumno.getGrupo().getGeneracion() == null
                || alumno.getGrupo().getGeneracion().getCarrera() == null
                || !materia.getCarrera().getId().equals(alumno.getGrupo().getGeneracion().getCarrera().getId())) {
            throw new IllegalStateException("La carrera de la materia no coincide con la carrera del grupo/generación del alumno");
        }

        validarNota(nota);
        validarCuatrimestreActual(alumno, materia);

        if (calificacionRepository.existsByAlumnoIdAndUnidadId(alumnoId, unidadId)) {
            throw new IllegalStateException("El alumno ya tiene calificación registrada en esta unidad; debe usarse la modificación");
        }

        Calificacion calificacion = new Calificacion();
        calificacion.setAlumno(alumno);
        calificacion.setUnidad(unidad);
        calificacion.setProfesor(profesor);
        calificacion.setCalificacion(nota);
        calificacion.setResultado(nota >= 7.0 ? "APROBADO" : "REPROBADO");

        return calificacionRepository.save(calificacion);
    }

    private void validarNota(double nota) {
        if (nota < 0 || nota > 10) {
            throw new IllegalArgumentException("La calificaci\u00f3n debe estar entre 0 y 10");
        }
    }

    private void validarCuatrimestreActual(Alumno alumno, Materia materia) {
        if (alumno.getGrupo() == null
                || alumno.getGrupo().getCuatrimestre_actual() != materia.getCuatrimestre()) {
            throw new IllegalStateException("Solo se puede calificar materias del cuatrimestre actual del grupo");
        }
    }

    @Transactional
    public Calificacion modificar(Long id, Long profesorId, double nuevaNota) {
        Calificacion calificacion = calificacionRepository.findById(id).orElse(null);
        if (calificacion == null) {
            throw new EntityNotFoundException("La calificación no existe");
        }

        Profesor profesor = profesorRepository.findById(profesorId).orElse(null);
        if (profesor == null) {
            throw new EntityNotFoundException("El profesor no existe");
        }

        if (!"ACTIVO".equalsIgnoreCase(calificacion.getAlumno().getEstado())) {
            throw new IllegalStateException("El alumno no está activo");
        }

        if (profesor.getUsuario() == null || !profesor.getUsuario().isActivo()) {
            throw new IllegalStateException("El profesor no está activo");
        }

        Materia materia = calificacion.getUnidad().getMateria();
        if (materia.getProfesor() == null || !materia.getProfesor().getId_profesor().equals(profesor.getId_profesor())) {
            throw new IllegalStateException("El profesor no imparte la materia correspondiente");
        }

        validarNota(nuevaNota);
        validarCuatrimestreActual(calificacion.getAlumno(), materia);

        calificacion.setCalificacion(nuevaNota);
        calificacion.setProfesor(profesor);
        calificacion.setFecha_modificacion(LocalDateTime.now());
        calificacion.setResultado(nuevaNota >= 7.0 ? "APROBADO" : "REPROBADO");

        return calificacionRepository.save(calificacion);
    }

    @Transactional(readOnly = true)
    public Calificacion detalle(Long id) {
        Calificacion calificacion = calificacionRepository.findById(id).orElse(null);
        if (calificacion == null) {
            throw new EntityNotFoundException("La calificación no existe");
        }

        if (calificacion.getCalificacion() >= 7.0) {
            calificacion.setResultado("APROBADO");
        } else {
            calificacion.setResultado("REPROBADO");
        }

        return calificacion;
    }

    @Transactional(readOnly = true)
    public List<Calificacion> listarPorAlumno(Long alumnoId) {
        if (!alumnoRepository.existsById(alumnoId)) {
            throw new EntityNotFoundException("El alumno no existe");
        }

        List<Calificacion> lista = calificacionRepository.findByAlumnoId(alumnoId);
        for (Calificacion c : lista) {
            c.setResultado(c.getCalificacion() >= 7.0 ? "APROBADO" : "REPROBADO");
        }
        return lista;
    }

    @Transactional(readOnly = true)
    public List<Calificacion> listarPorUnidad(Long unidadId) {
        if (!unidadRepository.existsById(unidadId)) {
            throw new EntityNotFoundException("La unidad no existe");
        }

        List<Calificacion> lista = calificacionRepository.findByUnidadId(unidadId);
        for (Calificacion c : lista) {
            c.setResultado(c.getCalificacion() >= 7.0 ? "APROBADO" : "REPROBADO");
        }
        return lista;
    }

    @Transactional(readOnly = true)
    public List<Calificacion> listarPorResultado(String resultado) {
        if ("APROBADO".equalsIgnoreCase(resultado)) {
            List<Calificacion> aprobadas = calificacionRepository.findByCalificacionGreaterThanEqual(7.0);
            for (Calificacion c : aprobadas) {
                c.setResultado("APROBADO");
            }
            return aprobadas;
        }

        if ("REPROBADO".equalsIgnoreCase(resultado)) {
            List<Calificacion> reprobadas = calificacionRepository.findByCalificacionLessThan(7.0);
            for (Calificacion c : reprobadas) {
                c.setResultado("REPROBADO");
            }
            return reprobadas;
        }

        throw new IllegalArgumentException("El resultado debe ser APROBADO o REPROBADO");
    }
}
