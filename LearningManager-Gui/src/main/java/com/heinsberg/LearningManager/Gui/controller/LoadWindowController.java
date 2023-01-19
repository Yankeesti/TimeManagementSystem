package com.heinsberg.LearningManager.Gui.controller;

import com.heinsberg.LearningManager.Gui.StudyManager;
import com.heinsberg.LearningManager.Gui.view.ViewFactory;
import com.heinsberg.LearningManagerProjekt.BackGround.Study;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoadWindowController extends BaseController {

    @FXML
    private TextField studyNameField;
    private boolean textCleared = false;

    @FXML
    private Label errorLabel;


    public LoadWindowController(StudyManager studyManager, ViewFactory viewFactory,String fxmlName) {
        super(studyManager, viewFactory,fxmlName);
    }


    @FXML
    void createStudyAction(ActionEvent event) {
        System.out.println("button clicked");
        if(!textCleared || studyNameField.getText().isEmpty()){ // no name For Study given
            errorLabel.setText("Please type in a Name for the Study");
        }else{
            studyManager.createStudy(studyNameField.getText());
        }

    }
    @FXML
    void clickedInStudyNameFieldAction(){
        if(!textCleared){
        studyNameField.clear();
        textCleared = true;}
    }

}
