package com.heinsberg.LearningManager.Gui.Model;

import java.util.Date;

public class TimeModel {
    private Date date;
    public TimeModel(Date date) {
        this.date = date;
    }

    /**
     * takes time in Seconds and calculates Hours and Minutes
     * @param time time in seconds
     */
    public TimeModel(long time){
        date = new Date(0,0,0,(int)time/60/60,(int)time/60);
    }

    @Override
    public String toString(){
        return date.getHours()+":"+ date.getMinutes();
    }

}
