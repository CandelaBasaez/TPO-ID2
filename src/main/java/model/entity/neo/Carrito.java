package model.entity.neo;





public class Carrito {

    private int codigoUsuario;

    public Carrito(int codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public Carrito() {
    }

    public int getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(int codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }
}
