package com.heinsberg.LearningManager.Gui.controller.services;

import com.google.gson.GsonBuilder;
import com.heinsberg.LearningManagerProjekt.BackGround.study.Study;
import com.heinsberg.LearningManagerProjekt.BackGround.study.typeAdapters.StudyTypeAdapter;

public class FileManagerSevice {

    private static GsonBuilder gson = new GsonBuilder().registerTypeAdapter(Study.class,new StudyTypeAdapter());


}
