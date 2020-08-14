package TwoReport.com.project;

import java.util.Date;

public class CrimeInfo  {
    private Usuario usuario;
    private Location ubicacion;
    private String descripcion;
    private Date fecha;
    private String TipoCrimen;

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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipoCrimen() {
        return TipoCrimen;
    }

    public void setTipoCrimen(String tipoCrimen) {
        TipoCrimen = tipoCrimen;
    }
}
