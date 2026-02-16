/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package daos;

import java.util.List;
import models.Problema;

/**
 *
 * @author valeria
 */
public interface IProblemaDAO {
    boolean insertar(Problema problema);
    Problema obtenerPorId(int idProblema);
    List<Problema> obtenerTodos();
    boolean actualizar(Problema problema);
    boolean eliminar(int idProblema); // Rara vez se usa, mejor dar de baja
}
