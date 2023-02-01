package com.heinsberg.TimeManagementSystem.BackGround.Project.TypeAdapter;

import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.heinsberg.TimeManagementSystem.BackGround.Project.Project;
import com.heinsberg.TimeManagementSystem.BackGround.TimeClasses.LearningPhase;
import com.heinsberg.TimeManagementSystem.BackGround.study.typeAdapters.LearningPhaseAdapter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ProjectTypeAdapter extends TypeAdapter<Project> {

    @Override
    public void write(JsonWriter jsonWriter, Project project) throws IOException {
        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(LearningPhase.class, new LearningPhaseAdapter());
        LearningPhase[] learningPhases = project.getLearningPhases().toArray(new LearningPhase[0]);
        jsonWriter.beginObject();
        jsonWriter.name("projectName").value(project.getName());
        jsonWriter.name("weekGoal").value(project.getWeekGoal());
        jsonWriter.name("learningPhases");
        jsonWriter.beginArray();
        for (LearningPhase learningPhase : learningPhases) {
            gsonBuilder.create().toJson(learningPhase, LearningPhase.class, jsonWriter); //writes LearningPhase
        }
        jsonWriter.endArray();
        jsonWriter.endObject();
    }

    @Override
    public Project read(JsonReader jsonReader) throws IOException {

        String projectName = null;
        int weekGoal = 0;
        jsonReader.beginObject();
        ArrayList<LearningPhase> learningPhases = new ArrayList<LearningPhase>();
        while (jsonReader.hasNext()) {
            switch (jsonReader.nextName()) {
                case "projectName":
                    projectName = jsonReader.nextString();
                    break;
                case "weekGoal":
                    weekGoal = jsonReader.nextInt();
                    break;
                case "learningPhases":
                    learningPhases = readLearningPhases(jsonReader);
            }
        }
        jsonReader.endObject();
        Project outPut = new Project(projectName);
        outPut.setWeekGoal(weekGoal);
        for (LearningPhase learningPhase : learningPhases){
            outPut.addLearningPhase(learningPhase);
            learningPhase.setTimeSpentContainer(outPut);
        }
        return outPut;
    }

    private ArrayList<LearningPhase> readLearningPhases(JsonReader jsonReader) {
        ArrayList<LearningPhase> outPut = new ArrayList<LearningPhase>();
        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(LearningPhase.class, new LearningPhaseAdapter());
        Type listType = new TypeToken<ArrayList<LearningPhase>>() {
        }.getType();
        outPut = gsonBuilder.create().fromJson(jsonReader, listType);
        return outPut;
    }
}
