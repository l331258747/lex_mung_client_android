package cn.lex_mung.client_android.mvp.model.entity.expert;

import android.text.TextUtils;

import java.io.Serializable;

import cn.lex_mung.client_android.app.TimeFormat;

public class ExpertPriceTimeEntity implements Serializable {

    /**
     * type : 0
     * start : 2019-08-08 19:15:10
     * end : 2019-08-09 19:15:10
     */

    private int type;
    private String start;
    private String end;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getTimeStr() {
        if (type == 0) {
            return "未来\n24小时均可";
        }

        long lcurrent = TimeFormat.strToLong(TimeFormat.getCurrentTime(),TimeFormat.s1);

        long lstart = TimeFormat.strToLong(start,TimeFormat.s1);
        long lend = TimeFormat.strToLong(end,TimeFormat.s1);

        if(lstart == 0 || lend == 0)
            return "----";

        if(TimeFormat.longToStr(lcurrent,"yyyy-MM-dd").equals(TimeFormat.longToStr(lend,"yyyy-MM-dd"))){
            return "今日\n" + TimeFormat.longToStr(lstart, "HH:mm") + " - " + TimeFormat.longToStr(lend, "HH:mm");
        }else{
            return "次日\n" + TimeFormat.longToStr(lstart, "HH:mm") + " - " + TimeFormat.longToStr(lend, "HH:mm");
        }
    }
}