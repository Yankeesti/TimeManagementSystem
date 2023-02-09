package com.heinsberg.TimeManagementSystem.Gui.controller.componentController.ContextMenue.ContextMenues;

import com.heinsberg.TimeManagementSystem.BackGround.Project.Project;
import com.heinsberg.TimeManagementSystem.BackGround.abstractClasses.TimeSpentContainer;
import com.heinsberg.TimeManagementSystem.Gui.ContentManager;
import com.heinsberg.TimeManagementSystem.Gui.controller.componentController.ContextMenue.ContextMenueManager;
import com.heinsberg.TimeManagementSystem.Gui.view.ViewFactory;
import javafx.scene.control.MenuItem;

public class ProjectContextMenue extends BaseContextMenu {

    private MenuItem editProject;

    public ProjectContextMenue(ContentManager contentManager, ViewFactory viewFactory, ContextMenueManager contextMenueManager) {
        super(null, contentManager, viewFactory, contextMenueManager);
    }


    @Override
    public void deleteObject() {
        viewFactory.getDialogViewFactory().deleteSubject((TimeSpentContainer) holdObject);
    }

    @Override
    public void editObject() {
        viewFactory.getDialogViewFactory().showProjectEditor((Project) holdObject);
    }
}
