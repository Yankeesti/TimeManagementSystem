module com.heinsberg.TimeManagementSystem.Gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.heinsberg.TimeManagementSystem.Background;
    requires com.google.gson;
    requires java.sql;


    opens com.heinsberg.TimeManagementSystem.Gui.Icons to javafx.graphics;
    opens com.heinsberg.TimeManagementSystem.Gui to javafx.fxml;
    opens com.heinsberg.TimeManagementSystem.Gui.controller to javafx.fxml;
    opens com.heinsberg.TimeManagementSystem.Gui.controller.componentController to javafx.fxml;
    opens com.heinsberg.TimeManagementSystem.Gui.view.DialogPaneControllers.Subject to javafx.fxml;
    opens com.heinsberg.TimeManagementSystem.Gui.view.DialogPaneControllers.Project to javafx.fxml;
    opens com.heinsberg.TimeManagementSystem.Gui.view.DialogPaneControllers.Semester to javafx.fxml;
    opens com.heinsberg.TimeManagementSystem.Gui.view.DialogPaneControllers.Study to javafx.fxml;
    opens com.heinsberg.TimeManagementSystem.Gui.controller.componentController.TimeSpentContainterController to javafx.fxml;
    opens com.heinsberg.TimeManagementSystem.Gui.controller.componentController.subComponents to javafx.fxml;

    exports com.heinsberg.TimeManagementSystem.Gui;
    opens com.heinsberg.TimeManagementSystem.Gui.controller.componentController.subComponents.charts to javafx.fxml;

}
