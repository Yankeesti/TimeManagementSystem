package com.heinsberg.TimeManagementSystem.BackGround.study.typeAdapters;

import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.heinsberg.TimeManagementSystem.BackGround.study.Study;
import com.heinsberg.TimeManagementSystem.BackGround.study.TimeClasses.Semester;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class StudyTypeAdapter extends TypeAdapter<Study> {
    @Override
    public void write(JsonWriter jsonWriter, Study study) throws IOException {
        GsonBuilder gson = new GsonBuilder().registerTypeAdapter(Semester.class, new SemesterTypeAdapter());
        jsonWriter.beginObject();
        jsonWriter.name("study Name").value(study.getName());
        jsonWriter.name("semesters");
        jsonWriter.beginArray();
        ObservableList<Semester> semesters = study.getSemesters();
        for (Semester semester : semesters) {
            gson.create().toJson(semester, Semester.class, jsonWriter); //writes Semester
        }
        jsonWriter.endArray();
        jsonWriter.endObject();

    }

    @Override
    public Study read(JsonReader jsonReader) throws IOException {
        String studyName = "";
        ArrayList<Semester> semesters = null;
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            switch (jsonReader.nextName()) {
                case "study Name":
                    studyName = jsonReader.nextString();
                    break;
                case "semesters":
                    semesters = readSemesters(jsonReader);
            }
        }
        jsonReader.endObject();
        Study outPut = new Study(studyName);
        setUpStudy(outPut, semesters);
        return outPut;
    }

    private void setUpStudy(Study study, ArrayList<Semester> semesters) {
        //Add Semesters to study
        for (Semester semester : semesters) {
            study.addSemester(semester);
        }
    }

    private ArrayList<Semester> readSemesters(JsonReader jsonReader) {
        ArrayList<Semester> outPut = new ArrayList<Semester>();
        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(Semester.class, new SemesterTypeAdapter());
        Type listType = new TypeToken<ArrayList<Semester>>() {
        }.getType();
        outPut = gsonBuilder.create().fromJson(jsonReader, listType);


        return outPut;
    }
}

