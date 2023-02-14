package com.heinsberg.TimeManagementSystem.BackGround.study;

import com.heinsberg.TimeManagementSystem.BackGround.LearningPhaseActionResult;
import com.heinsberg.TimeManagementSystem.BackGround.WeekFactory;
import com.heinsberg.TimeManagementSystem.BackGround.study.Listeners.ChangeEnums.StudyChange;
import com.heinsberg.TimeManagementSystem.BackGround.study.Listeners.StudyListener;
import com.heinsberg.TimeManagementSystem.BackGround.study.TimeClasses.Semester;
import com.heinsberg.TimeManagementSystem.BackGround.TimeClasses.TimePeriod;
import com.heinsberg.TimeManagementSystem.BackGround.study.subject.Subject;
import com.heinsberg.TimeManagementSystem.BackGround.TimeClasses.LearningPhase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Date;

/**
 * The Study class represents a study program, containing information about the semesters, subjects, and learning phases of the program.
 * It includes methods for controlling and editing the study program, such as starting and ending learning phases, adding new semesters and subjects, and retrieving information about the current state of the program.
 */
public class Study {
    private String studyName;

    private ObservableList<Semester> semesters;//Semesters that are ended
    private ObservableList<Subject> subjects;
    private LearningPhase currentLearningPhase;

    private Semester currentSemester; //stores the current Semester
    private ArrayList<StudyListener> listeners;
    private WeekFactory weekFactory;


    public Study(String studyName, WeekFactory weekFactory) {

        subjects = FXCollections.observableArrayList();
        semesters = FXCollections.observableArrayList();
        listeners = new ArrayList<StudyListener>();
        this.weekFactory = weekFactory;
    }

    /**
     * This method is used to Load Data from a Json file
     * Week Factory needs to be set through setWeekFactory Method
     *
     * @param studyName
     */
    public Study(String studyName) {
        this.studyName = studyName;
        subjects = FXCollections.observableArrayList();
        semesters = FXCollections.observableArrayList();
        listeners = new ArrayList<StudyListener>();
    }

    //Methodes to Control learningPhase

    public Semester getCurrentSemester() {
        upDateSemester();
        return currentSemester;
    }

    /**
     * starts a Learning Phase for the Subject subject
     * and stores it in currentLearningPhase
     *
     * @param subject - Subject to start Learning
     * @return started LearningPhase null if a unexpeced Error occurs
     */
    public LearningPhaseActionResult startLearningPhase(Subject subject) {
        if (currentLearningPhase != null)
            return LearningPhaseActionResult.LEARNINGPHASE_ALREADY_STARTED;
        if (!upDateSemester())
            return LearningPhaseActionResult.NO_CURRENT_SEMESTER;
        if (!currentSemester.includesSubject(subject))
            return LearningPhaseActionResult.CURRENT_SEMESTER_DOSENT_INCLUEDE_SUBJECT;

        currentLearningPhase = subject.startLearningPhase();
        return LearningPhaseActionResult.SUCCESS;
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
        currentLearningPhase = null;
        return timeLearned;
    }

    //Methods to Edit Study

    /**
     * adds a new Semester to the Study
     *
     * @param semesterToAdd - Semester to be added
     * @return true if Semester was added successfully, false if parts of this Semester are already included in another Semester or Semester Number is already taken
     */
    public AddSemesterResult addSemester(Semester semesterToAdd) {

        //check if there is already a Semester with the Same Dates in it
        for (int i = 0; i < semesters.size(); i++) {
            if (semesters.get(i).getSemester() == semesterToAdd.getSemester())
                return AddSemesterResult.SEMESTER_NUMBER_ALREADY_EXISTENT;
            int compared = semesters.get(i).compareTo(semesterToAdd);
            if (compared != 2 && compared != -2) {//semester is in a Semester that already exists
                return AddSemesterResult.SEMESTER_IN_OTHER_SEMESTER;
            }
        }
        //set WeekFactory to Studys WeekFactory
        semesterToAdd.setWeekFactory(weekFactory);
        semesterToAdd.setStudy(this);
        semesters.add(semesterToAdd);
        semesters.sort((Semester s1, Semester s2) -> s1.getSemester() - s2.getSemester());
        //Add subjects of semester to subjects (used when loading from json)
        subjects.addAll(semesterToAdd.getSubjects());
        return AddSemesterResult.SUCCESS;
    }

    /**
     * Adds a Subject to the Study
     *
     * @param subjectToAdd - subject to be added
     * @return true if Subject was added, false if not
     */
    public AddSubjectResult addSubject(Subject subjectToAdd) {
        if (!subjectAllreadyExistend(subjectToAdd)) {
            Semester subjectSemester = subjectToAdd.getSemester();
            if (subjectSemester != null) {
                if (semesters.contains(subjectSemester)) {
                    subjectSemester.addSubject(subjectToAdd);
                    subjects.add(subjectToAdd);
                    subjectToAdd.setWeekFactory(weekFactory);
                    subjectToAdd.setStudy(this);
                    return AddSubjectResult.SUCCESS;
                } else
                    return AddSubjectResult.SEMESTER_OF_SUBJECT_NOT_IN_STUDY;
            }
            return AddSubjectResult.SUBJECT_IS_NULL;
        }
        return AddSubjectResult.SUBJECT_ALLREADY_EXISTEND;
    }

    //Get Mehtodes

    /**
     * Returns an array of all semesters in this study, sorted by their semester number.
     *
     * @return an array of all semesters in this study.
     */
    public ObservableList<Semester> getSemesters() {
        return semesters;

    }


