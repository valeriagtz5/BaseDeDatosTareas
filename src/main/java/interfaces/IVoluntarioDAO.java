/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import java.sql.SQLException;
import java.util.List;
import models.Voluntario;

/**
 *
 * @author valeria
 */
public interface IVoluntarioDAO {
    boolean insertar(Voluntario voluntario);
    boolean actualizar(Voluntario voluntario);
    boolean eliminar(int idVoluntario) throws SQLException; // THROWS SQLEXCPETION
    Voluntario obtenerPorId(int idVoluntario);
    List<Voluntario> obtenerTodos();
    List<Voluntario> obtenerTodosPorFiltroNombre(String nombre);
    List<Voluntario> obtenerTodosPorFiltroEspecialidad(String especialidad);
    List<Voluntario> obtenerTodosPorFiltroCorreo(String especialidad);
    // List<Voluntario> obtenerPorFiltroEspecialidad(Especialidad especialidad);
    // List<Voluntario> obtenerPorRefugio(int idRefugio);
}
