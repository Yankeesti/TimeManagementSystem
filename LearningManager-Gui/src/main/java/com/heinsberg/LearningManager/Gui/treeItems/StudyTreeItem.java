package com.heinsberg.LearningManager.Gui.treeItems;

import com.heinsberg.LearningManager.Gui.view.ViewFactory;
import com.heinsberg.LearningManagerProjekt.BackGround.Study;
import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.Semester;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class StudyTreeItem<String> extends BaseTreeItem<String>{

    private Study study;
    public StudyTreeItem(Study study) {
        super((String) study.getName());
        this.study = study;
        setUpSemesters();
        setExpanded(true);
    }

    private void setUpSemesters(){
        ArrayList<Semester> semesters = study.getSemesters();
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
