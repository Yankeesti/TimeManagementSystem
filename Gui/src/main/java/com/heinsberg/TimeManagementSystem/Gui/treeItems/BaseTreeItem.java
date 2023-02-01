package com.heinsberg.TimeManagementSystem.Gui.treeItems;

import javafx.scene.control.TreeItem;

public abstract class BaseTreeItem<String> extends TreeItem<String> {


    public BaseTreeItem(String text) {
        super(text);
    }

    /**
     *
     * @return
     */
    public abstract Object getHoldObject();
}
