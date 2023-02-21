package com.heinsberg.TimeManagementSystem.Gui.controller.componentController.ContextMenue.ContextMenues;

import com.heinsberg.TimeManagementSystem.Gui.ContentManager;
import com.heinsberg.TimeManagementSystem.Gui.controller.componentController.ContextMenue.ContextMenueManager;
import com.heinsberg.TimeManagementSystem.Gui.view.ViewFactory;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

/**
 * Base Context Menu is the Skeleton for Context Menus to edit the Hold Objects of TreeView
 */
public abstract class BaseContextMenu extends ContextMenu {

    protected Object holdObject;
    protected ContentManager contentManager;
    protected ViewFactory viewFactory;
    protected ContextMenueManager contextMenueManager;
    protected MenuItem deleteMenuItem,editMenuItem;

    public BaseContextMenu(Object holdObject, ContentManager contentManager, ViewFactory viewFactory, ContextMenueManager contextMenueManager){
        this.holdObject = holdObject;
        this.contentManager = contentManager;
        this.viewFactory = viewFactory;
        this.contextMenueManager = contextMenueManager;
        setUpMenus();
        setUpItems();
    }

    /**
     * A Method that every child needs to Implement
     * thi method should setUp all additional Items and is called when BaseContextMenu is created
     */
    protected abstract void setUpItems();

    public void upDateObject(Object newObject){
        holdObject = newObject;
    }

    /**
     * Sets Up All Menus a the Base Context Menus should have
     */
    private void setUpMenus() {
        setUpDeleteItem();
        setUpEditMenuItem();

    }

    /**
     * Sets up the Edit MenuItem
     */
    private void setUpEditMenuItem() {
        editMenuItem = new MenuItem("Bearbeiten");
        editMenuItem.setOnAction(e->{
            editObject();
        });
        getItems().add(editMenuItem);
    }

    /**
     * Sets up the delete Menu Item
     */
    private void setUpDeleteItem() {
        deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(e ->{
            deleteObject();
        });
        getItems().add(deleteMenuItem);
    }

    /**
     * Sets the Hold Object to newHold Object
     * @param newHoldObject
     */
    public void setHoldObject(Object newHoldObject){
        holdObject = newHoldObject;
    }

    /**
     * Mehtod deletes the Item that is hold by the TreeItem
     * this method is called when the according MenuItem is clicked
     */
    public abstract void deleteObject();

    /**
     * Method opens editor for Object of tree Item
     * this method is called when the accordingMenuItem is clicked
     */
    public abstract void editObject();
}

