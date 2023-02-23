package com.heinsberg.TimeManagementSystem.BackGround.study.Listeners;

import com.heinsberg.TimeManagementSystem.BackGround.study.Listeners.ChangeEnums.SemesterChange;

public abstract class SemesterListener {

    public void notifyListener(SemesterChange change){
        changed(change);
    }

    public abstract void changed(SemesterChange change);
}
