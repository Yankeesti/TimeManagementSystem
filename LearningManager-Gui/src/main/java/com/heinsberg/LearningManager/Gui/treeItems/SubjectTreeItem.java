package com.heinsberg.LearningManager.Gui.treeItems;

import com.heinsberg.LearningManagerProjekt.BackGround.Listeners.ChangeEnums.SubjectChange;
import com.heinsberg.LearningManagerProjekt.BackGround.Listeners.SubjectListener;
import com.heinsberg.LearningManagerProjekt.BackGround.subject.Subject;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.control.TreeItem;

public class SubjectTreeItem<String> extends BaseTreeItem<String> {
    private Subject subject;

    public SubjectTreeItem(Subject subject) {
        super((String) subject.getSubjectName());
        setExpanded(true);
        this.subject = subject;
        subject.addListener(new SubjectListener() {//ADDS a Listner to the Subject Tree Item, when the name has changed the TreeItem gets updated
            @Override
            public void changed(SubjectChange subjectChange) {
                if(subjectChange == SubjectChange.CHANGED_NAME){
                    setValue((String) subject.getSubjectName());
                }
            }
        });
    }

    @Override
    public Object getHoldObject() {
        return subject;
    }
}
