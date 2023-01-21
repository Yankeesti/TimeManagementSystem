package com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses;

import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.TimePeriod;
import com.heinsberg.LearningManagerProjekt.BackGround.subject.Subject;

/**
 * Represents a specific period of learning for a subject.
 *
 * @author Tim Heinserg
 */
public class LearningPhase extends TimePeriod {
    private Subject subject;
    private long timeLearned; //Time learned in Seconds

    /**
     * Creates a new LearningPhase object with the current date and time as the start date, and the given subject.
     *
     * @param subject The subject being studied during this learning phase.
     */
    LearningPhase(Subject subject) {
        super(getAktDate());
        this.subject = subject;
    }

    /**
     * Ends this learning phase and calculates the time spent learning in seconds.
     *
     * @return the time learned in seconds.
     */
    public long endLearningPhase() {
        setEndTime(getAktDate().getTime());
        timeLearned = getDiffrence() / 1000;
        return timeLearned;
    }

    //Getter and Setter

    /**
     * Retrieves the subject studied during this learning phase.
     *
     * @return the subject studied during this learning phase.
     */
    public Subject getSubject() {
        return subject;
    }
}
