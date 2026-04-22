package POJO;

import java.util.Objects;

public class Fabricante {
    private int id_fab;
    private String nombre;

    public Fabricante(int id_fab, String nombre) {
        this.id_fab = id_fab;
        this.nombre = nombre;
    }

    public int getId_fab() {
        return id_fab;
    }

    public void setId_fab(int id_fab) {
        this.id_fab = id_fab;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Fabricante{" +
                "id_fab=" + id_fab +
                ", nombre='" + nombre + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Fabricante that = (Fabricante) o;
        return id_fab == that.id_fab;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id_fab);
    }
}
