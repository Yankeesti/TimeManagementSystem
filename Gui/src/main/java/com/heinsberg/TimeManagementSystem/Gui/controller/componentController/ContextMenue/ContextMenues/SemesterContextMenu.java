package com.heinsberg.TimeManagementSystem.Gui.controller.componentController.ContextMenue.ContextMenues;

import com.heinsberg.TimeManagementSystem.BackGround.study.TimeClasses.Semester;
import com.heinsberg.TimeManagementSystem.Gui.ContentManager;
import com.heinsberg.TimeManagementSystem.Gui.controller.componentController.ContextMenue.ContextMenueManager;
import com.heinsberg.TimeManagementSystem.Gui.view.ViewFactory;

public class SemesterContextMenu extends BaseContextMenu {
    public SemesterContextMenu(ContentManager contentManager, ViewFactory viewFactory, ContextMenueManager contextMenueManager) {
        super(null, contentManager, viewFactory, contextMenueManager);
    }

    @Override
    public void deleteObject() {
        viewFactory.getDialogViewFactory().deleteSemester((Semester) holdObject);
    }

    @Override
    public void editObject() {

    }
}
