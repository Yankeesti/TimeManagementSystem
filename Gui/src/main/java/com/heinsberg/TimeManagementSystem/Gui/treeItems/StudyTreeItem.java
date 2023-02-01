package com.heinsberg.TimeManagementSystem.Gui.treeItems;

import com.heinsberg.TimeManagementSystem.BackGround.study.Study;
import com.heinsberg.TimeManagementSystem.BackGround.study.TimeClasses.Semester;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.List;

public class StudyTreeItem<String> extends BaseTreeItem<String> {

    private Study study;
    ObservableList<Semester> semesters;
    public StudyTreeItem(Study study) {
        super((String) study.getName());
        this.study = study;
        this.semesters = study.getSemesters();

        //Comperator to sort the treeView
        semesters.addListener(new ListChangeListener<Semester>() { //Listener to check when element was added
            @Override
            public void onChanged(Change<? extends Semester> change) {
                change.next();
                if(change.wasAdded()){ //add new Semester to tree
                    //get the last addedObject
                    List<? extends Semester> addedList = change.getAddedSubList();
                    addSemesterTreeItem((Semester)addedList.get(addedList.size()-1));
                    getChildren().sort((SemesterTreeItem)getChildren().get(0));
                }else if (change.wasRemoved()){

                }
            }
        });
        setUpSemesters();
        setExpanded(true);
    }

    private void addSemesterTreeItem(Semester semester) {
        SemesterTreeItem newItem = new SemesterTreeItem(semester);
        getChildren().add(newItem);
    }

    private void setUpSemesters(){
        ObservableList<Semester> semesters = study.getSemesters();
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