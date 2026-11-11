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
import vallegrande.edu.pe.prehackaton.controller.InscripcionController;
import vallegrande.edu.pe.prehackaton.model.Inscripcion;
import vallegrande.edu.pe.prehackaton.model.InscripcionDao;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class InscripcionView {
    private TableView<Inscripcion> tablaInscripciones;
    private InscripcionController inscripcionController;
    private InscripcionDao inscripcionDao;

    public VBox crearVistaInscripciones() {
        inscripcionController = new InscripcionController();
        inscripcionDao = new InscripcionDao();

        tablaInscripciones = new TableView<>();

        TableColumn<Inscripcion, Integer> colIDInscripcion = new TableColumn<>("ID Inscripcion");
        colIDInscripcion.setCellValueFactory(new PropertyValueFactory<>("id_inscripcion"));

        TableColumn<Inscripcion, Integer> colIDEvento = new TableColumn<>("ID Evento");
        colIDEvento.setCellValueFactory(new PropertyValueFactory<>("id_eventos"));

        TableColumn<Inscripcion, Integer> colIDParticipante = new TableColumn<>("ID Participante");
        colIDParticipante.setCellValueFactory(new PropertyValueFactory<>("id_participante"));

        TableColumn<Inscripcion, String> colFecha_inscripcion = new TableColumn<>("Fecha_inscripcion");
        colFecha_inscripcion.setCellValueFactory(new PropertyValueFactory<>("fecha_inscripcion"));

        TableColumn<Inscripcion, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        tablaInscripciones.getColumns().addAll(colIDInscripcion, colIDEvento, colIDParticipante, colFecha_inscripcion, colEstado);

        actualizarTabla();

        Button btnNuevo = new Button("Nuevo Inscripcion");
        Button btnEditar = new Button("Editar");
        Button btnEliminar = new Button("Eliminar");

        btnNuevo.setOnAction(e -> abrirFormulario(null));

        btnEditar.setOnAction(e -> {
            Inscripcion seleccionado = tablaInscripciones.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                abrirFormulario(seleccionado);
            } else {
                System.out.println("Por favor seleccione una Inscripcion para editar.");
            }
        });

        btnEliminar.setOnAction(e -> {
            Inscripcion inscripcionSeleccionado = tablaInscripciones.getSelectionModel().getSelectedItem();
            if (inscripcionSeleccionado != null) {
                inscripcionDao.eliminarInscripcion(inscripcionSeleccionado.getId_inscripcion());
                actualizarTabla();
            } else {
                System.out.println("Seleccione una inscripcion de la tabla para eliminar.");
            }
        });

        HBox panelBotones = new HBox(10);
        panelBotones.getChildren().addAll(btnNuevo, btnEditar, btnEliminar);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.getChildren().addAll(panelBotones, tablaInscripciones);

        return layout;
    }

    private void actualizarTabla() {
        ObservableList<Inscripcion> datos = FXCollections.observableArrayList(inscripcionController.obtenerInscripcion());
        tablaInscripciones.setItems(datos);
    }

    private void mostrarAdvertencia(String mensaje) {
        new Alert(Alert.AlertType.WARNING, mensaje).showAndWait();
    }

    private boolean fechaValida(String fecha) {
        try {
            LocalDate.parse(fecha); // formato YYYY-MM-DD
            return true;
        } catch (DateTimeParseException ex) {
            return false;
        }
    }

    private void abrirFormulario(Inscripcion inscripcionExistente) {
        Stage ventana = new Stage();
        ventana.initModality(Modality.APPLICATION_MODAL);
        ventana.setTitle(inscripcionExistente == null ? "Registrar Nueva Inscripción" : "Editar Inscripción");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(15));
        grid.setHgap(10);
        grid.setVgap(10);

        TextField txtIdEvento = new TextField();
        TextField txtIdParticipante = new TextField();
        TextField txtFecha_inscripcion = new TextField();
        TextField txtEstado = new TextField();

        if (inscripcionExistente != null) {
            txtIdEvento.setText(String.valueOf(inscripcionExistente.getId_eventos()));
            txtIdParticipante.setText(String.valueOf(inscripcionExistente.getId_participante()));
            txtFecha_inscripcion.setText(inscripcionExistente.getFecha_inscripcion());
            txtEstado.setText(inscripcionExistente.getEstado());
        }

        grid.add(new Label("ID Evento:"), 0, 0);
        grid.add(txtIdEvento, 1, 0);
        grid.add(new Label("ID Participante:"), 0, 1);
        grid.add(txtIdParticipante, 1, 1);
        grid.add(new Label("Fecha (YYYY-MM-DD):"), 0, 2);
        grid.add(txtFecha_inscripcion, 1, 2);
        grid.add(new Label("Estado:"), 0, 3);
        grid.add(txtEstado, 1, 3);

        Button btnGuardar = new Button("Guardar");
        grid.add(btnGuardar, 1, 4);

        btnGuardar.setOnAction(e -> {
            String fecha = txtFecha_inscripcion.getText().trim();
            String estado = txtEstado.getText().trim();

            int idEvento;
            int idParticipante;
            try {
                idEvento = Integer.parseInt(txtIdEvento.getText().trim());
                idParticipante = Integer.parseInt(txtIdParticipante.getText().trim());
            } catch (NumberFormatException ex) {
                mostrarAdvertencia("ID Evento e ID Participante deben ser números enteros.");
                return;
            }

            if (idEvento <= 0 || idParticipante <= 0) {
                mostrarAdvertencia("Los IDs deben ser mayores que cero.");
                return;
            }
            if (!fechaValida(fecha)) {
                mostrarAdvertencia("La fecha debe tener el formato AAAA-MM-DD (ejemplo: 2026-11-20).");
                return;
            }
            if (estado.isEmpty()) {
                mostrarAdvertencia("El estado es obligatorio.");
                return;
            }

            // Evitar duplicados: mismo participante en el mismo evento
            int idActual = (inscripcionExistente == null) ? 0 : inscripcionExistente.getId_inscripcion();
            if (inscripcionDao.existeInscripcion(idEvento, idParticipante, idActual)) {
                mostrarAdvertencia("Este participante ya está inscrito en ese evento.");
                return;
            }

            if (inscripcionExistente == null) {
                Inscripcion nuevo = new Inscripcion();
                nuevo.setId_eventos(idEvento);
                nuevo.setId_participante(idParticipante);
                nuevo.setFecha_inscripcion(fecha);
                nuevo.setEstado(estado);

                inscripcionDao.registrarInscripcion(nuevo);
            } else {
                inscripcionExistente.setId_eventos(idEvento);
                inscripcionExistente.setId_participante(idParticipante);
                inscripcionExistente.setFecha_inscripcion(fecha);
                inscripcionExistente.setEstado(estado);

                inscripcionDao.actualizarInscripcion(inscripcionExistente);
            }

            actualizarTabla();
            ventana.close();
        });

        javafx.scene.Scene escena = new javafx.scene.Scene(grid, 350, 300);
        ventana.setScene(escena);
        ventana.showAndWait();
    }
}