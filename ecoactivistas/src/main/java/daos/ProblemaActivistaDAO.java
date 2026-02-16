/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import config.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Activista;
import models.ProblemaActivista;

/**
 *
 * @author valeria
 */
public class ProblemaActivistaDAO implements IProblemaActivistaDAO{

    @Override
    public boolean insertar(ProblemaActivista relacion) {
        String sql = "INSERT INTO Problema_Activista(idProblema,idActivista VALUES (?,?)";
        
        try (
                Connection con = ConexionDB.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
            ){
            
            ps.setInt(1, relacion.getIdProblema());
            ps.setInt(2, relacion.getIdActivista());
            
            return ps.executeUpdate() > 0;
            
        }catch(SQLException ex){
            System.out.println("Error al intentar insertar relacion Problema-Activista: "+ex.getMessage());
            return false;
        }
    }

    @Override
    public List<ProblemaActivista> obtenerPorProblema(int id) {
        String sql = "SELECT * FROM Problema_Activista WHERE idProblema =?";
        List<ProblemaActivista> lista = new ArrayList<>();
        
        try (
                Connection con = ConexionDB.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
            ){
            
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                ProblemaActivista relacion = new ProblemaActivista();
                relacion.setIdProblema(rs.getInt("idProblema"));
                relacion.setIdActivista(rs.getInt("idActivista"));
                lista.add(relacion);
            }
            
        }catch(SQLException ex){
            System.err.println("Error al intentar obtener relaciones por problema: "+ex.getMessage());
        }
        return lista;
       
    }

    @Override
    public List<ProblemaActivista> obtenerPorActivista(int id) {
        String sql = "SELECT * FROM Problema_Activista WHERE idActivista =?";
        List<ProblemaActivista> lista = new ArrayList<>();
        
        try (
                Connection con = ConexionDB.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
            ){
            
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                ProblemaActivista relacion = new ProblemaActivista();
                relacion.setIdProblema(rs.getInt("idProblema"));
                relacion.setIdActivista(rs.getInt("idActivista"));
                lista.add(relacion);
            }
            
        }catch(SQLException ex){
            System.err.println("Error al intentar obtener relaciones por activistas: "+ex.getMessage());
        }
        return lista;
       
    }

    @Override
    public boolean eliminar(int idProblema, int idActivista) {
        String sql = "DELETE FROM Problema_Activista WHERE idProblema = ? AND idActivista = ?";

        try (
            Connection con = ConexionDB.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, idProblema);
            ps.setInt(2, idActivista);
            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.err.println("Error al eliminar relacion Problema-Activista: " + ex.getMessage());
            return false;
        }
    }
    
}
