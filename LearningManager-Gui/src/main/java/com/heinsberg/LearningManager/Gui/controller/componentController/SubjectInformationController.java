package com.heinsberg.LearningManager.Gui.controller.componentController;

import com.heinsberg.LearningManager.Gui.ContentManager;
import com.heinsberg.LearningManager.Gui.view.ViewFactory;
import com.heinsberg.LearningManagerProjekt.BackGround.study.Listeners.ChangeEnums.SubjectChange;
import com.heinsberg.LearningManagerProjekt.BackGround.study.Listeners.SubjectListener;
import com.heinsberg.LearningManagerProjekt.BackGround.study.TimeClasses.LearningPhase;
import com.heinsberg.LearningManagerProjekt.BackGround.study.subject.Subject;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class SubjectInformationController extends BaseInformationComponentController implements Initializable {
    @FXML
    private Label titleLabel;
    @FXML
    private Label weekGoalLabel, learnedLabel;
    @FXML
    private TableView<LearningPhase> learningPhaseView;
    @FXML
    private TableColumn<LearningPhase, Date> dateColum;
    @FXML
    private TableColumn<LearningPhase, Long> learnedColum;
    @FXML
    private TableColumn<LearningPhase, Void> actionCoulumn;
    @FXML
    private GridPane learnedInformationPane;
    @FXML
    private AnchorPane mainPane;

    private Subject subject;

    public SubjectInformationController(ContentManager contentManager, ViewFactory viewFactory, String fxmlName) {
        super(contentManager, viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpLearningPhaseTableView();
    }

    @FXML
    void subjectSettingsAction() {
        viewFactory.getDialogViewFactory().showSubjectEditor(subject);
    }

    @Override
    public void upDateInformation(Object subject) throws ClassCastException {
        if (subject.getClass() == Subject.class) {
            this.subject = (Subject) subject; //updates the Subject
            this.titleLabel.setText(this.subject.getSubjectName());//updates Title
            setUpLearnedInformarmation();
            setWeekGoalLabel();
            if (((Subject) subject).gradeIsSet())
                setUpLearned(); //just needs to be setUp when grade is not set yet
            ObservableList<LearningPhase> learningPhases = ((Subject) subject).getLearningPhases();
            learningPhaseView.setItems(learningPhases);
            ((Subject) subject).addListener(new SubjectListener() {//when Something changes in Subject the information gets Updated
                @Override
                public void changed(SubjectChange subjectChange) {
                    Platform.runLater(() -> {//lets Actions in Lambda Expression run on javaFx Thread
                        upDateInformation(subject);
                    });
                }
            });
            learningPhases.addListener(new ListChangeListener<LearningPhase>() {
                @Override
                public void onChanged(Change<? extends LearningPhase> change) {
                    while (change.next()) {
                        if (change.wasAdded()) {
                            learningPhaseView.getSortOrder().clear();
                            learningPhaseView.getSortOrder().add(dateColum);
                            learningPhaseView.sort();
                        }
                    }
                }
            });

        } else {
            throw new ClassCastException("Object must be from type Subject");
        }
    }

    private void setUpLearnedInformarmation() {
        if (subject.gradeIsSet()) { // if grade is set it's not necessery to show learned Information
            learnedInformationPane.setVisible(false);
            learnedInformationPane.setManaged(false);
            //adjust Anchopane
            mainPane.setTopAnchor(learningPhaseView,64.0);
        }else{
            //adjust Anchopane
            mainPane.setTopAnchor(learningPhaseView,114.0);
            learnedInformationPane.setVisible(true);
            learnedInformationPane.setManaged(true);
            setUpLearned();
            setWeekGoalLabel();
        }
    }


    private void setUpLearningPhaseTableView() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd:MM:yyyy 'um' HH:mm 'Uhr'");
        dateColum.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        //set Up dateColumn
        dateColum.setCellFactory(column -> new TextFieldTableCell<LearningPhase, Date>() {//is called to format the cell
            @Override
            public void updateItem(Date date, boolean empty) {//when item is updated this method is called
                super.updateItem(date, empty);
                if (date == null || empty) {
                    setText(null);
                } else {
                    setText(dateFormat.format(date));
                }
            }
        });
        dateColum.setSortType(TableColumn.SortType.DESCENDING);

        //set up Learned Column
        learnedColum.setCellValueFactory(new PropertyValueFactory<>("timeLearned"));
        learnedColum.setCellFactory(column -> new TableCell<LearningPhase, Long>() { //Formats the showing of time learned to "... Minuten"
            @Override
            protected void updateItem(Long learned, boolean empty) {
                super.updateItem(learned, empty);
                if (learned == null || empty) {
                    setText(null);
                } else {
                    learned /= 60;//time learned in Minutes
                    setText(learned + " Minuten");
                }
            }
        });
        learnedColum.setSortable(false);
        //set up action Column
        Callback<TableColumn<LearningPhase, Void>, TableCell<LearningPhase, Void>> cellFactory = new Callback<TableColumn<LearningPhase, Void>, TableCell<LearningPhase, Void>>() { // new Cell Factory that is called when a new Cell needs to be created
            @Override
            public TableCell<LearningPhase, Void> call(TableColumn<LearningPhase, Void> learningPhaseVoidTableColumn) {//is called to create a Table cell for the column learningPhaseVoidTableColumn
                final TableCell<LearningPhase, Void> outPut = new TableCell<LearningPhase, Void>() {
                    private final Button deleteButton = new Button("Delete");

                    {//is called when instance of class is created ( everytime a new cell is "Renderd
                        deleteButton.setOnAction(e -> {
                            LearningPhase data = getTableView().getItems().get(getIndex());
                            viewFactory.getDialogViewFactory().showDeleteLearningPhaseDialog(data);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(deleteButton);
                        }
                    }
                };
                return outPut;
            }
        };

        actionCoulumn.setCellFactory(cellFactory);
        actionCoulumn.setSortable(false);


    }


    private void setWeekGoalLabel() {
        int weekGoal = ((Subject) subject).getWeekGoal();
        if (weekGoal > 0)//when weekGoal is set show goal
        {
            int[] time = getInHoursAndMinutes(subject.getWeekGoal());
            weekGoalLabel.setText(time[0] + " Stunden und " + time[1] + " Minuten ");
            weekGoalLabel.setStyle("-fx-background-color: transparent;");

        } else {
            weekGoalLabel.setStyle("-fx-background-color: red;");
            weekGoalLabel.setText("Es wurde noch kein Wochen Ziel Festgelegt");
        }

    }

    private void setUpLearned() {
        int learned = contentManager.getStudy().getLearnedInCurrentWeek(subject);
        if (learned < 0) {
            learnedLabel.setText("Dieses Fach ist nicht im aktuellen Semster");
            weekGoalLabel.setStyle("-fx-background-color: red;");
        } else {
            if (learned == 0) { //when it was not learned for subject yer
                learnedLabel.setText("Diese Woche wurde noch nicht für " + subject.getSubjectName() + " gelernt");
            } else {
                int[] learnedFormated = getInHoursAndMinutes(learned);
                if (learnedFormated[0] == 0) {
                    learnedLabel.setText("Diese woche wurde " + learnedFormated[1] + " Minuten für " + subject.getSubjectName() + " gelernt");
                }
                learnedLabel.setText("Diese woche wurde " + learnedFormated[0] + " Stunden und" + learnedFormated[1] + " Minuten für " + subject.getSubjectName() + " gelernt");
            }
            learnedLabel.setStyle("-fx-background-color: transparent;");
        }
    }

    /**
     * caluculates a time from Minutes to hours and mintes
     *
     * @param minutes - Time in Minutets
     * @return - [0] hours [1] Minutes
     */
    private int[] getInHoursAndMinutes(int minutes) {
        return new int[]{minutes / 60, minutes % 60};
    }


}
