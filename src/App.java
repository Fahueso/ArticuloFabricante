
import DAO.*;
import POJO.*;

import java.sql.Connection;
import java.sql.SQLException;


public class App {
    public static void main(String[] args) throws SQLException {

        Connection con = ConexionBD.getInstancia().getConexion();

        FabricanteDAO fabricanteDAO = new FabricanteDAO();
        PiezaDAO piezaDAO = new PiezaDAO();
        ArticuloDAO articuloDAO = new ArticuloDAO();
        ArticuloPiezaDAO apDAO = new ArticuloPiezaDAO();



        System.out.println("===== PRUEBA CRUD COMPLETA =====");

        // ---------------------------------------------------------
        // 1. INSERTAR FABRICANTES
        // ---------------------------------------------------------
        System.out.println("\n--- INSERTANDO FABRICANTES ---");

        fabricanteDAO.insertar(new Fabricante(1, "Sony"));
        fabricanteDAO.insertar(new Fabricante(2, "Samsung"));

        System.out.println("Fabricantes actuales:");
        fabricanteDAO.obtenerTodos().forEach(System.out::println);

        // ---------------------------------------------------------
        // 2. INSERTAR PIEZAS
        // ---------------------------------------------------------
        System.out.println("\n--- INSERTANDO PIEZAS ---");

        piezaDAO.insertar(new Pieza(1, "Pantalla"));
        piezaDAO.insertar(new Pieza(2, "Batería"));
        piezaDAO.insertar(new Pieza(3, "Placa Base"));

        System.out.println("Piezas actuales:");
        piezaDAO.obtenerTodos().forEach(System.out::println);

        // ---------------------------------------------------------
        // 3. INSERTAR ARTÍCULOS
        // ---------------------------------------------------------
        System.out.println("\n--- INSERTANDO ARTÍCULOS ---");

        Articulo a1 = new Articulo(1, "Televisor 4K", 499, fabricanteDAO.obtenerPorId(1));
        Articulo a2 = new Articulo(2, "Smartphone", 899, fabricanteDAO.obtenerPorId(2));

        articuloDAO.insertar(a1);
        articuloDAO.insertar(a2);

        System.out.println("Artículos actuales:");
        articuloDAO.obtenerTodos().forEach(System.out::println);

        // ---------------------------------------------------------
        // 4. RELACIONAR ARTÍCULOS CON PIEZAS
        // ---------------------------------------------------------
        System.out.println("\n--- RELACIONANDO ARTÍCULOS Y PIEZAS ---");

        apDAO.insertar(new ArticuloPieza(1, 1)); // TV → Pantalla
        apDAO.insertar(new ArticuloPieza(1, 3)); // TV → Placa Base
        apDAO.insertar(new ArticuloPieza(2, 1)); // Smartphone → Pantalla
        apDAO.insertar(new ArticuloPieza(2, 2)); // Smartphone → Batería

        System.out.println("Piezas del artículo 1:");
        articuloDAO.obtenerPorId(1).getPiezas().forEach(System.out::println);

        System.out.println("Piezas del artículo 2:");
        articuloDAO.obtenerPorId(2).getPiezas().forEach(System.out::println);

        // ---------------------------------------------------------
        // 5. ACTUALIZAR ARTÍCULO
        // ---------------------------------------------------------
        System.out.println("\n--- ACTUALIZANDO ARTÍCULO 1 ---");

        Articulo artUpdate = articuloDAO.obtenerPorId(1);
        artUpdate.setPrecio(399);
        articuloDAO.actualizar(artUpdate);

        System.out.println("Artículo actualizado:");
        System.out.println(articuloDAO.obtenerPorId(1));

        // ---------------------------------------------------------
        // 6. ELIMINAR UNA RELACIÓN ARTÍCULO–PIEZA
        // ---------------------------------------------------------
        System.out.println("\n--- ELIMINANDO RELACIÓN ARTÍCULO–PIEZA (1,3) ---");

        apDAO.eliminar(new ArticuloPieza(1, 3));

        System.out.println("Piezas del artículo 1 tras eliminar:");
        articuloDAO.obtenerPorId(1).getPiezas().forEach(System.out::println);

        // ---------------------------------------------------------
        // 7. ELIMINAR ARTÍCULO COMPLETO
        // ---------------------------------------------------------
        System.out.println("\n--- ELIMINANDO ARTÍCULO 2 ---");

        articuloDAO.eliminar(2);

        System.out.println("Artículos actuales:");
        articuloDAO.obtenerTodos().forEach(System.out::println);

        // ---------------------------------------------------------
        // 8. ELIMINAR FABRICANTE
        // ---------------------------------------------------------
        System.out.println("\n--- ELIMINANDO FABRICANTE 2 ---");

        fabricanteDAO.eliminar(2);

        System.out.println("Fabricantes actuales:");
        fabricanteDAO.obtenerTodos().forEach(System.out::println);

        System.out.println("\n===== FIN DE PRUEBA CRUD =====");

        con.close();
    }
}