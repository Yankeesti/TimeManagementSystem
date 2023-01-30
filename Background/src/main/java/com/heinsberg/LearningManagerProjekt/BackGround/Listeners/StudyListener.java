package com.heinsberg.LearningManagerProjekt.BackGround.Listeners;

import com.heinsberg.LearningManagerProjekt.BackGround.Listeners.ChangeEnums.StudyChange;
import com.heinsberg.LearningManagerProjekt.BackGround.Listeners.ChangeEnums.SubjectChange;

/**
 * The StudyListener class is an abstract class that provides a basic framework for listeners that want to be notified of changes to a study.
 * It defines a notifyListener method that is used to notify registered listeners of changes and an abstract changed method that needs to be implemented by any class wishing to be notified of changes.
 * The changed method is called by the notifyListener method when a change is detected.
 */
public abstract class StudyListener {
    /**
     * The notifyListener method is used to notify any registered listeners that a change has occurred.
     *
     * @param studyChange - an enumeration representing the type of change that has occurred
     */
    public void notifyListener(StudyChange studyChange) {
        changed(studyChange);
    }

    /**
     * The changed method is an abstract method that needs to be implemented by any class wishing to be notified of changes to a subject.
     * It is called by the notifyListener method when a change is detected.
     *
     * @param studyChange - an enumeration representing the type of change that has occurred
     */
    public abstract void changed(StudyChange studyChange);
}
