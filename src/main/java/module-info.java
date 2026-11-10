module vallegrande.edu.pe.prehackaton {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens vallegrande.edu.pe.prehackaton.model to javafx.base;
    opens vallegrande.edu.pe.prehackaton to javafx.fxml;
    exports vallegrande.edu.pe.prehackaton;
}