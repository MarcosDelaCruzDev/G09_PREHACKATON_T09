package vallegrande.edu.pe.prehackaton.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import vallegrande.edu.pe.prehackaton.model.Participante;
import vallegrande.edu.pe.prehackaton.model.ParticipanteDao;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ParticipanteController implements Initializable {

    @FXML
    private TableView<Participante> tablaParticipante;
    @FXML
    private TableColumn<Participante, Integer> colId;
    @FXML
    private TableColumn<Participante, String> colNombres;
    @FXML
    private TableColumn<Participante, String> colApellidos;
    @FXML
    private TableColumn<Participante, String> colDni;
    @FXML
    private TableColumn<Participante, String> colCorreo;
    @FXML
    private TableColumn<Participante, String> colTelefono;
    @FXML
    private TableColumn<Participante, String> colInstitucion;

    private ParticipanteDao participanteDao = new ParticipanteDao();
    private ObservableList<Participante> listaParticipante;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id_participante"));
        colNombres.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colInstitucion.setCellValueFactory(new PropertyValueFactory<>("institucion"));

        cargarDatosTabla();
    }
    public List<Participante> obtenerParticipante() {
        return participanteDao.listarParticipante();
    }
    public void cargarDatosTabla() {
        listaParticipante = FXCollections.observableArrayList(participanteDao.listarParticipante());
        tablaParticipante.setItems(listaParticipante);
    }

    @FXML
    private void abrirModalRegistrar() {
        System.out.println("Abrir formulario de registro...");
    }

    @FXML
    private void eliminarParticipanteSeleccionado() {
        Participante participanteSeleccionado = tablaParticipante.getSelectionModel().getSelectedItem();
        if (participanteSeleccionado != null) {
            participanteDao.eliminarParticipante(participanteSeleccionado.getId_participante());
            cargarDatosTabla();
        } else {
            System.out.println("Por favor seleccione un Participante para eliminar.");
        }
    }
}
