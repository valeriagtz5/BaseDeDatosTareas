/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import daos.ClienteDAO;
import daos.IClienteDAO;
import java.util.List;
import models.Cliente;

/**
 *
 * @author LABORATORIOS
 */
public class ClienteController implements IClienteController {

    private final IClienteDAO clienteDAO;
    
    public ClienteController(){
        this.clienteDAO = new ClienteDAO();
    }
    
    @Override
    public boolean agregar(String nombre, String direccion, String telefono) {
        if(nombre == null || nombre.trim().isEmpty()){
            System.err.println("El nombre del cliente no puede estar vacio");
            return false;
        }
        
        if(direccion == null || direccion.trim().isEmpty()){
            direccion = "";
        }
        if(telefono == null || telefono.trim().isEmpty()){
            telefono = "";
        }
        
        Cliente cliente = new Cliente();
        cliente.setNombre(nombre.trim());
        cliente.setDireccion(direccion.trim());
        cliente.setTelefono(telefono.trim());
        
        return clienteDAO.insertar(cliente);
    }
    
    public Cliente obtenerCliente(int idCliente) {
        if(idCliente <= 0) {
            System.err.println("ID de cliente invalido");
            return null;
        }
        return clienteDAO.obtenerPorId(idCliente);
    }
    
    public List<Cliente> listarClientes() {
        return clienteDAO.obtenerTodos();
    }
    
    public boolean actualizarCliente(int idCliente, String nombre, String direccion, String telefono){
        if(idCliente <= 0) {
            System.err.println("ID de cliente invalido");
            return false;
        }
        
        if(nombre == null || nombre.trim().isEmpty()) {
            System.out.println("El nombre del cliente no puede estar vacio");
            return false;
        }
        
        if(direccion == null || direccion.trim().isEmpty()){
            direccion = "";
        }
        if(telefono == null || telefono.trim().isEmpty()){
            telefono = "";
        }
        
        Cliente cliente = new Cliente();
        cliente.setIdCliente(idCliente);
        cliente.setNombre(nombre.trim());
        cliente.setDireccion(direccion.trim());
        cliente.setTelefono(telefono.trim());
        
        return clienteDAO.actualizar(cliente);
    }
    
    public boolean eliminarCliente(int idCliente) {
        if(idCliente <= 0) {
            System.err.println("ID de cliente invalido");
            return false;
        }
        return clienteDAO.eliminar(idCliente);
    }
    
}
