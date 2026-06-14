package DAO;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class ConexionMongoBD {

    // Datos de conexión
    private static final String HOST = "mongodb://localhost:27017";
    private static final String DB_NAME = "introbd";

    private static ConexionMongoBD instancia;
    private MongoClient cliente;
    private MongoDatabase baseDatos;

    // ✅ Singleton
    public static ConexionMongoBD getInstancia() {
        if (instancia == null) {
            instancia = new ConexionMongoBD();
        }
        return instancia;
    }

    private ConexionMongoBD() {
    }

    /**
     * Devuelve la base de datos MongoDB
     */
    public MongoDatabase getConexion() {
        if (baseDatos == null) {
            cliente = MongoClients.create(HOST);
            baseDatos = cliente.getDatabase(DB_NAME);
        }
        return baseDatos;
    }

    /**
     * Cerrar conexión (opcional pero recomendable)
     */
    public void cerrarConexion() {
        if (cliente != null) {
            cliente.close();
        }
    }
}