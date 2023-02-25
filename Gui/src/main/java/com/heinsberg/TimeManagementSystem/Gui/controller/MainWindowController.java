package com.heinsberg.TimeManagementSystem.Gui.controller;

import com.heinsberg.TimeManagementSystem.BackGround.Listeners.ChangeEnums.TimeManagementSystemChange;
import com.heinsberg.TimeManagementSystem.BackGround.Listeners.TimeManagementSystemListener;
import com.heinsberg.TimeManagementSystem.BackGround.abstractClasses.TimeSpentContainer;
import com.heinsberg.TimeManagementSystem.Gui.ContentManager;
import com.heinsberg.TimeManagementSystem.Gui.controller.componentController.*;
import com.heinsberg.TimeManagementSystem.Gui.controller.componentController.ContextMenue.ContextMenues.BaseContextMenu;
import com.heinsberg.TimeManagementSystem.Gui.controller.componentController.ContextMenue.ContextMenueManager;
import com.heinsberg.TimeManagementSystem.Gui.treeItems.BaseTreeItem;
import com.heinsberg.TimeManagementSystem.Gui.view.ViewFactory;

import com.heinsberg.TimeManagementSystem.BackGround.LearningPhaseActionResult;
import com.heinsberg.TimeManagementSystem.BackGround.Project.Project;
import com.heinsberg.TimeManagementSystem.BackGround.study.Study;
import com.heinsberg.TimeManagementSystem.BackGround.study.TimeClasses.Semester;
import com.heinsberg.TimeManagementSystem.BackGround.study.subject.Subject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;
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
    private ProjectInformationController projectInformationController;
    private ContextMenueManager contextMenueManager;

    public MainWindowController(ContentManager contentManager, ViewFactory viewFactory, String fxmlName) {
        super(contentManager, viewFactory, fxmlName);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTreeView();
        setUpInformationPane();
        setUpTimeManagementSystemListener();
        contextMenueManager = new ContextMenueManager(contentManager, viewFactory, treeView);
    }

    /**
     * Is called when the startLearningPhase Button is clicked
     * when a TimeSpentContainer was choosen it starts a LearningPhase for this Button
     * and makes the start LearningPhase Button invisible when nothing is selected nothing changes
     */
    @FXML
    void startLearningPhaseAction() {
        TimeSpentContainer learningPhaseTimeSpentContainer = viewFactory.getDialogViewFactory().showTimeSpentContainerChooser(contentManager.getLearnableTimeSpentContainers());//The selected TimeSpentContainer for which will be learned
        if (learningPhaseTimeSpentContainer != null) {
            LearningPhaseActionResult result = contentManager.startLearningPhase(learningPhaseTimeSpentContainer);
            if (result == LearningPhaseActionResult.SUCCESS) {//LearningPhase was started
                startLearningPhaseButton.setVisible(false);
                endLearrningPhaseButton.setVisible(true);
            }
            System.out.println(result);
        } else {
            System.out.println("No Subject selected");
        }
    }

    @FXML
    void endLearningPhaseAction() {
        System.out.println(contentManager.endLearningPhase());
        startLearningPhaseButton.setVisible(true);
        endLearrningPhaseButton.setVisible(false);
    }

    /**
     * Opens a Create Study Dialog
     */
    @FXML
    void createNewStudyAction(){
        viewFactory.getDialogViewFactory().showStudyCreator();
    }


    @FXML
    void saveAsAction() {
        String[][] filter = {{"Json File", "*.json"}};
        File file = viewFactory.showFileSaver((Stage) menueBar.getScene().getWindow(), filter, "Time Management System");
        System.out.println(contentManager.timeManagementSystemToJson(file));
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
            System.out.println(contentManager.timeManagementSystemToJson(currentFile));
        }
    }

    @FXML
    void createNewProjectAction() {
        contentManager.addProject(viewFactory.getDialogViewFactory().showProjectCreator());
    }


    private void setUpTimeManagementSystemListener() {
        contentManager.getTimeManagementSystem().addListener(new TimeManagementSystemListener() {
            @Override
            public void changed(TimeManagementSystemChange change) {
                if (change == TimeManagementSystemChange.CURRENT_LEARNINGPHASE_DELETED) ;
                startLearningPhaseButton.setVisible(true);
                endLearrningPhaseButton.setVisible(false);
            }
        });
    }

    private void setUpInformationPane() {
        //SetUp study Information Pane
        studyInformationController = new StudyInformationController(contentManager, viewFactory, "fxmlComponents/StudyInformation.fxml");
        setUpNode(studyInformationController);
        shownInformationNode = studyInformationController.getNode();// set Study information to be shown first default

        //SetUp SemesterInformationPane
        semesterInformationController = new SemesterInformationController(contentManager, viewFactory, "fxmlComponents/SemesterInformation.fxml");
        setUpNode(semesterInformationController);

        //SetUp SubjectInformationPane
        subjectInformationController = new SubjectInformationController(contentManager, viewFactory, "fxmlComponents/TimeSpentContainerInformation.fxml");
        setUpNode(subjectInformationController);

        //SetUp ProjectInformationPane
        projectInformationController = new ProjectInformationController(contentManager, viewFactory, "fxmlComponents/TimeSpentContainerInformation.fxml");
        setUpNode(projectInformationController);
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
                if (e.getButton() == MouseButton.PRIMARY) {
                    upDateInformationPane(item.getHoldObject());
                    contextMenueManager.closeMenu();
                } else if (e.getButton() == MouseButton.SECONDARY) {
                    contextMenueManager.showMenue((BaseTreeItem) treeView.getSelectionModel().getSelectedItem(), e.getScreenX(), e.getScreenY());
                }
            }else // when TreeView is clicked and not with Secondary click the menu gets closed
            contextMenueManager.closeMenu();

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
        } else if (holdObject.getClass() == Project.class) {
            System.out.println("show Project Information");
            projectInformationController.upDateInformation(holdObject);
            nodeToBeShown = projectInformationController.getNode();
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


    public void showStandardInformation() {
        shownInformationNode.setManaged(false);
        shownInformationNode.setVisible(false);
    }
}

