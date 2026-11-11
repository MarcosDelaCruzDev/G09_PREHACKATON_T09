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
import vallegrande.edu.pe.prehackaton.controller.ParticipanteController;
import vallegrande.edu.pe.prehackaton.model.Participante;
import vallegrande.edu.pe.prehackaton.model.ParticipanteDao;

public class ParticipanteView {
    private TableView<Participante> tablaParticipantes;
    private ParticipanteController participanteController;
    private ParticipanteDao participanteDao;

    public VBox crearVistaParticipantes() {
        participanteController = new ParticipanteController();
        participanteDao = new ParticipanteDao();
        tablaParticipantes = new TableView<>();

        TableColumn<Participante, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id_participante"));

        TableColumn<Participante, String> colNombre = new TableColumn<>("Nombres");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombres"));

        TableColumn<Participante, String> colApellidos = new TableColumn<>("Apellidos");
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));

        TableColumn<Participante, String> colDni = new TableColumn<>("DNI");
        colDni.setCellValueFactory(new PropertyValueFactory<>("dni"));

        TableColumn<Participante, String> colCorreo = new TableColumn<>("Correo");
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));

        TableColumn<Participante, String> colTelefono = new TableColumn<>("Teléfono");
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        TableColumn<Participante, String> colInstitucion = new TableColumn<>("Institución");
        colInstitucion.setCellValueFactory(new PropertyValueFactory<>("institucion"));

        tablaParticipantes.getColumns().addAll(colId, colNombre, colApellidos, colDni, colCorreo, colTelefono, colInstitucion);

        actualizarTabla();

        Button btnNuevo = new Button("Nuevo Participante");
        Button btnEditar = new Button("Editar");
        Button btnEliminar = new Button("Eliminar");

        btnNuevo.setOnAction(e -> abrirFormulario(null));

        btnEditar.setOnAction(e -> {
            Participante seleccionado = tablaParticipantes.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                abrirFormulario(seleccionado);
            } else {
                System.out.println("Por favor seleccione un participante para editar.");
            }
        });

        btnEliminar.setOnAction(e -> {
            Participante participanteSeleccionado = tablaParticipantes.getSelectionModel().getSelectedItem();
            if (participanteSeleccionado != null) {
                participanteDao.eliminarParticipante(participanteSeleccionado.getId_participante());
                actualizarTabla();
            } else {
                System.out.println("Seleccione un participante de la tabla para eliminar.");
            }
        });

        HBox panelBotones = new HBox(10);
        panelBotones.getChildren().addAll(btnNuevo, btnEditar, btnEliminar);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.getChildren().addAll(panelBotones, tablaParticipantes);

        return layout;
    }

    private void actualizarTabla() {
        ObservableList<Participante> datos = FXCollections.observableArrayList(participanteController.obtenerParticipante());
        tablaParticipantes.setItems(datos);
    }

    // Devuelve un mensaje de error, o null si los datos son válidos
    private String validar(String nombres, String apellidos, String dni, String correo, String telefono) {
        if (nombres.isEmpty() || apellidos.isEmpty()) return "Nombres y apellidos son obligatorios.";
        if (!dni.matches("\\d{8}")) return "El DNI debe tener exactamente 8 dígitos.";
        if (!correo.matches("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$")) return "El correo no es válido.";
        if (!telefono.matches("\\d{9}")) return "El teléfono debe tener 9 dígitos.";
        return null;
    }

    // Ventana emergente (Formulario) para Registrar o Editar Participante
    private void abrirFormulario(Participante participanteExistente) {
        Stage ventana = new Stage();
        ventana.initModality(Modality.APPLICATION_MODAL);
        ventana.setTitle(participanteExistente == null ? "Registrar Nuevo Participante" : "Editar Participante");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(15));
        grid.setHgap(10);
        grid.setVgap(10);

        TextField txtNombres = new TextField();
        TextField txtApellidos = new TextField();
        TextField txtDni = new TextField();
        TextField txtCorreo = new TextField();
        TextField txtTelefono = new TextField();
        TextField txtInstitucion = new TextField();

        if (participanteExistente != null) {
            txtNombres.setText(participanteExistente.getNombres());
            txtApellidos.setText(participanteExistente.getApellidos());
            txtDni.setText(participanteExistente.getDni());
            txtCorreo.setText(participanteExistente.getCorreo());
            txtTelefono.setText(participanteExistente.getTelefono());
            txtInstitucion.setText(participanteExistente.getInstitucion());
        }

        grid.add(new Label("Nombres:"), 0, 0);
        grid.add(txtNombres, 1, 0);
        grid.add(new Label("Apellidos:"), 0, 1);
        grid.add(txtApellidos, 1, 1);
        grid.add(new Label("DNI:"), 0, 2);
        grid.add(txtDni, 1, 2);
        grid.add(new Label("Correo:"), 0, 3);
        grid.add(txtCorreo, 1, 3);
        grid.add(new Label("Teléfono:"), 0, 4);
        grid.add(txtTelefono, 1, 4);
        grid.add(new Label("Institución:"), 0, 5);
        grid.add(txtInstitucion, 1, 5);

        Button btnGuardar = new Button("Guardar");
        grid.add(btnGuardar, 1, 6);

        btnGuardar.setOnAction(e -> {
            String error = validar(txtNombres.getText().trim(), txtApellidos.getText().trim(),
                    txtDni.getText().trim(), txtCorreo.getText().trim(), txtTelefono.getText().trim());
            if (error != null) {
                new Alert(Alert.AlertType.WARNING, error).showAndWait();
                return; // keep the form open so the user can fix the data
            }

            if (participanteExistente == null) {
                // Registrar nuevo
                Participante nuevo = new Participante();
                nuevo.setNombres(txtNombres.getText().trim());
                nuevo.setApellidos(txtApellidos.getText().trim());
                nuevo.setDni(txtDni.getText().trim());
                nuevo.setCorreo(txtCorreo.getText().trim());
                nuevo.setTelefono(txtTelefono.getText().trim());
                nuevo.setInstitucion(txtInstitucion.getText().trim());

                participanteDao.registrarParticipante(nuevo);
            } else {
                // Actualizar existente
                participanteExistente.setNombres(txtNombres.getText().trim());
                participanteExistente.setApellidos(txtApellidos.getText().trim());
                participanteExistente.setDni(txtDni.getText().trim());
                participanteExistente.setCorreo(txtCorreo.getText().trim());
                participanteExistente.setTelefono(txtTelefono.getText().trim());
                participanteExistente.setInstitucion(txtInstitucion.getText().trim());

                participanteDao.actualizarParticipante(participanteExistente);
            }

            actualizarTabla();
            ventana.close();
        });

        javafx.scene.Scene escena = new javafx.scene.Scene(grid, 350, 320);
        ventana.setScene(escena);
        ventana.showAndWait();
    }
}