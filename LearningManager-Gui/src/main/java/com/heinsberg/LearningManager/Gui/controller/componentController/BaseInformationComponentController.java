package com.heinsberg.LearningManager.Gui.controller.componentController;

import com.heinsberg.LearningManager.Gui.ContentManager;
import com.heinsberg.LearningManager.Gui.controller.BaseController;
import com.heinsberg.LearningManager.Gui.view.ViewFactory;
import javafx.scene.Node;

public abstract class BaseInformationComponentController extends BaseController {


    protected Node node; //The Node in wich the Information Panel is shown
    public BaseInformationComponentController(ContentManager contentManager, ViewFactory viewFactory, String fxmlName) {
        super(contentManager, viewFactory, fxmlName);
    }

    public void setNode(Node node){
        this.node = node;
    }

    public abstract void upDateInformation(Object object);

    public Node getNode(){return node;}
}
