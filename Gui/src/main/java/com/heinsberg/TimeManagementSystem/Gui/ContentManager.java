package com.heinsberg.TimeManagementSystem.Gui;

import com.google.gson.GsonBuilder;
import com.heinsberg.TimeManagementSystem.BackGround.TimeManagementSystem;
import com.heinsberg.TimeManagementSystem.BackGround.TypeAdapter.TimeManagementSystemTypeAdapter;
import com.heinsberg.TimeManagementSystem.BackGround.WeekFactory;
import com.heinsberg.TimeManagementSystem.Gui.controller.FileResult;
import com.heinsberg.TimeManagementSystem.Gui.treeItems.BaseTreeItem;
import com.heinsberg.TimeManagementSystem.Gui.treeItems.ProjectTreeItem;
import com.heinsberg.TimeManagementSystem.Gui.treeItems.StudyTreeItem;
import com.heinsberg.TimeManagementSystem.BackGround.Project.Project;
import com.heinsberg.TimeManagementSystem.BackGround.abstractClasses.TimeSpentContainer;
import com.heinsberg.TimeManagementSystem.BackGround.study.AddSemesterResult;
import com.heinsberg.TimeManagementSystem.BackGround.LearningPhaseActionResult;
import com.heinsberg.TimeManagementSystem.BackGround.study.Study;
import com.heinsberg.TimeManagementSystem.BackGround.TimeClasses.LearningPhase;
import com.heinsberg.TimeManagementSystem.BackGround.study.TimeClasses.Semester;
import com.heinsberg.TimeManagementSystem.BackGround.study.subject.Subject;
import com.heinsberg.TimeManagementSystem.BackGround.study.typeAdapters.StudyTypeAdapter;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;

import java.io.*;
import java.util.List;

/**
 * The StudyManager class is responsible for creating and managing a Study object and its associated TreeView in the GUI.
 */
public class ContentManager {
    private TimeManagementSystem timeManagementSystem;
    private TreeItem<String> foldersRoot = new TreeItem<String>(""); //
    private File currentFile;//saves the File currently editing.


    /**
     * Creates a new Study object with the given name and sets up the TreeView object to display the study information.
     *
     * @param studyName the name of the study.
     */
    public void createStudy(String studyName) {
        Study study = new Study(studyName);
        if(timeManagementSystem == null){
            timeManagementSystem = new TimeManagementSystem(new WeekFactory());
        }
        timeManagementSystem.addStudy(study);
        setUpTreeView();
        System.out.println("study Created");
    }

    public void addProject(Project projectToAdd) {
        timeManagementSystem.addProject(projectToAdd);
    }


    /**
     * sets up the TreeView object to display study and Projects in ContentManager
     */
    private void setUpTreeView() {

        ObservableList<Study> studies = timeManagementSystem.getStudies();
        //SetUp StudyTreeItems
        for (Study study : studies) { // Add a new Study Tree Item for each Study in the Time Management System
            foldersRoot.getChildren().add(new StudyTreeItem<>(study));
        }
        //Set Up Listener for studies
        studies.addListener(new ListChangeListener<Study>() {
                                @Override
                                public void onChanged(Change<? extends Study> change) {
                                    change.next();
                                    if (change.wasAdded()) {
                                        List<? extends Study> addedList = change.getAddedSubList();
                                        addSubjectTreeItem(addedList.get(addedList.size() - 1));
                                    } else if (change.wasRemoved()) {
                                        List<? extends Study> removedList = change.getRemoved();
                                        removeStudyTreeItem(removedList.get(removedList.size() - 1));
                                    }
                                }
                            }
        );

        //set Up listner for projects so that new projects get added to the treeview
        ObservableList<Project> projects = timeManagementSystem.getProjects();

        for(Project project:projects){
            foldersRoot.getChildren().add((new ProjectTreeItem<>(project)));
        }
        projects.addListener(new ListChangeListener<Project>() {
            @Override
            public void onChanged(Change<? extends Project> change) {
                change.next();
                if (change.wasAdded()) {
                    List<? extends Project> addedList = change.getAddedSubList();
                    addProjectTreeItem(addedList.get(addedList.size() - 1));
                } else if (change.wasRemoved()) {
                    List<? extends Project> removedList = change.getRemoved();
                    removeProjectTreeItem(removedList.get(removedList.size() - 1));
                }
            }
        });
    }

