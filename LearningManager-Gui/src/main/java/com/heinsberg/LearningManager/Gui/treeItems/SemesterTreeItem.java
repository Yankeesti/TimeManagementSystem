package com.heinsberg.LearningManager.Gui.treeItems;

import com.heinsberg.LearningManager.Gui.view.ViewFactory;
import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.Semester;
import com.heinsberg.LearningManagerProjekt.BackGround.subject.Subject;
import javafx.scene.control.TreeItem;

public class SemesterTreeItem<String> extends BaseTreeItem<String> {
    private Semester semester;

    public SemesterTreeItem(Semester semester) {
        super((String) (semester.getSemester()+". Semester"));
        this.semester = semester;
        setUpSubjects();
        setExpanded(true);
    }

    private void setUpSubjects() {
        Subject[] subjects = semester.getSubjects();
        for(Subject subject:subjects){
            SubjectTreeItem subjectTreeItem = new SubjectTreeItem(subject);
            getChildren().add(subjectTreeItem);
        }
    }

    @Override
    public Object getHoldObject() {
        return semester;
    }
}
