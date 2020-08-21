package TwoReport.com.project;

import java.util.Date;

public class CrimeInfo  {
    private Usuario usuario;
    private Location ubicacion;
    private String descripcion;
    private String fecha;
    private String TipoCrimen;

    public CrimeInfo( String descripcion, String fecha, String tipoCrimen) {

        this.descripcion = descripcion;
        this.fecha = fecha;
        TipoCrimen = tipoCrimen;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Location getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Location ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipoCrimen() {
        return TipoCrimen;
    }

    public void setTipoCrimen(String tipoCrimen) {
        TipoCrimen = tipoCrimen;
    }
}
