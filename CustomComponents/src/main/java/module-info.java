module com.heinsberg.LearningManagerProjekt.customcomponents {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.heinsberg.LearningManagerProjekt.customcomponents to javafx.fxml;
    opens com.heinsberg.LearningManagerProjekt.customcomponents.sceneController to javafx.fxml;
    exports com.heinsberg.LearningManagerProjekt.customcomponents;
}