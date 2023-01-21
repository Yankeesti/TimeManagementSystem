module com.heinsberg.LearningManager.Gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.heinsberg.LearningManagerProjekt.Background;
    requires com.google.gson;


    opens com.heinsberg.LearningManager.Gui to javafx.fxml;
    opens com.heinsberg.LearningManager.Gui.controller to javafx.fxml;
    opens com.heinsberg.LearningManager.Gui.controller.componentController to javafx.fxml;
    exports com.heinsberg.LearningManager.Gui;
}
