package com.heinsberg.TimeManagementSystem.Gui.controller.componentController.subComponents.charts;

import com.heinsberg.TimeManagementSystem.BackGround.Project.Project;
import com.heinsberg.TimeManagementSystem.BackGround.TimeClasses.Week;
import com.heinsberg.TimeManagementSystem.BackGround.WeekFactory;
import com.heinsberg.TimeManagementSystem.BackGround.abstractClasses.TimeSpentContainer;
import com.heinsberg.TimeManagementSystem.BackGround.study.subject.Subject;
import com.heinsberg.TimeManagementSystem.Gui.ContentManager;
import com.heinsberg.TimeManagementSystem.Gui.controller.componentController.subComponents.SubComponentController;
import com.heinsberg.TimeManagementSystem.Gui.view.ViewFactory;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.util.StringConverter;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;

public class LearnProgressBarChartController extends SubComponentController {
    @FXML
    private DatePicker fromDatePicker, toDatePicker; // The Pickers that decide which weeks should be shown
    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private CategoryAxis categoryAxis;
    private Line weekGoalLine;
    @FXML
    private AnchorPane anchorPane;

    private TimeSpentContainer shownTimeSpentContainer;//The Time SpentContainer for which the information is shown
    private WeekFactory weekFactory;
    private boolean rangeSetInProgramm = false; //indicates wather or not the date pickers were manipulated by the Program (shown other TimeSpentController)

    public LearnProgressBarChartController(ContentManager contentManager, ViewFactory viewFactory) {
        super(contentManager, viewFactory, "/com/heinsberg/TimeManagementSystem/Gui/controller/subComponents/LearnProgressBarChart");
        weekFactory = contentManager.getTimeManagementSystem().getWeekFactory();
        barChart.setAnimated(false);
        setUpDateChooser();
        setUpLine();
        setUpChartListener();
    }

    private void setUpLine() {
        weekGoalLine = new Line();
        Node chartArea = barChart.lookup(".chart-plot-background");
        anchorPane.getChildren().add(weekGoalLine);
    }

    /**
     * when the Bar Chart upDates the Line for the week Goal neeeds to be upDated
     */
    private void setUpChartListener() {
        barChart.boundsInLocalProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> observableValue, Bounds bounds, Bounds t1) {
                Platform.runLater(() ->{
                    if(shownTimeSpentContainer != null)
                    upDateLine();
                });
            }
        });

    }

    /**
     * UpDates the Position of the weekGoalLine to be Displayed at the right height of the Bar Chart
     */
    private void upDateLine() {
        // find chart area Node
        Node chartArea = barChart.lookup(".chart-plot-background");
        Bounds boundsInBarChart = chartArea.getBoundsInParent();
        Bounds boundsInAnchorPane = barChart.localToParent(boundsInBarChart);
        weekGoalLine.setStartX(boundsInAnchorPane.getMinX()+5);
        weekGoalLine.setEndX(boundsInAnchorPane.getMaxX()+5);
        //Calculate the y Position of the Line
        double y = boundsInAnchorPane.getMinY();//lowest position of barChartArae
        y += barChart.getYAxis().getDisplayPosition(shownTimeSpentContainer.getWeekGoal());

        weekGoalLine.setStartY(y+5);
        weekGoalLine.setEndY(y+5);

    }


    /**
     * Sets up the Date Choosers so that only Mondays/Sundays are selectable
     * and end is automatically after start
     */
    private void setUpDateChooser() {
        upDateToDatePicker();
        upDateFromDatePicker();
        setUpDatePickerListener();
    }

    private void setUpDatePickerListener() {
        fromDatePicker.valueProperty().addListener(e ->{
            upDateToDatePicker();
            if(fromDatePicker.getValue() != null && toDatePicker.getValue() != null && !rangeSetInProgramm)
            showWeeks();
        });
        toDatePicker.valueProperty().addListener(e->{
            upDateFromDatePicker();
            if(fromDatePicker.getValue() != null && toDatePicker.getValue() != null&& !rangeSetInProgramm)
                showWeeks();
        });
    }

    /**
     * shows the Weeks that are selected in the Bar Chart
     */
    private void showWeeks() {
        Week[] weeksToBeShown = contentManager.getTimeManagementSystem().getWeekFactory().getWeeks(Date.from(fromDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),Date.from(toDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant())).toArray(new Week[0]);
        showInformation(weeksToBeShown);
    }

    /**
     * Updates the Day Cell Factory of fromDate Picker so that only Mondays before endDate are choosable
     */
    private void upDateFromDatePicker() {
        fromDatePicker.dayCellFactoryProperty().set(datePicker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                if (toDatePicker.getValue() != null) {
                    if (toDatePicker.getValue().isBefore(date))
                        setDisable(true);//start Date would be before end Date
                }
                if(date.getDayOfWeek() != DayOfWeek.MONDAY)
                    setDisable(true);
            }
        });
    }

    private void upDateToDatePicker() {
        toDatePicker.dayCellFactoryProperty().set(datePicker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                if (fromDatePicker.getValue() != null) {
                    if (fromDatePicker.getValue().isAfter(date))
                        setDisable(true);//start Date would be before end Date
                }
                if(date.getDayOfWeek() != DayOfWeek.SUNDAY)
                    setDisable(true);
            }
        });
    }

    /**
     * shows the time learned for timeSpentContainer in every Week of weeks
     *
     * @param weeks
     */
    private void showInformation(Week[] weeks) {
        if(shownTimeSpentContainer != null) {
            barChart.getData().clear();
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            ObservableList<String> categories = FXCollections.observableArrayList();
            categoryAxis.setCategories(categories);
            series.setName(shownTimeSpentContainer.getName());
            for (Week week : weeks) {
                XYChart.Data<String, Number> data = new XYChart.Data<>(week.getWeekNumber()+"", week.getLearnedFor(shownTimeSpentContainer));
                series.getData().add(data);
                categories.add(week.getWeekNumber()+"");
            }
            barChart.getData().add(series);
            if(shownTimeSpentContainer.getWeekGoal() > 0){
                weekGoalLine.setVisible(true);
                upDateLine();

            }else{
                weekGoalLine.setVisible(false);
            }
        }
    }

    public void showInformation(TimeSpentContainer timeSpentContainer){
        this.shownTimeSpentContainer = timeSpentContainer;
        if(timeSpentContainer instanceof Subject){
            rangeSetInProgramm = true;
            toDatePicker.setValue((((Subject)timeSpentContainer).getSemester().getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
            fromDatePicker.setValue(((Subject)timeSpentContainer).getSemester().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }else if(timeSpentContainer instanceof Project){
            rangeSetInProgramm = true;
            LocalDate currentDate = LocalDate.now();
            toDatePicker.setValue(currentDate);
            fromDatePicker.setValue(LocalDate.of(currentDate.getYear(),1,1));
        }

        showWeeks();
        rangeSetInProgramm = false;
    }


    @Override
    public void refresh() {

    }
}
