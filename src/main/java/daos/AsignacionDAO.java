/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import config.ConexionDB;
import enums.Actividad;
import interfaces.IAsignacionDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import models.Asignacion;

/**
 *
 * @author valeria
 */
public class AsignacionDAO implements IAsignacionDAO{

    @Override
    public boolean insertar(Asignacion asignacion) {
        String sql = "INSERT INTO Asignaciones (fecha, actividad, id_animal, id_voluntario) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = ConexionDB.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, new java.sql.Date(asignacion.getFecha().getTime()));
            ps.setString(2, asignacion.getActividad().name()); // Guardar nombre del Enum
            ps.setInt(3, asignacion.getIdAnimal());
            ps.setInt(4, asignacion.getIdVoluntario());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar asignación: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean actualizar(Asignacion asignacion) {
        String sql = "UPDATE Asignaciones SET fecha = ?, actividad = ?, id_animal = ?, id_voluntario = ? WHERE id_asignacion = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, new java.sql.Date(asignacion.getFecha().getTime()));
            ps.setString(2, asignacion.getActividad().name());
            ps.setInt(3, asignacion.getIdAnimal());
            ps.setInt(4, asignacion.getIdVoluntario());
            ps.setInt(5, asignacion.getIdAsignacion());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar asignación: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int idAsignacion) throws SQLException {
        String sql = "DELETE FROM Asignaciones WHERE id_asignacion = ?";
        try (Connection conn = ConexionDB.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idAsignacion);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            // Nota: Aquí es raro que de error 1451 porque Asignación suele ser el nivel final
            if (e.getErrorCode() == 1451) {
                throw new SQLException("No se puede eliminar la asignación porque está referenciada en otros registros.");
            }
            throw e;
        }
    }

