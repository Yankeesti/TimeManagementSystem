package com.heinsberg.LearningManagerProjekt.BackGround.study;

import com.heinsberg.LearningManagerProjekt.BackGround.study.Listeners.ChangeEnums.StudyChange;
import com.heinsberg.LearningManagerProjekt.BackGround.study.Listeners.StudyListener;
import com.heinsberg.LearningManagerProjekt.BackGround.study.TimeClasses.Semester;
import com.heinsberg.LearningManagerProjekt.BackGround.study.TimeClasses.TimePeriod;
import com.heinsberg.LearningManagerProjekt.BackGround.study.subject.Subject;
import com.heinsberg.LearningManagerProjekt.BackGround.study.TimeClasses.LearningPhase;
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
     * @return 0 if a Learning Phase has started 1 when there is allready a started learningPhase -1 when there is no Semester that includes the current date and -2 if the current Semester doesn't include subject
     */
    public LearningPhaseActionResult startLearningPhase(Subject subject) {
        if (currentLearningPhase != null)
            return LearningPhaseActionResult.LEARNINGPHASE_ALREADY_STARTED;
        if (!upDateSemester())
            return LearningPhaseActionResult.NO_CURRENT_SEMESTER;
        if (!currentSemester.includesSubject(subject))
            return LearningPhaseActionResult.CURRENT_SEMESTER_DOSENT_INCLUEDE_SUBJECT;

        currentLearningPhase = subject.startLearningPhase();
        currentSemester.addLearningPhase(currentLearningPhase);
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
                subjectSemester.addSubject(subjectToAdd);
                subjects.add(subjectToAdd);
                return AddSubjectResult.SUCCESS;
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
     * @param semesternumber the semester number of the Semester to retrieve
     * @return the Semester with the specified semester number, or null if no such Semester exists.
     */
    public Semester getSemester(int semsterNumber) {
        for (int i = 0; i < semesters.size(); i++) {
            Semester temp = semesters.get(i);
            if (temp.getSemester() == semsterNumber)
                //when Semester is found return it
                return temp;
            if (temp.getSemester() > semsterNumber)
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
    private boolean upDateSemester() {
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

    /**
     * Calculates how much was learned for subject in the current Week
     *
     * @param subject - subject
     * @return learned for subject in current week in Minutes and -1 if this subject isn't in currentSemester
     */
    public int getLearnedInCurrentWeek(Subject subject) {
        upDateSemester();
        if (currentSemester == subject.getSemester()) {
            return currentSemester.getCurrentWeek().getLearnedFor(subject);
        }
        return -1;
    }

    public void deleteLearningPhase(LearningPhase learningPhase) {
        if (learningPhase.getTimeSpentContainer().getClass() == Subject.class) {
            if (currentLearningPhase == learningPhase) {
                notifyListners(StudyChange.CURRENT_LEARNINGPHASE_DELETED);
                currentLearningPhase = null;
            }
            ((Subject)learningPhase.getTimeSpentContainer()).getSemester().deleteLearningPhase(learningPhase);
        }else{
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
}
