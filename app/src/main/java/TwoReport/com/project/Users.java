package TwoReport.com.project;

public class Users {
    private String nombre;
    private String email;
    private String contrasenia;
    private String usuario;


    public Users() {
    }

    public Users(String nombre, String email, String contrasenia, String usuario) {
        this.nombre = nombre;
        this.email = email;
        this.contrasenia = contrasenia;
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
