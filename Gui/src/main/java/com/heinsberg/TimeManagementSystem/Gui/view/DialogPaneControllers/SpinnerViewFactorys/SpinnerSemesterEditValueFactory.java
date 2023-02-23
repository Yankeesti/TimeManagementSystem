package com.heinsberg.TimeManagementSystem.Gui.view.DialogPaneControllers.SpinnerViewFactorys;

import com.heinsberg.TimeManagementSystem.BackGround.study.TimeClasses.Semester;
import javafx.scene.control.SpinnerValueFactory;

import java.util.Arrays;

public class SpinnerSemesterEditValueFactory extends SpinnerValueFactory.IntegerSpinnerValueFactory{
    Semester toEdit;
    int[] takenSemesterNumbers;
    public SpinnerSemesterEditValueFactory(Semester toEdit) {
        super(1, 50);
        this.toEdit = toEdit;
        takenSemesterNumbers = toEdit.getStudy().getTakenSemester();
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
        if(takenIndex < 0 || getValue()-i == toEdit.getSemester()){
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
                    if(newValue == toEdit.getSemester()){
                        setValue(newValue);
                        break;
                    }
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
                    if(newValue == toEdit.getSemester()){
                        setValue(newValue);
                        break;
                    }
                    takenIndex++;
                }
            }
        }
    }
}
