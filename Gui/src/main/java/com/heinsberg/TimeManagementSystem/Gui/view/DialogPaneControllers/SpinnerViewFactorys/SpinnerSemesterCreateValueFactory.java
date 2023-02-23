package com.heinsberg.TimeManagementSystem.Gui.view.DialogPaneControllers.SpinnerViewFactorys;

import com.heinsberg.TimeManagementSystem.BackGround.study.Study;
import javafx.scene.control.SpinnerValueFactory;

import java.util.Arrays;

/**
 * Controlles the view of a Semester Spinner makes it only possible to choose Semester Numbers that are not taken yet
 */
public class SpinnerSemesterCreateValueFactory extends SpinnerValueFactory.IntegerSpinnerValueFactory {

    Study study;

    int[] takenSemesterNumbers;

    // when it is -1 it means there is no semester to edit
    public SpinnerSemesterCreateValueFactory(Study study) {
        super(1, 50);
        this.study = study;
        takenSemesterNumbers = study.getTakenSemester();
        int currentSemester = -1;
    }


    /**
     * when the number that comes out when decremented with i is not taken yet the Spinnert is set to current Value i
     * else to the next possible number under current Value
     *
     * @param i
     */
    @Override
    public void decrement(int i) {
        int takenIndex = Arrays.binarySearch(takenSemesterNumbers, getValue() - i); //The index where value-i is in the takenSemesterNumbers Array
        if(takenIndex < 0){
            setValue(getValue()-i);
        }else {
            //look for the next possible Value
            takenIndex --;
            for (int newValue = getValue() - i-1; newValue > 0; newValue--) {
                if(takenIndex < 0){//new Value possible because the lowest of the taken Semester Numbers is higher than new Value
                    setValue(newValue);
                    break;
                }else if(takenSemesterNumbers[takenIndex] < newValue){//new Value is possible
                    setValue(newValue);
                    break;
                }else{
                    takenIndex--;
                }
            }
        }
    }

    @Override
    public void increment(int i) {
        int takenIndex = Arrays.binarySearch(takenSemesterNumbers, getValue() + i); //The index where value-i is in the takenSemesterNumbers Array
        if(takenIndex < 0){
            setValue(getValue()+i);
        }else {
            //look for the next possible Value
            takenIndex ++;
            for (int newValue = getValue() + i+1; newValue < 50; newValue++) {
                if(takenIndex >= takenSemesterNumbers.length){//new Value possible because the highest of the taken Semester Numbers is lower than new Value
                    setValue(newValue);
                    break;
                }else if(takenSemesterNumbers[takenIndex] > newValue){//new Value is possible
                    setValue(newValue);
                    break;
                }else{
                    takenIndex++;
                }
            }
        }
    }

}
