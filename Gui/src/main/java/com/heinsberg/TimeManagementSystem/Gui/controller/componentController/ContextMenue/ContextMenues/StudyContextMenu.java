package com.heinsberg.TimeManagementSystem.Gui.controller.componentController.ContextMenue.ContextMenues;

import com.heinsberg.TimeManagementSystem.BackGround.study.Study;
import com.heinsberg.TimeManagementSystem.Gui.ContentManager;
import com.heinsberg.TimeManagementSystem.Gui.controller.componentController.ContextMenue.ContextMenueManager;
import com.heinsberg.TimeManagementSystem.Gui.view.ViewFactory;
import javafx.scene.control.MenuItem;

public class StudyContextMenu extends BaseContextMenu{
    public StudyContextMenu( ContentManager contentManager, ViewFactory viewFactory, ContextMenueManager contextMenueManager) {
        super(null, contentManager, viewFactory, contextMenueManager);
    }

    @Override
    protected void setUpItems() {
        setUpAddSemesterItem();
    }

    private void setUpAddSemesterItem() {
        MenuItem addSemester = new MenuItem("neues Semester");
        addSemester.setOnAction(e->{
            viewFactory.getDialogViewFactory().showSemesterCreator((Study) holdObject);
        });
        getItems().add(addSemester);
    }

    @Override
    public void deleteObject() {
        viewFactory.getDialogViewFactory().deleteStudy((Study) holdObject);
    }

    @Override
    public void editObject() {
        viewFactory.getDialogViewFactory().showStudyEditor((Study) holdObject);
    }
}
