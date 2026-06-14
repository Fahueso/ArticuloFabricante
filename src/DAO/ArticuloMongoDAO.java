package DAO;

import POJO.Articulo;
import POJO.Fabricante;
import POJO.Pieza;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class ArticuloMongoDAO implements InterfazDAO<Articulo> {

    private final MongoCollection<Document> coleccion;

    public ArticuloMongoDAO() {
        MongoDatabase db = ConexionMongoBD.getInstancia().getConexion();
        coleccion = db.getCollection("articulos");
    }

    // obtenerTodos
    @Override
    public ArrayList<Articulo> obtenerTodos() {
        ArrayList<Articulo> lista = new ArrayList<>();

        for (Document doc : coleccion.find()) {

            Fabricante fab = new Fabricante(
                    doc.get("fabricante", Document.class).getInteger("id"),
                    doc.get("fabricante", Document.class).getString("nombre")
            );

            Articulo a = new Articulo(
                    doc.getInteger("_id"),
                    doc.getString("nombre"),
                    doc.getInteger("precio"),
                    fab
            );

            // piezas
            List<Document> piezasDoc = doc.getList("piezas", Document.class);
            ArrayList<Pieza> piezas = new ArrayList<>();

            if (piezasDoc != null) {
                for (Document p : piezasDoc) {
                    piezas.add(new Pieza(
                            p.getInteger("id"),
                            p.getString("nombre")
                    ));
                }
            }

            a.setPiezas(piezas);
            lista.add(a);
        }

        return lista;
    }

    // obtenerPorId
    @Override
    public Articulo obtenerPorId(int id) {

        Document doc = coleccion.find(new Document("_id", id)).first();

        if (doc == null) return null;

        Fabricante fab = new Fabricante(
                doc.get("fabricante", Document.class).getInteger("id"),
                doc.get("fabricante", Document.class).getString("nombre")
        );

        Articulo a = new Articulo(
                doc.getInteger("_id"),
                doc.getString("nombre"),
                doc.getInteger("precio"),
                fab
        );

        // piezas
        List<Document> piezasDoc = doc.getList("piezas", Document.class);
        ArrayList<Pieza> piezas = new ArrayList<>();

        if (piezasDoc != null) {
            for (Document p : piezasDoc) {
                piezas.add(new Pieza(
                        p.getInteger("id"),
                        p.getString("nombre")
                ));
            }
        }

        a.setPiezas(piezas);
        return a;
    }

    // obtenerPorNombre
    @Override
    public Articulo obtenerPorNombre(String nombre) {

        Document doc = coleccion.find(new Document("nombre", nombre)).first();

        if (doc == null) return null;

        Fabricante fab = new Fabricante(
                doc.get("fabricante", Document.class).getInteger("id"),
                doc.get("fabricante", Document.class).getString("nombre")
        );

        Articulo a = new Articulo(
                doc.getInteger("_id"),
                doc.getString("nombre"),
                doc.getDouble("precio").intValue(),
                fab
        );

        // piezas
        List<Document> piezasDoc = doc.getList("piezas", Document.class);
        ArrayList<Pieza> piezas = new ArrayList<>();

        if (piezasDoc != null) {
            for (Document p : piezasDoc) {
                piezas.add(new Pieza(
                        p.getInteger("id"),
                        p.getString("nombre")
                ));
            }
        }

        a.setPiezas(piezas);
        return a;
    }

    //insertar
    @Override
    public boolean insertar(Articulo art) {

        Document doc = new Document("_id", art.getIdArticulo())
                .append("nombre", art.getNombre())
                .append("precio", art.getPrecio())
                .append("fabricante", new Document("id", art.getFabricante().getId_fab())
                        .append("nombre", art.getFabricante().getNombre()));

        // piezas
        List<Document> piezasDoc = new ArrayList<>();
        if (art.getPiezas() != null) {
            for (Pieza p : art.getPiezas()) {
                piezasDoc.add(new Document("id", p.getIdPieza())
                        .append("nombre", p.getNombre()));
            }
        }

        doc.append("piezas", piezasDoc);

        coleccion.insertOne(doc);
        return true;
    }

    // actualizar
    @Override
    public boolean actualizar(Articulo art) {

        Document filtro = new Document("_id", art.getIdArticulo());

        Document nuevosDatos = new Document("nombre", art.getNombre())
                .append("precio", art.getPrecio());

        coleccion.updateOne(filtro, new Document("$set", nuevosDatos));

        return true;
    }

    // eliminar
    @Override
    public boolean eliminar(int id) {

        Document filtro = new Document("_id", id);

        return coleccion.deleteOne(filtro).getDeletedCount() > 0;
    }

    public ArrayList<Fabricante> obtenerFabricantes() {

        ArrayList<Fabricante> lista = new ArrayList<>();

        for (Document doc : coleccion.distinct("fabricante", Document.class)) {

            Fabricante f = new Fabricante(
                    ((Number) doc.get("id")).intValue(),
                    doc.getString("nombre")
            );

            lista.add(f);
        }

        return lista;
    }


}