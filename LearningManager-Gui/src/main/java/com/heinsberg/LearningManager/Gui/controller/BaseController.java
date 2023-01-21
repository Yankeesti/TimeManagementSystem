package com.heinsberg.LearningManager.Gui.controller;

import com.heinsberg.LearningManager.Gui.StudyManager;
import com.heinsberg.LearningManager.Gui.view.ViewFactory;
import com.heinsberg.LearningManagerProjekt.BackGround.Study;

public abstract class BaseController {
    protected StudyManager studyManager;
    protected ViewFactory viewFactory;
    protected String fxmlName;

    public BaseController(StudyManager studyManager,ViewFactory viewFactory,String fxmlName) {
        this.studyManager = studyManager;
        this.viewFactory = viewFactory;
        this.fxmlName = fxmlName;
    }

    public String getFxmlName(){return fxmlName;}

}
