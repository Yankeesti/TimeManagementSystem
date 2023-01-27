package com.heinsberg.LearningManager.Gui.treeItems;

import com.heinsberg.LearningManager.Gui.view.ViewFactory;
import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.Semester;
import com.heinsberg.LearningManagerProjekt.BackGround.subject.Subject;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import java.util.List;

public class SemesterTreeItem<String> extends BaseTreeItem<String> {
    private Semester semester;
    private ObservableList<Subject> subjects;

    public SemesterTreeItem(Semester semester) {
        super((String) (semester.getSemester() + ". Semester"));
        this.semester = semester;
        setUpSubjects();
        setExpanded(true);
    }

    private void setUpSubjects() {
        subjects = semester.getSubjects();
        for (Subject subject : subjects) {
            SubjectTreeItem subjectTreeItem = new SubjectTreeItem(subject);
            getChildren().add(subjectTreeItem);
        }
        //Add Listener to Subjects
        subjects.addListener(new ListChangeListener<Subject>() {
            @Override
            public void onChanged(Change<? extends Subject> change) {
                change.next();
                if(change.wasAdded()){ //add new Semester to tree
                    //get the last addedObject
                    List<? extends Subject> addedList = change.getAddedSubList();

                    addSubjectTreeItem((Subject)addedList.get(addedList.size()-1));
                }else if (change.wasRemoved()){

                }
            }
        });
    }

    private void addSubjectTreeItem(Subject subject) {
        SubjectTreeItem newTreeItem = new SubjectTreeItem(subject);
        getChildren().add(newTreeItem);
    }

    @Override
    public Object getHoldObject() {
        return semester;
    }
}
