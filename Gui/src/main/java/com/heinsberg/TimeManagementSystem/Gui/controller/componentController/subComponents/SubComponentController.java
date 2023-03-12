package com.heinsberg.TimeManagementSystem.Gui.controller.componentController.subComponents;

import com.heinsberg.TimeManagementSystem.Gui.ContentManager;
import com.heinsberg.TimeManagementSystem.Gui.controller.BaseController;
import com.heinsberg.TimeManagementSystem.Gui.view.ViewFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

public abstract class SubComponentController extends BaseController {

    protected Node node;
    public SubComponentController(ContentManager contentManager, ViewFactory viewFactory, String fxmlName) {
        super(contentManager, viewFactory, fxmlName);
        loadNode();
    }

    /**
     * @return loads the Node of this TableView
     */
    private void loadNode() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlName + ".fxml"));
        fxmlLoader.setController(this);
        try {
            node = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Node getNode(){
        return node;
    }

    public abstract void refresh();
}
