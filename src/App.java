import DAO.ArticuloDAO;
import POJO.Articulo;

public class App {
    static void main() {
        ArticuloDAO articuloDAO = new ArticuloDAO();

        System.out.println(articuloDAO.obtenerTodos());

        Articulo x = new Articulo(999,"xxxxx", 500, 1);
        if (articuloDAO.insertar(x))
            System.out.println("Funcionó");

        x.setNombre("NombreCambiado");
        x.setPrecio(200);
        articuloDAO.actualizar(x);
        System.out.println(articuloDAO.obtenerPorNombre("NombreCambiado"));
        System.out.println(articuloDAO.obtenerPorId(999));
        if (articuloDAO.eliminar(999))
            System.out.println("Funcionó");

    }
}
