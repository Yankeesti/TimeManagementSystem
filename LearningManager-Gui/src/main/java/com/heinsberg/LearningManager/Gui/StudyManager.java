package com.heinsberg.LearningManager.Gui;

import com.heinsberg.LearningManager.Gui.treeItems.SemesterTreeItem;
import com.heinsberg.LearningManager.Gui.treeItems.StudyTreeItem;
import com.heinsberg.LearningManager.Gui.treeItems.SubjectTreeItem;
import com.heinsberg.LearningManagerProjekt.BackGround.Study;
import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.Semester;
import com.heinsberg.LearningManagerProjekt.BackGround.subject.Subject;
import javafx.scene.control.TreeItem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

/**
 * The StudyManager class is responsible for creating and managing a Study object and its associated TreeView in the GUI.
 */
public class StudyManager {
    private Study study;
    private TreeItem<String> foldersRoot = new TreeItem<String>(""); //

    /**
     * Creates a new Study object with the given name and sets up the TreeView object to display the study information.
     *
     * @param studyName the name of the study.
     */
    public void createStudy(String studyName) {
        study = new Study(studyName);
        //just for testing
        study.addSemester(new Semester(3, new Date(1922, 9, 1), new Date(1923, 2, 28)));
        study.addSubject(new Subject("EG", 3, 5));
        setUpTreeView();
        System.out.println("study Created");
    }

    /**
     * sets up the TreeView object to display the study information.
     */
    private void setUpTreeView() {
        //Set up Study root
        TreeItem<String> studyTreeItem = new StudyTreeItem(study);
        foldersRoot.getChildren().add(studyTreeItem);
    }

    /**
     * Returns the root of the TreeView object which is used to display the study information in the GUI.
     *
     * @return the root of the TreeView object.
     */
    public TreeItem<String> getFoldersRoot() {
        return foldersRoot;
    }

    /**
     * Returns the Study object managed by this class.
     *
     * @return the Study object.
     */
    public Study getStudy() {
        return study;
    }
}
