import DAO.ArticuloDAO;
import DAO.FabricanteDAO;
import DAO.PiezaDAO;
import POJO.Articulo;
import POJO.Fabricante;

public class App {
    static void main() {
        ArticuloDAO articuloDAO = new ArticuloDAO();
        FabricanteDAO fabricanteDAO = new FabricanteDAO();


        System.out.println(articuloDAO.obtenerTodos());

        Fabricante f = new Fabricante(100,"Prueba");
        fabricanteDAO.insertar(f);
        Articulo x = new Articulo(999,"xxxxx", 500, f);
        if (articuloDAO.insertar(x))
            System.out.println("Funcionó");

        x.setNombre("NombreCambiado");
        x.setPrecio(200);
        articuloDAO.actualizar(x);
        System.out.println(articuloDAO.obtenerPorNombre("NombreCambiado"));
        System.out.println(articuloDAO.obtenerPorId(999));
        if (articuloDAO.eliminar(999))
            System.out.println("Funcionó");

        fabricanteDAO.insertar(f);

    }
}