    @Override
    public Asignacion obtenerPorId(int idAsignacion) {
        String sql = "SELECT * FROM Asignaciones WHERE id_asignacion = ?";
        Asignacion asignacion = null;

        try (Connection conn = ConexionDB.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idAsignacion);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                asignacion = new Asignacion();
                asignacion.setIdAsignacion(rs.getInt("id_asignacion"));
                asignacion.setFecha(rs.getObject("fecha", Date.class));
                asignacion.setActividad(Actividad.valueOf(rs.getString("especialidad")));
                asignacion.setIdAnimal(rs.getInt("id_animal"));
                asignacion.setIdVoluntario(rs.getInt("id_voluntario"));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener asignación por ID: " + e.getMessage());
        }
        return asignacion;
    }

    @Override
    public List<Asignacion> obtenerTodos(int idRefugioActual) {
        // Usamos un JOIN con la tabla Animales para saber a qué refugio pertenece el animal asignado
        String sql = "SELECT asig.* FROM Asignaciones asig " +
                     "JOIN Animales ani ON asig.id_animal = ani.id_animal " +
                     "WHERE ani.id_refugio = ?";

        List<Asignacion> lista = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idRefugioActual);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Asignacion asignacion = new Asignacion();
                asignacion.setIdAsignacion(rs.getInt("id_asignacion"));
                asignacion.setFecha(rs.getDate("fecha"));

                // Asegúrate que la columna en la DB se llame "actividad"
                asignacion.setActividad(Actividad.valueOf(rs.getString("actividad")));

                asignacion.setIdAnimal(rs.getInt("id_animal"));
                asignacion.setIdVoluntario(rs.getInt("id_voluntario"));

                lista.add(asignacion); // Indispensable para que no devuelva lista vacía
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener asignaciones del refugio: " + e.getMessage());
        }
        return lista;
    }
    
    @Override
    public List<Object[]> obtenerDatosParaTablaAsignaciones(int idRefugio) { // Agregamos el parámetro
        List<Object[]> lista = new ArrayList<>();

        // Agregamos el WHERE ani.id_refugio = ?
        String sql = "SELECT asig.id_asignacion, asig.fecha, asig.actividad, " +
                     "asig.id_animal, ani.nombre AS nombre_animal, " + 
                     "asig.id_voluntario, vol.nombre AS nombre_voluntario " + 
                     "FROM Asignaciones asig " +
                     "JOIN Animales ani ON asig.id_animal = ani.id_animal " +
                     "JOIN Voluntarios vol ON asig.id_voluntario = vol.id_voluntario " +
                     "WHERE ani.id_refugio = ?"; // Solo trae los de este refugio

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idRefugio); // Seteamos el ID del refugio

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Object[] fila = {
                        rs.getInt("id_asignacion"),      
                        rs.getDate("fecha"),             
                        rs.getString("actividad"),       
                        rs.getInt("id_animal"),          
                        rs.getString("nombre_animal"),
                        rs.getInt("id_voluntario"),      
                        rs.getString("nombre_voluntario")
                    };
                    lista.add(fila);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al filtrar asignaciones por refugio: " + e.getMessage());
        }
        return lista;
    }
    
    public List<Object[]> obtenerAsignacionesPorFiltroActividad(String actividad, int id) {
        List<Object[]> lista = new ArrayList<>();
        // Mantenemos el JOIN para que la tabla sea legible
        String sql = "SELECT asig.id_asignacion, asig.fecha, asig.actividad, " +
                     "asig.id_animal, ani.nombre AS nombre_animal, " + 
                     "asig.id_voluntario, vol.nombre AS nombre_voluntario " + 
                     "FROM Asignaciones asig " +
                     "JOIN Animales ani ON asig.id_animal = ani.id_animal " +
                     "JOIN Voluntarios vol ON asig.id_voluntario = vol.id_voluntario " +
                     "WHERE ani.id_refugio = ? AND asig.actividad LIKE ?"; // Filtro por actividad

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.setString(2, "%" + actividad + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] fila = {
                    rs.getInt("id_asignacion"),
                    rs.getDate("fecha"),
                    rs.getString("actividad"),
                    rs.getInt("id_animal"),
                    rs.getString("nombre_animal"),
                    rs.getInt("id_voluntario"),
                    rs.getString("nombre_voluntario")
                };
                lista.add(fila);
            }
        } catch (SQLException e) {
            System.err.println("Error al filtrar asignaciones por actividad: " + e.getMessage());
        }
        return lista;
    }
    
    @Override
    public List<Object[]> obtenerAsignacionesPorFiltroVoluntario(String nombreVoluntario, int id) {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT asig.id_asignacion, asig.fecha, asig.actividad, " +
                     "asig.id_animal, ani.nombre AS nombre_animal, " + 
                     "asig.id_voluntario, vol.nombre AS nombre_voluntario " + 
                     "FROM Asignaciones asig " +
                     "JOIN Animales ani ON asig.id_animal = ani.id_animal " +
                     "JOIN Voluntarios vol ON asig.id_voluntario = vol.id_voluntario " +
                     // CAMBIO AQUÍ: Filtramos por el nombre en la tabla Voluntarios (vol)
                     "WHERE ani.id_refugio = ? AND vol.nombre LIKE ?"; 

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(2, "%" + nombreVoluntario + "%");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] fila = {
                    rs.getInt("id_asignacion"),
                    rs.getDate("fecha"),
                    rs.getString("actividad"),
                    rs.getInt("id_animal"),
                    rs.getString("nombre_animal"),
                    rs.getInt("id_voluntario"),
                    rs.getString("nombre_voluntario")
                };
                lista.add(fila);
            }
        } catch (SQLException e) {
            System.err.println("Error al filtrar asignaciones por animal: " + e.getMessage());
        }
        return lista;
    }
    
    @Override
    public List<Object[]> obtenerAsignacionesPorFiltroAnimal(String animal, int id) {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT asig.id_asignacion, asig.fecha, asig.actividad, " +
                     "asig.id_animal, ani.nombre AS nombre_animal, " + 
                     "asig.id_voluntario, vol.nombre AS nombre_voluntario " + 
                     "FROM Asignaciones asig " +
                     "JOIN Animales ani ON asig.id_animal = ani.id_animal " +
                     "JOIN Voluntarios vol ON asig.id_voluntario = vol.id_voluntario " +
                     // CAMBIO AQUÍ: Filtramos por el nombre en la tabla Voluntarios (vol)
                     "WHERE ani.id_refugio = ? AND ani.nombre LIKE ?"; 

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.setString(2, "%" + animal + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] fila = {
                    rs.getInt("id_asignacion"),
                    rs.getDate("fecha"),
                    rs.getString("actividad"),
                    rs.getInt("id_animal"),
                    rs.getString("nombre_animal"),
                    rs.getInt("id_voluntario"),
                    rs.getString("nombre_voluntario")
                };
                lista.add(fila);
            }
        } catch (SQLException e) {
            System.err.println("Error al filtrar asignaciones por animal: " + e.getMessage());
        }
        return lista;
    }
    
}
