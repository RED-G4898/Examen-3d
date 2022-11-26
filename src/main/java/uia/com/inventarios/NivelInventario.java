package uia.com.inventarios;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class NivelInventario extends PartidaInventario{

    //private HashMap<String, HashMap<String, PartidaInventario>> reporteNIvelInventario = new HashMap<String, HashMap<String, PartidaInventario>>();


    public NivelInventario() {
        super();
    }

    public InfoItem agrega(String idPartida, String descPartida, String idSubpartida, String descSubpartida, String idCat, String descCat, Lote lote, int minimoNivel)
    {
        InfoItem item = null;
        String cantidad = lote.getCantidad();

        if(this.getItems().get(idPartida) != null) {
            if (this.getItems().get(idPartida).getItems().get(idSubpartida) != null)
            {
                if (this.getItems().get(idPartida).getItems().get(idSubpartida).getItems().get(idCat) == null)
                {
                    String ubicP = String.valueOf(Integer.parseInt(idPartida)/100);
                    char ubicS = (char) (65 + (Integer.parseInt(idSubpartida)/100)/10);
                    String ubicC = String.valueOf((Integer.parseInt(idCat)-Integer.parseInt(idSubpartida)));
                    CategoriaInventario cat = new CategoriaInventario("Categoria", idCat, descCat, "Disponible", cantidad, "SinPosicion", minimoNivel);
                    this.getItems().get(idPartida).getItems().get(idSubpartida).getItems().put(idCat, cat);

                    for(int i=0; i<Integer.parseInt(cantidad); i++)
                    {
                        String ubic = ubicP+ubicS+ubicC+"-"+String.valueOf(i);
                        String idItem = idCat+"-"+String.valueOf(i);
                        item = new InfoItem("Item", idItem, descCat, "Disponible", "1", ubic);
                        item.setLote(lote);
                        this.getItems().get(idPartida).getItems().get(idSubpartida).getItems().get(idCat).getItems().put(idItem, item);
                    }
                }
            } else {
                SubpartidaInventario subpartida = new SubpartidaInventario("Subpartida", idSubpartida, descSubpartida,  "Disponible", "", "");
                this.getItems().get(idPartida).getItems().put(idSubpartida, subpartida);
                String ubicP = String.valueOf(Integer.parseInt(idPartida)/100);
                char ubicS = (char) (65 + (Integer.parseInt(idSubpartida)/100)/10);
                String ubicC = String.valueOf((Integer.parseInt(idCat)-Integer.parseInt(idSubpartida)));

                CategoriaInventario cat = new CategoriaInventario("Categoria", idCat, descCat, "Disponible", cantidad, "SinPosicion", minimoNivel);
                this.getItems().get(idPartida).getItems().get(idSubpartida).getItems().put(idCat, cat);

                for(int i=0; i<Integer.parseInt(cantidad); i++)
                {
                    String ubic = ubicP+ubicS+ubicC+"-"+String.valueOf(i);
                    String idItem = idCat+"-"+String.valueOf(i);
                    item = new InfoItem("Item", idItem, descCat, "Disponible", "1", ubic);
                    item.setLote(lote);
                    this.getItems().get(idPartida).getItems().get(idSubpartida).getItems().get(idCat).getItems().put(idItem, item);
                }

            }
        }
        else
        {
            PartidaInventario partida = new PartidaInventario("Partida", idPartida, descPartida, "", "Disponible", "");
            SubpartidaInventario subpartida = new SubpartidaInventario("Subpartida", idSubpartida, descSubpartida, "Disponible", "", "");

            String ubicP = String.valueOf(Integer.parseInt(idPartida)/100);
            char ubicS = (char) (65 + (Integer.parseInt(idSubpartida)/100)/10);
            String ubicC = String.valueOf((Integer.parseInt(idCat)-Integer.parseInt(idSubpartida)));
            this.getItems().put(idPartida, partida);
            partida.getItems().put(idSubpartida, subpartida);

            CategoriaInventario cat = new CategoriaInventario("Categoria", idCat, descCat, "Disponible", cantidad, "SinPosicion", minimoNivel);
            this.getItems().get(idPartida).getItems().get(idSubpartida).getItems().put(idCat, cat);

            for(int i=0; i<Integer.parseInt(cantidad); i++)
            {
                String ubic = ubicP+ubicS+ubicC+"-"+String.valueOf(i);
                String idItem = idCat+"-"+String.valueOf(i);
                item = new InfoItem("Item", idItem, descCat, "Disponible", "1", ubic);
                item.setLote(lote);
                this.getItems().get(idPartida).getItems().get(idSubpartida).getItems().get(idCat).getItems().put(idItem, item);
            }
        }

        return item;
    }

    public InfoItem agrega(String clase, String idPartida, String descPartida, String idSubpartida, String descSubpartida, String idCat, String descCat, Lote lote, int minimoNivel, String fechaActualizacion)
    {
        InfoItem item = null;
        String cantidad = lote.getCantidad();

        if(this.getItems().get(idPartida) != null) {
            if (this.getItems().get(idPartida).getItems().get(idSubpartida) != null)
            {
                if (this.getItems().get(idPartida).getItems().get(idSubpartida).getItems().get(idCat) == null)
                {
                    String ubicP = String.valueOf(Integer.parseInt(idPartida)/100);
                    char ubicS = (char) (65 + (Integer.parseInt(idSubpartida)/100)/10);
                    String ubicC = String.valueOf((Integer.parseInt(idCat)-Integer.parseInt(idSubpartida)));
                    CategoriaInventario cat = new CategoriaInventario("Categoria", idCat, descCat, "ActualizaNivel", cantidad, "SinPosicion");
                    cat.setMinimoNivel(minimoNivel);
                    cat.setFechaActualizacionNivel(fechaActualizacion);
                    cat.setCantidadAjusteNivel(Integer.parseInt(cat.getCantidad())-cat.getMinimoNivel());
                    this.getItems().get(idPartida).getItems().get(idSubpartida).getItems().put(idCat, cat);

                    for(int i=0; i<Integer.parseInt(cat.getCantidad()); i++)
                    {
                        String ubic = ubicP+ubicS+ubicC+"-"+String.valueOf(i);
                        String idItem = idCat+"-"+String.valueOf(i);
                        item = new InfoItem(clase, idItem,  descCat, "ActualizaNivel", "1", ubic);
                        item.setMinimoNivel(minimoNivel);
                        cat.setFechaActualizacionNivel(fechaActualizacion);
                        cat.setCantidadAjusteNivel(Integer.parseInt(cat.getCantidad())-cat.getMinimoNivel());
                        item.setLote(lote);
                        this.getItems().get(idPartida).getItems().get(idSubpartida).getItems().get(idCat).getItems().put(idItem, item);
                    }
                }
            } else {
                SubpartidaInventario subpartida = new SubpartidaInventario("Subpartida", idSubpartida, descSubpartida,  "ActualizaNivel", "", "");
                this.getItems().get(idPartida).getItems().put(idSubpartida, subpartida);
                String ubicP = String.valueOf(Integer.parseInt(idPartida)/100);
                char ubicS = (char) (65 + (Integer.parseInt(idSubpartida)/100)/10);
                String ubicC = String.valueOf((Integer.parseInt(idCat)-Integer.parseInt(idSubpartida)));

                CategoriaInventario cat = new CategoriaInventario("Categoria", idCat, descCat, "ActualizaNivel", cantidad, "");
                cat.setFechaActualizacionNivel(fechaActualizacion);
                cat.setCantidadAjusteNivel(Integer.parseInt(cat.getCantidad())-cat.getMinimoNivel());
                this.getItems().get(idPartida).getItems().get(idSubpartida).getItems().put(idCat, cat);

                for(int i=0; i<Integer.parseInt(cat.getCantidad()); i++)
                {
                    String ubic = ubicP+ubicS+ubicC+"-"+String.valueOf(i);
                    String idItem = idCat+"-"+String.valueOf(i);
                    item = new InfoItem(clase, idItem,  descCat, "ActualizaNivel", "1", ubic);
                    item.setMinimoNivel(minimoNivel);
                    cat.setFechaActualizacionNivel(fechaActualizacion);
                    cat.setCantidadAjusteNivel(Integer.parseInt(cat.getCantidad())-cat.getMinimoNivel());
                    item.setLote(lote);
                    this.getItems().get(idPartida).getItems().get(idSubpartida).getItems().get(idCat).getItems().put(idItem, item);
                }
            }
        }
        else
        {
            PartidaInventario partida = new PartidaInventario("Partida", idPartida, descPartida, "", "ActualizaNivel", "");
            SubpartidaInventario subpartida = new SubpartidaInventario("Subpartida", idSubpartida, descSubpartida, "ActualizaNivel", "", "");

            String ubicP = String.valueOf(Integer.parseInt(idPartida)/100);
            char ubicS = (char) (65 + (Integer.parseInt(idSubpartida)/100)/10);
            String ubicC = String.valueOf((Integer.parseInt(idCat)-Integer.parseInt(idSubpartida)));
            this.getItems().put(idPartida, partida);
            partida.getItems().put(idSubpartida, subpartida);

            CategoriaInventario cat = new CategoriaInventario("Categoria", idCat, descCat, "ActualizaNivel", cantidad, "");
            cat.setMinimoNivel(minimoNivel);
            this.getItems().get(idPartida).getItems().get(idSubpartida).getItems().put(idCat, cat);

            for(int i=0; i<Integer.parseInt(cat.getCantidad()); i++)
            {
                String ubic = ubicP+ubicS+ubicC+"-"+String.valueOf(i);
                String idItem = idCat+"-"+String.valueOf(i);
                item = new InfoItem(clase, idItem,  descCat, "ActualizaNivel", "1", ubic);
                item.setMinimoNivel(minimoNivel);
                cat.setFechaActualizacionNivel(fechaActualizacion);
                cat.setCantidadAjusteNivel(Integer.parseInt(cat.getCantidad())-cat.getMinimoNivel());
                item.setLote(lote);
                this.getItems().get(idPartida).getItems().get(idSubpartida).getItems().get(idCat).getItems().put(idItem, item);
            }
        }

        return item;
    }


    public void serializa() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("ReporteNivelInventario-14.json"), this);
    }

    public List<InfoItem> busca(int id, String name, String descCat, String cantidad, String idPartida, String idSubpartida, String idCat)
    {
        if(this.getItems().get(idPartida) != null)
        {
            if (this.getItems().get(idPartida).getItems().get(idSubpartida) != null)
            {
                if (this.getItems().get(idPartida).getItems().get(idSubpartida).getItems().get(idCat) != null)
                {
                    CategoriaInventario cat = (CategoriaInventario) this.getItems().get(idPartida).getItems().get(idSubpartida).getItems().get(idCat);

                    if(Integer.parseInt(cat.getCantidad()) >= Integer.parseInt(cantidad))
                    {
                        return cat.getItems(cantidad);
                    }
                }
            }
        }
        return null;
    }



    public InfoItem busca(String idPartida, String idSubpartida, String idCat)
    {
        if(this.getItems().get(idPartida) != null)
        {
            if (this.getItems().get(idPartida).getItems().get(idSubpartida) != null)
            {
                if (this.getItems().get(idPartida).getItems().get(idSubpartida).getItems().get(idCat) != null)
                {
                    CategoriaInventario cat = (CategoriaInventario) this.getItems().get(idPartida).getItems().get(idSubpartida).getItems().get(idCat);
                    return cat;
                }
            }
        }
        return null;
    }

    public void print()
    {
        if (this.getItems() != null)
        {
            System.out.println("----- Items List -----");
            for (Map.Entry<String, InfoItem> item : getItems().entrySet())
            {
                PartidaInventario nodo = (PartidaInventario) item.getValue();
                nodo.print();
            }
        }
    }


    public void serializa(String s) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(s), this);
    }

    public InfoItem ajustaNivelInventario(String idPartida, String idSubpartida, String idCat,
            CategoriaInventario cat, String descCat, String cantidadSEM, int minimoNivelSEM, String fechaActualizacionSEM, String cantidadNivelInventario, int minimoNivelInventario, String fechaActualizacionInventario, Lote lote)
            throws IOException {
        InfoItem item = null;

        if (this.getItems().get(idPartida) != null) {
            if (this.getItems().get(idPartida).getItems().get(idSubpartida) != null) {
                if (this.getItems().get(idPartida).getItems().get(idSubpartida).getItems().get(idCat) != null) {

                    String ubicP = String.valueOf(Integer.parseInt(idPartida) / 100);
                    char ubicS = (char) (65 + (Integer.parseInt(idSubpartida) / 100) / 10);
                    String ubicC = String.valueOf((Integer.parseInt(idCat) - Integer.parseInt(idSubpartida)));
                    this.getItems().get(idPartida).getItems().get(idSubpartida).getItems().put(idCat, cat);

                    // Ajustes de inventario de acuerdo con SEM
                    int cantidadAjuste = Integer.parseInt(cantidadSEM);
                    int minimoNivelAjuste = minimoNivelSEM;
                    String fechaActualizacionAjuste = fechaActualizacionSEM;
                    for (int i = 0; i < cantidadAjuste; i++) {
                        String ubic = ubicP + ubicS + ubicC + String.valueOf(i);
                        String idItem = idCat + String.valueOf(i);
                        item = new InfoItem("Item", idItem, descCat, "SinEstatus", "1", ubic);
                        item.setLote(lote);
                        this.getItems().get(idPartida).getItems().get(idSubpartida).getItems().get(idCat).getItems()
                                .put(idItem, item);
                    }
                    // Asignación de valores en SEM a Inventario
                    this.getItems().get(idPartida).getItems().get(idSubpartida).getItems().get(idCat)
                            .setCantidad(String.valueOf(cantidadAjuste));
                    this.getItems().get(idPartida).getItems().get(idSubpartida).getItems().get(idCat)
                            .setMinimoNivel(minimoNivelAjuste);
                    this.getItems().get(idPartida).getItems().get(idSubpartida).getItems().get(idCat)
                            .setFechaActualizacionNivel(fechaActualizacionAjuste);
                }

            }

        }
        return item;
    }

}
