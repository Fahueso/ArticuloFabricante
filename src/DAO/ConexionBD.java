package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    // 1. Definimos los datos de conexión granulares como constantes de clase
    private static final String HOST = "localhost:3306";
    private static final String DB_NAME = "prueba";
    private static final String USUARIO = "alumno";
    private static final String PASSWORD = "alumno";

    // Construimos la URL completa uniendo las variables
    private static final String URL = "jdbc:mysql://" + HOST + "/" + DB_NAME;

    private static ConexionBD instancia;

    public static ConexionBD getInstancia() {
        if (instancia == null){
            instancia = new ConexionBD();
        }
        return instancia;
    }

    private ConexionBD() {
    }

    /**
     * Establece y devuelve una conexión con la base de datos
     */
    public  Connection conectar() {
        Connection conexion = null;
        try {
            System.out.println("Intentando conectar a la base de datos...");
            // Abrimos la comunicación usando nuestras constantes
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            System.out.println("¡Conexión establecida con éxito!");
        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }
        return conexion;
    }




    public static void main(String[] args) {
        System.out.println("Iniciando aplicación de prueba...");

        // Llamamos al método conectar.
        // Lo envolvemos en un try-with-resources para asegurar que se cierre al terminar.
        try (Connection con = ConexionBD.getInstancia().conectar()) {

            if (con != null) {
                System.out.println("=> La base de datos está lista para recibir comandos SQL.");
                // Aquí irían nuestras futuras operaciones de inserción, borrado, etc.
            }

        } catch (SQLException e) {
            System.err.println("Fallo inesperado al cerrar la base de datos: " + e.getMessage());
        }
    }
}