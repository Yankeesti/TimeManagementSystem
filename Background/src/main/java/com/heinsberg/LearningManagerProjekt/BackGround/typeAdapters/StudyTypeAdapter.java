package com.heinsberg.LearningManagerProjekt.BackGround.typeAdapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.heinsberg.LearningManagerProjekt.BackGround.Study;
import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.Semester;

import java.io.IOException;

public class StudyTypeAdapter extends TypeAdapter<Study> {
    @Override
    public void write(JsonWriter jsonWriter, Study study) throws IOException {
        GsonBuilder gson = new GsonBuilder().registerTypeAdapter(Semester.class,new SemesterTypeAdapter());
        jsonWriter.beginObject();
        jsonWriter.name("study Name").value(study.getName());
        jsonWriter.name("semesters");
        jsonWriter.beginArray();
        Semester[] semesters = study.getSemesters();
        for(Semester semester: semesters){
            gson.create().toJson(semester, Semester.class, jsonWriter); //writes Semester
        }
        jsonWriter.endArray();
        jsonWriter.endObject();

    }

    @Override
    public Study read(JsonReader jsonReader) throws IOException {
        return null;
    }

}
