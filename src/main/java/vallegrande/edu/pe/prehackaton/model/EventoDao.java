package vallegrande.edu.pe.prehackaton.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventoDao {

    public List<Evento> listarEventos() {
        List<Evento> lista = new ArrayList<>();
        String sql = "SELECT * FROM Eventos";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Evento evento = new Evento();
                evento.setId_eventos(rs.getInt("id_eventos"));
                evento.setNombre(rs.getString("nombre"));
                evento.setDescripcion(rs.getString("descripcion"));
                evento.setFecha(rs.getString("fecha"));
                evento.setLugar(rs.getString("lugar"));
                evento.setModalidad(rs.getString("modalidad"));
                evento.setEstado(rs.getString("estado"));

                lista.add(evento);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar eventos: " + e.getMessage());
        }

        return lista;
    }

    public void registrarEvento(Evento evento) {
        String sql = "INSERT INTO Eventos (nombre, descripcion, fecha, lugar, modalidad, estado) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, evento.getNombre());
            ps.setString(2, evento.getDescripcion());
            ps.setString(3, evento.getFecha());
            ps.setString(4, evento.getLugar());
            ps.setString(5, evento.getModalidad());
            ps.setString(6, evento.getEstado());
            ps.executeUpdate();
            System.out.println("Evento registrado con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al registrar evento: " + e.getMessage());
        }
    }

    public void actualizarEvento(Evento evento) {
        String sql = "UPDATE Eventos SET nombre = ?, descripcion = ?, fecha = ?, lugar = ?, modalidad = ?, estado = ? WHERE id_eventos = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, evento.getNombre());
            ps.setString(2, evento.getDescripcion());
            ps.setString(3, evento.getFecha());
            ps.setString(4, evento.getLugar());
            ps.setString(5, evento.getModalidad());
            ps.setString(6, evento.getEstado());
            ps.setInt(7, evento.getId_eventos());
            ps.executeUpdate();
            System.out.println("Evento actualizado con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar evento: " + e.getMessage());
        }
    }

    public void eliminarEvento(int idEvento) {
        String sql = "DELETE FROM Eventos WHERE id_eventos = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idEvento);
            ps.executeUpdate();
            System.out.println("Evento eliminado con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar evento: " + e.getMessage());
        }
    }

    // Indica si ya existe un evento con el mismo nombre en la misma fecha.
    // idExcluir: id del evento que se está editando (usar 0 al registrar uno nuevo),
    // para que al editar no se compare contra sí mismo.
    public boolean existeEvento(String nombre, String fecha, int idExcluir) {
        String sql = "SELECT COUNT(*) FROM Eventos WHERE nombre = ? AND fecha = ? AND id_eventos <> ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, fecha);
            ps.setInt(3, idExcluir);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar evento duplicado: " + e.getMessage());
            return false;
        }
    }

}