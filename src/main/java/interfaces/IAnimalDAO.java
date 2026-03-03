/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;

import java.sql.SQLException;
import java.util.List;
import models.Animal;

/**
 *
 * @author valeria
 */
public interface IAnimalDAO {
    boolean insertar(Animal animal);
    boolean actualizar(Animal animal);
    boolean eliminar(int idAnimal) throws SQLException; // THROWS SQLEXCPETION
    Animal obtenerPorId(int idAnimal);
    List<Animal> obtenerTodos(int id);
    int contarAnimalesPorRefugio(int idRefugio);
    List<Animal> obtenerTodosFiltroNombre(String nombre, int id);
}
