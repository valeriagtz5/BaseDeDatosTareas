/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author LABORATORIOS
 */
public class ConexionDB {
    private static String url;
    private static String user;
    private static String password;
    private static String driver;
    
    static {
        try (InputStream input = ConexionDB.class.getClassLoader().getResourceAsStream("db.properties")) {
            Properties props = new Properties();
            
            if (input == null) {
                throw new RuntimeException("No se lograron cargar las variables del entorno");
            }
            
            props.load(input);
            
            url = props.getProperty("db.url");
            user = props.getProperty("db.user");
            password = props.getProperty("db.password");
            driver = props.getProperty("db.driver");
            
            Class.forName(driver);
        } catch (IOException | ClassNotFoundException ex){
            throw new RuntimeException("No se lograron cargar las variables del entorno");
        }
    }
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
