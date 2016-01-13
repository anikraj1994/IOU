package me.anikraj.iou;

import android.content.Context;
import android.text.format.DateUtils;

import java.util.Date;

public class TimeAgo {
Context context;
    public TimeAgo(Context c){
        this.context=c;
    }
    public String timeagostring(Long d){
        try {
            Date date= new Date(d);
           return DateUtils.getRelativeDateTimeString(context, date.getTime(), DateUtils.MINUTE_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, 0).toString();

        } catch (Exception e) {
            e.printStackTrace();
            return  "erroorroor";
        }
    }
}
