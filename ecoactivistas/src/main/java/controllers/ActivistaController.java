/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import daos.ActivistaDAO;
import daos.IActivistaDAO;
import java.sql.Date;
import java.util.List;
import models.Activista;

/**
 *
 * @author valeria
 */
public class ActivistaController implements IActivistaController{

    private final IActivistaDAO activistaDAO;

    public ActivistaController() {
        this.activistaDAO = new ActivistaDAO();
    }

    // Insertar un nuevo activista con validaciones
    @Override
    public boolean agregarActivista(String nombre, String telefono, Date fchIngreso) {
        if (nombre == null || nombre.trim().isEmpty()) {
            System.err.println("El nombre del activista no puede estar vacío.");
            return false;
        }

        if (fchIngreso == null) {
            System.err.println("La fecha de ingreso es obligatoria.");
            return false;
        }

        if (telefono == null) {
            telefono = "";
        }

        Activista activista = new Activista();
        activista.setNombre(nombre.trim());
        activista.setTelefono(telefono.trim());
        activista.setFchIngreso(fchIngreso);

        return activistaDAO.insertar(activista);
    }

    // Obtener un activista por ID
    @Override
    public Activista obtenerActivista(int idActivista) {
        if (idActivista <= 0) {
            System.err.println("ID de activista inválido.");
            return null;
        }

        return activistaDAO.obtenerPorId(idActivista);
    }
    // Obtener todos los activistas
    @Override
    public List<Activista> listarActivistas() {
        return activistaDAO.obtenerTodos();
    }

    // Actualizar activista con validaciones
    @Override
    public boolean actualizarActivista(int idActivista, String nombre, String telefono, Date fchIngreso) {
        if (idActivista <= 0) {
            System.err.println("ID de activista inválido.");
            return false;
        }

        if (nombre == null || nombre.trim().isEmpty()) {
            System.err.println("El nombre del activista no puede estar vacío.");
            return false;
        }

        if (fchIngreso == null) {
            System.err.println("La fecha de ingreso es obligatoria.");
            return false;
        }

        if (telefono == null) {
            telefono = "";
        }

        Activista activista = new Activista();
        activista.setIdActivista(idActivista);
        activista.setNombre(nombre.trim());
        activista.setTelefono(telefono.trim());
        activista.setFchIngreso(fchIngreso);

        return activistaDAO.actualizar(activista);
    }

    // Eliminar activista con validación de ID
    @Override
    public boolean eliminarActivista(int idActivista) {
        if (idActivista <= 0) {
            System.err.println("ID de activista inválido.");
            return false;
        }

        return activistaDAO.eliminar(idActivista);
    }
}