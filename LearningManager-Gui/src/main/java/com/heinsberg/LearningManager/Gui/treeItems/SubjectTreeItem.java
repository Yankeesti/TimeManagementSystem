package com.heinsberg.LearningManager.Gui.treeItems;

import com.heinsberg.LearningManagerProjekt.BackGround.study.Listeners.ChangeEnums.SubjectChange;
import com.heinsberg.LearningManagerProjekt.BackGround.study.Listeners.SubjectListener;
import com.heinsberg.LearningManagerProjekt.BackGround.study.subject.Subject;
import javafx.application.Platform;

public class SubjectTreeItem<String> extends BaseTreeItem<String> {
    private Subject subject;

    public SubjectTreeItem(Subject subject) {
        super((String) subject.getSubjectName());
        setExpanded(true);
        this.subject = subject;
        subject.addListener(new SubjectListener() {//ADDS a Listner to the Subject Tree Item, when the name has changed the TreeItem gets updated
            @Override
            public void changed(SubjectChange subjectChange) {
                if(subjectChange == SubjectChange.CHANGED_NAME|| subjectChange == SubjectChange.EDITED_SUBJECT){
                    Platform.runLater(() ->{ //lets Actions in Lambda Expression run on javaFx Thread
                        setValue((String) subject.getSubjectName());
                    });
                }
            }
        });
    }

    @Override
    public Object getHoldObject() {
        return subject;
    }
}
