/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import java.sql.SQLException;
import java.util.List;
import models.Asignacion;

/**
 *
 * @author valeria
 */
public interface IAsignacionDAO {
    boolean insertar(Asignacion asignacion);
    boolean actualizar(Asignacion asignacion);
    boolean eliminar(int idAsignacion) throws SQLException; // THROWS SQLEXCPETION
    Asignacion obtenerPorId(int idAsignacion);
    List<Asignacion> obtenerTodos(int idRefugioActual);

    public List<Object[]> obtenerDatosParaTablaAsignaciones(int idRefugio);

    public List<Object[]> obtenerAsignacionesPorFiltroActividad(String actividad,int id);
    List<Object[]> obtenerAsignacionesPorFiltroVoluntario(String nombreVoluntario,int id);
    List<Object[]> obtenerAsignacionesPorFiltroAnimal(String animal,int id);
}
