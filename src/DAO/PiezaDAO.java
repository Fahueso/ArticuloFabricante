package DAO;// Archivo: ArticuloDAO.java



import POJO.Articulo;
import POJO.Pieza;

import java.sql.*;
import java.util.ArrayList;

public class PiezaDAO implements InterfazDAO<Pieza> {


    private final Connection con;

    public PiezaDAO() throws SQLException{
        this.con = ConexionBD.getInstancia().getConexion();
    }


    public ArrayList<Pieza> obtenerTodosArticulo(int id){
        ArrayList<Pieza> lista = new ArrayList<>();
        String sql = "SELECT p.id_pieza,  p.nombre FROM piezas p where p.id_pieza= ? ";
        // Abrimos la tubería en el propio DAO mediante nuestra clase de apoyo DAO.ConexionBD
        try (PreparedStatement pstmt = con.prepareStatement(sql))
        {
            pstmt.setInt(1,id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Traducimos de Relacional a Orientado a Objetos (Mapeo)
                    Pieza a = new Pieza(

                            rs.getInt("id_pieza"),
                            rs.getString("nombre")
                    );
                    lista.add(a); // Añadimos a la lista
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener las piezas del articulo  " + id + "" +  e.getMessage());
        }

        return lista;
    }

    @Override
    public ArrayList<Pieza> obtenerTodos() {
        //forma 1
        ArrayList<Pieza> lista = new ArrayList<>();
        String sql = "SELECT id_pieza,  nombre FROM piezas ";
        // Abrimos la tubería en el propio DAO mediante nuestra clase de apoyo DAO.ConexionBD
        try (PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                // Traducimos de Relacional a Orientado a Objetos (Mapeo)
                Pieza a = new Pieza(

                        rs.getInt("id_pieza"),
                        rs.getString("nombre")
                );
                lista.add(a); // Añadimos a la lista
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener artículos: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public Pieza obtenerPorId(int id) {
        Pieza a = null;
        String sql = "SELECT id_pieza,  nombre FROM piezas  where id_pieza=?";
        // Abrimos la tubería en el propio DAO mediante nuestra clase de apoyo DAO.ConexionBD
        try (PreparedStatement pstmt = con.prepareStatement(sql))
        {
            pstmt.setInt(1,id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Traducimos de Relacional a Orientado a Objetos (Mapeo)
                    a = new Pieza(

                            rs.getInt("id_pieza"),
                            rs.getString("nombre")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el artículo con id : " +id + " " + e.getMessage());
        }
        return a;
    }

    @Override
    public Pieza obtenerPorNombre(String nombre) {
        Pieza a = null;
        String sql = "SELECT id_pieza,  nombre FROM piezas  where nombre=?";
        // Abrimos la tubería en el propio DAO mediante nuestra clase de apoyo DAO.ConexionBD
        try (PreparedStatement pstmt = con.prepareStatement(sql))
        {
            pstmt.setString(1,nombre);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Traducimos de Relacional a Orientado a Objetos (Mapeo)
                    a = new Pieza(

                            rs.getInt("id_pieza"),
                            rs.getString("nombre")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el artículo con nombre : " +nombre + " " + e.getMessage());
        }
        return a;
    }

    @Override
    public boolean insertar(Pieza pieza) {
        String sql = "INSERT INTO piezas (id_pieza, nombre) VALUES (?, ?)";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, pieza.getIdPieza());
            pstmt.setString(2, pieza.getNombre());


            return pstmt.executeUpdate() > 0; // Devuelve true si afectó a alguna fila
        } catch (SQLException e) {
            System.err.println("Error al insertar: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean actualizar(Pieza pieza) {
        String sql = "UPDATE piezas SET nombre=?  WHERE id_pieza=?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {


            pstmt.setInt(1, pieza.getIdPieza());



            return pstmt.executeUpdate() > 0; // Devuelve true si afectó a alguna fila
        } catch (SQLException e) {
            System.err.println("Error al insertar: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int id){
        String sql = "DELETE FROM piezas WHERE id_pieza = ?";

        try(PreparedStatement pstmt = con.prepareStatement(sql)){

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;

        }catch (SQLException e){
            System.out.println("Error al eliminar la pieza con id " + id + " " + e.getMessage());
            return  false;
        }

    }
}