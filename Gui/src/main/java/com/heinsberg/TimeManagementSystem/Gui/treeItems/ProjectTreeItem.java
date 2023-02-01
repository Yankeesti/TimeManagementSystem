package com.heinsberg.TimeManagementSystem.Gui.treeItems;

import com.heinsberg.TimeManagementSystem.BackGround.Project.Project;
import com.heinsberg.TimeManagementSystem.BackGround.study.Listeners.ChangeEnums.SubjectChange;
import com.heinsberg.TimeManagementSystem.BackGround.study.Listeners.SubjectListener;
import javafx.application.Platform;

public class ProjectTreeItem<String> extends BaseTreeItem<String>{
    Project project;
    public ProjectTreeItem(Project project) {
        super((String) project.getName());
        this.project = project;
        project.addListener(new SubjectListener() {
            @Override
            public void changed(SubjectChange subjectChange) {
                if(subjectChange == subjectChange.CHANGED_NAME || subjectChange == subjectChange.EDITED_SUBJECT){
                    Platform.runLater(()->{
                        setValue((String) project.getName());
                    });
                }
            }
        });
    }

    @Override
    public Object getHoldObject() {
        return project;
    }
}
