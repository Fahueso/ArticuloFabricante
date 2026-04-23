package POJO;

public class ArticuloPieza {

    private int idArticulo;
    private int idPieza;

    public ArticuloPieza(int idArticulo, int idPieza) {
        this.idArticulo = idArticulo;
        this.idPieza = idPieza;
    }

    public int getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(int idArticulo) {
        this.idArticulo = idArticulo;
    }

    public int getIdPieza() {
        return idPieza;
    }

    public void setIdPieza(int idPieza) {
        this.idPieza = idPieza;
    }

    @Override
    public String toString() {
        return "ArticuloPieza{" +
                "idArticulo=" + idArticulo +
                ", idPieza=" + idPieza +
                '}';
    }
}