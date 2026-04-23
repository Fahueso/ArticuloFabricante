package DAO;

import POJO.ArticuloPieza;
import POJO.Pieza;

import java.sql.*;
import java.util.ArrayList;

public class ArticuloPiezaDAO implements InterfazDAO<ArticuloPieza> {

    private final Connection con;

    public ArticuloPiezaDAO() throws SQLException{
        this.con = ConexionBD.getInstancia().getConexion();
    }

    @Override
    public ArrayList<ArticuloPieza> obtenerTodos() {
        ArrayList<ArticuloPieza> lista = new ArrayList<>();

        String sql = "SELECT id_articulo, id_pieza FROM articulo_piezas";

        try (PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                ArticuloPieza ap = new ArticuloPieza(
                        rs.getInt("id_articulo"),
                        rs.getInt("id_pieza")
                );
                lista.add(ap);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener relaciones artículo-pieza: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public ArticuloPieza obtenerPorId(int idArticulo) {
        ArticuloPieza ap = null;

        String sql = "SELECT id_articulo, id_pieza FROM articulo_piezas WHERE id_articulo = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, idArticulo);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    ap = new ArticuloPieza(
                            rs.getInt("id_articulo"),
                            rs.getInt("id_pieza")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener relación artículo-pieza: " + e.getMessage());
        }

        return ap;
    }

    @Override
    public ArticuloPieza obtenerPorNombre(String nombre) {
        // NO APLICA porque la tabla no tiene nombre
        return null;
    }

    @Override
    public boolean insertar(ArticuloPieza ap) {
        String sql = "INSERT INTO articulo_piezas (id_articulo, id_pieza) VALUES (?, ?)";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, ap.getIdArticulo());
            pstmt.setInt(2, ap.getIdPieza());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar relación artículo-pieza: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean actualizar(ArticuloPieza ap) {
        // NO APLICA porque la relación no tiene más campos
        return false;
    }

    @Override
    public boolean eliminar(int idArticulo) {
        return eliminarPiezasArticulo(idArticulo);
    }




    public boolean eliminar(ArticuloPieza ap) {
        String sql = "DELETE FROM articulo_piezas WHERE id_articulo = ? AND id_pieza = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, ap.getIdArticulo());
            pstmt.setInt(2, ap.getIdPieza());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar relación artículo-pieza: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarPiezasArticulo(int idArticulo) {
        String sql = "DELETE FROM articulo_piezas WHERE id_articulo = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, idArticulo);
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error al eliminar piezas del artículo " + idArticulo + ": " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Pieza> obtenerPiezasDeArticulo(int idArticulo) {
        ArrayList<Pieza> lista = new ArrayList<>();

        String sql =
                "SELECT p.id_pieza, p.nombre " +
                        "FROM piezas p " +
                        "INNER JOIN articulo_piezas ap ON p.id_pieza = ap.id_pieza " +
                        "WHERE ap.id_articulo = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, idArticulo);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Pieza p = new Pieza(
                            rs.getInt("id_pieza"),
                            rs.getString("nombre")
                    );
                    lista.add(p);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener piezas del artículo " + idArticulo + ": " + e.getMessage());
        }

        return lista;
    }
}