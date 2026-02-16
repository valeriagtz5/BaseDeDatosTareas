/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package daos;

import java.util.List;
import models.Activista;

/**
 *
 * @author valeria
 */
public interface IActivistaDAO {
    boolean insertar(Activista activista);
    Activista obtenerPorId(int idActivista);
    List<Activista> obtenerTodos();
    boolean actualizar(Activista activista);
    boolean eliminar(int idActivista); // Rara vez se usa, mejor dar de baja

}
