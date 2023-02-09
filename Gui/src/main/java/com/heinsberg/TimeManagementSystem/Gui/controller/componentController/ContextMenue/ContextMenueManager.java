package com.heinsberg.TimeManagementSystem.Gui.controller.componentController.ContextMenue;

import com.heinsberg.TimeManagementSystem.BackGround.study.Study;
import com.heinsberg.TimeManagementSystem.Gui.ContentManager;
import com.heinsberg.TimeManagementSystem.Gui.controller.BaseController;
import com.heinsberg.TimeManagementSystem.Gui.controller.componentController.ContextMenue.ContextMenues.*;
import com.heinsberg.TimeManagementSystem.Gui.treeItems.*;
import com.heinsberg.TimeManagementSystem.Gui.view.ViewFactory;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/**
 * Class Manages all Context menus for the MainWindow
 * controlls that only one ContextMenue at a Time can be shown
 */
public class ContextMenueManager extends BaseController {

    private BaseContextMenu shownMenu;
    private ProjectContextMenue projectContextMenu;
    private SubjectContextMenu subjectContextMenu;
    private StudyContextMenu studyContextMenu;
    private SemesterContextMenu semesterContextMenu;
    private TreeView treeView;

    public ContextMenueManager(ContentManager contentManager, ViewFactory viewFactory, TreeView treeView) {
        super(contentManager, viewFactory, "");
        projectContextMenu = new ProjectContextMenue(contentManager, viewFactory, this);
        subjectContextMenu = new SubjectContextMenu(contentManager, viewFactory, this);
        studyContextMenu = new StudyContextMenu(contentManager, viewFactory, this);
        semesterContextMenu = new SemesterContextMenu(contentManager, viewFactory, this);
        this.treeView = treeView;
        setUpBaseContextMenu();
    }

    private void setUpBaseContextMenu() {
    }

    public void showMenue(BaseTreeItem treeItem, double xPosition, double yPosition) {
        if (shownMenu != null) {// close Menu
            shownMenu.hide();
            shownMenu = null;
        }
        if (treeItem instanceof ProjectTreeItem) {
            showMenu(projectContextMenu,treeItem,xPosition,yPosition);
        } else if (treeItem instanceof SubjectTreeItem) {
            showMenu(subjectContextMenu,treeItem,xPosition,yPosition);
        } else if (treeItem instanceof StudyTreeItem) {
            showMenu(studyContextMenu,treeItem,xPosition,yPosition);
        } else if (treeItem instanceof SemesterTreeItem) {
            showMenu(semesterContextMenu,treeItem,xPosition,yPosition);
        }
    }

    private void showMenu(BaseContextMenu menuToShow, BaseTreeItem treeItem, double xPosition, double yPosition) {
        menuToShow.setHoldObject(treeItem.getHoldObject());
        shownMenu = menuToShow;
        menuToShow.show(treeView, xPosition, yPosition);
    }

    /**
     * Mehtod is closed when the shown Menu was closed, sets shownMenu to null
     */
    public void shownMenuClosed() {
        shownMenu = null;
    }

    /**
     * closes the currently shown Menu
     */
    public void closeMenu() {
        if (shownMenu != null) {
            shownMenu.hide();
            shownMenu = null;
        }
    }
}
