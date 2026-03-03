/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import enums.Especialidad;
import java.util.Date;


/**
 *
 * @author valeria
 */
public class Voluntario {
    private int idVoluntario;
    private String nombre;
    private Date fecha_nac;
    private Especialidad especialidad;
    private String correo;
    private String telefono;

    public Voluntario() {
    }

    public Voluntario(int idVoluntario, String nombre, Date fecha_nac, Especialidad especialidad, String correo, String telefono) {
        this.idVoluntario = idVoluntario;
        this.nombre = nombre;
        this.fecha_nac = fecha_nac;
        this.especialidad = especialidad;
        this.correo = correo;
        this.telefono = telefono;
    }

    public int getIdVoluntario() {
        return idVoluntario;
    }

    public void setIdVoluntario(int idVoluntario) {
        this.idVoluntario = idVoluntario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha_nac() {
        return fecha_nac;
    }

    public void setFecha_nac(Date fecha_nac) {
        this.fecha_nac = fecha_nac;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    
    
}
