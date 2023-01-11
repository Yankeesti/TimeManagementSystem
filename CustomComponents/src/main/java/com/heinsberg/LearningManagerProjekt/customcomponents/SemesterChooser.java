package com.heinsberg.LearningManagerProjekt.customcomponents;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class SemesterChooser extends GridPane {
    private int selectedSemester;
    Button[] buttons;

    Label text;
    public SemesterChooser(){
        super();
        text = new Label("Study");
        add(text,0,1);
    }

    /**
     * creates a Button for each Semestes
     * @param semesterAmount - amount of Semesters
     */
    public void setSemesters(int semesterAmount,String studyName, EventHandler<ActionEvent> eventHandler){
        buttons = new Button[semesterAmount];

        text.setText(studyName);
        for(int i = 1; i<= semesterAmount;i++){
            buttons[i-1] = new Button();
            buttons[i-1].setText(i+"");
            buttons[i-1].setOnAction(eventHandler);
            add(buttons[i-1],0,i+1);
        }
     }
}
