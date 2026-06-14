import DAO.*;
import POJO.*;

import java.util.ArrayList;

public class AppMongo {
    public static void main(String[] args) {

        ArticuloMongoDAO articuloDAO = new ArticuloMongoDAO();

        System.out.println("===== PRUEBA CRUD COMPLETA (MongoDB) =====");


        //Imprimir fabricantes:

        System.out.println(articuloDAO.obtenerFabricantes());

        // ---------------------------------------------------------
        // 1. INSERTAR O ACTUALIZAR ARTÍCULOS
        // ---------------------------------------------------------
        System.out.println("\n--- INSERTANDO / ACTUALIZANDO ARTÍCULOS ---");

        Fabricante fab1 = new Fabricante(1, "Sony");

        ArrayList<Pieza> piezas1 = new ArrayList<>();
        piezas1.add(new Pieza(1, "Pantalla"));
        piezas1.add(new Pieza(3, "Placa Base"));

        Articulo a1 = new Articulo(1, "Televisor 4K", 499, fab1);
        a1.setPiezas(piezas1);

        Fabricante fab2 = new Fabricante(2, "Samsung");

        ArrayList<Pieza> piezas2 = new ArrayList<>();
        piezas2.add(new Pieza(1, "Pantalla"));
        piezas2.add(new Pieza(2, "Batería"));

        Articulo a2 = new Articulo(2, "Smartphone", 899, fab2);
        a2.setPiezas(piezas2);

        guardarSeguro(articuloDAO, a1);
        guardarSeguro(articuloDAO, a2);

        System.out.println("Artículos actuales:");
        articuloDAO.obtenerTodos().forEach(System.out::println);

        // ---------------------------------------------------------
        // 2. CONSULTAR PIEZAS
        // ---------------------------------------------------------
        System.out.println("\n--- CONSULTANDO PIEZAS ---");

        articuloDAO.obtenerPorId(1).getPiezas().forEach(System.out::println);

        // ---------------------------------------------------------
        // 3. ACTUALIZAR ARTÍCULO
        // ---------------------------------------------------------
        System.out.println("\n--- ACTUALIZANDO ARTÍCULO 1 ---");

        Articulo artUpdate = articuloDAO.obtenerPorId(1);
        artUpdate.setPrecio(399);

        articuloDAO.actualizar(artUpdate);

        System.out.println(articuloDAO.obtenerPorId(1));

        // ---------------------------------------------------------
        // 4. MODIFICAR PIEZAS
        // ---------------------------------------------------------
        System.out.println("\n--- MODIFICANDO PIEZAS ---");

        Articulo artMod = articuloDAO.obtenerPorId(1);

        artMod.getPiezas().removeIf(p -> p.getNombre().equals("Placa Base"));

        articuloDAO.actualizar(artMod);

        articuloDAO.obtenerPorId(1).getPiezas().forEach(System.out::println);

        // ---------------------------------------------------------
        // 5. ELIMINAR ARTÍCULO
        // ---------------------------------------------------------
        System.out.println("\n--- ELIMINANDO ARTÍCULO 2 ---");

        articuloDAO.eliminar(2);

        articuloDAO.obtenerTodos().forEach(System.out::println);

        System.out.println("\n===== FIN DE PRUEBA CRUD =====");
    }

    private static void guardarSeguro(ArticuloMongoDAO dao, Articulo art) {

        if (dao.obtenerPorId(art.getIdArticulo()) == null) {
            dao.insertar(art);     // no existe → insertar
        } else {
            dao.actualizar(art);   // existe → actualizar
        }
    }
}