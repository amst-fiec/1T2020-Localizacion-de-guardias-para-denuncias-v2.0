package TwoReport.com.project;

public class Guardia extends Users{
    private Location ubicacion;

    public Guardia(String nombre, String email, String contrasenia, String usuario, Location ubicacion) {
        super(nombre, email, contrasenia, usuario);
        this.ubicacion = ubicacion;
    }

    public Location getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Location ubicacion) {
        this.ubicacion = ubicacion;
    }
}
