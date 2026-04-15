package DAO;// Archivo: ArticuloDAO.java
import POJO.Articulo;

import java.sql.*;
import java.util.ArrayList;

public class ArticuloDAO implements InterfazDAO<Articulo> {


    @Override
    public ArrayList<Articulo> obtenerTodos() {
        ArrayList<Articulo> lista = new ArrayList<>();
        String sql = "SELECT id_articulo, nombre, precio, id_fab FROM articulos";

        // Abrimos la tubería en el propio DAO mediante nuestra clase de apoyo DAO.ConexionBD
        try (Connection con = ConexionBD.getInstancia().conectar();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                // Traducimos de Relacional a Orientado a Objetos (Mapeo)
                Articulo a = new Articulo(
                        rs.getInt("id_articulo"),
                        rs.getString("nombre"),
                        rs.getInt("precio"),
                        rs.getInt("id_fab")
                );
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
        String sql = "SELECT id_articulo, nombre, precio, id_fab FROM articulos where id_articulo=?";
        // Abrimos la tubería en el propio DAO mediante nuestra clase de apoyo DAO.ConexionBD
        try (Connection con = ConexionBD.getInstancia().conectar();
             PreparedStatement pstmt = con.prepareStatement(sql))
              {
               pstmt.setInt(1,id);
               try (ResultSet rs = pstmt.executeQuery()) {
                   if (rs.next()) {
                       // Traducimos de Relacional a Orientado a Objetos (Mapeo)
                       a = new Articulo(
                               rs.getInt("id_articulo"),
                               rs.getString("nombre"),
                               rs.getInt("precio"),
                               rs.getInt("id_fab")
                       );
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
        String sql = "SELECT id_articulo, nombre, precio, id_fab FROM articulos where nombre = ?";
        // Abrimos la tubería en el propio DAO mediante nuestra clase de apoyo DAO.ConexionBD
        try (Connection con = ConexionBD.getInstancia().conectar();
             PreparedStatement pstmt = con.prepareStatement(sql))
        {
            pstmt.setString(1,nombre);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Traducimos de Relacional a Orientado a Objetos (Mapeo)
                    a = new Articulo(
                            rs.getInt("id_articulo"),
                            rs.getString("nombre"),
                            rs.getInt("precio"),
                            rs.getInt("id_fab")
                    );
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
            pstmt.setInt(4, art.getIdFabricante());

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