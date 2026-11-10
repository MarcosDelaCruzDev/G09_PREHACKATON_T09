package vallegrande.edu.pe.prehackaton.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import vallegrande.edu.pe.prehackaton.model.Inscripcion;
import vallegrande.edu.pe.prehackaton.model.InscripcionDao;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class InscripcionController implements Initializable {

    @FXML
    private TableView<Inscripcion> tablaInscripcion;
    @FXML
    private TableColumn<Inscripcion, Integer> colId_inscripcion;
    @FXML
    private TableColumn<Inscripcion, Integer> colId_eventos;
    @FXML
    private TableColumn<Inscripcion, Integer> colId_participante;
    @FXML
    private TableColumn<Inscripcion, String> colFecha_inscripcion;
    @FXML
    private TableColumn<Inscripcion, String> colEstado;
    @FXML
    private InscripcionDao inscripcionDao = new InscripcionDao();
    private ObservableList<Inscripcion> listaInscripcion;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId_inscripcion.setCellValueFactory(new PropertyValueFactory<>("id_inscripcion"));
        colId_eventos.setCellValueFactory(new PropertyValueFactory<>("id_eventos"));
        colId_participante.setCellValueFactory(new PropertyValueFactory<>("id_participante"));
        colFecha_inscripcion.setCellValueFactory(new PropertyValueFactory<>("fecha_inscripcion")); // Corregido a fecha_inscripcion
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        cargarDatosTabla();
    }
    public List<Inscripcion> obtenerInscripcion() {
        return inscripcionDao.listarInscripcion();
    }
    public void cargarDatosTabla() {
        listaInscripcion = FXCollections.observableArrayList(inscripcionDao.listarInscripcion());
        tablaInscripcion.setItems(listaInscripcion);
    }

    @FXML
    private void abrirModalRegistrar() {
        System.out.println("Abrir formulario de registro...");
    }

    @FXML
    private void eliminarInscripcionSeleccionado() {
        Inscripcion inscripcionSeleccionado = tablaInscripcion.getSelectionModel().getSelectedItem();
        if (inscripcionSeleccionado != null) {
            inscripcionDao.eliminarInscripcion(inscripcionSeleccionado.getId_inscripcion());
            cargarDatosTabla();
        } else {
            System.out.println("Por favor seleccione una inscripcion para eliminar.");
        }
    }
}