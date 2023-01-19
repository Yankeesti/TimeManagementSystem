module com.heinsberg.LearningManager.Gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.heinsberg.LearningManagerProjekt.Background;

    opens com.heinsberg.LearningManager.Gui to javafx.fxml;
    opens com.heinsberg.LearningManager.Gui.controller to javafx.fxml;
    exports com.heinsberg.LearningManager.Gui;
}
