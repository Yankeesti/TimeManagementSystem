package com.heinsberg.LearningManager.Gui.treeItems;

import com.heinsberg.LearningManager.Gui.view.ViewFactory;
import com.heinsberg.LearningManagerProjekt.BackGround.Study;
import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.Semester;

public class StudyTreeItem<String> extends BaseTreeItem<String>{

    private Study study;

    public StudyTreeItem(Study study) {
        super((String) study.getName());
        this.study = study;
        setUpSemesters();
        setExpanded(true);
    }

    private void setUpSemesters(){
        Semester[] semesters = study.getSemesters();
        for(Semester semester:semesters){
            SemesterTreeItem semesterTreeItem = new SemesterTreeItem(semester);
            getChildren().add(semesterTreeItem);
        }
    }

    @Override
    public Object getHoldObject() {
        return study;
    }
}
