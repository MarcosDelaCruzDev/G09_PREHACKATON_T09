package vallegrande.edu.pe.prehackaton.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import vallegrande.edu.pe.prehackaton.model.Evento;
import vallegrande.edu.pe.prehackaton.model.EventoDao;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EventoController implements Initializable {

    @FXML
    private TableView<Evento> tablaEventos;
    @FXML
    private TableColumn<Evento, Integer> colId;
    @FXML
    private TableColumn<Evento, String> colNombre;
    @FXML
    private TableColumn<Evento, String> colDescripcion;
    @FXML
    private TableColumn<Evento, String> colFecha;
    @FXML
    private TableColumn<Evento, String> colLugar;
    @FXML
    private TableColumn<Evento, String> colModalidad;
    @FXML
    private TableColumn<Evento, String> colEstado;

    private EventoDao eventoDao = new EventoDao();
    private ObservableList<Evento> listaEventos;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id_eventos"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colLugar.setCellValueFactory(new PropertyValueFactory<>("lugar"));
        colModalidad.setCellValueFactory(new PropertyValueFactory<>("modalidad"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        cargarDatosTabla();
    }
    public List<Evento> obtenerEventos() {
        return eventoDao.listarEventos();
    }
    public void cargarDatosTabla() {
        listaEventos = FXCollections.observableArrayList(eventoDao.listarEventos());
        tablaEventos.setItems(listaEventos);
    }

    @FXML
    private void abrirModalRegistrar() {
        System.out.println("Abrir formulario de registro...");
    }

    @FXML
    private void eliminarEventoSeleccionado() {
        Evento eventoSeleccionado = tablaEventos.getSelectionModel().getSelectedItem();
        if (eventoSeleccionado != null) {
            eventoDao.eliminarEvento(eventoSeleccionado.getId_eventos());
            cargarDatosTabla();
        } else {
            System.out.println("Por favor seleccione un evento para eliminar.");
        }
    }
}