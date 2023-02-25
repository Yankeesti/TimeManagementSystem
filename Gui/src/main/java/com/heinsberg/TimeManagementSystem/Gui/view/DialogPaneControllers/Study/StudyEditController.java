package com.heinsberg.TimeManagementSystem.Gui.view.DialogPaneControllers.Study;

import com.heinsberg.TimeManagementSystem.BackGround.study.Study;
import com.heinsberg.TimeManagementSystem.Gui.ContentManager;
import com.heinsberg.TimeManagementSystem.Gui.view.ViewFactory;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class StudyEditController extends StudyManipulateController implements Initializable {

    private Study studyToEdit;
    public StudyEditController(ContentManager contentManager, ViewFactory viewFactory, String fxmlName, Study studyToEdit) {
        super(contentManager, viewFactory, fxmlName);
        this.studyToEdit = studyToEdit;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpBasicFunction();
        setUpOkButton();
        setUpNameField();
    }

    /**
     * Sets up the Study Name Field with Study Name
     */
    private void setUpNameField() {
        studyNameField.setText(studyToEdit.getName());
    }

    public void submitChanges(){
        studyToEdit.changeName(studyNameField.getText());
    }

    /**
     * Sets up the Ok/Save Button
     */
    private void setUpOkButton() {
        okButton.setText("Speichern");
    }
}
