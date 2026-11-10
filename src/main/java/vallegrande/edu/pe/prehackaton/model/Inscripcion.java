package vallegrande.edu.pe.prehackaton.model;

public class Inscripcion {
    private int id_inscripcion;
    private int id_eventos;
    private int id_participante;
    private String fecha_inscripcion;
    private String estado;

    public Inscripcion() {}

    public Inscripcion(int id_inscripcion, int id_eventos, int id_participante, String fecha_inscripcion,  String estado) {
        this.id_inscripcion = id_inscripcion;
        this.id_eventos = id_eventos;
        this.id_participante = id_participante;
        this.fecha_inscripcion = fecha_inscripcion;
        this.estado = estado;
    }
    // Getters y Setters (Obligatorios para que JavaFX y las tablas lean los datos)
    public int getId_inscripcion() { return id_inscripcion; }
    public void setId_inscripcion(int id_inscripcion) { this.id_inscripcion = id_inscripcion; }

    public int getId_eventos() { return id_eventos; }
    public void setId_eventos(int id_eventos) { this.id_eventos = id_eventos; }

    public int getId_participante() { return id_participante; }
    public void setId_participante(int id_participante) { this.id_participante = id_participante; }

    public String getFecha_inscripcion() { return fecha_inscripcion; }
    public void setFecha_inscripcion(String fecha_inscripcion) { this.fecha_inscripcion = fecha_inscripcion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}