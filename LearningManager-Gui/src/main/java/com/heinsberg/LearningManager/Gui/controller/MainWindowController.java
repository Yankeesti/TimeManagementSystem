package com.heinsberg.LearningManager.Gui.controller;

import com.heinsberg.LearningManager.Gui.StudyManager;
import com.heinsberg.LearningManager.Gui.view.ViewFactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;

public class MainWindowController extends BaseController {

    @FXML
    private AnchorPane informationPane;

    @FXML
    private MenuBar menueBar;

    @FXML
    private Button startLearningPhaseButton;

    @FXML
    private TreeView<?> treeView;

    public MainWindowController(StudyManager studyManager, ViewFactory viewFactory, String fxmlName) {
        super(studyManager, viewFactory, fxmlName);
    }

    @FXML
    void startLearningPhaseAction(ActionEvent event) {
        System.out.println("startLearningPhase");
    }

}

