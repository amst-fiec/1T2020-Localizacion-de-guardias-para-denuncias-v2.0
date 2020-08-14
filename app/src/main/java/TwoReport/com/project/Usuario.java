package TwoReport.com.project;

public class Usuario extends Users {
    private Location ubicacion;
    private String telefono;

    public Usuario(String nombre, String email, String contrasenia, String usuario, Location ubicacion, String telefono) {
        super(nombre, email, contrasenia, usuario);
        this.ubicacion = ubicacion;
        this.telefono = telefono;
    }



    public Location getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Location ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
