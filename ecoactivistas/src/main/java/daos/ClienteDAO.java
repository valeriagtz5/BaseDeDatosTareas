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
import models.Cliente;

/**
 *
 * @author LABORATORIOS
 */
public class ClienteDAO implements IClienteDAO {

    @Override
    public boolean insertar(Cliente cliente) {
        String sql = "INSERT INTO cliente(nombre,direccion,telefonos) VALUES (?,?,?)";
        
        try (
                Connection con = ConexionDB.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
            ){
            
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getDireccion());
            ps.setString(3, cliente.getTelefono());
            
            return ps.executeUpdate() > 0;
            
        }catch(SQLException ex){
            System.out.println("Error al intentar insertar al cliente: "+ex.getMessage());
            return false;
        }
    }
    

    @Override
    public Cliente obtenerPorId(int id) {
        String sql = "SELECT * FROM cliente WHERE idCliente=?";
        Cliente cliente = null;
        
        try (
                Connection con = ConexionDB.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
            ){
            
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("idCliente"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setTelefono(rs.getString("telefono"));
            }
            
        }catch(SQLException ex){
            System.err.println("Error al intentar obtener al cliente: "+ex.getMessage());
        }
        return cliente;
       
    }

    @Override
    public List<Cliente> obtenerTodos() {
        String sql = "SELECT * FROM Cliente";
        List<Cliente> lista = new ArrayList<>();
        
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("idCliente"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setTelefono(rs.getString("telefono"));
                lista.add(cliente);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los clientes: "+ e.getMessage());
        }
        return lista;
    }

    @Override
    public boolean actualizar(Cliente cliente) {
        String sql = "UPDATE Cliente SET nombre = ?, direccion = ?, telefono = ? where idCliente = ?";
        
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getDireccion());
            ps.setString(3, cliente.getTelefono());
            ps.setInt(4, cliente.getIdCliente());
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar al cliente: "+ e.getMessage());
            return false;
        }
        
        
    }

    @Override
    public boolean eliminar(int idCliente) {
        String sql = "DELETE FROM Cliente WHERE idCliente = ?";
        
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idCliente);
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar al cliente: "+ e.getMessage());
            return false;
        }
    }   
}
