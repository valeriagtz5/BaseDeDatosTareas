/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;


import enums.EstadoSalud;
import java.util.Date;
import java.time.Year;

/**
 *
 * @author valeria
 */
public class Animal {
    private int idAnimal;
    private String nombre;
    private String especie;
    private EstadoSalud estadoSalud;
    private Year anioNac;
    private Date fechaIng;
    private int idRefugio;

    public Animal() {
    }

    public Animal(int idAnimal, String nombre, String especie, EstadoSalud estadoSalud, Year anioNac, Date fechaIng, int idRefugio) {
        this.idAnimal = idAnimal;
        this.nombre = nombre;
        this.especie = especie;
        this.estadoSalud = estadoSalud;
        this.anioNac = anioNac;
        this.fechaIng = fechaIng;
        this.idRefugio = idRefugio;
    }

    public int getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(int idAnimal) {
        this.idAnimal = idAnimal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public EstadoSalud getEstadoSalud() {
        return estadoSalud;
    }

    public void setEstadoSalud(EstadoSalud estadoSalud) {
        this.estadoSalud = estadoSalud;
    }

    public Year getAnioNac() {
        return anioNac;
    }

    public void setAnioNac(Year anioNac) {
        this.anioNac = anioNac;
    }

    public Date getFechaIng() {
        return fechaIng;
    }

    public void setFechaIng(Date fechaIng) {
        this.fechaIng = fechaIng;
    }

    public int getIdRefugio() {
        return idRefugio;
    }

    public void setIdRefugio(int idRefugio) {
        this.idRefugio = idRefugio;
    }
    
    
}
