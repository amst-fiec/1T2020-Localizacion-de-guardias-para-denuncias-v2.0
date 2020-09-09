package TwoReport.com.project;

public class Guardia {
    String uid;
    String nombreCompleto;
    String email;
    String telefono;
    String photoUrl;
    Double latitud;
    Double longitud;

    public Guardia() {
    }

    public Guardia(String nombreCompleto, String email, String telefono, String photoUrl) {
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.telefono = telefono;
        this.photoUrl = photoUrl;
    }

    public Guardia(String nombreCompleto, String email, String telefono, String photoUrl,Double latitud, Double longitud) {
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.telefono = telefono;
        this.photoUrl = photoUrl;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Guardia(String uid, String nombreCompleto, String email, String telefono, String photoUrl) {
        this.uid = uid;
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.telefono = telefono;
        this.photoUrl = photoUrl;
    }

    public String getUid() {
        return uid;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}