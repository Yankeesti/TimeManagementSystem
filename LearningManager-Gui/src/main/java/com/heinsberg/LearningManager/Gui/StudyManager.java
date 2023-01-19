package com.heinsberg.LearningManager.Gui;

import com.heinsberg.LearningManagerProjekt.BackGround.Study;

public class StudyManager {
    private Study study;

    public void createStudy(String studyName) {
        study = new Study(studyName);
        System.out.println("study Created");
    }
}
