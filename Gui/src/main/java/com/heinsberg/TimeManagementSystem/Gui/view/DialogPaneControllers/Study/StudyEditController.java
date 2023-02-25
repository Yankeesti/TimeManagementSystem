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

    public void submitChanges(){
        studyToEdit.changeName(studyNameField.getText());
    }

    /**
     * Sets up the Study Name Field with Study Name
     * and adds a Listener
     */
    private void setUpNameField() {
        studyNameField.setText(studyToEdit.getName());
        setUpNameFieldListener();
    }

    /**
     * Sets up a Listener to the Text Field so when no name is given
     * the ok Button gets Disabled
     */
    private void setUpNameFieldListener() {
        studyNameField.textProperty().addListener((observable -> {
            checkInput();
        }));
    }

    /**
     * Checks weather the Name Field is Empty or not and disables/enables the save Button
     */
    private void checkInput() {
        if(studyNameField.getText().isEmpty()){
            okButton.setDisable(true);
        }else{
            okButton.setDisable(false);
        }
    }

    /**
     * Sets up the Ok/Save Button
     */
    private void setUpOkButton() {
        okButton.setText("Speichern");
    }
}
