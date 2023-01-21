package com.heinsberg.LearningManager.Gui.treeItems;

import com.heinsberg.LearningManagerProjekt.BackGround.subject.Subject;
import javafx.scene.control.TreeItem;

public class SubjectTreeItem<String> extends BaseTreeItem<String> {
    private Subject subject;

    public SubjectTreeItem(Subject subject) {
        super((String) subject.getSubjectName());
        setExpanded(true);
        this.subject = subject;
    }

    @Override
    public Object getHoldObject() {
        return subject;
    }
}
