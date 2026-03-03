/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import config.ConexionDB;
import interfaces.IRefugioDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Refugio;

/**
 *
 * @author valeria
 */
public class RefugioDAO implements IRefugioDAO{

    @Override
    public boolean insertar(Refugio refugio) {
        // Sentencia de insercion en SQL
        String sql = "INSERT INTO Refugios (nombre, capacidad, ubicacion, nombre_responsable) VALUES (?, ?, ?, ?)";
        
        // Crear una coneccion para conectarse a la base de datos
        // PreparedStatement para evitar inyecciones 
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            // Insertar en esa posicion (numero a la izq.) del "?" el valor de una variable
            ps.setString(1, refugio.getNombre());
            ps.setInt(2, refugio.getCapacidad());
            ps.setString(3, refugio.getUbicacion());
            ps.setString(4, refugio.getNombreResponsable());

            // Regresa true o false
            // Si es mayor a cero es por que se hizo un cambio en la base de datos
            // Si es menor a cero es por que no paso nada
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar el refugio: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean actualizar(Refugio refugio) {
        String sql = "UPDATE Refugios SET nombre = ?, capacidad = ?, ubicacion = ?, nombre_responsable = ? WHERE id_refugio = ?";

        try (Connection conn = ConexionDB.getConnection(); 
                PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, refugio.getNombre());
            ps.setInt(2, refugio.getCapacidad());
            ps.setString(3, refugio.getUbicacion());
            ps.setString(4, refugio.getNombreResponsable());
            ps.setInt(5, refugio.getIdRefugio());
            
            // Regresa true o false
            // Si es mayor a cero es por que se hizo un cambio en la base de datos
            // Si es menor a cero es por que no paso nada
            return ps.executeUpdate()>0;
            
        } catch (SQLException e){
            System.err.println("No se pudo actualizar al cliente: "+e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int idRefugio) throws SQLException {
        String sql = "DELETE FROM Refugios WHERE id_refugio = ?";
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idRefugio);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            if(e.getErrorCode()==1451){
                throw new SQLException("No se puede eliminar el refugio porque tiene relaciones con otras tablas.");        
            }
            throw e;
        }
    }
    
    @Override
    public Refugio obtenerPorId(int idRefugio) {
        // Sentencia de consulta SQl
        String sql = "SELECT id_refugio, nombre, capacidad, ubicacion, nombre_responsable FROM Refugios WHERE id_refugio = ?";
        // Crear un objeto nulo
        Refugio refugio = null;
        
        // Crear una coneccion para conectarse a la base de datos
        // PreparedStatement para evitar inyecciones 
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idRefugio); // Insertar en esa posicion del ? el idRefugio
            ResultSet rs = ps.executeQuery(); // Tabla temporal de los datos encontrados
                                              //ps.executeQuery() le pide a la base de datos que ejecute la consulta.
                            
            //Mientras haya una fila más, sigue leyendo           
            if (rs.next()) {
                refugio = new Refugio(); // Crear un objeto para setearle los datos
                refugio.setIdRefugio(rs.getInt("id_refugio"));
                refugio.setNombre(rs.getString("nombre"));
                refugio.setCapacidad(rs.getInt("capacidad"));
                refugio.setUbicacion(rs.getString("ubicacion"));
                refugio.setNombreResponsable(rs.getString("nombre_responsable"));
            }
            
        } catch (SQLException e){
            System.err.println("Error al obtener el refugio por ID: " + e.getMessage());
        }

        // Si no ocurrio ningun error se devuelve ese objeto que se busca por id
        return refugio;
    }
    
    @Override
    public List<Refugio> obtenerTodos() {
        // Sentencia de consulta SQL
        String sql = "SELECT id_refugio, nombre, capacidad, ubicacion, nombre_responsable FROM Refugios";
        List<Refugio> lista = new ArrayList<>(); // Crear un objeto de lista para guardar la consulta
        
        try (Connection conn = ConexionDB.getConnection(); 
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            
            while(rs.next()){
                Refugio refugio = new Refugio(); // Crear objeto para setear datos
                refugio.setIdRefugio(rs.getInt("id_refugio"));
                refugio.setNombre(rs.getString("nombre"));
                refugio.setCapacidad(rs.getInt("capacidad"));
                refugio.setUbicacion(rs.getString("ubicacion"));
                refugio.setNombreResponsable(rs.getString("nombre_responsable"));
                lista.add(refugio); // Agregar objeto a la lista
            }

        } catch(SQLException e){
            System.err.println("Error al obtener los refugios: "+e.getMessage());
        }
       
        // Si no ocurre ningun error se regresa la lista
        return lista;
    }
    
    @Override
    public List<Refugio> obtenerTodosPorFiltroNombre(String nombre) {
        String sql = "SELECT id_refugio, nombre, capacidad, ubicacion, nombre_responsable FROM Refugios WHERE nombre LIKE ?";
        List<Refugio> lista = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + nombre + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Refugio refugio = new Refugio();
                refugio.setIdRefugio(rs.getInt("id_refugio"));
                refugio.setNombre(rs.getString("nombre"));
                refugio.setCapacidad(rs.getInt("capacidad"));
                refugio.setUbicacion(rs.getString("ubicacion"));
                refugio.setNombreResponsable(rs.getString("nombre_responsable"));
                lista.add(refugio); // Agregar objeto a la lista
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener refugios filtrados por nombre: " + e.getMessage());
        }

        return lista;
    }
    
    @Override
    public List<Refugio> obtenerTodosPorFiltroUbi(String ubi) {
        String sql = "SELECT id_refugio, nombre, capacidad, ubicacion, nombre_responsable FROM Refugios WHERE ubicacion LIKE ?";
        List<Refugio> lista = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + ubi + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Refugio refugio = new Refugio();
                refugio.setIdRefugio(rs.getInt("id_refugio"));
                refugio.setNombre(rs.getString("nombre"));
                refugio.setCapacidad(rs.getInt("capacidad"));
                refugio.setUbicacion(rs.getString("ubicacion"));
                refugio.setNombreResponsable(rs.getString("nombre_responsable"));
                lista.add(refugio); // Agregar objeto a la lista
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener refugios filtrados por ubicacion: " + e.getMessage());
        }

        return lista;
    }
    
    @Override
    public List<Refugio> obtenerTodosPorFiltroResp(String r) {
        String sql = "SELECT id_refugio, nombre, capacidad, ubicacion, nombre_responsable FROM Refugios WHERE nombre_responsable LIKE ?";
        List<Refugio> lista = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + r + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Refugio refugio = new Refugio();
                refugio.setIdRefugio(rs.getInt("id_refugio"));
                refugio.setNombre(rs.getString("nombre"));
                refugio.setCapacidad(rs.getInt("capacidad"));
                refugio.setUbicacion(rs.getString("ubicacion"));
                refugio.setNombreResponsable(rs.getString("nombre_responsable"));
                lista.add(refugio); // Agregar objeto a la lista
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener refugios filtrados por responsable: " + e.getMessage());
        }

        return lista;
    }
    

    public List<Refugio> obtenerTodosAcceder() {
        String sql = "SELECT id_refugio, nombre, ubicacion FROM Refugios";
        List<Refugio> lista = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection(); 
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Refugio refugio = new Refugio();
                refugio.setIdRefugio(rs.getInt("id_refugio"));
                refugio.setNombre(rs.getString("nombre"));
                refugio.setUbicacion(rs.getString("ubicacion"));
                lista.add(refugio); // Agregar objeto a la lista
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener refugios para acceder: " + e.getMessage());
        }

        return lista;
    }
    
}
