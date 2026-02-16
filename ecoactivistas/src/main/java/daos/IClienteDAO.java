/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package daos;

import java.util.List;
import models.Cliente;

/**
 *
 * @author LABORATORIOS
 */
public interface IClienteDAO {
    
    boolean insertar(Cliente cliente);
    Cliente obtenerPorId(int id);
    List<Cliente> obtenerTodos();
    boolean actualizar(Cliente cliente);
    boolean eliminar(int idCliente); // Rara vez se usa, mejor dar de baja
}
