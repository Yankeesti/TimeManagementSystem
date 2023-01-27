package com.heinsberg.LearningManager.Gui.controller;

import com.heinsberg.LearningManager.Gui.StudyManager;
import com.heinsberg.LearningManager.Gui.view.ViewFactory;
import com.heinsberg.LearningManagerProjekt.BackGround.Study;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;

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
            viewFactory.showMainWindow();
            closeStage();
        }

    }
    @FXML
    void clickedInStudyNameFieldAction(){
        if(!textCleared){
        studyNameField.clear();
        textCleared = true;}
    }

    @FXML
    void loadFromFileAction(){
        File file = viewFactory.showFileOpener((Stage)studyNameField.getScene().getWindow(),new String[][]{{"JSon File", "*.json"}});
        if(studyManager.studyFromJson(file) == FileResult.SUCCESS)//succesfull loaded
        {
            viewFactory.showMainWindow();
            closeStage();
        }
    }

    private void closeStage(){
        viewFactory.closeStage((Stage)studyNameField.getScene().getWindow());
    }

}
