package DAO;// Archivo: ArticuloDAO.java
import POJO.Articulo;
import POJO.Fabricante;
import DAO.PiezaDAO;

import java.sql.*;
import java.util.ArrayList;

public class ArticuloDAO implements InterfazDAO<Articulo> {

    PiezaDAO piezaDAO = new PiezaDAO();

    @Override
    public ArrayList<Articulo> obtenerTodos() {
        //forma 1
        ArrayList<Articulo> lista = new ArrayList<>();
        String sql = "SELECT a.id_articulo, a.nombre, a.precio, f.id_fabricante, f.nombre" +
                " FROM articulos a INNER JOIN fabricante f on a.id_fab=f.id_fabricante";
        // Abrimos la tubería en el propio DAO mediante nuestra clase de apoyo DAO.ConexionBD
        try (Connection con = ConexionBD.getInstancia().conectar();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                // Traducimos de Relacional a Orientado a Objetos (Mapeo)

                Articulo a = new Articulo(
                        rs.getInt("a.id_articulo"),
                        rs.getString("a.nombre"),
                        rs.getInt("a.precio"),
                        new Fabricante(rs.getInt("f.id_fabricante"),
                                rs.getString("f.nombre")
                                )
                );

                a.setPiezas(piezaDAO.obtenerTodosArticulo(a.getIdArticulo()));
                lista.add(a); // Añadimos a la lista
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener artículos: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public Articulo obtenerPorId(int id) {
        Articulo a = null;
        String sql = "SELECT a.id_articulo, a.nombre, a.precio, a.id_fab, f.nombre FROM articulos a inner join fabricante f on a.id_fab = f.id_fabricante where a.id_articulo=?";
        // Abrimos la tubería en el propio DAO mediante nuestra clase de apoyo DAO.ConexionBD
        try (Connection con = ConexionBD.getInstancia().conectar();
             PreparedStatement pstmt = con.prepareStatement(sql))
              {
               pstmt.setInt(1,id);
               try (ResultSet rs = pstmt.executeQuery()) {
                   if (rs.next()) {
                       // Traducimos de Relacional a Orientado a Objetos (Mapeo)
                       a = new Articulo(
                               rs.getInt("a.id_articulo"),
                               rs.getString("a.nombre"),
                               rs.getInt("a.precio"),
                               new Fabricante(rs.getInt("a.id_fab"),
                                       rs.getString("f.nombre")));

                   }
               }
        } catch (SQLException e) {
            System.err.println("Error al obtener el artículo con id : " +id + " " + e.getMessage());
        }
        return a;
    }

    @Override
    public Articulo obtenerPorNombre(String nombre) {
        Articulo a = null;
        String sql = "SELECT a.id_articulo, a.nombre, a.precio, a.id_fab, f.nombre FROM articulos a inner join fabricante f on a.id_fab = f.id_fabricante where  a.nombre = ?";
        // Abrimos la tubería en el propio DAO mediante nuestra clase de apoyo DAO.ConexionBD
        try (Connection con = ConexionBD.getInstancia().conectar();
             PreparedStatement pstmt = con.prepareStatement(sql))
        {
            pstmt.setString(1,nombre);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Traducimos de Relacional a Orientado a Objetos (Mapeo)
                    a = new Articulo(
                            rs.getInt("a.id_articulo"),
                            rs.getString("a.nombre"),
                            rs.getInt("a.precio"),
                            new Fabricante(rs.getInt("a.id_fab"),
                                    rs.getString("f.nombre")));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el artículo con nombre : " +nombre + " " + e.getMessage());
        }
        return a;
    }

    @Override
    public boolean insertar(Articulo art) {
        String sql = "INSERT INTO articulos (id_articulo, nombre, precio, id_fab) VALUES (?, ?, ?, ?)";

        try (Connection con = ConexionBD.getInstancia().conectar();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, art.getIdArticulo());
            pstmt.setString(2, art.getNombre());
            pstmt.setInt(3, art.getPrecio());
            pstmt.setInt(4, art.getFabricante().getId_fab());

            return pstmt.executeUpdate() > 0; // Devuelve true si afectó a alguna fila
        } catch (SQLException e) {
            System.err.println("Error al insertar: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean actualizar(Articulo art) {
        String sql = "UPDATE articulos SET nombre=?, precio=? WHERE id_articulo=?";

        try (Connection con = ConexionBD.getInstancia().conectar();
             PreparedStatement pstmt = con.prepareStatement(sql)) {


            pstmt.setString(1, art.getNombre());
            pstmt.setInt(2, art.getPrecio());
            pstmt.setInt(3, art.getIdArticulo());


            return pstmt.executeUpdate() > 0; // Devuelve true si afectó a alguna fila
        } catch (SQLException e) {
            System.err.println("Error al insertar: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int id){
        String sql = "DELETE FROM articulos WHERE id_articulo = ?";

        try(Connection con = ConexionBD.getInstancia().conectar();
            PreparedStatement pstmt = con.prepareStatement(sql)){

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;

        }catch (SQLException e){
            System.out.println("Error al eliminar el articulo con id " + id + " " + e.getMessage());
            return  false;
        }

    }
}