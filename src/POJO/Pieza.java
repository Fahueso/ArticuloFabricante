package POJO;

import java.util.Objects;

public class Pieza {
    private int idPieza;
    private String nombre;

    public Pieza(int idPieza, String nombre) {
        this.idPieza = idPieza;
        this.nombre = nombre;
    }

    public int getIdPieza() {
        return idPieza;
    }

    public void setIdPieza(int idPieza) {
        this.idPieza = idPieza;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Pieza{" +
                "idPieza=" + idPieza +
                ", nombre='" + nombre + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pieza that = (Pieza) o;
        return idPieza == that.idPieza;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idPieza);
    }
}
