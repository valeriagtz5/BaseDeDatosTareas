/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import daos.AnimalDAO;
import daos.RefugioDAO;
import enums.EstadoSalud;
import interfaces.IAnimalDAO;
import interfaces.IRefugioDAO;
import java.sql.SQLException;
import java.time.Year;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import models.Animal;
import models.Refugio;

/**
 *
 * @author valeria
 */
public class AnimalController {
    private final IAnimalDAO animalDAO;
    private final IRefugioDAO refugioDAO; // Necesario para el método obtenerRefugio

    public AnimalController() {
        this.animalDAO = new AnimalDAO();
        this.refugioDAO = new RefugioDAO();
    }

    public boolean agregarAnimal(String nombre, String especie, EstadoSalud estadoSalud, Year anioNac, Date fechaIng, int idRefugio) {
        // Validaciones de negocio
        Refugio refugio = refugioDAO.obtenerPorId(idRefugio);
        if (refugio == null) {
            System.err.println("El refugio seleccionado no existe.");
            return false;
        }
        
        if (nombre == null || nombre.trim().isEmpty()) {
            System.err.println("El nombre del animal es obligatorio.");
            return false;
        }
        if (especie == null || especie.trim().isEmpty()) {
            System.err.println("La especie es obligatoria.");
            return false;
        }
        if (estadoSalud == null) {
            System.err.println("El estado de salud es obligatorio.");
            return false;
        }
        if (anioNac == null || anioNac.isAfter(Year.now())) {
            System.err.println("El año de nacimiento no es válido.");
            return false;
        }
        if (idRefugio <= 0) {
            System.err.println("Debe asignar un refugio válido.");
            return false;
        }
        
        // Contar cuántos animales hay actualmente en ese refugio
        int animalesActuales = animalDAO.contarAnimalesPorRefugio(idRefugio);
            if (animalesActuales >= refugio.getCapacidad()) {
            System.err.println("¡ERROR! El refugio '" + refugio.getNombre() + 
                               "' ha alcanzado su capacidad máxima (" + refugio.getCapacidad() + ").");
            return false; // Bloqueamos la inserción
        }

        Animal animal = new Animal();
        animal.setNombre(nombre.trim());
        animal.setEspecie(especie.trim());
        animal.setEstadoSalud(estadoSalud);
        animal.setAnioNac(anioNac);
        animal.setFechaIng(fechaIng != null ? fechaIng : new java.util.Date());
        animal.setIdRefugio(idRefugio);

        return animalDAO.insertar(animal);
    }

    public boolean editarAnimal(int idAnimal, String nombre, String especie, EstadoSalud estadoSalud, Year anioNac, Date fechaIng, int idRefugio) {
        if (idAnimal <= 0) {
            System.err.println("ID de animal inválido.");
            return false;
        }

        Animal animal = new Animal();
        animal.setIdAnimal(idAnimal);
        animal.setNombre(nombre.trim());
        animal.setEspecie(especie.trim());
        animal.setEstadoSalud(estadoSalud);
        animal.setAnioNac(anioNac);
        animal.setFechaIng(fechaIng);
        animal.setIdRefugio(idRefugio);

        return animalDAO.actualizar(animal);
    }

    public boolean eliminarAnimal(int idAnimal) throws SQLException {
        if (idAnimal <= 0) {
            System.err.println("ID de animal inválido.");
            return false;
        }
        return animalDAO.eliminar(idAnimal);
    }

    // El método adaptado que solicitaste
    public Animal obtenerAnimal(int id) {
        if (id <= 0) {
            System.err.println("ID de animal inválido.");
            return null;
        }
        return animalDAO.obtenerPorId(id);
    }

    public DefaultTableModel obtenerTablaAnimales(int id) {
        String[] columnas = {"ID", "NOMBRE", "ESPECIE", "SALUD", "AÑO NAC.", "INGRESO", "REFUGIO ID"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        List<Animal> lista = animalDAO.obtenerTodos(id);

        for (Animal a : lista) {
            modelo.addRow(new Object[]{
                a.getIdAnimal(),
                a.getNombre(),
                a.getEspecie(),
                a.getEstadoSalud(),
                a.getAnioNac(),
                a.getFechaIng(),
                a.getIdRefugio()
            });
        }
        return modelo;
    }
    
    public DefaultTableModel obtenerTablaAnimalesFiltradoNombre(String nombre, int id) {
        String[] columnas = {"ID", "NOMBRE", "ESPECIE", "SALUD", "AÑO NAC.", "INGRESO", "REFUGIO ID"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        List<Animal> lista = animalDAO.obtenerTodosFiltroNombre(nombre, id);

        for (Animal a : lista) {
            modelo.addRow(new Object[]{
                a.getIdAnimal(),
                a.getNombre(),
                a.getEspecie(),
                a.getEstadoSalud(),
                a.getAnioNac(),
                a.getFechaIng(),
                a.getIdRefugio()
            });
        }
        return modelo;
    }

    public List<Animal> listarAnimales(int id) {
        return animalDAO.obtenerTodos(id);
    }
    
    public int contarAnimalesPorRefugio(int idRefugio) {
        return animalDAO.contarAnimalesPorRefugio(idRefugio);
    }
}
