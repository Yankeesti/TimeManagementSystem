package com.heinsberg.LearningManagerProjekt.BackGround;

import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.Semester;
import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.TimePeriod;
import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.Week;
import com.heinsberg.LearningManagerProjekt.BackGround.subject.Subject;
import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.LearningPhase;

import java.util.ArrayList;
import java.util.Date;

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
     * @param subject - Subject to start Learning
     * @return 0 if a Learning Phase has started 1 when there is allready a started learningPhase and -1 when there is no Semester that includes the current date
     */
    public int startLearningPhase(Subject subject){
        if(currentLearningPhase != null)
            return 1;
        if(!upDateSemester())
            return -1;

        currentLearningPhase = currentSemester.startLearningPhase(subject);
        subject.addLearningPhase(currentLearningPhase);
        return 0;
    }

    /**
     * ends the current learningPhase
     * @return if there isn't a current Learning Phase -1 and if there is a current Learning Phase, the Time learned in Seconds.
     *
     */
    public long finishLearningPhase(){
        if(currentLearningPhase == null)
        return-1;
        long timeLearned = currentLearningPhase.endLearningPhase();
        currentLearningPhase = null;
        return timeLearned;
    }

    /**
     * starts a Break in the current learningPhase
     * @return true if action worked and false if not
     */

    //Methods to Edit Study

    /**
     * adds a new Semester to the Study
     * @param semesterToAdd
     * @return true if Semester was edit Succesfully, false if parts of this Semester are already included in a other Semester or semesterValue is already taken
     */
    public boolean addSemester(Semester semesterToAdd){

        //check if ther is already a Semester with the Same Dates in it
        for(int i = 0; i < semesters.size();i++){
            if(semesters.get(i).getSemester() == semesterToAdd.getSemester())
                return false;
            int compared = semesters.get(i).compareTo(semesterToAdd);
            if(compared != 2 && compared != -2){//semester is in a Semester that already exists
                return false;
            }
        }
        semesters.add(semesterToAdd);
        semesters.sort((Semester s1,Semester s2) -> s1.getSemester() - s2.getSemester());
        return true;
    }

    /**
     * Adds a Subject to the Study
     * @param subjectToAdd - subject to be added
     * @return true if Subject was added false if not
     */
    public boolean addSubject(Subject subjectToAdd){
    if(!subjectAllreadyExistend(subjectToAdd)){
        Semester subjectSemester = findSemester(subjectToAdd.getSemester());
        if(subjectSemester != null){
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
     * sorts and returns the Semesters
     * @return - sorted Array of all semesters
     */
    public Semester[] getSemesters(){
        Semester[] outPut = semesters.toArray(new Semester[semesters.size()]);
        return outPut;

    }


    /**
     *
     * @param semsterNumber semester Number
     * @return the Semester with semesterNumber when ther is no Semester with semester number null
     */
    public Semester getSemester(int semsterNumber){
        for(int i = 0; i<semesters.size();i++){
            Semester temp = semesters.get(i);
            if(temp.getSemester() == semsterNumber)
                //when Semester is found return it
                return temp;
            if(temp.getSemester()> semsterNumber)
                //when temp semester number is higher then semesterNumber ther can't be a Semester with semesterNumber because
                //semesters is sorted --> return null;
                return null;
        }
        //no Semester found
        return null;
    }

    /**
     *
     * @return - Name of the Study
     */
    public String getName(){return studyName;};

    //private Methodes

    /**
     * upDates the Semester
     * @return true if there is a Semester wich containes the current Week and false if not
     */
    private boolean upDateSemester(){
        Date aktDate = TimePeriod.getAktDate();
        if(currentSemester == null || currentSemester.compareTo(aktDate) != 0){
            for(int i = 0; i<semesters.size();i++){
                if(semesters.get(i).compareTo(aktDate) == 0){
                    currentSemester = semesters.get(i);
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    /**
     * chekcs if subject is already existend
     * @param subject - Subjct to Check
     * @return true if there is already a subject with the name of subject and the semester of subject and false if not
     */
    private boolean subjectAllreadyExistend(Subject subject){
        for(int i = 0 ; i<subjects.size();i++){
            if(subjects.get(i).getSubjectName() == subject.getSubjectName() && subjects.get(i).getSemester() == subject.getSemester()){
                //subject is already existends
                return true;
            }
        }
        return false;
    }

    /**
     * finds the semester with semesterNumber
     * @param semesterNumber
     * @return Semester with semester number if it's ther and null if not
     */
    private Semester findSemester(int semesterNumber){
        for(int i = 0; i<semesters.size();i++){
            if(semesters.get(i).getSemester() == semesterNumber){//Semester found
                return semesters.get(i);}
        }
        return null;
    }

}
