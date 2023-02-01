package com.heinsberg.LearningManager.Gui.view.DialogPaneControllers.Project;

import com.heinsberg.LearningManager.Gui.ContentManager;
import com.heinsberg.LearningManager.Gui.view.ViewFactory;
import com.heinsberg.LearningManagerProjekt.BackGround.Project.Project;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;

import java.net.URL;
import java.util.ResourceBundle;

public class ProjectEditorController extends ProjectManipulateController implements Initializable {
    private ButtonType deleteButton;
    Project project;
    public ProjectEditorController(ContentManager contentManager, ViewFactory viewFactory, String fxmlName, Project project) {
        super(contentManager, viewFactory, fxmlName);
        this.project = project;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpBasicFunctions();
        setUpNameField();
        setUpDeleteButton();
        setUpShownWeekGoal();
    }

    /**
     * Saves the changes to Project
     */
    public void submitChanges(){
        project.editInformation(projectNameField.getText(),getWeekGoal());
    }
    private void setUpDeleteButton() {
        deleteButton = new ButtonType("Delete");
        dialogPane.getButtonTypes().add(deleteButton);
    }


    private void setUpNameField() {
        projectNameField.setText(project.getName());
    }

    public ButtonType getDeleteButton() {
        return deleteButton;
    }

    private void setUpShownWeekGoal(){
        hourViewFactory.setValue(project.getWeekGoal()/60);
        minuteViewFactory.setValue(project.getWeekGoal()%60);
    }



}
