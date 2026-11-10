package vallegrande.edu.pe.prehackaton.model;

public class Participante {
    private int id_participante;
    private String nombres;
    private String apellidos;
    private String dni;
    private String correo;
    private String telefono;
    private String institucion;

    // Constructor vacío
    public Participante() {}

    // Constructor con parámetros (para cuando crees o edites un evento)
    public Participante(int id_participante, String nombres, String apellidos, String dni, String correo, String telefono, String institucion) {
        this.id_participante = id_participante;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dni = dni;
        this.correo = correo;
        this.telefono = telefono;
        this.institucion = institucion;
    }

    // Getters y Setters (Obligatorios para que JavaFX y las tablas lean los datos)
    public int getId_participante() { return id_participante; }
    public void setId_participante(int id_participante) { this.id_participante = id_participante; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getInstitucion() { return institucion; }
    public void setInstitucion(String institucion) { this.institucion = institucion; }
}