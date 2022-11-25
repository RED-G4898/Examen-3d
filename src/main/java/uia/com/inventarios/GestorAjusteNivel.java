package uia.com.inventarios;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GestorAjusteNivel extends ReporteAjusteNivel
    
{
    List<SolicitudAjusteNivel> items = new ArrayList<SolicitudAjusteNivel>();


    public GestorAjusteNivel(IAjusteNivel inventario)
    {
        super(inventario);
    }

    public GestorAjusteNivel(String nombre)
    {
        super();
        //this.gestorApartado = new GestorApartado();
    }

    public GestorAjusteNivel() {

    }




    public List<SolicitudAjusteNivel> getItems()
    {
        return this.items;
    }


    public void setItems(List<SolicitudAjusteNivel> items) {
        this.items = items;
    }


    public SolicitudAjusteNivel agregaSolicitudSalidaMaterial(String nomArchivo, String solicitante, String categoria) throws IOException {
        String id = nomArchivo+"_"+String.valueOf(this.getItems().size()+1)+".json";
        String existencia = "";
        IAjusteNivel inventario=null;
        SolicitudAjusteNivel newSSM = new SolicitudAjusteNivel(super.inventario, id, solicitante, categoria);
        return newSSM;
    }

    @Override
    public void print() {

    }


}
