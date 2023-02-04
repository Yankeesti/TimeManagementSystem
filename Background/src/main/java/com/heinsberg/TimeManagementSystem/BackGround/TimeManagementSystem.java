package com.heinsberg.TimeManagementSystem.BackGround;

import com.heinsberg.TimeManagementSystem.BackGround.Project.Project;
import com.heinsberg.TimeManagementSystem.BackGround.study.Study;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;

public class TimeManagementSystem {
    private ObservableList<Study> studies;
    private ObservableList<Project> projects;

    public TimeManagementSystem(){
        studies = FXCollections.observableArrayList();
        projects = FXCollections.observableArrayList();
    }

    public ObservableList<Study> getStudies() {
        return studies;
    }

    public ObservableList<Project> getProjects() {
        return projects;
    }

    public void addStudy(Study newStudy){
        studies.add(newStudy);
    }
    public void removeStudy(Study studyToRemove){
        studies.remove(studyToRemove);
    }
    public void addProject(Project  newProject){
        projects.add(newProject);
    }

    public void removeProject(Project projectToRemove){
        projects.remove(projectToRemove);
    }
}
