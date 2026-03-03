/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import daos.VoluntarioDAO;
import enums.Especialidad;
import interfaces.IVoluntarioDAO;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import models.Voluntario;

/**
 *
 * @author valeria
 */
public class VoluntarioController {
    private final IVoluntarioDAO voluntarioDAO;

    public VoluntarioController() {
        this.voluntarioDAO = new VoluntarioDAO();
    }
    
    public boolean agregarVoluntario(String nombre, Date fechaNac, Especialidad especialidad, String correo, String telefono) {
        // Validaciones de negocio
        if (nombre == null || nombre.trim().isEmpty()) {
            System.err.println("El nombre del voluntario es obligatorio.");
            return false;
        }

        java.util.Date hoy = new java.util.Date();
        if (fechaNac == null || fechaNac.after(hoy)) {
            System.err.println("Fecha de nacimiento inválida.");
            return false;
        }

        if (especialidad == null) {
            System.err.println("La especialidad es obligatoria.");
            return false;
        }

        if (correo == null || !correo.contains("@")) {
            System.err.println("Correo electrónico inválido.");
            return false;
        }

        Voluntario voluntario = new Voluntario();
        voluntario.setNombre(nombre.trim());
        voluntario.setFecha_nac(fechaNac);
        voluntario.setEspecialidad(especialidad);
        voluntario.setCorreo(correo.trim());
        voluntario.setTelefono(telefono != null ? telefono.trim() : "");

        return voluntarioDAO.insertar(voluntario);
    }
    
    public boolean editarVoluntario(int idVoluntario, String nombre, Date fechaNac, Especialidad especialidad, String correo, String telefono) {
        if (idVoluntario <= 0) {
            System.err.println("ID de voluntario inválido.");
            return false;
        }
        
        // Validaciones de negocio
        if (nombre == null || nombre.trim().isEmpty()) {
            System.err.println("El nombre del voluntario es obligatorio.");
            return false;
        }

        java.util.Date hoy = new java.util.Date();
        if (fechaNac == null || fechaNac.after(hoy)) {
            System.err.println("Fecha de nacimiento inválida.");
            return false;
        }

        if (especialidad == null) {
            System.err.println("La especialidad es obligatoria.");
            return false;
        }

        if (correo == null || !correo.contains("@")) {
            System.err.println("Correo electrónico inválido.");
            return false;
        }

        Voluntario voluntario = new Voluntario();
        voluntario.setIdVoluntario(idVoluntario);
        voluntario.setNombre(nombre.trim());
        voluntario.setFecha_nac(fechaNac);
        voluntario.setEspecialidad(especialidad);
        voluntario.setCorreo(correo.trim());
        voluntario.setTelefono(telefono.trim());

        return voluntarioDAO.actualizar(voluntario);
    }
    
    public boolean eliminarVoluntario(int idVoluntario) throws SQLException {
        if (idVoluntario <= 0) {
            System.err.println("ID inválido.");
            return false;
        }
        return voluntarioDAO.eliminar(idVoluntario);
    }
    
    public Voluntario obtenerVoluntario(int id) {
        if (id <= 0) {
            System.err.println("ID de refugio inválido.");
            return null;
        }
        return voluntarioDAO.obtenerPorId(id);
    }
    
    public DefaultTableModel obtenerTablaVoluntarios() {
        String[] columnas = {"ID", "NOMBRE", "FECHA NAC.", "ESPECIALIDAD", "CORREO", "TELÉFONO"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        List<Voluntario> lista = voluntarioDAO.obtenerTodos();

        for (Voluntario v : lista) {
            modelo.addRow(new Object[]{
                v.getIdVoluntario(),
                v.getNombre(),
                v.getFecha_nac(),
                v.getEspecialidad(),
                v.getCorreo(),
                v.getTelefono()
            });
        }
        return modelo;
    }
    
    public DefaultTableModel obtenerTablaVoluntarioFiltroNombre(String nombre){
        String[] columnas = {"ID", "NOMBRE", "FECHA NAC.", "ESPECIALIDAD", "CORREO", "TELÉFONO"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        List<Voluntario> lista = voluntarioDAO.obtenerTodosPorFiltroNombre(nombre);
        
        for (Voluntario v : lista) {
            modelo.addRow(new Object[]{
                v.getIdVoluntario(),
                v.getNombre(),
                v.getFecha_nac(),
                v.getEspecialidad(),
                v.getCorreo(),
                v.getTelefono()
            });
        }
        return modelo;
    }
    
    public DefaultTableModel obtenerTablaVoluntarioFiltroEspecialidad(String especialidad){
        String[] columnas = {"ID", "NOMBRE", "FECHA NAC.", "ESPECIALIDAD", "CORREO", "TELÉFONO"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        List<Voluntario> lista = voluntarioDAO.obtenerTodosPorFiltroEspecialidad(especialidad);
        
        for (Voluntario v : lista) {
            modelo.addRow(new Object[]{
                v.getIdVoluntario(),
                v.getNombre(),
                v.getFecha_nac(),
                v.getEspecialidad(),
                v.getCorreo(),
                v.getTelefono()
            });
        }
        return modelo;
    }
    
    public DefaultTableModel obtenerTablaVoluntarioFiltroCorreo(String correo){
        String[] columnas = {"ID", "NOMBRE", "FECHA NAC.", "ESPECIALIDAD", "CORREO", "TELÉFONO"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        List<Voluntario> lista = voluntarioDAO.obtenerTodosPorFiltroCorreo(correo);
        
        for (Voluntario v : lista) {
            modelo.addRow(new Object[]{
                v.getIdVoluntario(),
                v.getNombre(),
                v.getFecha_nac(),
                v.getEspecialidad(),
                v.getCorreo(),
                v.getTelefono()
            });
        }
        return modelo;
    }
    
    public List<Voluntario> listarVoluntarios() {
        return voluntarioDAO.obtenerTodos();
    }
    
}
