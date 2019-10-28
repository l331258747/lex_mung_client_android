package cn.lex_mung.client_android.mvp.model.entity.other;

import java.io.Serializable;

public class QuickTimeBean implements Serializable {
    /**
     * id : 480
     * beginTime : 2019-03-15 15:03:29
     * endTime : 2019-03-15 15:06:38
     */

    private int id;
    private String beginTime;
    private String endTime;
    private int callLength;

    public String getCalllength() {
        long hours = callLength / 3600;//转换小时数
        callLength = callLength % 3600;//剩余秒数
        long minutes = callLength / 60;//转换分钟
        callLength = callLength % 60;//剩余秒数
        if (hours > 0) {
            return hours + "小时" + minutes + "分" + callLength + "秒";
        }
        if (minutes > 0) {
            return minutes + "分" + callLength + "秒";
        }
        return callLength + "秒";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}