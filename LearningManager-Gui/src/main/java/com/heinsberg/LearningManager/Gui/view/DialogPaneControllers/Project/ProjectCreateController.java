package com.heinsberg.LearningManager.Gui.view.DialogPaneControllers.Project;

import com.heinsberg.LearningManager.Gui.ContentManager;
import com.heinsberg.LearningManager.Gui.view.ViewFactory;
import com.heinsberg.LearningManagerProjekt.BackGround.Project.Project;
import javafx.fxml.Initializable;

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
    }
}
