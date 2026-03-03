/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import daos.RefugioDAO;
import interfaces.IRefugioDAO;
import java.sql.SQLException;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import models.Refugio;

/**
 *
 * @author valeria
 */
public class RefugioController {
    private final IRefugioDAO refugioDAO;

    public RefugioController() {
        this.refugioDAO = new RefugioDAO();
    }
    
    public boolean agregarRefugio(String nombre, int capacidad, String ubicacion, String nombreResponsable){
        if (nombre == null || nombre.trim().isEmpty()) {
            System.err.println("El nombre del refugio no puede estar vacío.");
            return false;
        }
        
        if (capacidad <= 0) {
            System.err.println("La capacidad del refugio de es obligatoria.");
            return false;
        }
        
        if(ubicacion == null || ubicacion.trim().isEmpty()){
            System.err.println("La ubicacion del refugio es obligatoria");
        }
        
        if(nombreResponsable == null || nombreResponsable.trim().isEmpty()){
            System.err.println("El nombre del responsable del refugio es obligatorio");
        }
        
        Refugio refugio = new Refugio();
        refugio.setNombre(nombre.trim());
        refugio.setCapacidad(capacidad);
        refugio.setUbicacion(ubicacion.trim());
        refugio.setNombreResponsable(nombreResponsable.trim());
        
        return refugioDAO.insertar(refugio);
    }
    
    public boolean editarRefugio(int idRefugio, String nombre, int capacidad, String ubicacion, String nombreResponsable){
        if(idRefugio <= 0) {
            System.err.println("ID de refugio invalido");
        }
        
        if (nombre == null || nombre.trim().isEmpty()) {
            System.err.println("El nombre del refugio no puede estar vacío.");
            return false;
        }
        
        if (capacidad <= 0) {
            System.err.println("La capacidad del refugio de es obligatoria.");
            return false;
        }
        
        if(ubicacion == null || ubicacion.trim().isEmpty()){
            System.err.println("La ubicacion del refugio es obligatoria");
        }
        
        if(nombreResponsable == null || nombreResponsable.trim().isEmpty()){
            System.err.println("El nombre del responsable del refugio es obligatorio");
        }
        
        Refugio refugio = new Refugio();
        refugio.setIdRefugio(idRefugio);
        refugio.setNombre(nombre.trim());
        refugio.setCapacidad(capacidad);
        refugio.setUbicacion(ubicacion.trim());
        refugio.setNombreResponsable(nombreResponsable.trim());
        
        return refugioDAO.actualizar(refugio);
    }
    
    public boolean eliminarRefugio(int idRefugio) throws SQLException{
        if (idRefugio <= 0) {
            System.err.println("ID de refugio inválido.");
            return false;
        }
        return refugioDAO.eliminar(idRefugio);
    }
    
    public Refugio obtenerRefugio(int idRefugio) {
        if (idRefugio <= 0) {
            System.err.println("ID de refugio inválido.");
            return null;
        }
        return refugioDAO.obtenerPorId(idRefugio);
    }
    
    public DefaultTableModel obtenerTablaRefugios(){
        String[] columnas = {"ID", "NOMBRE","CAPACIDAD","UBICACION","NOMBRE RESP."};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        List<Refugio> lista = refugioDAO.obtenerTodos();
        
        for (Refugio r:lista){
            modelo.addRow(new Object[]{r.getIdRefugio(), r.getNombre(), r.getCapacidad(),r.getUbicacion(),r.getNombreResponsable()});
        }
        return modelo;
    }
    
    public DefaultTableModel obtenerTablaRefugiosFiltroNombre(String nombre){
        String[] columnas = {"ID", "NOMBRE","CAPACIDAD","UBICACION","NOMBRE RESP."};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        List<Refugio> lista = refugioDAO.obtenerTodosPorFiltroNombre(nombre);
        
        for (Refugio r:lista){
            modelo.addRow(new Object[]{r.getIdRefugio(), r.getNombre(), r.getCapacidad(),r.getUbicacion(),r.getNombreResponsable()});
        }
        return modelo;
    }
    
    public DefaultTableModel obtenerTablaRefugiosFiltroUbi(String nombre){
        String[] columnas = {"ID", "NOMBRE","CAPACIDAD","UBICACION","NOMBRE RESP."};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        List<Refugio> lista = refugioDAO.obtenerTodosPorFiltroUbi(nombre);
        
        for (Refugio r:lista){
            modelo.addRow(new Object[]{r.getIdRefugio(), r.getNombre(), r.getCapacidad(),r.getUbicacion(),r.getNombreResponsable()});
        }
        return modelo;
    }
    
    public DefaultTableModel obtenerTablaRefugiosFiltroResp(String nombre){
        String[] columnas = {"ID", "NOMBRE","CAPACIDAD","UBICACION","NOMBRE RESP."};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        List<Refugio> lista = refugioDAO.obtenerTodosPorFiltroResp(nombre);
        
        for (Refugio r:lista){
            modelo.addRow(new Object[]{r.getIdRefugio(), r.getNombre(), r.getCapacidad(),r.getUbicacion(),r.getNombreResponsable()});
        }
        return modelo;
    }
    
    public DefaultTableModel obtenerTablaAcceder(){
        String[] columnas = {"ID", "NOMBRE","UBICACION"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        List<Refugio> lista = refugioDAO.obtenerTodosAcceder();
        
        for (Refugio r:lista){
            modelo.addRow(new Object[]{r.getIdRefugio(), r.getNombre(), r.getUbicacion()});
        }
        return modelo;
    }
    
    public List<Refugio> listarRefugios(){
        return refugioDAO.obtenerTodos();
    }
}
