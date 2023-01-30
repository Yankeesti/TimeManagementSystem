package com.heinsberg.LearningManager.Gui.treeItems;

import com.heinsberg.LearningManager.Gui.view.ViewFactory;
import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.Semester;
import com.heinsberg.LearningManagerProjekt.BackGround.subject.Subject;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import java.util.Comparator;
import java.util.List;

public class SemesterTreeItem<String> extends BaseTreeItem<String> implements Comparator<SemesterTreeItem> {
    private Semester semester;
    private ObservableList<Subject> subjects;

    public SemesterTreeItem(Semester semester) {
        super((String) (semester.getSemester() + ". Semester"));
        this.semester = semester;
        setUpSubjects();
        setExpanded(true);
    }

    /**
     * Sets up the Subject TreeView and adds a Listener to the Subject List
     */
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
                    ObservableList<TreeItem<String>> children = getChildren();
                    List<? extends Subject> removedItems = change.getRemoved();
                    for(TreeItem<String> child: children){
                        if(((SubjectTreeItem)child).getHoldObject() == removedItems.get(removedItems.size()-1)){
                            getChildren().remove(child);
                            break;
                        }
                    }
                }
            }
        });
    }

    /**
     * Adds subject to Tree View
     * gets called when changes were detected in the Subjects List
     * @param subject
     */
    private void addSubjectTreeItem(Subject subject) {
        SubjectTreeItem newTreeItem = new SubjectTreeItem(subject);
        getChildren().add(newTreeItem);
    }

    @Override
    public Object getHoldObject() {
        return semester;
    }

    @Override
    public int compare(SemesterTreeItem o1, SemesterTreeItem o2) {
        return ((Semester)o1.getHoldObject()).getSemester() -((Semester)o2.getHoldObject()).getSemester();
    }
}
