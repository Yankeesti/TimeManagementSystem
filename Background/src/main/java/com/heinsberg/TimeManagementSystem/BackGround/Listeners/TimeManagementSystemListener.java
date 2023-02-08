package com.heinsberg.TimeManagementSystem.BackGround.Listeners;

import com.heinsberg.TimeManagementSystem.BackGround.Listeners.ChangeEnums.TimeManagementSystemChange;
import com.heinsberg.TimeManagementSystem.BackGround.study.Listeners.ChangeEnums.StudyChange;

/**
 * The TimeManagementSystemListener class is an abstract class that provides a basic framework for listeners that want to be notified of changes to a TimeManagementSystem.
 * It defines a notifyListener method that is used to notify registered listeners of changes and an abstract changed method that needs to be implemented by any class wishing to be notified of changes.
 * The changed method is called by the notifyListener method when a change is detected.
 */
public abstract class TimeManagementSystemListener {

    /**
     * The notifyListener method is used to notify any registered listeners that a change has occurred.
     *
     * @param TimeManagementSystemChange - an enumeration representing the type of change that has occurred
     */
    public void notifyListener(TimeManagementSystemChange change) {
        changed(change);
    }

    public abstract void changed(TimeManagementSystemChange change);
}
