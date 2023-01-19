module com.heinsberg.LearningManager.Gui {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.heinsberg.LearningManager.Gui to javafx.fxml;
    exports com.heinsberg.LearningManager.Gui;
}
