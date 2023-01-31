package com.heinsberg.LearningManagerProjekt.BackGround.study.dataHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.heinsberg.LearningManagerProjekt.BackGround.study.Study;

import java.io.*;
public class StudyIO {
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

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

    public static Study loadStudy(File file) {
        Study study = null;
        try {
            FileReader reader = new FileReader(file);
            study = gson.fromJson(reader, Study.class);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return study;
    }
}
