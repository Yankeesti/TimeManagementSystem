package com.heinsberg.LearningManager.Gui.controller;

import com.heinsberg.LearningManager.Gui.ContentManager;
import com.heinsberg.LearningManager.Gui.controller.componentController.BaseInformationComponentController;
import com.heinsberg.LearningManager.Gui.controller.componentController.SemesterInformationController;
import com.heinsberg.LearningManager.Gui.controller.componentController.StudyInformationController;
import com.heinsberg.LearningManager.Gui.controller.componentController.SubjectInformationController;
import com.heinsberg.LearningManager.Gui.treeItems.BaseTreeItem;
import com.heinsberg.LearningManager.Gui.view.ViewFactory;

import com.heinsberg.LearningManagerProjekt.BackGround.study.LearningPhaseActionResult;
import com.heinsberg.LearningManagerProjekt.BackGround.study.Listeners.ChangeEnums.StudyChange;
import com.heinsberg.LearningManagerProjekt.BackGround.study.Listeners.StudyListener;
import com.heinsberg.LearningManagerProjekt.BackGround.study.Study;
import com.heinsberg.LearningManagerProjekt.BackGround.study.TimeClasses.Semester;
import com.heinsberg.LearningManagerProjekt.BackGround.study.subject.Subject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController extends BaseController implements Initializable {


    @FXML
    private MenuBar menueBar;

    @FXML
    private Button startLearningPhaseButton, endLearrningPhaseButton;

    @FXML
    private TreeView<String> treeView;

    @FXML
    private AnchorPane informationMainPane;//pane in which other information nodes take place
    private Node shownInformationNode;//the currently shown information Node

    //InformationControllers
    private StudyInformationController studyInformationController;
    private SemesterInformationController semesterInformationController;
    private SubjectInformationController subjectInformationController;

    public MainWindowController(ContentManager contentManager, ViewFactory viewFactory, String fxmlName) {
        super(contentManager, viewFactory, fxmlName);
    }

    @FXML
    void startLearningPhaseAction(ActionEvent event) {
        Subject learningPhaseSubject = viewFactory.getDialogViewFactory().showSubjectChooser(contentManager.getStudy().getCurrentSemester().getSubjects());//The selected subjects for wich will be learned
        if (learningPhaseSubject != null) {
            LearningPhaseActionResult result = contentManager.startLearningPhase(learningPhaseSubject);
            if (result == LearningPhaseActionResult.SUCCESS) {//LearningPhase was started
                startLearningPhaseButton.setVisible(false);
                endLearrningPhaseButton.setVisible(true);
            }
            System.out.println(result);
        }else{
            System.out.println("No Subject selected");
        }
    }

    @FXML
    void endLearningPhaseAction() {
        System.out.println(contentManager.endLearningPhase());
        startLearningPhaseButton.setVisible(true);
        endLearrningPhaseButton.setVisible(false);
    }


    @FXML
    void saveAsAction() {
        String[][] filter = {{"Json File", "*.json"}};
        File file = viewFactory.showFileSaver((Stage) menueBar.getScene().getWindow(), filter, contentManager.getStudy().getName() + " Study Progress");
        System.out.println(contentManager.studyToJson(file));
    }

    /**
     * When save Button is clicked and there is a current File the study gets saved to that file,
     * when there is no current File selected saveAsAction Method is called
     */
    @FXML
    void saveAction() {
        File currentFile = contentManager.getCurrentFile();
        if (currentFile == null) {//there is now current file --> save as new File
            saveAsAction();
        } else {
            System.out.println(contentManager.studyToJson(currentFile));
        }
    }

    @FXML
    void createNewProjectAction(){
        contentManager.addProject(viewFactory.getDialogViewFactory().showProjectCreator());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTreeView();
        setUpInformationPane();
        setUpStudyListner();
    }

    private void setUpStudyListner() {
        contentManager.getStudy().addListener(new StudyListener() {
            @Override
            public void changed(StudyChange studyChange) {
                if(studyChange == StudyChange.CURRENT_LEARNINGPHASE_DELETED){
                    startLearningPhaseButton.setVisible(true);
                    endLearrningPhaseButton.setVisible(false);
                }
            }
        });
    }

    private void setUpInformationPane() {
        //SetUp study Information Pane
        studyInformationController = new StudyInformationController(contentManager, viewFactory, "fxmlComponents/StudyInformation.fxml");
        setUpNode(studyInformationController);
        studyInformationController.getNode().setManaged(true);
        studyInformationController.getNode().setVisible(true);
        shownInformationNode = studyInformationController.getNode();// set Study information to be shown first default

        //SetUp SemesterInformationPane
        semesterInformationController = new SemesterInformationController(contentManager, viewFactory, "fxmlComponents/SemesterInformation.fxml");
        setUpNode(semesterInformationController);

        //SetUp SubjectInformationPane
        subjectInformationController = new SubjectInformationController(contentManager, viewFactory, "fxmlComponents/SubjectInformation.fxml");
        setUpNode(subjectInformationController);
    }

    private void setUpNode(BaseInformationComponentController informationController) {
        Node informationPanel = loadFXML(informationController);
        if (informationPanel != null) {
            setInformationMainPaneAnchor(informationPanel);
            informationMainPane.getChildren().add(informationPanel);
            informationPanel.setManaged(false);
            informationPanel.setVisible(false);
            informationController.setNode(informationPanel);
        } else {
            System.err.println("Node not provided");
        }
    }

    private void setInformationMainPaneAnchor(Node node) {
        informationMainPane.setTopAnchor(node, 0.0);
        informationMainPane.setBottomAnchor(node, 0.0);
        informationMainPane.setRightAnchor(node, 0.0);
        informationMainPane.setLeftAnchor(node, 0.0);

    }

    private void setTreeView() {
        treeView.setRoot(contentManager.getFoldersRoot());
        treeView.setShowRoot(false);
        treeView.setOnMouseClicked(e -> {
            BaseTreeItem<String> item = (BaseTreeItem<String>) treeView.getSelectionModel().getSelectedItem();
            if (item != null) {
                upDateInformationPane(item.getHoldObject());
            }
        });
    }


    public void upDateInformationPane(Object holdObject) {
        Node nodeToBeShown = null;
        if (holdObject.getClass() == Semester.class) {
            System.out.println("show Semester Information");
            semesterInformationController.upDateInformation(holdObject);
            nodeToBeShown = semesterInformationController.getNode();
        } else if (holdObject.getClass() == Subject.class) {
            System.out.println("show Subject Information");
            subjectInformationController.upDateInformation(holdObject);
            nodeToBeShown = subjectInformationController.getNode();
        } else if (holdObject.getClass() == Study.class) {
            System.out.println("show Study Information");
            studyInformationController.upDateInformation(holdObject);
            nodeToBeShown = studyInformationController.getNode();
        }

        shownInformationNode.setManaged(false);
        shownInformationNode.setVisible(false);
        nodeToBeShown.setManaged(true);
        nodeToBeShown.setVisible(true);
        shownInformationNode = nodeToBeShown;
    }

    public Node loadFXML(BaseController controller) {
        String fxmlFileName = controller.getFxmlName();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(fxmlFileName);
            if (url == null) {
                System.err.println("Error");
                return null;
            }
            fxmlLoader.setLocation(getClass().getResource(fxmlFileName));
            fxmlLoader.setController(controller);
            return fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}

