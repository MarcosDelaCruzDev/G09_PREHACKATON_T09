package vallegrande.edu.pe.prehackaton.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import vallegrande.edu.pe.prehackaton.controller.EventoController;
import vallegrande.edu.pe.prehackaton.model.Evento;
import vallegrande.edu.pe.prehackaton.model.EventoDao;

public class EventoView {

    private TableView<Evento> tablaEventos;
    private EventoController eventoController;
    private EventoDao eventoDao;

    public VBox crearVistaEventos() {
        eventoController = new EventoController();
        eventoDao = new EventoDao();

        tablaEventos = new TableView<>();

        TableColumn<Evento, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id_eventos"));

        TableColumn<Evento, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Evento, String> colDescripcion = new TableColumn<>("Descripción");
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        TableColumn<Evento, String> colFecha = new TableColumn<>("Fecha");
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        TableColumn<Evento, String> colLugar = new TableColumn<>("Lugar");
        colLugar.setCellValueFactory(new PropertyValueFactory<>("lugar"));

        TableColumn<Evento, String> colModalidad = new TableColumn<>("Modalidad");
        colModalidad.setCellValueFactory(new PropertyValueFactory<>("modalidad"));

        TableColumn<Evento, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        tablaEventos.getColumns().addAll(colId, colNombre, colDescripcion, colFecha, colLugar, colModalidad, colEstado);

        actualizarTabla();

        Button btnNuevo = new Button("Nuevo Evento");
        Button btnEditar = new Button("Editar");
        Button btnEliminar = new Button("Eliminar");

        btnNuevo.setOnAction(e -> abrirFormulario(null));

        btnEditar.setOnAction(e -> {
            Evento seleccionado = tablaEventos.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                abrirFormulario(seleccionado);
            } else {
                System.out.println("Por favor seleccione un evento para editar.");
            }
        });

        btnEliminar.setOnAction(e -> {
            Evento eventoSeleccionado = tablaEventos.getSelectionModel().getSelectedItem();
            if (eventoSeleccionado != null) {
                eventoDao.eliminarEvento(eventoSeleccionado.getId_eventos());
                actualizarTabla();
            } else {
                System.out.println("Seleccione un evento de la tabla para eliminar.");
            }
        });

        HBox panelBotones = new HBox(10);
        panelBotones.getChildren().addAll(btnNuevo, btnEditar, btnEliminar);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.getChildren().addAll(panelBotones, tablaEventos);

        return layout;
    }

    private void actualizarTabla() {
        ObservableList<Evento> datos = FXCollections.observableArrayList(eventoController.obtenerEventos());
        tablaEventos.setItems(datos);
    }

    private void abrirFormulario(Evento eventoExistente) {
        Stage ventana = new Stage();
        ventana.initModality(Modality.APPLICATION_MODAL);
        ventana.setTitle(eventoExistente == null ? "Registrar Nuevo Evento" : "Editar Evento");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(15));
        grid.setHgap(10);
        grid.setVgap(10);

        TextField txtNombre = new TextField();
        TextField txtDescripcion = new TextField();
        TextField txtFecha = new TextField();
        TextField txtLugar = new TextField();
        TextField txtModalidad = new TextField();
        TextField txtEstado = new TextField();

        if (eventoExistente != null) {
            txtNombre.setText(eventoExistente.getNombre());
            txtDescripcion.setText(eventoExistente.getDescripcion());
            txtFecha.setText(eventoExistente.getFecha());
            txtLugar.setText(eventoExistente.getLugar());
            txtModalidad.setText(eventoExistente.getModalidad());
            txtEstado.setText(eventoExistente.getEstado());
        }

        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(txtNombre, 1, 0);
        grid.add(new Label("Descripción:"), 0, 1);
        grid.add(txtDescripcion, 1, 1);
        grid.add(new Label("Fecha (YYYY-MM-DD):"), 0, 2);
        grid.add(txtFecha, 1, 2);
        grid.add(new Label("Lugar:"), 0, 3);
        grid.add(txtLugar, 1, 3);
        grid.add(new Label("Modalidad:"), 0, 4);
        grid.add(txtModalidad, 1, 4);
        grid.add(new Label("Estado:"), 0, 5);
        grid.add(txtEstado, 1, 5);

        Button btnGuardar = new Button("Guardar");
        grid.add(btnGuardar, 1, 6);

        btnGuardar.setOnAction(e -> {
            if (eventoExistente == null) {
                Evento nuevo = new Evento();
                nuevo.setNombre(txtNombre.getText());
                nuevo.setDescripcion(txtDescripcion.getText());
                nuevo.setFecha(txtFecha.getText());
                nuevo.setLugar(txtLugar.getText());
                nuevo.setModalidad(txtModalidad.getText());
                nuevo.setEstado(txtEstado.getText());

                eventoDao.registrarEvento(nuevo);
            } else {
                eventoExistente.setNombre(txtNombre.getText());
                eventoExistente.setDescripcion(txtDescripcion.getText());
                eventoExistente.setFecha(txtFecha.getText());
                eventoExistente.setLugar(txtLugar.getText());
                eventoExistente.setModalidad(txtModalidad.getText());
                eventoExistente.setEstado(txtEstado.getText());

                eventoDao.actualizarEvento(eventoExistente);
            }

            actualizarTabla();
            ventana.close();
        });

        javafx.scene.Scene escena = new javafx.scene.Scene(grid, 350, 320);
        ventana.setScene(escena);
        ventana.showAndWait();
    }
}