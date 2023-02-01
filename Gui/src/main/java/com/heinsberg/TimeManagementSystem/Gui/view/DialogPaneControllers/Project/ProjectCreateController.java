package com.heinsberg.TimeManagementSystem.Gui.view.DialogPaneControllers.Project;

import com.heinsberg.TimeManagementSystem.Gui.ContentManager;
import com.heinsberg.TimeManagementSystem.Gui.view.ViewFactory;
import com.heinsberg.TimeManagementSystem.BackGround.Project.Project;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.net.URL;
import java.util.ResourceBundle;

public class ProjectCreateController extends ProjectManipulateController implements Initializable {
    public ProjectCreateController(ContentManager contentManager, ViewFactory viewFactory, String fxmlName) {
        super(contentManager, viewFactory, fxmlName);
    }

    public Project getCreatedProject() {
        if (!projectNameField.getText().isEmpty()) {
            Project outPut = new Project(projectNameField.getText());
            outPut.setWeekGoal(getWeekGoal());
            return outPut;
        }
        return null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpBasicFunctions();
        setUpNameField();
    }
    private void setUpNameField() {
        projectNameField.setPromptText("ProjectName");
        checkInput();
        projectNameField.textProperty().addListener((observable) -> {
            checkInput(); //when text is edited Check input
        });
    }

    private void checkInput() {
        Button okButton = okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        if (!projectNameField.getText().isEmpty()) {
            okButton.setDisable(false);
        } else {
            okButton.setDisable(true);
        }
    }
}
