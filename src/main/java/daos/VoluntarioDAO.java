/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import config.ConexionDB;
import enums.Especialidad;
import interfaces.IVoluntarioDAO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import models.Voluntario;

/**
 *
 * @author valeria
 */
public class VoluntarioDAO implements IVoluntarioDAO {

    @Override
    public boolean insertar(Voluntario voluntario) {
        // Sentencia de insercion en SQL
        String sql = "INSERT INTO Voluntarios (nombre, fecha_nac, especialidad, correo, telefono) VALUES (?, ?, ?, ?, ?)";
        
        // Crear una coneccion para conectarse a la base de datos
        // PreparedStatement para evitar inyecciones 
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            // Insertar en esa posicion (numero a la izq.) del "?" el valor de una variable
            ps.setString(1, voluntario.getNombre());
            ps.setDate(2, new java.sql.Date(voluntario.getFecha_nac().getTime()));
            ps.setString(3, voluntario.getEspecialidad().name());
            ps.setString(4, voluntario.getCorreo());
            ps.setString(5, voluntario.getTelefono());

            // Regresa true o false
            // Si es mayor a cero es por que se hizo un cambio en la base de datos
            // Si es menor a cero es por que no paso nada
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar el voluntario: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean actualizar(Voluntario voluntario) {
        String sql = """
            UPDATE Voluntarios
            SET nombre = ?, fecha_nac = ?, especialidad = ?, correo = ?, telefono = ?
            WHERE id_voluntario = ?
        """;

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, voluntario.getNombre());
            ps.setDate(2, new java.sql.Date(voluntario.getFecha_nac().getTime()));
            ps.setString(3, voluntario.getEspecialidad().name());
            ps.setString(4, voluntario.getCorreo());
            ps.setString(5, voluntario.getTelefono());
            ps.setInt(6, voluntario.getIdVoluntario());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al voluntario: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int idVoluntario) throws SQLException {
        String sql = "DELETE FROM Voluntarios WHERE id_voluntario = ?";
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idVoluntario);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            if(e.getErrorCode()==1451){
                throw new SQLException("No se puede eliminar el voluntario porque tiene relaciones con otras tablas.");        
            }
            throw e;
        }
    }

    @Override
    public Voluntario obtenerPorId(int idVoluntario) {
        // Sentencia de consulta SQl
        String sql = "SELECT * FROM Voluntarios WHERE id_voluntario = ?";
        // Crear un objeto nulo
        Voluntario voluntario = null;
        
        // Crear una coneccion para conectarse a la base de datos
        // PreparedStatement para evitar inyecciones 
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idVoluntario); // Insertar en esa posicion del ? el idRefugio
            ResultSet rs = ps.executeQuery(); // Tabla temporal de los datos encontrados
                                              //ps.executeQuery() le pide a la base de datos que ejecute la consulta.
                            
            //Mientras haya una fila más, sigue leyendo           
            if (rs.next()) {
                voluntario = new Voluntario(); // Crear un objeto para setearle los datos
                voluntario.setIdVoluntario(rs.getInt("id_voluntario"));
                voluntario.setNombre(rs.getString("nombre"));
                voluntario.setFecha_nac(rs.getObject("fecha_nac", Date.class));
                voluntario.setEspecialidad(Especialidad.valueOf(rs.getString("especialidad")));
                voluntario.setCorreo(rs.getString("correo"));
                voluntario.setTelefono(rs.getString("telefono"));
            }
            
        } catch (SQLException e){
            System.err.println("Error al obtener el voluntario por ID: " + e.getMessage());
        }

        // Si no ocurrio ningun error se devuelve ese objeto que se busca por id
        return voluntario;
    }

    @Override
    public List<Voluntario> obtenerTodos() {
        // Sentencia de consulta SQL
        String sql = "SELECT id_voluntario, nombre, fecha_nac, especialidad, correo, telefono FROM Voluntarios";
        List<Voluntario> lista = new ArrayList<>(); // Crear un objeto de lista para guardar la consulta
        
        try (Connection conn = ConexionDB.getConnection(); 
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            
            while(rs.next()){
                Voluntario v = new Voluntario(); // Crear objeto para setear datos
                v.setIdVoluntario(rs.getInt("id_voluntario"));
                v.setNombre(rs.getString("nombre"));
                
                java.sql.Date fechaSQL = rs.getDate("fecha_nac");
                if (fechaSQL != null) {
                    // Como ya cambiaste el import en Voluntario, esto NO necesita cast
                    v.setFecha_nac(new java.util.Date(fechaSQL.getTime()));
                }
                
                v.setEspecialidad(Especialidad.valueOf(rs.getString("especialidad")));
                v.setCorreo(rs.getString("correo"));
                v.setTelefono(rs.getString("telefono"));


                lista.add(v); // Agregar objeto a la lista
            }

        } catch(SQLException e){
            System.err.println("Error al obtener los volunarios: "+e.getMessage());
        }
       
        // Si no ocurre ningun error se regresa la lista
        return lista;
    }
    
    @Override
    public List<Voluntario> obtenerTodosPorFiltroNombre(String nombre) {
        // Sentencia de consulta SQL
        String sql = "SELECT id_voluntario, nombre, fecha_nac, especialidad, correo, telefono FROM Voluntarios where nombre LIKE ?";
        List<Voluntario> lista = new ArrayList<>(); // Crear un objeto de lista para guardar la consulta
        
        try (Connection conn = ConexionDB.getConnection(); 
                PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, "%" + nombre + "%");
            ResultSet rs = ps.executeQuery();
            
            
            while(rs.next()){
                Voluntario v = new Voluntario(); // Crear objeto para setear datos
                v.setIdVoluntario(rs.getInt("id_voluntario"));
                v.setNombre(rs.getString("nombre"));
                v.setFecha_nac(rs.getDate("fecha_nac"));
                v.setEspecialidad(Especialidad.valueOf(rs.getString("especialidad")));
                v.setCorreo(rs.getString("correo"));
                v.setTelefono(rs.getString("telefono"));


                lista.add(v); // Agregar objeto a la lista
            }

        } catch(SQLException e){
            System.err.println("Error al obtener los voluntarios: "+e.getMessage());
        }
       
        // Si no ocurre ningun error se regresa la lista
        return lista;
    }
    
    public List<Voluntario> obtenerTodosPorFiltroEspecialidad(String especialidad) {
        // Sentencia de consulta SQL
        String sql = "SELECT id_voluntario, nombre, fecha_nac, especialidad, correo, telefono FROM Voluntarios where especialidad LIKE ?";
        List<Voluntario> lista = new ArrayList<>(); // Crear un objeto de lista para guardar la consulta
        
        try (Connection conn = ConexionDB.getConnection(); 
                PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, "%" + especialidad + "%");
            ResultSet rs = ps.executeQuery();
            
            
            while(rs.next()){
                Voluntario v = new Voluntario(); // Crear objeto para setear datos
                v.setIdVoluntario(rs.getInt("id_voluntario"));
                v.setNombre(rs.getString("nombre"));
                v.setFecha_nac(rs.getDate("fecha_nac"));
                v.setEspecialidad(Especialidad.valueOf(rs.getString("especialidad")));
                v.setCorreo(rs.getString("correo"));
                v.setTelefono(rs.getString("telefono"));


                lista.add(v); // Agregar objeto a la lista
            }

        } catch(SQLException e){
            System.err.println("Error al obtener los voluntarios por especialidad: "+e.getMessage());
        }
       
        // Si no ocurre ningun error se regresa la lista
        return lista;
    }
    
    @Override
    public List<Voluntario> obtenerTodosPorFiltroCorreo(String correo) {
        // Sentencia de consulta SQL
        String sql = "SELECT id_voluntario, nombre, fecha_nac, especialidad, correo, telefono FROM Voluntarios where correo LIKE ?";
        List<Voluntario> lista = new ArrayList<>(); // Crear un objeto de lista para guardar la consulta
        
        try (Connection conn = ConexionDB.getConnection(); 
                PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, "%" + correo + "%");
            ResultSet rs = ps.executeQuery();
            
            
            while(rs.next()){
                Voluntario v = new Voluntario(); // Crear objeto para setear datos
                v.setIdVoluntario(rs.getInt("id_voluntario"));
                v.setNombre(rs.getString("nombre"));
                v.setFecha_nac(rs.getDate("fecha_nac"));
                v.setEspecialidad(Especialidad.valueOf(rs.getString("especialidad")));
                v.setCorreo(rs.getString("correo"));
                v.setTelefono(rs.getString("telefono"));


                lista.add(v); // Agregar objeto a la lista
            }

        } catch(SQLException e){
            System.err.println("Error al obtener los voluntarios por especialidad: "+e.getMessage());
        }
       
        // Si no ocurre ningun error se regresa la lista
        return lista;
    }
    
    
    
}
