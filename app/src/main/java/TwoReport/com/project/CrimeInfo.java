package TwoReport.com.project;

import java.util.Date;

public class CrimeInfo  {
    private Usuario usuario;
    private Location ubicacion;
    private String descripcion;
    private String lugar;
    private String fecha;
    private String TipoCrimen;
    private Long tsLong;

    public CrimeInfo(){}
    public CrimeInfo(Usuario usuario, Location ubicacion, String descripcion,String lugar, String fecha, String tipoCrimen,Long tsLong) {
        this.usuario = usuario;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.TipoCrimen = tipoCrimen;
        this.lugar = lugar;
        this.tsLong = tsLong;

    }

    public Long getTsLong() {
        return tsLong;
    }

    public void setTsLong(Long tsLong) {
        this.tsLong = tsLong;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
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
