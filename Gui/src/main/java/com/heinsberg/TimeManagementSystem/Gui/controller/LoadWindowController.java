package com.heinsberg.TimeManagementSystem.Gui.controller;

import com.heinsberg.TimeManagementSystem.Gui.ContentManager;
import com.heinsberg.TimeManagementSystem.Gui.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class LoadWindowController extends BaseController implements Initializable {

    @FXML
    private TextField studyNameField;
    @FXML
    private TextField projectNameField;
    @FXML
    private Button createProjectButton;
    @FXML
    private Button createStudyButton;

    @FXML
    private Label errorLabel;


    public LoadWindowController(ContentManager contentManager, ViewFactory viewFactory, String fxmlName) {
        super(contentManager, viewFactory,fxmlName);
    }

    @FXML
    void createProjectAction(){
        System.out.println("Create Project");
        if(!projectNameField.getText().isEmpty()){
            contentManager.createProject(projectNameField.getText());
            viewFactory.showMainWindow();
            closeStage();
        }
    }

    @FXML
    void createStudyAction() {
        System.out.println("Create Study");
        if(!studyNameField.getText().isEmpty()){ // no name For Study given
            contentManager.createStudy(studyNameField.getText());
            viewFactory.showMainWindow();
            closeStage();
        }

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpButtons();
    }

    /**
     * makes Buttons unclickable when no Name is given
     */
    private void setUpButtons() {
        projectNameField.textProperty().addListener((observable,oldValue,newValue)->{
            checkInputProject(newValue);
        });
        studyNameField.textProperty().addListener((observable,oldValue,newValue)-> {
            checkInputStudy(newValue);
        });
    }

    private void checkInputStudy(String newValue) {
        if(newValue.isEmpty()){
            createStudyButton.setDisable(true);
        }else{
            createStudyButton.setDisable(false);
        }
    }

    private void checkInputProject(String newValue) {
        if(newValue.isEmpty()){
            createProjectButton.setDisable(true);
        }else{
            createProjectButton.setDisable(false);
        }
    }
}
