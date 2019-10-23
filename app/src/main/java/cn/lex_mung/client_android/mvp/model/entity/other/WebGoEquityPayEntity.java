package cn.lex_mung.client_android.mvp.model.entity.other;

import java.util.List;

public class WebGoEquityPayEntity {

    private int requireTypeId;
    private List<String> legalAdviserIds;
    private float priceTotal;
    private int meetNum;

    public int getRequireTypeId() {
        return requireTypeId;
    }

    public void setRequireTypeId(int requireTypeId) {
        this.requireTypeId = requireTypeId;
    }

    public List<String> getLegalAdviserIds() {
        return legalAdviserIds;
    }

    public void setLegalAdviserIds(List<String> legalAdviserIds) {
        this.legalAdviserIds = legalAdviserIds;
    }

    public float getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(float priceTotal) {
        this.priceTotal = priceTotal;
    }

    public int getMeetNum() {
        return meetNum;
    }

    public void setMeetNum(int meetNum) {
        this.meetNum = meetNum;
    }

}
