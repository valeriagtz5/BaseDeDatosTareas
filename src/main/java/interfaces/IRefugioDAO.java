/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import java.sql.SQLException;
import java.util.List;
import models.Refugio;

/**
 *
 * @author valeria
 */
public interface IRefugioDAO {
    boolean insertar(Refugio refugio);
    boolean actualizar(Refugio refugio);
    boolean eliminar(int idRefugio) throws SQLException; // THROWS SQLEXCPETION
    Refugio obtenerPorId(int idRefugio);
    List<Refugio> obtenerTodos();
    List<Refugio> obtenerTodosPorFiltroNombre(String nombre);
    List<Refugio> obtenerTodosPorFiltroUbi(String ubi);
    List<Refugio> obtenerTodosPorFiltroResp(String r);
    List<Refugio> obtenerTodosAcceder();
}
