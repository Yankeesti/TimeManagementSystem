package com.heinsberg.TimeManagementSystem.BackGround.TypeAdapter;

import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.heinsberg.TimeManagementSystem.BackGround.Project.Project;
import com.heinsberg.TimeManagementSystem.BackGround.Project.TypeAdapter.ProjectTypeAdapter;
import com.heinsberg.TimeManagementSystem.BackGround.TimeManagementSystem;
import com.heinsberg.TimeManagementSystem.BackGround.study.Study;
import com.heinsberg.TimeManagementSystem.BackGround.study.typeAdapters.StudyTypeAdapter;
import javafx.collections.ObservableList;

import java.io.IOException;

public class TimeManagementSystemTypeAdapter extends TypeAdapter<TimeManagementSystem> {
    @Override
    public void write(JsonWriter jsonWriter, TimeManagementSystem timeManagementSystem) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("studies");
        writeStudiesArray(jsonWriter, timeManagementSystem);
        jsonWriter.name("projects");
        writeProjects(jsonWriter,timeManagementSystem);
        jsonWriter.endObject();
    }

    private void writeProjects(JsonWriter jsonWriter, TimeManagementSystem timeManagementSystem) throws IOException {
        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(Project.class, new ProjectTypeAdapter());
        ObservableList<Project> projects = timeManagementSystem.getProjects();
        jsonWriter.beginArray();
        for(Project project: projects){
            gsonBuilder.create().toJson(project,Project.class,jsonWriter);
        }
        jsonWriter.endArray();
    }

    /**
     * Writes a array that contains all Studies in TimeManagementSystem
     */
    private void writeStudiesArray(JsonWriter jsonWriter, TimeManagementSystem timeManagementSystem) throws IOException {
        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(Study.class,new StudyTypeAdapter());
        ObservableList<Study> studies = timeManagementSystem.getStudies();
        jsonWriter.beginArray();
        for(Study study: studies){
            gsonBuilder.create().toJson(study,Study.class,jsonWriter);
        }
        jsonWriter.endArray();
    }

    @Override
    public TimeManagementSystem read(JsonReader jsonReader) throws IOException {
        return null;
    }
}
