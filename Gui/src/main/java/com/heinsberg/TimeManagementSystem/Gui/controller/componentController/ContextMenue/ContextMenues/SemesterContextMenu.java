package com.heinsberg.TimeManagementSystem.Gui.controller.componentController.ContextMenue.ContextMenues;

import com.heinsberg.TimeManagementSystem.BackGround.study.TimeClasses.Semester;
import com.heinsberg.TimeManagementSystem.BackGround.study.subject.Subject;
import com.heinsberg.TimeManagementSystem.Gui.ContentManager;
import com.heinsberg.TimeManagementSystem.Gui.controller.componentController.ContextMenue.ContextMenueManager;
import com.heinsberg.TimeManagementSystem.Gui.view.ViewFactory;
import javafx.scene.control.MenuItem;

public class SemesterContextMenu extends BaseContextMenu {
    protected MenuItem addSubjectItem;

    public SemesterContextMenu(ContentManager contentManager, ViewFactory viewFactory, ContextMenueManager contextMenueManager) {
        super(null, contentManager, viewFactory, contextMenueManager);
    }

    @Override
    protected void setUpItems() {
        setUpAddSubjectItem();
    }

    /**
     * creates a new Menu Item that when clicked lets the User create a new Subject for the Semester
     */
    private void setUpAddSubjectItem() {
        addSubjectItem = new MenuItem(("Fach hinzufügen"));
        addSubjectItem.setOnAction(e -> {
            addSubject();
        });
        getItems().add(addSubjectItem);
    }

    @Override
    public void deleteObject() {
        viewFactory.getDialogViewFactory().deleteSemester((Semester) holdObject);
    }

    @Override
    public void editObject() {
        viewFactory.getDialogViewFactory().showSemesterEditor((Semester) holdObject);
    }

    /**
     * Called when add Subject is clicked
     *
     */
    public void addSubject() {
        Subject newSubject = viewFactory.getDialogViewFactory().showSubjectCreator((Semester) holdObject);
        if(newSubject != null){
            contentManager.addNewSubject(((Semester)holdObject).getStudy(),newSubject);
        }
    }
}
