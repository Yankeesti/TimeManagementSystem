package com.heinsberg.TimeManagementSystem.Gui.view.DialogPaneControllers.Study;

import com.heinsberg.TimeManagementSystem.Gui.ContentManager;
import com.heinsberg.TimeManagementSystem.Gui.view.ViewFactory;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class StudyCreatorController extends StudyManipulateController implements Initializable {
    public StudyCreatorController(ContentManager contentManager, ViewFactory viewFactory, String fxmlName) {
        super(contentManager, viewFactory, fxmlName);
    }

    /**
     * Creates a new Study in Time Management System with the given Data
     */
    public void createStudy(){
        contentManager.addNewStudy(studyNameField.getText());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpBasicFunction();
        setUpNameField();
    }

    private void setUpNameField() {
        studyNameField.setPromptText("Studiums Name");
    }
}
