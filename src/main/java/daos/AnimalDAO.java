/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import config.ConexionDB;
import enums.EstadoSalud;
import interfaces.IAnimalDAO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import models.Animal;

/**
 *
 * @author valeria
 */
public class AnimalDAO implements IAnimalDAO {

    @Override
    public boolean insertar(Animal animal) {
        String sql = "INSERT INTO Animales (nombre, especie, estado_salud, anioNacimiento, fecha_ingreso, id_refugio) VALUES (?, ?, ?, ?, ?, ?) ";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, animal.getNombre());
            ps.setString(2, animal.getEspecie());
            ps.setString(3, animal.getEstadoSalud().name());
            ps.setInt(4, animal.getAnioNac().getValue());
            ps.setDate(5, new java.sql.Date(animal.getFechaIng().getTime()));
            ps.setInt(6, animal.getIdRefugio());

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.err.println("Error al insertar animal: " + ex.getMessage());
            return false;
        }
        
    }

    @Override
    public boolean actualizar(Animal animal) {
        String sql = """
            UPDATE Animales
            SET nombre = ?, especie = ?, estado_salud = ?, anioNacimiento = ?, fecha_ingreso = ?, id_refugio = ?
            WHERE id_animal = ?
        """;

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, animal.getNombre());
            ps.setString(2, animal.getEspecie());
            ps.setString(3, animal.getEstadoSalud().name());
            ps.setInt(4, animal.getAnioNac().getValue());
            ps.setDate(5, new java.sql.Date(animal.getFechaIng().getTime()));
            ps.setInt(6, animal.getIdRefugio());
            ps.setInt(7, animal.getIdAnimal());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar animal: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int idAnimal) throws SQLException{
        String sql = "DELETE FROM Animales WHERE id_animal = ?";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idAnimal);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            if(e.getErrorCode()==1451){
                throw new SQLException("No se puede eliminar el cliente porque tiene problemas asociados.");        
            }
            
            throw e;
        }
    }
    
    @Override
    public Animal obtenerPorId(int idAnimal) {
        String sql = "SELECT * FROM Animales WHERE id_animal = ?";
        Animal animal = null;

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idAnimal);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                animal = new Animal();
                animal.setIdAnimal(rs.getInt("id_animal"));
                animal.setNombre(rs.getString("nombre"));
                animal.setEspecie(rs.getString("especie"));
                animal.setEstadoSalud(EstadoSalud.valueOf(rs.getString("estado_salud")));
                animal.setAnioNac(Year.of(rs.getInt("anioNacimiento")));
                animal.setFechaIng(rs.getObject("fecha_ingreso", Date.class));
                animal.setIdRefugio(rs.getInt("id_refugio"));
            }

        } catch (SQLException ex) {
            System.err.println("Error al obtener animal: " + ex.getMessage());
        }
        return animal;
    }

    @Override
    public List<Animal> obtenerTodos(int id) {
        String sql = "SELECT id_animal, nombre, especie, estado_salud, anioNacimiento, fecha_ingreso, id_refugio FROM Animales WHERE id_refugio = ?";
        List<Animal> lista = new ArrayList<>();

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                try {
                    Animal animal = new Animal();
                    animal.setIdAnimal(rs.getInt("id_animal"));
                    animal.setNombre(rs.getString("nombre"));
                    animal.setEspecie(rs.getString("especie"));

                    // 1. Manejo del ENUM (SANO, OBSERVACION, etc.)
                    String estadoStr = rs.getString("estado_salud");
                    if (estadoStr != null) {
                        animal.setEstadoSalud(EstadoSalud.valueOf(estadoStr.trim().toUpperCase()));
                    }

                    // 2. Manejo del tipo YEAR
                    int anioVal = rs.getInt("anioNacimiento");
                    if (!rs.wasNull() && anioVal > 0) {
                        animal.setAnioNac(Year.of(anioVal));
                    }

                    // 3. Manejo de la fecha
                    animal.setFechaIng(rs.getDate("fecha_ingreso"));

                    animal.setIdRefugio(rs.getInt("id_refugio"));

                    lista.add(animal);

                } catch (Exception e) {
                    System.err.println("Error procesando fila del animal: " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.err.println("Error de SQL: " + e.getMessage());
        }
        return lista;
    }
    
    @Override
    public List<Animal> obtenerTodosFiltroNombre(String nombre, int id) {
        String sql = "SELECT * FROM Animales WHERE nombre LIKE ? AND id_refugio = ?";
        List<Animal> lista = new ArrayList<>();

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + nombre + "%");
            ps.setInt(2, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                try {
                    Animal animal = new Animal();
                    animal.setIdAnimal(rs.getInt("id_animal"));
                    animal.setNombre(rs.getString("nombre"));
                    animal.setEspecie(rs.getString("especie"));

                    // 1. Manejo seguro del Enum (evita errores si hay espacios o minúsculas)
                    String estadoStr = rs.getString("estado_salud");
                    if (estadoStr != null) {
                        animal.setEstadoSalud(EstadoSalud.valueOf(estadoStr.trim().toUpperCase()));
                    }

                    // 2. Manejo seguro del tipo YEAR (evita error Year.of(0))
                    int anioVal = rs.getInt("anioNacimiento");
                    if (!rs.wasNull() && anioVal > 0) {
                        animal.setAnioNac(Year.of(anioVal));
                    }

                    // 3. Uso de getDate en lugar de getObject para estabilidad
                    animal.setFechaIng(rs.getDate("fecha_ingreso"));

                    animal.setIdRefugio(rs.getInt("id_refugio"));

                    lista.add(animal);

                } catch (Exception e) {
                    // Si un animal específico tiene un dato corrupto, te avisará pero seguirá con el siguiente
                    System.err.println("Error procesando animal '" + rs.getString("nombre") + "': " + e.getMessage());
                }
            }

        } catch (SQLException e) {
            System.err.println("Error de SQL al filtrar por nombre: " + e.getMessage());
        }
        return lista;
    }
    
    @Override
    public int contarAnimalesPorRefugio(int idRefugio) {
        String sql = "SELECT COUNT(*) FROM Animales WHERE id_refugio = ?";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idRefugio);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1); // Retorna el número de filas encontradas
            }
        } catch (SQLException e) {
            System.err.println("Error al contar animales: " + e.getMessage());
        }
        return 0;
    }
        
}
    
    
   