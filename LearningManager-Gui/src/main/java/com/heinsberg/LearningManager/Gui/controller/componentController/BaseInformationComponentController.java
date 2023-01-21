package com.heinsberg.LearningManager.Gui.controller.componentController;

import com.heinsberg.LearningManager.Gui.StudyManager;
import com.heinsberg.LearningManager.Gui.controller.BaseController;
import com.heinsberg.LearningManager.Gui.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;

public abstract class BaseInformationComponentController extends BaseController {


    protected Node node; //The Node in wich the Information Panel is shown
    public BaseInformationComponentController(StudyManager studyManager, ViewFactory viewFactory, String fxmlName) {
        super(studyManager, viewFactory, fxmlName);
    }

    public void setNode(Node node){
        this.node = node;
    }

    public abstract void upDateInformation(Object object);

    public Node getNode(){return node;}
}
