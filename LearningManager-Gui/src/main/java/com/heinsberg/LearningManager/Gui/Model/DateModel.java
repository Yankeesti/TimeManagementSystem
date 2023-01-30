package com.heinsberg.LearningManager.Gui.Model;

import java.util.Date;

public class DateModel implements Comparable<DateModel>{
    private Date date;
    public DateModel(Date date) {
        this.date = date;
    }

   @Override
   public String toString(){
        return date.getDate()+"."+date.getMonth()+1+"."+date.getYear()+1900;
   }

   @Override
    public int compareTo(DateModel o){
        return date.compareTo(o.getDate());
   }
    public Date getDate() {
        return date;
    }
}
