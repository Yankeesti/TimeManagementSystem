module com.heinsberg.LearningManagerProjekt.Gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.heinsberg.LearningManagerProjekt.customcomponents;
    requires com.heinsberg.LearningManagerProjekt.Background;


    opens com.heinsberg.LearningManagerProjekt.Lernmanager.gui to javafx.fxml;
    exports com.heinsberg.LearningManagerProjekt.LearningManagerProjekt.Gui;
}