    /**
     * Retrieves the Semester with the specified semester number.
     *
     * @param semesterNumber the semester number of the Semester to retrieve
     * @return the Semester with the specified semester number, or null if no such Semester exists.
     */
    public Semester getSemester(int semesterNumber) {
        for (int i = 0; i < semesters.size(); i++) {
            Semester temp = semesters.get(i);
            if (temp.getSemester() == semesterNumber)
                //when Semester is found return it
                return temp;
            if (temp.getSemester() > semesterNumber)
                //when temp semester number is higher then semesterNumber ther can't be a Semester with semesterNumber because
                //semesters is sorted --> return null;
                return null;
        }
        //no Semester found
        return null;
    }

    /**
     * Returns the name of the study.
     *
     * @return the name of the study.
     */
    public String getName() {
        return studyName;
    }

    ;

    //private Methodes

    /**
     * updates the current semester to the semester that includes the current date
     *
     * @return true if current semester is updated, false if no semester includes the current date
     */
    public boolean upDateSemester() {
        Date aktDate = TimePeriod.getAktDate();
        if (currentSemester == null || currentSemester.compareTo(aktDate) != 0) {
            for (int i = 0; i < semesters.size(); i++) {
                if (semesters.get(i).compareTo(aktDate) == 0) {
                    currentSemester = semesters.get(i);
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    /**
     * Checks if a given subject already exists within the study
     *
     * @param subject - Subject to check for existence
     * @return true if the subject already exists within the study, false otherwise
     */
    private boolean subjectAllreadyExistend(Subject subject) {
        for (int i = 0; i < subjects.size(); i++) {
            if (subjects.get(i).getSubjectName().equals(subject.getSubjectName()) && subjects.get(i).getSemester() == subject.getSemester()) {
                //subject is already existends
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves the Semester with the specified semester number from the list of semesters in this Study.
     *
     * @param semesterNumber - the number of the semester to be retrieved
     * @return the Semester object if found, null otherwise.
     */
    private Semester findSemester(int semesterNumber) {
        for (int i = 0; i < semesters.size(); i++) {
            if (semesters.get(i).getSemester() == semesterNumber) {//Semester found
                return semesters.get(i);
            }
        }
        return null;
    }

    //for Test purposes

    /**
     * Method only for testing and loading Data
     * Loads a learningphase in subject and in Semester
     *
     * @param learningPhase
     */
    public void addLearningPhase(LearningPhase learningPhase) {
        if (learningPhase.getTimeSpentContainer().getClass() == Subject.class) {
            ((Subject) learningPhase.getTimeSpentContainer()).getSemester().addLearningPhaseHard(learningPhase);
            learningPhase.getTimeSpentContainer().addLearningPhase(learningPhase);
            if (learningPhase.getEndDate() == null) {
                currentLearningPhase = learningPhase;
            }
        } else {
            System.err.println("only LearningPhases for Subjects can be Stored in a study");
        }
    }

    public LearningPhase getCurrentLearningPhase() {
        return currentLearningPhase;
    }

    /**
     * Calculates how much was learned for subject in the current Week
     *
     * @param subject - subject
     * @return learned for subject in current week in Minutes and -1 if this subject isn't in currentSemester
     */
    public int getLearnedInCurrentWeek(Subject subject) {
        upDateSemester();
        if (currentSemester == subject.getSemester()) {
            return weekFactory.getCurrentWeek().getLearnedFor(subject);
        }
        return -1;
    }

    public void deleteLearningPhase(LearningPhase learningPhase) {
        if (learningPhase.getTimeSpentContainer().getClass() == Subject.class) {
            if (currentLearningPhase == learningPhase) {
                notifyListners(StudyChange.CURRENT_LEARNINGPHASE_DELETED);
                currentLearningPhase = null;
            }
            learningPhase.getTimeSpentContainer().deleteLearningPhase(learningPhase);
            notifyListners(StudyChange.DELETED_LEARNINGPHASE);
        } else {
            System.err.println("The LearningPhase isn't for a Subject");
        }
    }

    //Listener Methods
    private void notifyListners(StudyChange changed) {
        for (StudyListener listener : listeners) {
            listener.notifyListener(changed);
        }
    }

    public void addListener(StudyListener listener) {
        listeners.add(listener);
    }

    /**
     * Deletes a Subject and all LearningPhases for this Subject
     *
     * @param subject - subject to be deleted
     */
    public void deleteSubject(Subject subject) {
        subjects.remove(subject);
        subject.getSemester().deleteSubject(subject);
    }

    /**
     * Method used when loading from a Json File
     * Sets the Week Factory of Study and all its Members to the given WekFactory
     *
     * @param weekFactory
     */
    public void setWeekFactory(WeekFactory weekFactory) {
        this.weekFactory = weekFactory;
        for (Subject subject : subjects) {
            subject.setWeekFactory(weekFactory);
        }
        for (Semester semester : semesters) {
            semester.setWeekFactory(weekFactory);
        }
    }

    /**
     * Deletes the given semester, all its Subjects and LearningPhases that belong to the Subjects in the given Semester
     *
     * @param semesterToDelete
     */
    public void deleteSemester(Semester semesterToDelete) {
        if (currentLearningPhase != null)
            if (semesterToDelete.includesSubject((Subject) currentLearningPhase.getTimeSpentContainer())) {
                currentLearningPhase = null;
                notifyListners(StudyChange.CURRENT_LEARNINGPHASE_DELETED);
            }
        semesters.remove(semesterToDelete);
        semesterToDelete.delete();
        notifyListners(StudyChange.DELETED_SEMESTER);
    }

    /**
     * Deletes all Semesters and Subjects of this Study
     */
    public void delete() {
        for(Semester semester:semesters){
            semester.delete();
        }
        semesters.clear();
        subjects.clear();
        notifyListners(StudyChange.STUDY_DELETED);
    }
}
