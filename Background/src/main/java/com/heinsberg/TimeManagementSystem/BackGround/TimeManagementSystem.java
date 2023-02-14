package com.heinsberg.TimeManagementSystem.BackGround;

import com.heinsberg.TimeManagementSystem.BackGround.Listeners.ChangeEnums.TimeManagementSystemChange;
import com.heinsberg.TimeManagementSystem.BackGround.Listeners.TimeManagementSystemListener;
import com.heinsberg.TimeManagementSystem.BackGround.Project.Project;
import com.heinsberg.TimeManagementSystem.BackGround.TimeClasses.LearningPhase;
import com.heinsberg.TimeManagementSystem.BackGround.abstractClasses.TimeSpentContainer;
import com.heinsberg.TimeManagementSystem.BackGround.study.AddSemesterResult;
import com.heinsberg.TimeManagementSystem.BackGround.study.AddSubjectResult;
import com.heinsberg.TimeManagementSystem.BackGround.study.Study;
import com.heinsberg.TimeManagementSystem.BackGround.study.TimeClasses.Semester;
import com.heinsberg.TimeManagementSystem.BackGround.study.subject.Subject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.ArrayList;

public class TimeManagementSystem {
    private ObservableList<Study> studies;
    private ObservableList<Project> projects;
    private WeekFactory weekFactory;
    private LearningPhase currentLearningPhase;

    private ArrayList<TimeManagementSystemListener> listeners;

    public TimeManagementSystem(WeekFactory weekFactory) {
        studies = FXCollections.observableArrayList();
        projects = FXCollections.observableArrayList();
        this.weekFactory = weekFactory;
        listeners = new ArrayList<TimeManagementSystemListener>();
    }

    //Methods to control learning in Project/Studies

    /**
     * Deletes the given time spentContainer and all it's LearningPhases
     *
     * @param toDelete
     */
    public void deleteTimeSpentContainer(TimeSpentContainer toDelete) {
        if (toDelete instanceof Subject) {
            ((Subject) toDelete).getStudy().deleteSubject((Subject) toDelete);
        } else if (toDelete instanceof Project) {
            projects.remove(toDelete);
            toDelete.deleteLearningPhases();
        }
    }

    /**
     * Delets a Learning Phase and all references from this LearningPhase
     *
     * @param learningPhase
     */
    public void deleteLearningPhase(LearningPhase learningPhase) {
        if (currentLearningPhase == learningPhase) {
            deleteCurrentLearningPhase();
            learningPhase.deleteLearningPhase();
        } else {
            learningPhase.deleteLearningPhase();
        }
    }


    //Study Control

    /**
     * Starts a new LearningPhase for the given Subject
     * when the LearningPhase is started the current LearningPhase reference is
     * set to the started LearningPhase
     *
     * @param study   - Study that contains Subject
     * @param subject - Subject to learn for
     * @return Result of started LearningPhase
     */
    public LearningPhaseActionResult startLearningPhase(Study study, Subject subject) {
        if (currentLearningPhase == null) {//new LearningPhase can be started
            if (studies.contains(study)) {//start LearningPhase
                LearningPhaseActionResult outPut = study.startLearningPhase(subject);
                currentLearningPhase = study.getCurrentLearningPhase();
                return outPut;
            } else {
                return LearningPhaseActionResult.STUDY_IS_NOT_REGISTERED;
            }
        } else {
            return LearningPhaseActionResult.LEARNINGPHASE_ALREADY_STARTED;
        }
    }

    /**
     * Starts a new LearningPhase for the given Project
     * when the LearningPhase is started the current LearningPhase reference is
     * set to the started LearningPhase
     *
     * @param project - Study that contains Subject
     * @return Result of started LearningPhase
     */
    public LearningPhaseActionResult startLearningPhase(Project project) {
        if (currentLearningPhase == null) {
            if (projects.contains(project)) { //LearningPhase can be started
                currentLearningPhase = project.startLearningPhase();
                if (currentLearningPhase != null) {
                    return LearningPhaseActionResult.SUCCESS;
                } else {
                    return LearningPhaseActionResult.UNEXPECTED_ERROR;
                }
            } else {
                return LearningPhaseActionResult.PROJECT_IS_NOT_REGISTERED;
            }
        } else {
            return LearningPhaseActionResult.LEARNINGPHASE_ALREADY_STARTED;
        }
    }

    /**
     * ends the current learningPhase
     *
     * @return if there isn't a current Learning Phase -1 and if there is a current Learning Phase, the Time learned in Seconds.
     */
    public long finishLearningPhase() {
        if (currentLearningPhase == null)
            return -1;
        long timeLearned = currentLearningPhase.endLearningPhase();
        //check weather learningPhase s in a Study
        if (currentLearningPhase.getTimeSpentContainer() instanceof Subject) {
            ((Subject) currentLearningPhase.getTimeSpentContainer()).getStudy().finishLearningPhase();
        }
        currentLearningPhase = null;
        return timeLearned;
    }

