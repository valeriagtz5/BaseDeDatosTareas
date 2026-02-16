/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.ecoactivistas;

import config.ConexionDB;
import controllers.ActivistaController;
import controllers.ClienteController;
import controllers.ProblemaController;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import models.Activista;
import models.Cliente;
import models.Problema;

/**
 *
 * @author LABORATORIOS
 */
public class Ecoactivistas {

    public static void main(String[] args) throws SQLException {
        
        try(Connection con = ConexionDB.getConnection()) {
            System.out.println("Conexion establecida con exito: "+con);
        } catch(SQLException e){
            System.err.println("Error de conexion "+e.getMessage());
        }
        
        /*
        
        // ---------- CLIENTE ----------
        ClienteController clienteCtrl = new ClienteController();
        System.out.println("=== Pruebas Cliente ===");

        // Agregar cliente
        boolean agregado = clienteCtrl.agregar("Juan Pérez", "Calle Falsa 123", "555-1234");
        System.out.println("Cliente agregado: " + agregado);

        // Listar clientes
        List<Cliente> clientes = clienteCtrl.listarClientes();
        clientes.forEach(System.out::println);

        // Actualizar cliente
        if (!clientes.isEmpty()) {
            Cliente c = clientes.get(0);
            boolean actualizado = clienteCtrl.actualizarCliente(c.getIdCliente(), "Juan P.", "Av. Siempre Viva 742", "555-4321");
            System.out.println("Cliente actualizado: " + actualizado);
        }

        // ---------- ACTIVISTA ----------
        ActivistaController activistaCtrl = new ActivistaController();
        System.out.println("\n=== Pruebas Activista ===");

        // Agregar activista
        boolean activistaAgregado = activistaCtrl.agregarActivista("Ana López", "555-9876", Date.valueOf("2025-01-01"));
        System.out.println("Activista agregado: " + activistaAgregado);

        // Listar activistas
        List<Activista> activistas = activistaCtrl.listarActivistas();
        activistas.forEach(System.out::println);

        // ---------- PROBLEMA ----------
        ProblemaController problemaCtrl = new ProblemaController();
        System.out.println("\n=== Pruebas Problema ===");

        if (!clientes.isEmpty()) {
            Cliente cliente = clientes.get(0);
            boolean problemaAgregado = problemaCtrl.agregarProblema(Date.valueOf("2025-09-07"), null, "pendiente", cliente.getIdCliente());
            System.out.println("Problema agregado: " + problemaAgregado);
        }

        List<Problema> problemas = problemaCtrl.listarProblemas();
        problemas.forEach(System.out::println);
        */
    }
}
