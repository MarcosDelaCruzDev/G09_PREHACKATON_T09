package vallegrande.edu.pe.prehackaton;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import vallegrande.edu.pe.prehackaton.view.EventoView;
import vallegrande.edu.pe.prehackaton.view.ParticipanteView;
import vallegrande.edu.pe.prehackaton.view.InscripcionView;

public class Launcher extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 1. Instanciamos las tres vistas que ya creaste
        EventoView eventoView = new EventoView();
        ParticipanteView participanteView = new ParticipanteView();
        InscripcionView inscripcionView = new InscripcionView();

        // 2. Creamos un contenedor de pestañas (TabPane)
        TabPane tabPane = new TabPane();

        Tab tab1 = new Tab("Gestión de Eventos", eventoView.crearVistaEventos());
        Tab tab2 = new Tab("Gestión de Participantes", participanteView.crearVistaParticipantes());
        Tab tab3 = new Tab("Gestión de Inscripciones", inscripcionView.crearVistaInscripciones());

        // Hacemos que las pestañas no se puedan cerrar accidentalmente
        tab1.setClosable(false);
        tab2.setClosable(false);
        tab3.setClosable(false);

        // Agregamos las tres pestañas al panel principal
        tabPane.getTabs().addAll(tab1, tab2, tab3);

        // 4. Creamos la escena principal con el panel de pestañas
        Scene scene = new Scene(tabPane, 900, 600);

        // 5. Configuramos y mostramos la ventana
        primaryStage.setTitle("Sistema PreHackaton - Panel General");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}