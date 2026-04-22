package POJO;

import java.util.ArrayList;

// Archivo: Articulo.java
public class Articulo {
    private int idArticulo;
    private String nombre;
    private int precio;
    private Fabricante Fabricante;
    private ArrayList<Pieza> piezas = new ArrayList<>();

    // Constructor


    public Articulo(int idArticulo, String nombre, int precio, Fabricante fabricante) {
        this.idArticulo = idArticulo;
        this.nombre = nombre;
        this.precio = precio;
        Fabricante = fabricante;
    }

    public ArrayList<Pieza> getPiezas() {
        return piezas;
    }

    public void setPiezas(ArrayList<Pieza> piezas) {
        this.piezas = piezas;
    }

    // Getters y Setters
    public int getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(int idArticulo) {
        this.idArticulo = idArticulo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public Fabricante getFabricante() {
        return Fabricante;
    }

    public void setFabricante(Fabricante fabricante) {
        Fabricante = fabricante;
    }

    @Override
    public String toString() {
        return "Articulo{" +
                "idArticulo=" + idArticulo +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", Fabricante=" + Fabricante +
                ", piezas=" + piezas +
                '}';
    }
}