/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gutierrez.valeria.tiendajpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 *
 * @author valeria
 */
public class ProductoDAO {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("MyPU");
    
    public void insertar(Producto producto){
        EntityManager em = emf.createEntityManager(); // Crea el gestor: Nueva sesion
        em.getTransaction().begin(); // Avisar a BD que se va a hacer algo
        em.persist(producto); // Toma el objeto y lo guarda. Se usa para objetos nuevos
        em.getTransaction().commit(); // Confirma y aplica en BD lo que se realizo durante la transaccion
        em.close(); // Cierra el gestor
    }
    
    public Producto buscar(int id){ // Operacion de lectura
        EntityManager em = emf.createEntityManager(); // Crear el gestor
        Producto p = em.find(Producto.class, id); 
        /* Busca en BD un registro que coincida con el id
           Lo convierte en un objeto java
        */
        em.close();
        return p;
    }
    /*
        Las transacciones se necesitan para escribir (insertar, actualizar, borrar). 
        Como solo estás "leyendo" datos de la base de datos sin modificar nada, 
        JPA no requiere una transacción activa.
    */
    
    public void actualizar(Producto p){
        EntityManager em = emf.createEntityManager(); // Crea el gestor: Nueva sesion
        em.getTransaction().begin(); // Le avisa a la base de datos que va a hacer algo
        em.merge(p); 
        /* Busca objeto en la BD con el mismo id
            - Si lo encuentra actualiza
            - Si no lo encuentra crea uno nuevo
        
           Se usa para objetos que ya existen
        */
        em.getTransaction().commit(); // Guarda cambios de forma permanente. Si algo falla antes no se guarda
        em.close(); // Cierra el gestor: Libera la memoria y la conexión a BD. Para no agotar recursos
    }
    
    public void eliminar(int id){
        EntityManager em = emf.createEntityManager(); // Crea el gestor
        Producto p = em.find(Producto.class, id); // Busca un registro que coincida con el id y lo traduce a objeto de java
        em.getTransaction().begin(); // Le dice a la BD que va a hacer algo
        em.remove(p);
        em.getTransaction().commit(); // Confirma los cambios de forma permanente
        em.close(); // Cierra el gestor
    }
    
    
}
