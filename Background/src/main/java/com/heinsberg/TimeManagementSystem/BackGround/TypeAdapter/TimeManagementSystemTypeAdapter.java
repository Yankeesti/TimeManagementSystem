package com.heinsberg.TimeManagementSystem.BackGround.TypeAdapter;

import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.heinsberg.TimeManagementSystem.BackGround.Project.Project;
import com.heinsberg.TimeManagementSystem.BackGround.Project.TypeAdapter.ProjectTypeAdapter;
import com.heinsberg.TimeManagementSystem.BackGround.TimeManagementSystem;
import com.heinsberg.TimeManagementSystem.BackGround.WeekFactory;
import com.heinsberg.TimeManagementSystem.BackGround.study.Study;
import com.heinsberg.TimeManagementSystem.BackGround.study.TimeClasses.Semester;
import com.heinsberg.TimeManagementSystem.BackGround.study.typeAdapters.StudyTypeAdapter;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class TimeManagementSystemTypeAdapter extends TypeAdapter<TimeManagementSystem> {
    @Override
    public void write(JsonWriter jsonWriter, TimeManagementSystem timeManagementSystem) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("studies");
        writeStudiesArray(jsonWriter, timeManagementSystem);
        jsonWriter.name("projects");
        writeProjects(jsonWriter, timeManagementSystem);
        jsonWriter.endObject();
    }

    private void writeProjects(JsonWriter jsonWriter, TimeManagementSystem timeManagementSystem) throws IOException {
        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(Project.class, new ProjectTypeAdapter());
        ObservableList<Project> projects = timeManagementSystem.getProjects();
        jsonWriter.beginArray();
        for (Project project : projects) {
            gsonBuilder.create().toJson(project, Project.class, jsonWriter);
        }
        jsonWriter.endArray();
    }

    /**
     * Writes a array that contains all Studies in TimeManagementSystem
     */
    private void writeStudiesArray(JsonWriter jsonWriter, TimeManagementSystem timeManagementSystem) throws IOException {
        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(Study.class, new StudyTypeAdapter());
        ObservableList<Study> studies = timeManagementSystem.getStudies();
        jsonWriter.beginArray();
        for (Study study : studies) {
            gsonBuilder.create().toJson(study, Study.class, jsonWriter);
        }
        jsonWriter.endArray();
    }

    /**
     * Creates a new TimeManagementSystem based on th Json File
     * sets the WeekFactory for all Members to a new created WeekFactory
     *
     * @param jsonReader
     * @return read TimeManagementSystem
     * @throws IOException
     */
    @Override
    public TimeManagementSystem read(JsonReader jsonReader) throws IOException {
        ArrayList<Study> studies = null;
        ArrayList<Project> projects = null;
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            switch (jsonReader.nextName()) {
                case "studies":
                    studies = readStudies(jsonReader);
                    break;
                case "projects":
                    projects = readProjects(jsonReader);
                    break;
            }
        }
        jsonReader.endObject();
        TimeManagementSystem outPut = new TimeManagementSystem(new WeekFactory());
        outPut.loadStudies(studies);
        outPut.loadProjects(projects);
        return outPut;
    }

    private ArrayList<Project> readProjects(JsonReader jsonReader) {
        ArrayList<Project> outPut = null;
        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(Project.class, new ProjectTypeAdapter());
        Type listType = new TypeToken<ArrayList<Project>>() {
        }.getType();

        outPut = gsonBuilder.create().fromJson(jsonReader , listType);
        return outPut;
    }

    private ArrayList<Study> readStudies(JsonReader jsonReader) {
        ArrayList<Study> outPut = new ArrayList<Study>();
        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(Study.class, new StudyTypeAdapter());
        Type listType = new TypeToken<ArrayList<Study>>() {
        }.getType();

        outPut = gsonBuilder.create().fromJson(jsonReader, listType);
        return outPut;
    }
}