    /**
     * Removes the given Study from the View
     * @param study
     */
    private void removeStudyTreeItem(Study study) {
        for(TreeItem child : foldersRoot.getChildren()){
            if(((BaseTreeItem)child).getHoldObject() == study){
                foldersRoot.getChildren().remove(child);
                break;
            }
        }
    }

    private void addSubjectTreeItem(Study study) {
        TreeItem<String> studyTreeItem = new StudyTreeItem(study);
        foldersRoot.getChildren().add(studyTreeItem);
    }

    private void removeProjectTreeItem(Project project) {
        for (TreeItem child : foldersRoot.getChildren()) {
            if (((BaseTreeItem) child).getHoldObject() == project) {
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
     * Returns the List of Studies managed by TimeManagementSystem
     *
     * @return the Study object.
     */
    public ObservableList<Study> getStudy() {
        return timeManagementSystem.getStudies();
    }

    public FileResult timeManagementSystemToJson(File file) {
        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(TimeManagementSystem.class, new TimeManagementSystemTypeAdapter());
        //Write in File
        if (file != null) {
            try {
                FileWriter writer = new FileWriter(file);
                writer.write(gsonBuilder.create().toJson(timeManagementSystem)); //Just for short term needs to be rewriten when everything works
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
     * loads a study from json and adds it to the given TimeManager, when TimeManager is null a new is created
     *
     * @param file
     * @return true when succes and false when not
     */
    public FileResult timeManagementSystemFromJson(File file) {
        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(TimeManagementSystem.class, new TimeManagementSystemTypeAdapter());
        if (file == null) {
            return FileResult.FILE_NOT_FOUND;
        }
        try {
            FileReader simpleReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(simpleReader);
            timeManagementSystem = gsonBuilder.create().fromJson(reader.readLine(), TimeManagementSystem.class);
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

    /**
     * Adds a new Semester to the given Study in TimeManagementSystem
     *
     * @param study
     * @param semester
     */
    public void addNewSemester(Study study, Semester semester) {
        AddSemesterResult result = timeManagementSystem.addSemester(study, semester);
        System.out.println(result);
    }

    public void addNewSubject(Study study, Subject subject) {
        System.out.println(timeManagementSystem.addSubject(study, subject));
    }

    /**
     * starts a LearningPhase for the given timeSpentContainer
     *
     * @param timeSpentContainer - timeSpent Container to start LearningPhase for
     * @return result of starting the LearningPhase
     */
    public LearningPhaseActionResult startLearningPhase(TimeSpentContainer timeSpentContainer) {
        LearningPhaseActionResult result = null;
        if (timeSpentContainer instanceof Subject) { //when learned for a Subject
            result = timeManagementSystem.startLearningPhase(((Subject) timeSpentContainer).getStudy(), (Subject) timeSpentContainer);
        } else {
            result = timeManagementSystem.startLearningPhase((Project) timeSpentContainer);
        }
        return result;
    }

    /**
     * Ends the current LearningPhase
     *
     * @return the Time Learned when there was no current LearningPhase a number lower than 0
     */
    public long endLearningPhase() {
        return timeManagementSystem.finishLearningPhase();
    }

    public void deleteLearningPhase(LearningPhase learningPhase) {
        timeManagementSystem.deleteLearningPhase(learningPhase);
    }

    /**
     * Calculates how much was learned for the given TimeSpentContainer in the current Week
     * @param object
     * @return
     */
    public int getLearnedInCurrentWeek(TimeSpentContainer object) {
        return 0;
//        return timeManagementSystem.getLearnedInCurrentWeek(object);
    }

    public void deleteTimeSpentContainer(TimeSpentContainer timeSpentContainer) {
        timeManagementSystem.deleteTimeSpentContainer(timeSpentContainer);
    }

    public TimeManagementSystem getTimeManagementSystem() {
        return timeManagementSystem;
    }

    /**
     * Returns a List with all TimeSpentContainers that can be learned for currently
     * @return
     */
    public ObservableList getLearnableTimeSpentContainers() {
        return timeManagementSystem.getLearnableTimeSpentContainers();
    }

    /**
     * Creates a new Time Management System and adds a Project with the given Name to is
     *
     * @param projectName - projectName
     */
    public void createProject(String projectName) {
        Project newProject = new Project(projectName);
        timeManagementSystem = new TimeManagementSystem(new WeekFactory());
        timeManagementSystem.addProject(newProject);
        setUpTreeView();
    }

    public void deleteSemester(Semester semesterToDelete) {
    }

    public void deleteStudy(Study studyToDelete) {
    }
}
