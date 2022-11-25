package uia.com.inventarios;

import java.io.IOException;
import java.util.List;

public interface IAjusteNivel {
    public void cargaInventario(String nombre);


    public List<InfoItem> busca(int id, String descripcion, String categoria, String cantidad, String idPartida, String idSubpartida, String idCategoria);

    public void serializa() throws IOException;

    public void print();

    public void agrega(String idPartida, String descPartida, String idSubpartida, String descSubpartida, String idCat, String descCat,
                       Lote lote, int minimoNivel, String fechaActualizacion);

    //void cargaSolicitudAjusteNivelToInventario();
}