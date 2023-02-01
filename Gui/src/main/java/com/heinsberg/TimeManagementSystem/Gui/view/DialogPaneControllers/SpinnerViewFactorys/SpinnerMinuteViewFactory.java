package com.heinsberg.TimeManagementSystem.Gui.view.DialogPaneControllers.SpinnerViewFactorys;

import javafx.scene.control.SpinnerValueFactory;

/**
 * Class to controll Minute Spinners
 * when a Spinner HourFactory is set the the Spinner contected to this Class only goes to 59 minutes
 * and after it surpasses 59 minutes it starts by 0 and ads one hour to the hourFactory
 */
public class SpinnerMinuteViewFactory extends SpinnerValueFactory.IntegerSpinnerValueFactory {

    SpinnerValueFactory.IntegerSpinnerValueFactory spinnerHourViewFactory;

    public SpinnerMinuteViewFactory() {
        super(0, Integer.MAX_VALUE);
    }

    public SpinnerMinuteViewFactory(SpinnerValueFactory.IntegerSpinnerValueFactory spinnerHourFactory) {
        super(0, 59);
        this.spinnerHourViewFactory = spinnerHourFactory;
    }

    @Override
    public void decrement(int i) {//when decrementing and under 0 start by 60
        if (spinnerHourViewFactory != null) {
            if (getValue() - i < 0) {
                spinnerHourViewFactory.setValue(spinnerHourViewFactory.getValue() - 1);
                setValue(59);
            } else
                setValue(getValue() - i);
        } else {
            super.decrement(i);
        }
    }

    @Override
    public void increment(int i) { // when incrementing and over 60 start by 0
        if (spinnerHourViewFactory != null) {
            if (getValue() + i > 59) {
                spinnerHourViewFactory.setValue(spinnerHourViewFactory.getValue() + 1);
                setValue(0);
            } else
                setValue(getValue() + i);
        } else {
            super.increment(i);
        }
    }
}
