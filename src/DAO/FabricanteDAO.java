package DAO;// Archivo: ArticuloDAO.java



import POJO.Articulo;
import POJO.Fabricante;

import java.sql.*;
import java.util.ArrayList;

public class FabricanteDAO implements InterfazDAO<Fabricante> {


    private final Connection con;

    public FabricanteDAO() throws SQLException{
        this.con = ConexionBD.getInstancia().getConexion();
    }

    public ArrayList<Fabricante> obtenerTodos(){
        ArrayList<Fabricante> lista = new ArrayList<>();
        String sql = "SELECT f.id_fabricante,  f.nombre FROM fabricantes f";
        // Abrimos la tubería en el propio DAO mediante nuestra clase de apoyo DAO.ConexionBD
        try (PreparedStatement pstmt = con.prepareStatement(sql))
        {

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // Traducimos de Relacional a Orientado a Objetos (Mapeo)
                    Fabricante a = new Fabricante(

                            rs.getInt("id_fabricante"),
                            rs.getString("nombre")
                    );
                    lista.add(a); // Añadimos a la lista
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener los fabricante " +  e.getMessage());
        }

        return lista;
    }


    @Override
    public Fabricante obtenerPorId(int id) {
        Fabricante a = null;
        String sql = "SELECT f.id_fabricante,  f.nombre FROM fabricantes f where f.id_fabricante=?";
        // Abrimos la tubería en el propio DAO mediante nuestra clase de apoyo DAO.ConexionBD
        try (PreparedStatement pstmt = con.prepareStatement(sql))
        {
            pstmt.setInt(1,id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Traducimos de Relacional a Orientado a Objetos (Mapeo)
                    a = new Fabricante(

                            rs.getInt("id_fabricante"),
                            rs.getString("nombre")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el fabricante con id : " +id + " " + e.getMessage());
        }
        return a;
    }

    @Override
    public Fabricante obtenerPorNombre(String nombre) {
        Fabricante a = null;
        String sql = "SELECT f.id_fabricante,  f.nombre FROM fabricantes f  where  f.nombre=?";
        // Abrimos la tubería en el propio DAO mediante nuestra clase de apoyo DAO.ConexionBD
        try (PreparedStatement pstmt = con.prepareStatement(sql))
        {
            pstmt.setString(1,nombre);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Traducimos de Relacional a Orientado a Objetos (Mapeo)
                    a = new Fabricante(

                            rs.getInt("id_fabricante"),
                            rs.getString("nombre")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el fabricante con nombre : " +nombre + " " + e.getMessage());
        }
        return a;
    }

    @Override
    public boolean insertar(Fabricante fabricante) {
        String sql = "INSERT INTO fabricantes (id_fabricante, nombre) VALUES (?, ?)";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, fabricante.getId_fab());
            pstmt.setString(2, fabricante.getNombre());


            return pstmt.executeUpdate() > 0; // Devuelve true si afectó a alguna fila
        } catch (SQLException e) {
            System.err.println("Error al insertar: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean actualizar(Fabricante fabricante) {
        String sql = "UPDATE fabricantes SET nombre=? WHERE id_fabricante=?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, fabricante.getNombre());
            pstmt.setInt(2, fabricante.getId_fab());



            return pstmt.executeUpdate() > 0; // Devuelve true si afectó a alguna fila
        } catch (SQLException e) {
            System.err.println("Error al actualizar: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int id){
        String sql = "DELETE FROM fabricantes WHERE id_fabricante = ?";

        try(PreparedStatement pstmt = con.prepareStatement(sql)){

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;

        }catch (SQLException e){
            System.out.println("Error al eliminar el fabricante con id " + id + " " + e.getMessage());
            return  false;
        }

    }
}