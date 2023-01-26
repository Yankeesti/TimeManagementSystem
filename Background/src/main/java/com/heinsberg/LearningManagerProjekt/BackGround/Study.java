package com.heinsberg.LearningManagerProjekt.BackGround;

import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.Semester;
import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.TimePeriod;
import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.Week;
import com.heinsberg.LearningManagerProjekt.BackGround.subject.Subject;
import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.LearningPhase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The Study class represents a study program, containing information about the semesters, subjects, and learning phases of the program.
 * It includes methods for controlling and editing the study program, such as starting and ending learning phases, adding new semesters and subjects, and retrieving information about the current state of the program.
 */
public class Study {
    private String studyName;

    private ArrayList<Semester> semesters;//Semesters that are ended
    private ArrayList<Subject> subjects;
    private LearningPhase currentLearningPhase;

    private Semester currentSemester; //stores the current Semester

    public Study(String studyName) {
        this.studyName = studyName;
        subjects = new ArrayList<Subject>();
        semesters = new ArrayList<Semester>();
    }

    //Methodes to Control learningPhase

    /**
     * starts a Learning Phase for the Subject subject
     * and stores it in currentLearningPhase
     *
     * @param subject - Subject to start Learning
     * @return 0 if a Learning Phase has started 1 when there is allready a started learningPhase -1 when there is no Semester that includes the current date and -2 if the current Semester doesn't include subject
     */
    public int startLearningPhase(Subject subject) {
        if (currentLearningPhase != null)
            return 1;
        if (!upDateSemester())
            return -1;
        if(!currentSemester.includesSubject(subject))
            return -2;

        currentLearningPhase = subject.startLearningPhase();
        currentSemester.addLearningPhase(currentLearningPhase);
        return 0;
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
     * @return true if Semester was added successfully, false if parts of this Semester are already included in another Semester or semesterValue is already taken
     */
    public boolean addSemester(Semester semesterToAdd) {

        //check if ther is already a Semester with the Same Dates in it
        for (int i = 0; i < semesters.size(); i++) {
            if (semesters.get(i).getSemester() == semesterToAdd.getSemester())
                return false;
            int compared = semesters.get(i).compareTo(semesterToAdd);
            if (compared != 2 && compared != -2) {//semester is in a Semester that already exists
                return false;
            }
        }
        semesters.add(semesterToAdd);
        semesters.sort((Semester s1, Semester s2) -> s1.getSemester() - s2.getSemester());
        //Add subjects of semester to subjects (used when loading from json)
        for(Semester semester: semesters){
            subjects.addAll(List.of(semester.getSubjects()));
        }
        return true;
    }

    /**
     * Adds a Subject to the Study
     *
     * @param subjectToAdd - subject to be added
     * @return true if Subject was added, false if not
     */
    public boolean addSubject(Subject subjectToAdd) {
        if (!subjectAllreadyExistend(subjectToAdd)) {
            Semester subjectSemester = subjectToAdd.getSemester();
            if (subjectSemester != null) {
                subjectSemester.addSubject(subjectToAdd);
                subjects.add(subjectToAdd);
                return true;
            }
            return false;
        }
        return false;
    }

    //Get Mehtodes

    /**
     * Returns an array of all semesters in this study, sorted by their semester number.
     *
     * @return an array of all semesters in this study.
     */
    public ArrayList<Semester> getSemesters() {
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
            if (subjects.get(i).getSubjectName() == subject.getSubjectName() && subjects.get(i).getSemester() == subject.getSemester()) {
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
     * @param learningPhase
     */
    public void addLearningPhase(LearningPhase learningPhase){
        learningPhase.getSubject().getSemester().addLearningPhaseHard(learningPhase);
        learningPhase.getSubject().addLearningPhase(learningPhase);
        if(learningPhase.getEndDate() == null){
            currentLearningPhase = learningPhase;
        }
    }

}
