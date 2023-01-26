package com.heinsberg.LearningManager.Gui.controller.componentController.studyInformationControlls;

import com.heinsberg.LearningManagerProjekt.BackGround.Study;
import com.heinsberg.LearningManagerProjekt.BackGround.TimeClasses.Semester;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.SpinnerValueFactory;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Class used to specifie which semester number can be chosen for a new Semester
 * Note: Semesters that are already in the study cant be chosen.
 */
public class SemesterSpinnerValueFactory extends SpinnerValueFactory.IntegerSpinnerValueFactory{
    Study study;
    private ObservableList<Semester> semesters;
    private int[] bannedSelection;//Contains all Semester numbers that are in study

    public SemesterSpinnerValueFactory(Study study) {
        super(0,Integer.MAX_VALUE);
        this.study = study;
        semesters = FXCollections.observableList(study.getSemesters());
        updateBannedSelection();
        semesters.addListener(new ListChangeListener() {
            @Override
            public void onChanged(Change change) {
                updateBannedSelection();
            }
        });
    }

    private void updateBannedSelection() {
        bannedSelection = new int[semesters.size()];
        for (int i = 0; i < bannedSelection.length; i++) {
            bannedSelection[0] = semesters.get(i).getSemester();
        }
    }


    /**
     * sets the Semester Value to the next possible value under i
     * @param i
     */
    @Override
    public void decrement(int i) {
        int newValue = getValue()-i;
        if(newValue>0 && semesterPossible(newValue))
            setValue(newValue);
        else if(newValue > 1)
            decrement(newValue);
    }

    /**
     * sets the Semester Value to the next possible Value over i
     * @param i
     */
    @Override
    public void increment(int i) {
        int newValue = i+getValue();
        if(semesterPossible(newValue)){
            setValue(newValue);}
        else {increment(newValue);}
    }

    private boolean semesterPossible(int i) {
        for (int banned : bannedSelection) {
            if (i == banned)
                return false;
        }
        return true;
    }
}
