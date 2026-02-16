/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.Date;

/**
 *
 * @author valeria
 */
public class Problema {
    private int idProblema;
    private Date fchIni;
    private Date fchFin;
    private String estado;
    private int idCliente;

    public int getIdProblema() {
        return idProblema;
    }

    public void setIdProblema(int idProblema) {
        this.idProblema = idProblema;
    }

    public Date getFchIni() {
        return fchIni;
    }

    public void setFchIni(Date fchIni) {
        this.fchIni = fchIni;
    }

    public Date getFchFin() {
        return fchFin;
    }

    public void setFchFin(Date fchFin) {
        this.fchFin = fchFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    
    
}