    /**
     * Adds the given Semester to the given Study
     *
     * @param study    - Study to add Semester to
     * @param semester - Semester to add
     * @return Result of Semester added
     */
    public AddSemesterResult addSemester(Study study, Semester semester) {
        //Check if Study is Registered
        if (studies.contains(study)) {
            return study.addSemester(semester);
        } else {
            return AddSemesterResult.STUDY_IS_NOT_REGISTERED;
        }
    }

    /**
     * Adds the given Subject to the given Study
     *
     * @param study   - Study to add Subject to
     * @param subject - Subject to add
     * @return Result of Subject Added
     */
    public AddSubjectResult addSubject(Study study, Subject subject) {
        if (studies.contains(study)) {
            return study.addSubject(subject);
        } else {
            return AddSubjectResult.STUDY_IS_NOT_REGISTERED;
        }
    }

    //Management of Projects


    public ObservableList<Study> getStudies() {
        return studies;
    }

    public ObservableList<Project> getProjects() {
        return projects;
    }

    public void addStudy(Study newStudy) {
        studies.add(newStudy);
        newStudy.setWeekFactory(weekFactory);
    }

    public void removeStudy(Study studyToRemove) {
        studies.remove(studyToRemove);
    }

    public void addProject(Project newProject) {
        projects.add(newProject);
        newProject.setWeekFactory(weekFactory);
    }

    public void removeProject(Project projectToRemove) {
        projects.remove(projectToRemove);
    }

    /**
     * Method is only used to Load studies from Json File studies are loaded
     * and the WeekFactory for all studies is set to TimeManagementSytsems WeekFactory
     *
     * @param studies - Studies to Add
     * @Warning: This Method should only be used When loading from a json File because it deletes all studies that were created before call
     */
    public void loadStudies(ArrayList<Study> studies) {
        this.studies = FXCollections.observableArrayList(studies); // load studies
        //set WeekFactory from ever Study
        for (Study study : studies) {
            study.setWeekFactory(weekFactory);
        }

    }

    /**
     * Method is only used to Load Project from Json File studies are loaded
     * and the WeekFactory for all studies is set to TimeManagementSytsems WeekFactory
     *
     * @param projects - Studies to Add
     * @Warning: This Method should only be used When loading from a json File because it deletes all studies that were created before call
     */
    public void loadProjects(ArrayList<Project> projects) {
        this.projects = FXCollections.observableArrayList(projects);
        for (Project project : projects) {
            project.setWeekFactory(weekFactory);
        }
    }

    /**
     * Calculates how much was Learned for the given TimeSpentContainer in Minutes
     *
     * @param timeSpentContainer
     * @return time Learned
     */
    public int getLearnedInCurrentWeek(TimeSpentContainer timeSpentContainer) {
        return weekFactory.getCurrentWeek().getLearnedFor(timeSpentContainer);
    }

    public ObservableList<TimeSpentContainer> getLearnableTimeSpentContainers() {
        ObservableList<TimeSpentContainer> outPut = FXCollections.observableArrayList();
        outPut.addAll(projects);
        for (Study study : studies) {
            outPut.addAll(study.getCurrentSemester().getSubjects());
        }
        return outPut;
    }

    //Methods for Listener Management
    public void addListener(TimeManagementSystemListener listener) {
        listeners.add(listener);
    }

    public void removeListener(TimeManagementSystemListener listener) {
        listeners.remove(listener);
    }


    /**
     * Notifies all Listeners taht something has changed in The timeManagementSystem
     *
     * @param change
     */
    private void notifyListeners(TimeManagementSystemChange change) {
        for (TimeManagementSystemListener listener : listeners) {
            listener.notifyListener(change);
        }
    }

    /**
     * Deletes the Semester and all it's Subjetcts and LearningPhases
     *
     * @param semesterToDelete
     */
    public void deleteSemester(Semester semesterToDelete) {
        if (currentLearningPhase != null)
            if (semesterToDelete.includesSubject((Subject) currentLearningPhase.getTimeSpentContainer())) {// delete the current learningPhase
                deleteCurrentLearningPhase();
            }
        semesterToDelete.getStudy().deleteSemester(semesterToDelete);
    }

    public void deleteStudy(Study studyToDelete) {
        studies.remove(studyToDelete);
        studyToDelete.delete();
    }

    /**
     * Deletes the current LearningPhase and notifyes the Listners
     */
    private void deleteCurrentLearningPhase() {
        currentLearningPhase = null;
        notifyListeners(TimeManagementSystemChange.CURRENT_LEARNINGPHASE_DELETED);
    }
}
