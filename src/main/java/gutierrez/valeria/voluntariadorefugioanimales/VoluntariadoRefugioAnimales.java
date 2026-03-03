/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package gutierrez.valeria.voluntariadorefugioanimales;
import controllers.RefugioController;
import config.ConexionDB;
import controllers.AnimalController;
import enums.EstadoSalud;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Year;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import models.Animal;

/**
 *
 * @author valeria
 */
public class VoluntariadoRefugioAnimales {

    public static void main(String[] args) {
        
        /*
        
        try(Connection con = ConexionDB.getConnection()) {
            System.out.println("Conexion establecida con exito: "+con);
        } catch(SQLException e){
            System.err.println("Error de conexion "+e.getMessage());
        }
        
        */
        
        
        RefugioController controller = new RefugioController();

        System.out.println("=== PRUEBAS FUNCIONALES: RefugioController ===");

        // 1. PRUEBA: Agregar Refugio Válido
        System.out.println("\n[Test 1] Agregando refugio válido...");
        boolean insertado = controller.agregarRefugio("Patitas Felices", 50, "Calle Falsa 123", "Juan Pérez");
        System.out.println("Resultado esperado (true): " + insertado);

        // 2. PRUEBA: Agregar Refugio con Capacidad Inválida (Negativa)
        System.out.println("\n[Test 2] Agregando refugio con capacidad -5...");
        boolean capacidadInvalida = controller.agregarRefugio("Refugio Error", -5, "Centro", "Maria");
        System.out.println("Resultado esperado (false): " + capacidadInvalida);

        // 3. PRUEBA: Agregar con Nombre Vacío
        System.out.println("\n[Test 3] Agregando refugio sin nombre...");
        boolean nombreVacio = controller.agregarRefugio("", 10, "Norte", "Pedro");
        System.out.println("Resultado esperado (false): " + nombreVacio);

        // 4. PRUEBA: Obtener Tabla Completa
        System.out.println("\n[Test 4] Obteniendo modelo de tabla...");
        DefaultTableModel modelo = controller.obtenerTablaRefugios();
        System.out.println("Columnas detectadas: " + modelo.getColumnCount());
        System.out.println("Filas recuperadas: " + modelo.getRowCount());

        // 5. PRUEBA: Editar Refugio (Asumiendo que el ID 1 existe)
        System.out.println("\n[Test 5] Editando refugio ID 1...");
        boolean editado = controller.editarRefugio(1, "Patitas Editadas", 60, "Nueva Ubicacion", "Juan P.");
        System.out.println("¿Editado con éxito?: " + editado);

        // 6. PRUEBA: Filtrado por Nombre
        System.out.println("\n[Test 6] Probando filtro por nombre 'Patitas'...");
        DefaultTableModel tablaFiltrada = controller.obtenerTablaRefugiosFiltroNombre("Patitas");
        if (tablaFiltrada.getRowCount() > 0) {
            System.out.println("Éxito: Se encontró al menos un refugio con ese nombre.");
        } else {
            System.out.println("Aviso: No se encontraron coincidencias (verificar datos en BD).");
        }

        // 7. PRUEBA: Eliminar con ID inválido
        System.out.println("\n[Test 7] Eliminando ID inexistente (0 o negativo)...");
        try {
            boolean eliminado = controller.eliminarRefugio(-1);
            System.out.println("Resultado esperado (false): " + eliminado);
        } catch (SQLException e) {
            System.err.println("Error de SQL inesperado: " + e.getMessage());
        }

        System.out.println("\n=== FIN DE LAS PRUEBAS ===");
        
        
        AnimalController Acontroller = new AnimalController();

        System.out.println("=== PRUEBAS FUNCIONALES: AnimalController ===");

        // 1. PRUEBA: Agregar Animal Válido
        // NOTA: Asegúrate de que el ID de refugio 1 exista en tu BD
        System.out.println("\n[Test 1] Agregando animal válido (Rex)...");
        boolean insertado1 = Acontroller.agregarAnimal("Rex", "Perro", EstadoSalud.SANO, Year.of(2022), new Date(), 1);
        System.out.println("Resultado esperado (true): " + insertado1);

        // 2. PRUEBA: Validación de Año Futuro
        System.out.println("\n[Test 2] Agregando animal con año de nacimiento inválido (2030)...");
        boolean anioInvalido = Acontroller.agregarAnimal("Miau", "Gato", EstadoSalud.SANO, Year.of(2030), new Date(), 1);
        System.out.println("Resultado esperado (false): " + anioInvalido);

        // 3. PRUEBA: Validación de Refugio Inexistente (ID 999)
        System.out.println("\n[Test 3] Agregando animal en refugio que no existe (ID 999)...");
        boolean refugioInexistente = Acontroller.agregarAnimal("Boby", "Perro", EstadoSalud.EN_TRATAMIENTO, Year.of(2021), new Date(), 999);
        System.out.println("Resultado esperado (false): " + refugioInexistente);

        // 4. PRUEBA: Obtener Tabla de Animales por Refugio
        System.out.println("\n[Test 4] Obteniendo tabla de animales para el refugio 1...");
        DefaultTableModel modelo1 = Acontroller.obtenerTablaAnimales(1);
        System.out.println("Columnas detectadas: " + modelo1.getColumnCount());
        System.out.println("Animales encontrados en tabla: " + modelo1.getRowCount());

        // 5. PRUEBA: Editar datos de un animal
        // Asumiendo que el ID 1 es el animal que acabamos de insertar
        System.out.println("\n[Test 5] Editando animal ID 1 (Cambio de estado a EN_TRATAMIENTO)...");
        boolean editado1 = Acontroller.editarAnimal(1, "Rex Editado", "Perro", EstadoSalud.EN_TRATAMIENTO, Year.of(2022), new Date(), 1);
        System.out.println("¿Editado con éxito?: " + editado1);

        // 6. PRUEBA: Filtrado por nombre de animal
        System.out.println("\n[Test 6] Buscando animales con nombre 'Rex' en refugio 1...");
        DefaultTableModel tablaFiltro = Acontroller.obtenerTablaAnimalesFiltradoNombre("Rex", 1);
        System.out.println("Coincidencias encontradas: " + tablaFiltro.getRowCount());

        // 7. PRUEBA: Eliminar animal con ID inválido
        System.out.println("\n[Test 7] Intentando eliminar ID de animal negativo...");
        try {
            boolean eliminado = Acontroller.eliminarAnimal(-5);
            System.out.println("Resultado esperado (false): " + eliminado);
        } catch (SQLException e) {
            System.err.println("Error de SQL: " + e.getMessage());
        }

        System.out.println("\n=== FIN DE LAS PRUEBAS DE ANIMALES ===");
    }
        
        
    }

