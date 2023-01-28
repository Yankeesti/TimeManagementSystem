package com.heinsberg.LearningManagerProjekt.BackGround.Listeners;

import com.heinsberg.LearningManagerProjekt.BackGround.Listeners.ChangeEnums.SubjectChange;

/**
 * The SubjectListener class is an abstract class that serves as a base for objects that need to be notified of changes in a subject.
 * It provides a method called 'notifyListener' which can be called by the subject to notify all registered listeners of a change.
 * The 'changed' method needs to be implemented by any class that extends this class, and will be called by the 'notifyListener' method.
 * This class also uses an enumeration called 'SubjectChange' to indicate the type of change that has occurred.
 */
public abstract class SubjectListener {

    /**
     * The notifyListener method is used to notify any registered listeners that a change has occurred.
     *
     * @param subjectChange - an enumeration representing the type of change that has occurred
     */
    public void notifyListener(SubjectChange subjectChange) {
        changed(subjectChange);
    }

    /**
     * The changed method is an abstract method that needs to be implemented by any class wishing to be notified of changes to a subject.
     * It is called by the notifyListener method when a change is detected.
     *
     * @param subjectChange - an enumeration representing the type of change that has occurred
     */
    public abstract void changed(SubjectChange subjectChange);
}
