package com.heinsberg.LearningManager.Gui.controller.componentController.studyInformationControlls;

import com.heinsberg.LearningManagerProjekt.BackGround.study.Study;
import com.heinsberg.LearningManagerProjekt.BackGround.study.TimeClasses.Semester;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.SpinnerValueFactory;

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
        semesters = study.getSemesters();
        updateBannedSelection();
        semesters.addListener(new ListChangeListener() {
            @Override
            public void onChanged(Change change) {//changes to be made when a new Semester was Added/ a Semester was deleted
                updateBannedSelection();
                setLowestPossibleValue();
            }
        });
    }

    private void updateBannedSelection() {
        bannedSelection = new int[semesters.size()];
        for (int i = 0; i < bannedSelection.length; i++) {
            bannedSelection[i] = semesters.get(i).getSemester();
        }
    }


    /**
     * sets the Semester Value to the next possible value under i
     * @param i
     */
    @Override
    public void decrement(int i) {
        int newValue = getValue()-1;
        while(true){
            if(semesterPossible(newValue)){
                setValue(newValue);
                break;
            } else if (newValue <= 1) {
                break;
            }else
                newValue--;
        }
    }

    /**
     * sets the Semester Value to the next possible Value over i
     * @param i
     */
    @Override
    public void increment(int i) {
        int newValue = getValue()+1;
        while (true){
            if(semesterPossible(newValue)){
                setValue(newValue);
                break;
            }else{
                newValue++;
            }
        }
    }

    /**
     * Sets the Value to the lowest possible choosable Value
     */
    public void setLowestPossibleValue(){
        int i = 1;
        while(true){
            if(semesterPossible(i)){
                setValue(i);
                break;
            }else
                i++;
        }
    }

    private boolean semesterPossible(int i) {
        for (int banned : bannedSelection) {
            if (i == banned)
                return false;
        }
        return true;
    }
}
