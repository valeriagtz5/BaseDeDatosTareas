/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package controllers;

import java.sql.Date;
import java.util.List;
import models.Activista;

/**
 *
 * @author valeria
 */
public interface IActivistaController {

    boolean agregarActivista(String nombre, String telefono, Date fchIngreso);

    Activista obtenerActivista(int idActivista);

    List<Activista> listarActivistas();

    boolean actualizarActivista(int idActivista, String nombre, String telefono, Date fchIngreso);

    boolean eliminarActivista(int idActivista);
}
