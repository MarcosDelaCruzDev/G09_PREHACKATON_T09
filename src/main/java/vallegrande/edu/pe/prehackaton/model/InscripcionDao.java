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
                inscripcion.setEstado(rs.getString("estado")); // o inscripcion.setEstado(...)

                lista.add(inscripcion);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar inscripcion: " + e.getMessage());
        }

        return lista;
    }
    public void registrarInscripcion(Inscripcion inscripcion) {
        String sql = "INSERT INTO Inscripciones (fecha_inscripcion, estado) VALUES (?, ?)";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, inscripcion.getFecha_inscripcion());
            ps.setString(2, inscripcion.getEstado());
            ps.executeUpdate();
            System.out.println("Inscripcion registrado con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al registrar Inscripcion: " + e.getMessage());
        }
    }

    public void actualizarInscripcion(Inscripcion inscripcion) {
        String sql = "UPDATE Inscripciones SET fecha_inscripcion = ?, estado = ? WHERE id_inscripcion = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, inscripcion.getFecha_inscripcion());
            ps.setString(2, inscripcion.getEstado());
            ps.executeUpdate();
            System.out.println("Inscripcion actualizado con éxito.");
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
            System.out.println("Inscripcion eliminado con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar Inscripcion: " + e.getMessage());
        }
    }

}