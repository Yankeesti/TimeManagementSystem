package com.heinsberg.LearningManager.Gui.controller.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.heinsberg.LearningManager.Gui.controller.LoadFileResult;
import com.heinsberg.LearningManagerProjekt.BackGround.Study;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileManagerSevice {

    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static LoadFileResult loadFile(){
        System.out.println("loadFile");
        return LoadFileResult.SUCCESS;
    }
    public static void saveStudy(Study study, File file) {
        try {
            String json = gson.toJson(study);
            FileWriter writer = new FileWriter(file);
            writer.write(json);
            writer.close();
            System.out.println("Study information saved to " + file.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
