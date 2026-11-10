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

public class InscripcionView {
    private TableView<Inscripcion> tablaInscripciones;
    private InscripcionController InscripcionController;
    private InscripcionDao inscripcionDao;

    public VBox crearVistaInscripciones() {
        InscripcionController = new InscripcionController();
        inscripcionDao =  new InscripcionDao();

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
                System.out.println("Seleccione un evento de la tabla para eliminar.");
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
        ObservableList<Inscripcion> datos = FXCollections.observableArrayList(InscripcionController.obtenerInscripcion());
        tablaInscripciones.setItems(datos);
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
            try {
                if (inscripcionExistente == null) {
                    Inscripcion nuevo = new Inscripcion();
                    nuevo.setId_eventos(Integer.parseInt(txtIdEvento.getText()));
                    nuevo.setId_participante(Integer.parseInt(txtIdParticipante.getText()));
                    nuevo.setFecha_inscripcion(txtFecha_inscripcion.getText());
                    nuevo.setEstado(txtEstado.getText());

                    inscripcionDao.registrarInscripcion(nuevo);
                } else {
                    inscripcionExistente.setId_eventos(Integer.parseInt(txtIdEvento.getText()));
                    inscripcionExistente.setId_participante(Integer.parseInt(txtIdParticipante.getText()));
                    inscripcionExistente.setFecha_inscripcion(txtFecha_inscripcion.getText());
                    inscripcionExistente.setEstado(txtEstado.getText());

                    inscripcionDao.actualizarInscripcion(inscripcionExistente);
                }

                actualizarTabla();
                ventana.close();
            } catch (NumberFormatException ex) {
                System.out.println("Error: ID Evento e ID Participante deben ser números enteros.");
            }
        });

        javafx.scene.Scene escena = new javafx.scene.Scene(grid, 350, 300);
        ventana.setScene(escena);
        ventana.showAndWait();
    }
}