package com.heinsberg.TimeManagementSystem.Gui.view.DialogPaneControllers.Study;

import com.heinsberg.TimeManagementSystem.Gui.ContentManager;
import com.heinsberg.TimeManagementSystem.Gui.controller.BaseController;
import com.heinsberg.TimeManagementSystem.Gui.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;

public abstract class StudyManipulateController extends BaseController {
    @FXML
    protected TextField studyNameField;
    @FXML
    protected DialogPane dialogPane;
    protected Button okButton;

    public StudyManipulateController(ContentManager contentManager, ViewFactory viewFactory, String fxmlName) {
        super(contentManager, viewFactory, fxmlName);
    }

    protected void setUpBasicFunction(){
        setUpOkButton();
        setUpNameFieldListener();
    }

    private void setUpOkButton() {
        okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
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
}
