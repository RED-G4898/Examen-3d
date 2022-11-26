package uia.com.inventarios;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SolicitudAjusteNivel.class, name = "SRM")
})

public class ReporteAjusteNivel implements IAjusteNivel
{
    protected NivelInventario inventario;
    protected SolicitudAjusteNivel sem;

    public ReporteAjusteNivel(IAjusteNivel inventario)
    {
        super();
        this.inventario = (NivelInventario) new NivelInventario();
    }

    public ReporteAjusteNivel() {
        super();
    }


    public void cargaSolicitudRetiro(String nombre)
    {
        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            sem =  mapper.readValue(new FileInputStream(nombre), SolicitudAjusteNivel.class );
        }catch (JsonParseException e) {

            e.printStackTrace();
        }catch (JsonMappingException e) {

            e.printStackTrace();
        }catch (IOException e) {

            e.printStackTrace();
        }

        this.sem.getInventario().print();
    }

    @Override
    public List<InfoItem> busca(int id, String descripcion, String categoria, String cantidad, String idPartida, String idSubpartida, String idCategoria)
    {
        return inventario.busca(id, descripcion, categoria, cantidad, idPartida, idSubpartida, idCategoria);
    }

    @Override
    public void serializa()
    {
    }

    @Override
    public void print() {

    }



    @Override
    public void agrega(String idPartida, String descPartida, String idSubpartida, String descSubpartida, String idCat, String descCat,
                       Lote lote, int minimoNivel, String fechaActualizacion)
    {
        InfoItem item = new InfoItem("Item", idPartida, descPartida, descCat,  idPartida, idSubpartida, idCat,
                lote, minimoNivel, fechaActualizacion);
    }


    public void cargaInventario(String nombre)
    {
        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            inventario =  mapper.readValue(new FileInputStream(nombre), NivelInventario.class );
        }catch (JsonParseException e) {
            e.printStackTrace();
        }catch (JsonMappingException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }

        this.inventario.print();

    }

    public void cargaSolicitudAjusteNivelToInventario() throws IOException {
        sem.getInventario().getItems().keySet().stream().forEach(partida -> { // Por cada partida

            sem.getInventario().getItems().get(partida).getItems().keySet().stream().forEach(subPartida -> { // Por cada subpartida de cada partida

                sem.getInventario().getItems().get(partida).getItems().get(subPartida).getItems().keySet().stream().forEach(categoria -> { // Por cada categoria de cada subpartida de cada partida

                    // Por cada item de cada categoria de cada subpartida de cada partida
                    String idPartida = this.sem.getInventario().getItems().get(partida).getId();
                    String idSubpartida = this.sem.getInventario().getItems().get(partida).getItems().get(subPartida).getId();
                    String idCategoria = this.sem.getInventario().getItems().get(partida).getItems().get(subPartida).getItems()
                            .get(categoria).getId();
                    CategoriaInventario categoriaSubPartida = (CategoriaInventario) this.sem.getInventario().getItems().get(partida).getItems()
                            .get(subPartida).getItems().get(categoria);
                    String cantidadSEM = sem.getInventario().getItems().get(partida).getItems().get(subPartida).getItems()
                            .get(categoria).getCantidad();
                    int minimoNivelSEM = sem.getInventario().getItems().get(partida).getItems().get(subPartida).getItems()
                            .get(categoria).getMinimoNivel();
                    String fechaActualizacionSEM = sem.getInventario().getItems().get(partida).getItems().get(subPartida)
                            .getItems().get(categoria).getFechaActualizacionNivel();
                    String cantidadNivelInventario = inventario.getItems().get(partida).getItems().get(subPartida).getItems()
                            .get(categoria).getCantidad();
                    int minimoNivelInventario = inventario.getItems().get(partida).getItems().get(subPartida).getItems()
                            .get(categoria).getMinimoNivel();
                    String fechaActualizacionInventario = inventario.getItems().get(partida).getItems().get(subPartida)
                            .getItems().get(categoria).getFechaActualizacionNivel();
                    String descripcionCategoria = sem.getInventario().getItems().get(partida).getItems().get(subPartida)
                            .getItems().get(categoria).getName();
                    Lote lote = this.sem.getInventario().getItems().get(partida).getItems().get(subPartida).getItems()
                            .get(categoria).getLote();

                    // Ajusar el inventario
                    try {
                        this.inventario.ajustaNivelInventario(idPartida, idSubpartida, idCategoria, categoriaSubPartida,
                                descripcionCategoria, cantidadSEM, minimoNivelSEM, fechaActualizacionSEM, cantidadNivelInventario, minimoNivelInventario, fechaActualizacionInventario, lote);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            });
        });
    }

    public void serializaNivelInventario(String fileName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(fileName), this.inventario);
    }
}
