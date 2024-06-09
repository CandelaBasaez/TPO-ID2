package model.entity.neo;

import io.grpc.Codec;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Catalogo {
    @Id
    @GeneratedValue
    private int catalogoId; //!CHEQUEAR COMO SE HACE PARA QUE SE AUTOGENERE

    public Catalogo(int catalogoId) {
        this.catalogoId = catalogoId;
    }

    public Catalogo() {
    }

    public int getCatalogoId() {
        return catalogoId;
    }

    public void setCatalogoId(int catalogoId) {
        this.catalogoId = catalogoId;
    }
}
