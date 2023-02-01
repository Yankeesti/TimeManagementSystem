package com.heinsberg.LearningManager.Gui;

import com.google.gson.GsonBuilder;
import com.heinsberg.LearningManager.Gui.controller.FileResult;
import com.heinsberg.LearningManager.Gui.treeItems.BaseTreeItem;
import com.heinsberg.LearningManager.Gui.treeItems.ProjectTreeItem;
import com.heinsberg.LearningManager.Gui.treeItems.StudyTreeItem;
import com.heinsberg.LearningManagerProjekt.BackGround.Project.Project;
import com.heinsberg.LearningManagerProjekt.BackGround.abstractClasses.TimeSpentContainer;
import com.heinsberg.LearningManagerProjekt.BackGround.study.AddSemesterResult;
import com.heinsberg.LearningManagerProjekt.BackGround.LearningPhaseActionResult;
import com.heinsberg.LearningManagerProjekt.BackGround.study.Study;
import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.LearningPhase;
import com.heinsberg.LearningManagerProjekt.BackGround.study.TimeClasses.Semester;
import com.heinsberg.LearningManagerProjekt.BackGround.study.subject.Subject;
import com.heinsberg.LearningManagerProjekt.BackGround.study.typeAdapters.StudyTypeAdapter;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import java.io.*;
import java.util.List;

/**
 * The StudyManager class is responsible for creating and managing a Study object and its associated TreeView in the GUI.
 */
public class ContentManager {
    private Study study;
    private ObservableList<Project> projects;
    private TreeItem<String> foldersRoot = new TreeItem<String>(""); //
    private File currentFile;//saves the File currently editing.

    public ContentManager() {
        projects = FXCollections.observableArrayList();
    }

    /**
     * Creates a new Study object with the given name and sets up the TreeView object to display the study information.
     *
     * @param studyName the name of the study.
     */
    public void createStudy(String studyName) {
        study = new Study(studyName);
        setUpTreeView();
        System.out.println("study Created");
    }

    public void addProject(Project projectToAdd) {
        if (projectToAdd != null){
            projects.add(projectToAdd);
        }
        else
            System.err.println("Project was Null");
    }


    /**
     * sets up the TreeView object to display study and Projects in ContentManager
     */
    private void setUpTreeView() {
        //Set up Study root
        TreeItem<String> studyTreeItem = new StudyTreeItem(study);
        foldersRoot.getChildren().add(studyTreeItem);

        //set Up listner for projects so that new projects get added to the treeview
        projects.addListener(new ListChangeListener<Project>() {
            @Override
            public void onChanged(Change<? extends Project> change) {
                change.next();
                if(change.wasAdded()){
                    List<? extends Project> addedList = change.getAddedSubList();
                    addProjectTreeItem(addedList.get(addedList.size()-1));
                }else if(change.wasRemoved()){
                    List<? extends Project> removedList = change.getRemoved();
                    removeProjectTreeItem(removedList.get(removedList.size()-1));
                }
            }
        });
    }

    private void removeProjectTreeItem(Project project) {
        for(TreeItem child: foldersRoot.getChildren()){
            if(((BaseTreeItem)child).getHoldObject() == project){
                foldersRoot.getChildren().remove(child);
                break;
            }
        }
    }

    private void addProjectTreeItem(Project project) {
        TreeItem<String> projectTreeItem = new ProjectTreeItem<>(project);
        foldersRoot.getChildren().add(projectTreeItem);
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

    public FileResult studyToJson(File file) {
        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(Study.class, new StudyTypeAdapter());
        //Write in File
        if (file != null) {
            try {
                FileWriter writer = new FileWriter(file);
                writer.write(gsonBuilder.create().toJson(study));
                writer.close();
                currentFile = file;
                return FileResult.SUCCESS;
            } catch (IOException e) {
                return FileResult.UNEXPECTED_ERROR;
            }
        } else {
            System.err.println("File null");
            return FileResult.FILE_NOT_FOUND;
        }
    }

    /**
     * loads a study from json
     *
     * @param file
     * @return true when succes and false when not
     */
    public FileResult studyFromJson(File file) {
        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(Study.class, new StudyTypeAdapter());
        if (file == null) {
            return FileResult.FILE_NOT_FOUND;
        }
        try {
            FileReader simpleReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(simpleReader);
            study = gsonBuilder.create().fromJson(reader.readLine(), Study.class);
            setUpTreeView();
            currentFile = file;
            return FileResult.SUCCESS;
        } catch (FileNotFoundException e) {
            return FileResult.FILE_NOT_FOUND;
        } catch (IOException e) {
            return FileResult.UNEXPECTED_ERROR;
        }
    }

    public File getCurrentFile() {
        return currentFile;
    }

    public void addNewSemester(Semester semester) {
        AddSemesterResult result = study.addSemester(semester);
        System.out.println(result);
    }

    public void addNewSubject(Subject subject) {
        System.out.println(study.addSubject(subject));
    }

    public LearningPhaseActionResult startLearningPhase(Subject subjectToStartLearningPhase) {
        LearningPhaseActionResult result = study.startLearningPhase(subjectToStartLearningPhase);
        return result;
    }

    /**
     * Ends the current LearningPhase
     *
     * @return the Time Learned when there was no current LearningPhase a number lower than 0
     */
    public long endLearningPhase() {
        study.finishLearningPhase();
        return 0;
    }

    public void deleteLearningPhase(LearningPhase learningPhase) {
        study.deleteLearningPhase(learningPhase);
        System.out.println("deleted LearningPhase");
    }

    public int getLearnedInCurrentWeek(TimeSpentContainer object) {
        if(object.getClass() == Subject.class){//show how much was learned in current week for the Subject
            return study.getLearnedInCurrentWeek((Subject)  object);
        }else if(object.getClass() == Project.class){//show how much was learned in current weejk for the Project
            System.out.println("show learned for project");
        }
        return 0;
    }

    public void deleteTimeSpentContainer(TimeSpentContainer timeSpentContainer) {
        if(timeSpentContainer instanceof Subject){
            study.deleteSubject((Subject) timeSpentContainer);
            System.out.println("deleted");
        } else if (timeSpentContainer instanceof Project) {
            projects.remove(timeSpentContainer);
            System.out.println("deleted");
        }
    }
}
