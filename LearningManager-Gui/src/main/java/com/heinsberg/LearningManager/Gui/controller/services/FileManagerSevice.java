package com.heinsberg.LearningManager.Gui.controller.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.heinsberg.LearningManager.Gui.controller.FileResult;
import com.heinsberg.LearningManagerProjekt.BackGround.Study;
import com.heinsberg.LearningManagerProjekt.BackGround.typeAdapters.StudyTypeAdapter;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileManagerSevice {

    private static GsonBuilder gson = new GsonBuilder().registerTypeAdapter(Study.class,new StudyTypeAdapter());


}
