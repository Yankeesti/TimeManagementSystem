package com.heinsberg.LearningManagerProjekt.BackGround;

import com.heinsberg.LearningManagerProjekt.BackGround.semester.Semester;
import com.heinsberg.LearningManagerProjekt.BackGround.study.Study;
import com.heinsberg.LearningManagerProjekt.BackGround.subject.Subject;

import java.util.Date;

public class Interface {
    private Study study;

    /**
     * uses study and operates on it
     * @param study
     */
    public Interface(Study study){
        this.study = study;
    }

    /**
     * creates a new Study
     */
    public Interface(String studyName){
        study = new Study(studyName);
    }


    //Methodes to Control learningPhase
    /**
     * starts a learningPhase for the Subject with subject Id
     * and stores it in currentLearningPhase
     * @param subjectId - Subject Id
     * @return  true when the learning Phase could be startet and false when not
     */
    public boolean startLearningPhase(int subjectId){
        return false;
    }

    /**
     * @return if there isnt a current Learning Phase -1 and if there is a current Learning Phase, the Time learned in Seconds.
     *
     */
    public long finishLearningPhase(){
        return-1;
    }

    /**
     * starts a Break in the current learningPhase
     * @return true if action worked and false if not
     */
    public boolean startBreak() {
        return false;
    }

    /**
     * end LearningPhase Break
     * @return time of break in second if there is no break to end -1
     */
    public long endBreak() {
        return -1;
    }

    //Methods to Edit the Study

    /**
     * Adds a Semester to the Study
     * @param semester - Number of Semeser
     * @param semesterStart - Start of Semester
     * @param semesterEnd   - end of Semester
     */
    public void addSemester(int semester, Date semesterStart, Date semesterEnd) {

    }

    /**
     * Adds a Subject to the given Semester
     * @param semester - Semester number
     * @param subjectName - name of Subject
     */
    public void addSubject(int semester,String subjectName){

    }

    public void setWeekGoal(int semester, int subjectId, int weekGoal){

    }

    //Methods to get Data out of the Study

    public Semester[] getSemesters(){
        return null;
    }

    /**
     *
     * @param semester - semerster of wich the Subjects are wanted
     * @return subjects of semester
     */
    public Subject[] getSubjects(int semester){
        return null;
    }

}
