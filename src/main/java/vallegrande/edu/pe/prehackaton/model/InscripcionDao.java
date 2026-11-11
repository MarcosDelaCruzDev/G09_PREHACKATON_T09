package vallegrande.edu.pe.prehackaton.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InscripcionDao {

    public List<Inscripcion> listarInscripcion() {
        List<Inscripcion> lista = new ArrayList<>();
        String sql = "SELECT * FROM Inscripciones";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Inscripcion inscripcion = new Inscripcion();
                inscripcion.setId_inscripcion(rs.getInt("id_inscripcion"));
                inscripcion.setId_eventos(rs.getInt("id_eventos"));
                inscripcion.setId_participante(rs.getInt("id_participante"));
                inscripcion.setFecha_inscripcion(rs.getString("fecha_inscripcion"));
                inscripcion.setEstado(rs.getString("estado"));

                lista.add(inscripcion);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar inscripcion: " + e.getMessage());
        }

        return lista;
    }

    public void registrarInscripcion(Inscripcion inscripcion) {
        String sql = "INSERT INTO Inscripciones (id_eventos, id_participante, fecha_inscripcion, estado) VALUES (?, ?, ?, ?)";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, inscripcion.getId_eventos());
            ps.setInt(2, inscripcion.getId_participante());
            ps.setString(3, inscripcion.getFecha_inscripcion());
            ps.setString(4, inscripcion.getEstado());
            ps.executeUpdate();
            System.out.println("Inscripcion registrada con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al registrar Inscripcion: " + e.getMessage());
        }
    }

    public void actualizarInscripcion(Inscripcion inscripcion) {
        String sql = "UPDATE Inscripciones SET id_eventos = ?, id_participante = ?, fecha_inscripcion = ?, estado = ? WHERE id_inscripcion = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, inscripcion.getId_eventos());
            ps.setInt(2, inscripcion.getId_participante());
            ps.setString(3, inscripcion.getFecha_inscripcion());
            ps.setString(4, inscripcion.getEstado());
            ps.setInt(5, inscripcion.getId_inscripcion());
            ps.executeUpdate();
            System.out.println("Inscripcion actualizada con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar Inscripcion: " + e.getMessage());
        }
    }

    public void eliminarInscripcion(int idInscripcion) {
        String sql = "DELETE FROM Inscripciones WHERE id_inscripcion = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idInscripcion);
            ps.executeUpdate();
            System.out.println("Inscripcion eliminada con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar Inscripcion: " + e.getMessage());
        }
    }

    // Indica si ya existe una inscripción del mismo participante en el mismo evento.
    // idExcluir: id de la inscripción que se está editando (usar 0 al registrar una nueva),
    // para que al editar no se compare contra sí misma.
    public boolean existeInscripcion(int idEvento, int idParticipante, int idExcluir) {
        String sql = "SELECT COUNT(*) FROM Inscripciones WHERE id_eventos = ? AND id_participante = ? AND id_inscripcion <> ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idEvento);
            ps.setInt(2, idParticipante);
            ps.setInt(3, idExcluir);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar inscripcion duplicada: " + e.getMessage());
            return false;
        }
    }

}