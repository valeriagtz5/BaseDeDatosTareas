/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package gutierrez.valeria.tiendajpa;

/**
 *
 * @author valeria
 */
public class TiendaJPA {

    public static void main(String[] args) {
        //Instancia
        ProductoDAO productoDAO = new ProductoDAO();
        //Objetos
        Producto p1 = new Producto(0,"Papita",19.90); // 1
        Producto p2 = new Producto(0,"Galletas",21.90); // 2
        Producto p3 = new Producto(0,"CocaCola",25.90); // 3
        Producto p4 = new Producto(0,"Gansito",15.90); // 4
        //Insertar
        productoDAO.insertar(p1);
        productoDAO.insertar(p2);
        productoDAO.insertar(p3);
        productoDAO.insertar(p4);
        //Buscar
        Producto pBuscado = productoDAO.buscar(3); 
        System.out.println("Producto buscado: "+pBuscado.getNombre()); // CocaCola
        
        pBuscado.setPrecio(26.90); // Cambio en memoria, no afecta a la base de datos
        productoDAO.actualizar(pBuscado); // Cocacola a 26.90
        productoDAO.eliminar(4); // Cambio en base de datos. Cambio permanente
        
    }
}
