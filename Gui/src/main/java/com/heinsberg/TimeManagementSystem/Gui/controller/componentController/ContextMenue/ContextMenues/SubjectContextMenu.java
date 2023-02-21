package com.heinsberg.TimeManagementSystem.Gui.controller.componentController.ContextMenue.ContextMenues;

import com.heinsberg.TimeManagementSystem.BackGround.abstractClasses.TimeSpentContainer;
import com.heinsberg.TimeManagementSystem.BackGround.study.Study;
import com.heinsberg.TimeManagementSystem.BackGround.study.subject.Subject;
import com.heinsberg.TimeManagementSystem.Gui.ContentManager;
import com.heinsberg.TimeManagementSystem.Gui.controller.componentController.ContextMenue.ContextMenueManager;
import com.heinsberg.TimeManagementSystem.Gui.view.ViewFactory;

public class SubjectContextMenu extends BaseContextMenu{
    public SubjectContextMenu( ContentManager contentManager, ViewFactory viewFactory, ContextMenueManager contextMenueManager) {
        super(null, contentManager, viewFactory, contextMenueManager);
    }

    @Override
    protected void setUpItems() {

    }

    @Override
    public void deleteObject() {
        viewFactory.getDialogViewFactory().deleteTimeSpentContainer((TimeSpentContainer) holdObject);
    }

    @Override
    public void editObject() {
        viewFactory.getDialogViewFactory().showSubjectEditor((Subject) holdObject);
    }
}
