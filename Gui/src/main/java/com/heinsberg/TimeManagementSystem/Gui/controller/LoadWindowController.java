package com.heinsberg.TimeManagementSystem.Gui.controller;

import com.heinsberg.TimeManagementSystem.Gui.ContentManager;
import com.heinsberg.TimeManagementSystem.Gui.view.ViewFactory;
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


    public LoadWindowController(ContentManager contentManager, ViewFactory viewFactory, String fxmlName) {
        super(contentManager, viewFactory,fxmlName);
    }


    @FXML
    void createStudyAction() {
        System.out.println("button clicked");
        if(!textCleared || studyNameField.getText().isEmpty()){ // no name For Study given
            errorLabel.setText("Please type in a Name for the Study");
        }else{
            contentManager.createStudy(studyNameField.getText());
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
        if(contentManager.timeManagementSystemFromJson(file) == FileResult.SUCCESS)//succesfull loaded
        {
            viewFactory.showMainWindow();
            closeStage();
        }
    }

    private void closeStage(){
        viewFactory.closeStage((Stage)studyNameField.getScene().getWindow());
    }

}
