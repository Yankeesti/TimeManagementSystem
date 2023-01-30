package com.heinsberg.LearningManager.Gui.controller;

import com.heinsberg.LearningManager.Gui.StudyManager;
import com.heinsberg.LearningManager.Gui.controller.componentController.BaseInformationComponentController;
import com.heinsberg.LearningManager.Gui.controller.componentController.SemesterInformationController;
import com.heinsberg.LearningManager.Gui.controller.componentController.StudyInformationController;
import com.heinsberg.LearningManager.Gui.controller.componentController.SubjectInformationController;
import com.heinsberg.LearningManager.Gui.treeItems.BaseTreeItem;
import com.heinsberg.LearningManager.Gui.view.ViewFactory;

import com.heinsberg.LearningManagerProjekt.BackGround.LearningPhaseActionResult;
import com.heinsberg.LearningManagerProjekt.BackGround.Listeners.ChangeEnums.StudyChange;
import com.heinsberg.LearningManagerProjekt.BackGround.Listeners.StudyListener;
import com.heinsberg.LearningManagerProjekt.BackGround.Study;
import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.Semester;
import com.heinsberg.LearningManagerProjekt.BackGround.subject.Subject;
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

    public MainWindowController(StudyManager studyManager, ViewFactory viewFactory, String fxmlName) {
        super(studyManager, viewFactory, fxmlName);
    }

    @FXML
    void startLearningPhaseAction(ActionEvent event) {
        Subject learningPhaseSubject = viewFactory.getDialogViewFactory().showSubjectChooser(studyManager.getStudy().getCurrentSemester().getSubjects());//The selected subjects for wich will be learned
        if (learningPhaseSubject != null) {
            LearningPhaseActionResult result = studyManager.startLearningPhase(learningPhaseSubject);
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
        System.out.println(studyManager.endLearningPhase());
        startLearningPhaseButton.setVisible(true);
        endLearrningPhaseButton.setVisible(false);
    }


    @FXML
    void saveAsAction() {
        String[][] filter = {{"Json File", "*.json"}};
        File file = viewFactory.showFileSaver((Stage) menueBar.getScene().getWindow(), filter, studyManager.getStudy().getName() + " Study Progress");
        System.out.println(studyManager.studyToJson(file));
    }

    /**
     * When save Button is clicked and there is a current File the study gets saved to that file,
     * when there is no current File selected saveAsAction Method is called
     */
    @FXML
    void saveAction() {
        File currentFile = studyManager.getCurrentFile();
        if (currentFile == null) {//there is now current file --> save as new File
            saveAsAction();
        } else {
            System.out.println(studyManager.studyToJson(currentFile));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTreeView();
        setUpInformationPane();
        setUpStudyListner();
    }

    private void setUpStudyListner() {
        studyManager.getStudy().addListener(new StudyListener() {
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
        studyInformationController = new StudyInformationController(studyManager, viewFactory, "fxmlComponents/StudyInformation.fxml");
        setUpNode(studyInformationController);
        studyInformationController.getNode().setManaged(true);
        studyInformationController.getNode().setVisible(true);
        shownInformationNode = studyInformationController.getNode();// set Study information to be shown first default

        //SetUp SemesterInformationPane
        semesterInformationController = new SemesterInformationController(studyManager, viewFactory, "fxmlComponents/SemesterInformation.fxml");
        setUpNode(semesterInformationController);

        //SetUp SubjectInformationPane
        subjectInformationController = new SubjectInformationController(studyManager, viewFactory, "fxmlComponents/SubjectInformation.fxml");
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
        treeView.setRoot(studyManager.getFoldersRoot());
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

