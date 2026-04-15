package DAO;

import POJO.Articulo;

import java.util.ArrayList;

public interface InterfazDAO<T> {
    ArrayList<T> obtenerTodos();

    T obtenerPorId(int id);

    T obtenerPorNombre(String nombre);

    boolean insertar(T item);

    boolean actualizar(T item);

    boolean eliminar(int id);
}
