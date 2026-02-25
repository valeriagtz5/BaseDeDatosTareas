/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ecoactivistas.controller;

import com.mycompany.ecoactivistas.dao.ProblemaDAO;
import com.mycompany.ecoactivistas.interfaces.IProblemaDAO;
import com.mycompany.ecoactivistas.model.Problema;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author martinbl
 * Valeria
 */
public class ProblemaController {
    private final IProblemaDAO problemaDAO;
    private final List<String> estadosValidos = Arrays.asList("pendiente", "concluido", "cancelado");

    public ProblemaController() {
        this.problemaDAO = new ProblemaDAO();
    }
    
    public boolean agregarProblema(Date fchIni, Date fchFin, String estado, int idCliente, String descripcion) {
        if (fchIni == null) {
            System.err.println("La fecha de inicio es obligatoria.");
            return false;
        }
        if (estado == null || !estadosValidos.contains(estado.toLowerCase())) {
            System.err.println("Estado inválido. Debe ser: pendiente, concluido o cancelado.");
            return false;
        }
        if (idCliente <= 0) {
            System.err.println("ID de cliente inválido.");
            return false;
        }

        Problema problema = new Problema();
        problema.setFchIni(fchIni);
        problema.setFchFin(fchFin);
        problema.setEstado(estado.toLowerCase());
        problema.setIdCliente(idCliente);
        problema.setDescripcion(descripcion);
        
        return problemaDAO.insertar(problema);
    }

    // Obtener un problema por ID
    public Problema obtenerProblema(int idProblema) {
        if (idProblema <= 0) {
            System.err.println("ID de problema inválido.");
            return null;
        }
        return problemaDAO.obtenerPorId(idProblema);
    }

    // Obtener todos los problemas
    public List<Problema> listarProblemas() {
        return problemaDAO.obtenerTodos();
    }

    // Actualizar problema con validaciones
    public boolean actualizarProblema(int idProblema, Date fchIni, Date fchFin, String estado, int idCliente, String desc) {
        if (idProblema <= 0) {
            System.err.println("ID de problema inválido.");
            return false;
        }
        if (fchIni == null) {
            System.err.println("La fecha de inicio es obligatoria.");
            return false;
        }
        if (estado == null || !estadosValidos.contains(estado.toLowerCase())) {
            System.err.println("Estado inválido. Debe ser: pendiente, concluido o cancelado.");
            return false;
        }
        if (idCliente <= 0) {
            System.err.println("ID de cliente inválido.");
            return false;
        }
        
        if(desc == null){
            System.err.println("La descripcion es obligatoria.");
            return false;
        }

        Problema problema = new Problema();
        problema.setIdProblema(idProblema);
        problema.setFchIni(fchIni);
        problema.setFchFin(fchFin);
        problema.setEstado(estado.toLowerCase());
        problema.setIdCliente(idCliente);
        problema.setDescripcion(desc);

        return problemaDAO.actualizar(problema);
    }

    // Eliminar problema
    public boolean eliminarProblema(int idProblema) {
        if (idProblema <= 0) {
            System.err.println("ID de problema inválido.");
            return false;
        }
        return problemaDAO.eliminar(idProblema);
    }
    
    public DefaultTableModel obtenerTablaProblemas(){
        String[] columnas = {"ID", "F. INICIO","F. FIN","ESTADO","CLIENTE","DESCRIPCION"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        List<Problema> lista = problemaDAO.obtenerTodos();
        
        for (Problema p:lista){
            modelo.addRow(new Object[]{p.getIdProblema(),p.getFchIni(),p.getFchFin(),p.getEstado(),p.getIdCliente(),p.getDescripcion()});
        }
        return modelo;
    }
    
    public DefaultTableModel obtenerTablaProblemasFiltrado(String filtro){
        String[] columnas = {"ID", "F. INICIO","F. FIN","ESTADO","CLIENTE","DESCRIPCION"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        List<Problema> lista = problemaDAO.obtenerTodosPorFiltro(filtro);
        
        for (Problema p:lista){
            modelo.addRow(new Object[]{p.getIdProblema(),p.getFchIni(),p.getFchFin(),p.getEstado(),p.getIdCliente(),p.getDescripcion()});
        }
        return modelo;
    }
}
