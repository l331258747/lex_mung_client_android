package cn.lex_mung.client_android.mvp.model.entity.expert;

import java.io.Serializable;

import cn.lex_mung.client_android.app.TimeFormat;

public class ExpertPriceTime2Entity implements Serializable {
    private int time;
    private String timestr;
    public int getTime() {

        return time;
    }

    public String getTimestr() {
        return timestr;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setTimestr(String timestr) {
        this.timestr = timestr;
    }
}