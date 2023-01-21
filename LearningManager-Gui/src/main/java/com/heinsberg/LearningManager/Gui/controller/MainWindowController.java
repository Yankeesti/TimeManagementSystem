package com.heinsberg.LearningManager.Gui.controller;

import com.heinsberg.LearningManager.Gui.StudyManager;
import com.heinsberg.LearningManager.Gui.controller.componentController.StudyInformationController;
import com.heinsberg.LearningManager.Gui.controller.services.FileManagerSevice;
import com.heinsberg.LearningManager.Gui.treeItems.BaseTreeItem;
import com.heinsberg.LearningManager.Gui.treeItems.SemesterTreeItem;
import com.heinsberg.LearningManager.Gui.treeItems.StudyTreeItem;
import com.heinsberg.LearningManager.Gui.treeItems.SubjectTreeItem;
import com.heinsberg.LearningManager.Gui.view.ViewFactory;

import com.heinsberg.LearningManagerProjekt.BackGround.Study;
import com.heinsberg.LearningManagerProjekt.BackGround.subject.Subject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController extends BaseController implements Initializable {


    @FXML
    private MenuBar menueBar;

    @FXML
    private Button startLearningPhaseButton;

    @FXML
    private TreeView<String> treeView;

    @FXML
    private AnchorPane informationMainPane;//pane in which other information nodes take place

    //InformationControllers
    private StudyInformationController studyInformationController;

    public MainWindowController(StudyManager studyManager, ViewFactory viewFactory, String fxmlName) {
        super(studyManager, viewFactory, fxmlName);
    }

    @FXML
    void startLearningPhaseAction(ActionEvent event) {
        System.out.println("startLearningPhase");
    }



    @FXML
    void saveAsAction(){
        File file = new File("/Users/timheinsberg/Desktop/Programmieren/WorkSpace/LearningManager/Data/test.json");
        FileManagerSevice.saveStudy(studyManager.getStudy(),file);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTreeView();
        setUpInformationPane();
    }

    private void setUpInformationPane() {
        studyInformationController = new StudyInformationController(studyManager,viewFactory,"fxmlComponents/StudyInformation.fxml");
        Node informationPanel = loadFXML(studyInformationController);
        if(informationPanel != null) {
        informationMainPane.getChildren().add(informationPanel);
        setInformationMainPaneAnchor(informationPanel);}
    }

    private void setInformationMainPaneAnchor(Node node){
        informationMainPane.setTopAnchor(node,0.0);
        informationMainPane.setBottomAnchor(node,0.0);
        informationMainPane.setRightAnchor(node,0.0);
        informationMainPane.setLeftAnchor(node,0.0);

    }

    private void setTreeView(){
        treeView.setRoot(studyManager.getFoldersRoot());
        treeView.setShowRoot(false);
        treeView.setOnMouseClicked(e ->{
            BaseTreeItem<String> item = (BaseTreeItem<String>) treeView.getSelectionModel().getSelectedItem();
            if(item != null){//Item is Selected

            }
        });
    }

    public Node loadFXML(BaseController controller){
        String fxmlFileName = controller.getFxmlName();
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(fxmlFileName);
            if(url == null) {
                System.err.println("Error");
                return null;
            }
            fxmlLoader.setLocation(getClass().getResource(fxmlFileName));
            fxmlLoader.setController(controller);
            return fxmlLoader.load();
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }


}

