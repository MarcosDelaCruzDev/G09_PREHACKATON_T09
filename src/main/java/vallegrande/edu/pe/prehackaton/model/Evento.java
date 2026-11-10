package vallegrande.edu.pe.prehackaton.model;

public class Evento {
    private int id_eventos;
    private String nombre;
    private String descripcion;
    private String fecha;
    private String lugar;
    private String modalidad;
    private String estado;

    // Constructor vacío
    public Evento() {}

    // Constructor con parámetros (para cuando crees o edites un evento)
    public Evento(int id_eventos, String nombre, String descripcion, String fecha, String lugar, String modalidad, String estado) {
        this.id_eventos = id_eventos;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.lugar = lugar;
        this.modalidad = modalidad;
        this.estado = estado;
    }

    // Getters y Setters (Obligatorios para que JavaFX y las tablas lean los datos)
    public int getId_eventos() { return id_eventos; }
    public void setId_eventos(int id_eventos) { this.id_eventos = id_eventos; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getLugar() { return lugar; }
    public void setLugar(String lugar) { this.lugar = lugar; }

    public String getModalidad() { return modalidad; }
    public void setModalidad(String modalidad) { this.modalidad = modalidad; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}