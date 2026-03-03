/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import daos.AnimalDAO;
import daos.AsignacionDAO;
import daos.VoluntarioDAO;
import enums.Actividad;
import interfaces.IAnimalDAO;
import interfaces.IAsignacionDAO;
import interfaces.IVoluntarioDAO;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import models.Animal;
import models.Asignacion;
import models.Voluntario;

/**
 *
 * @author valeria
 */
public class AsignacionController {
    private final IAsignacionDAO asignacionDAO;
    private final IAnimalDAO animalDAO;
    private final IVoluntarioDAO voluntarioDAO;

    public AsignacionController() {
        this.asignacionDAO = new AsignacionDAO();
        this.animalDAO = new AnimalDAO();
        this.voluntarioDAO = new VoluntarioDAO();
    }

    public boolean agregarAsignacion(Date fecha, Actividad actividad, int idAnimal, int idVoluntario) {
        // Validaciones básicas
        if (fecha == null) {
            System.err.println("La fecha es obligatoria.");
            return false;
        }
        if (actividad == null) {
            System.err.println("La actividad es obligatoria.");
            return false;
        }
        if (idAnimal <= 0 || idVoluntario <= 0) {
            System.err.println("Debe seleccionar un animal y un voluntario válidos.");
            return false;
        }

        Asignacion asignacion = new Asignacion();
        asignacion.setFecha(fecha);
        asignacion.setActividad(actividad);
        asignacion.setIdAnimal(idAnimal);
        asignacion.setIdVoluntario(idVoluntario);

        return asignacionDAO.insertar(asignacion);
    }

    public boolean editarAsignacion(int idAsignacion, Date fecha, Actividad actividad, int idAnimal, int idVoluntario) {
        if (idAsignacion <= 0) return false;

        Asignacion asignacion = new Asignacion();
        asignacion.setIdAsignacion(idAsignacion);
        asignacion.setFecha(fecha);
        asignacion.setActividad(actividad);
        asignacion.setIdAnimal(idAnimal);
        asignacion.setIdVoluntario(idVoluntario);

        return asignacionDAO.actualizar(asignacion);
    }

    public boolean eliminarAsignacion(int idAsignacion) throws SQLException {
        if (idAsignacion <= 0) return false;
        return asignacionDAO.eliminar(idAsignacion);
    }

    public DefaultTableModel obtenerTablaAsignaciones(int id) {
        String[] columnas = {"ID", "FECHA", "ACTIVIDAD", "ID_ANI", "ANIMAL", "ID_VOL", "VOLUNTARIO"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);

        List<Object[]> datos = asignacionDAO.obtenerDatosParaTablaAsignaciones(id);
        for (Object[] fila : datos) {
            modelo.addRow(fila);
        }
        return modelo;
    }
    
    public DefaultTableModel obtenerTablaAsignacionesFiltroActividad(String actividad, int id) {
        // Las mismas 7 columnas que definimos para la tabla general
        String[] columnas = {"ID", "FECHA", "ACTIVIDAD", "ID ANI", "ANIMAL", "ID VOL", "VOLUNTARIO"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);

        // Llamamos al DAO
        List<Object[]> datos = asignacionDAO.obtenerAsignacionesPorFiltroActividad(actividad, id);

        // Llenamos el modelo
        for (Object[] fila : datos) {
            modelo.addRow(fila);
        }

        return modelo;
    }
    
    public DefaultTableModel obtenerTablaAsignacionesFiltroVoluntario(String vol, int id) {
        // Las mismas 7 columnas que definimos para la tabla general
        String[] columnas = {"ID", "FECHA", "ACTIVIDAD", "ID ANI", "ANIMAL", "ID VOL", "VOLUNTARIO"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);

        // Llamamos al DAO
        List<Object[]> datos = asignacionDAO.obtenerAsignacionesPorFiltroVoluntario(vol, id);

        // Llenamos el modelo
        for (Object[] fila : datos) {
            modelo.addRow(fila);
        }

        return modelo;
    }
    
    public DefaultTableModel obtenerTablaAsignacionesFiltroAnimal(String animal, int id) {
        // Las mismas 7 columnas que definimos para la tabla general
        String[] columnas = {"ID", "FECHA", "ACTIVIDAD", "ID ANI", "ANIMAL", "ID VOL", "VOLUNTARIO"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);

        // Llamamos al DAO
        List<Object[]> datos = asignacionDAO.obtenerAsignacionesPorFiltroAnimal(animal, id);

        // Llenamos el modelo
        for (Object[] fila : datos) {
            modelo.addRow(fila);
        }

        return modelo;
    }

    public List<Asignacion> listarAsignaciones(int idRefugio) {
        return asignacionDAO.obtenerTodos(idRefugio);
    }
}
