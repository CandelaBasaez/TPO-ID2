package model.entity.neo;


import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Carrito {
    @Id
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
