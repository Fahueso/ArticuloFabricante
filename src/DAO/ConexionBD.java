package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    // 1. Definimos los datos de conexión granulares como constantes de clase

    private static final String HOST = "localhost:3306";
    private static final String DB_NAME = "introbd";
    private static final String USUARIO = "alumno";
    private static final String PASSWORD = "alumno";

    // Construimos la URL completa uniendo las variables
    private static final String URL = "jdbc:mysql://" + HOST + "/" + DB_NAME;

    private static ConexionBD instancia;
    private Connection conexion;

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
    public Connection getConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
        }
        return conexion;
    }





}