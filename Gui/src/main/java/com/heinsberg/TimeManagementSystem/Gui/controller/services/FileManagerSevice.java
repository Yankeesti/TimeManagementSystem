package com.heinsberg.TimeManagementSystem.Gui.controller.services;

import com.google.gson.GsonBuilder;
import com.heinsberg.TimeManagementSystem.BackGround.study.Study;
import com.heinsberg.TimeManagementSystem.BackGround.study.typeAdapters.StudyTypeAdapter;

public class FileManagerSevice {

    private static GsonBuilder gson = new GsonBuilder().registerTypeAdapter(Study.class,new StudyTypeAdapter());


}
