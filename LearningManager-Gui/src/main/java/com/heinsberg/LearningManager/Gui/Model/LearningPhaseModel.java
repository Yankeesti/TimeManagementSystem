package com.heinsberg.LearningManager.Gui.Model;

import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.LearningPhase;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Time;
import java.util.Date;

public class LearningPhaseModel {
    private SimpleObjectProperty<DateModel> startDate;
    private SimpleObjectProperty startTime;
    private SimpleObjectProperty<TimeModel> learned;
    private LearningPhase learningPhase;

    public LearningPhaseModel(LearningPhase learningPhase){
        this.learningPhase = learningPhase;
        this.startTime= new SimpleObjectProperty<TimeModel>(new TimeModel(learningPhase));
        this.startDate = new SimpleObjectProperty<DateModel>(new DateModel(learningPhase));
        this.learned = new SimpleObjectProperty<TimeModel>(new TimeModel(learningPhase.getTimeLearned()));
    }
}
