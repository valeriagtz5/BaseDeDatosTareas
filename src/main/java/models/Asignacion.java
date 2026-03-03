/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import enums.Actividad;
import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author valeria
 */
public class Asignacion {
    private int idAsignacion;
    private Date fecha;
    private Actividad actividad;
    private int idAnimal;
    private int idVoluntario;

    public Asignacion() {
    }

    public Asignacion(int idAsignacion, Date fecha, Actividad actividad, int idAnimal, int idVoluntario) {
        this.idAsignacion = idAsignacion;
        this.fecha = fecha;
        this.actividad = actividad;
        this.idAnimal = idAnimal;
        this.idVoluntario = idVoluntario;
    }

    
    public int getIdAsignacion() {
        return idAsignacion;
    }

    public void setIdAsignacion(int idAsignacion) {
        this.idAsignacion = idAsignacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public int getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(int idAnimal) {
        this.idAnimal = idAnimal;
    }

    public int getIdVoluntario() {
        return idVoluntario;
    }

    public void setIdVoluntario(int idVoluntario) {
        this.idVoluntario = idVoluntario;
    }
    
}
