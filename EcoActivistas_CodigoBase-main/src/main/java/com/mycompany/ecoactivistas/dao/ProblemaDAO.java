/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ecoactivistas.dao;

import com.mycompany.ecoactivistas.config.ConexionDB;
import com.mycompany.ecoactivistas.interfaces.IProblemaDAO;
import com.mycompany.ecoactivistas.model.Problema;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author martinbl
 */
public class ProblemaDAO implements IProblemaDAO {
    @Override
    public boolean insertar(Problema problema) {
        String sql = "INSERT INTO Problema (fch_ini, fch_fin, estado, idCliente, descripcion) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, problema.getFchIni());
            ps.setDate(2, problema.getFchFin());
            ps.setString(3, problema.getEstado());
            ps.setInt(4, problema.getIdCliente());
            ps.setString(5, problema.getDescripcion());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar problema: " + e.getMessage());
            return false;
        }
    }
    @Override
    public Problema obtenerPorId(int idProblema) {
        String sql = "SELECT * FROM Problema WHERE idProblema = ?";
        Problema problema = null;

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idProblema);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                problema = new Problema();
                problema.setIdProblema(rs.getInt("idProblema"));
                problema.setFchIni(rs.getDate("fch_ini"));
                problema.setFchFin(rs.getDate("fch_fin"));
                problema.setEstado(rs.getString("estado"));
                problema.setIdCliente(rs.getInt("idCliente"));
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener problema por ID: " + e.getMessage());
        }
        return problema;
    }

    @Override
    public List<Problema> obtenerTodos() {
        String sql = "SELECT * FROM Problema";
        List<Problema> lista = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Problema problema = new Problema();
                problema.setIdProblema(rs.getInt("idProblema"));
                problema.setFchIni(rs.getDate("fch_ini"));
                problema.setFchFin(rs.getDate("fch_fin"));
                problema.setEstado(rs.getString("estado"));
                problema.setIdCliente(rs.getInt("idCliente"));
                problema.setDescripcion(rs.getString("descripcion"));
                lista.add(problema);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener todos los problemas: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public boolean actualizar(Problema problema) {
        String sql = "UPDATE Problema SET fch_ini = ?, fch_fin = ?, estado = ?, idCliente = ?, descripcion=? WHERE idProblema = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, problema.getFchIni());
            
            if (problema.getFchFin() != null) {
                ps.setDate(2, problema.getFchFin());
            } else {
                ps.setNull(2, java.sql.Types.DATE);
            }
            ps.setString(3, problema.getEstado());
            
            ps.setInt(4, problema.getIdCliente());
            
            ps.setString(5, problema.getDescripcion());  // descripción
            ps.setInt(6, problema.getIdProblema());   
            /*
            ps.setInt(5, problema.getIdProblema());
            ps.setString(6, problema.getDescripcion());
            */
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar problema: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int idProblema) {
        String sql = "DELETE FROM Problema WHERE idProblema = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idProblema);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar problema: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Problema> obtenerTodosPorFiltro(String filtro) {
        String sql = "SELECT * FROM Problema WHERE descripcion LIKE ?";
        List<Problema> lista = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + filtro + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Problema problema = new Problema();
                problema.setIdProblema(rs.getInt("idProblema"));
                
                problema.setFchIni(rs.getDate("fch_ini"));
            
                // fch_fin puede ser NULL
                Date fechaFin = rs.getDate("fch_fin");
                if (fechaFin != null) {
                    problema.setFchFin(fechaFin);
                }

                problema.setEstado(rs.getString("estado"));
                problema.setIdCliente(rs.getInt("idCliente"));
                problema.setDescripcion(rs.getString("descripcion"));
                
                lista.add(problema);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener problemas por filtro: " + e.getMessage());
        }

        return lista;
    }
}
