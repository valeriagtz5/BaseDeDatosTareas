/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.util.List;
import models.Cliente;

/**
 *
 * @author LABORATORIOS
 */
public interface IClienteController {
    boolean agregar(String nombre, String direccion, String telefono);

    Cliente obtenerCliente(int idCliente);

    List<Cliente> listarClientes();

    boolean actualizarCliente(int idCliente, String nombre, String direccion, String telefono);

    boolean eliminarCliente(int idCliente);
}
