package vallegrande.edu.pe.prehackaton.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ParticipanteDao {
    public List<Participante> listarParticipante() {
        List<Participante> lista = new ArrayList<>();
        String sql = "SELECT * FROM Participantes";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Participante participante = new Participante();
                participante.setId_participante(rs.getInt("id_participante"));
                participante.setNombres(rs.getString("nombres"));
                participante.setApellidos(rs.getString("apellidos"));
                participante.setDni(rs.getString("dni"));
                participante.setCorreo(rs.getString("correo"));
                participante.setTelefono(rs.getString("telefono"));
                participante.setInstitucion(rs.getString("institucion"));

                lista.add(participante);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar participantes: " + e.getMessage());
        }

        return lista;
    }
    public void registrarParticipante(Participante participante) {
        String sql = "INSERT INTO Participantes (nombres, apellidos, dni, correo, telefono, institucion) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, participante.getNombres());
            ps.setString(2, participante.getApellidos());
            ps.setString(3, participante.getDni());
            ps.setString(4, participante.getCorreo());
            ps.setString(5, participante.getTelefono());
            ps.setString(6, participante.getInstitucion());
            ps.executeUpdate();
            System.out.println("Participante registrado con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al registrar Participante: " + e.getMessage());
        }
    }

    public void actualizarParticipante(Participante participante) {
        String sql = "UPDATE Participantes SET nombre = ?, descripcion = ?, fecha = ?, lugar = ?, modalidad = ?, estado = ? WHERE id_eventos = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, participante.getNombres());
            ps.setString(2, participante.getApellidos());
            ps.setString(3, participante.getDni());
            ps.setString(4, participante.getCorreo());
            ps.setString(5, participante.getTelefono());
            ps.setString(6, participante.getInstitucion());
            ps.executeUpdate();
            System.out.println("Participante actualizado con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar Participante: " + e.getMessage());
        }
    }

    public void eliminarParticipante(int idParticipante) {
        String sql = "DELETE FROM Participantes WHERE id_Participante = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idParticipante);
            ps.executeUpdate();
            System.out.println("Participante eliminado con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar Participante: " + e.getMessage());
        }
    }

}