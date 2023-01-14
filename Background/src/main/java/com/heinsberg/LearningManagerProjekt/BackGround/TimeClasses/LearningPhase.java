package com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses;

import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.TimePeriod;
import com.heinsberg.LearningManagerProjekt.BackGround.subject.Subject;

public class LearningPhase extends TimePeriod {
    private Subject subject;
    private long timeLearned; //Time learned in Seconds
    LearningPhase(Subject subject){
        super(getAktDate());
        this.subject = subject;
    }

    /**
     * ends this LearningPhase
     * @return the time learned in Seconds
     */
    public long endLearningPhase(){
        setEndTime(getAktDate().getTime());
        timeLearned = getDiffrence()/1000;
        return timeLearned;
    }

    //Getter and Setter
    public Subject getSubject(){
        return subject;
    }
}
