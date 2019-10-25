package cn.lex_mung.client_android.mvp.model.entity.other;

public class WebGoOintmentEntity {


    /**
     * lawsuiId : 125-01
     * title : 诉讼无忧保服务
     * amount : 500
     * orderNo : ZJ191024300867913449
     * legalAdviserId : 6
     * requireTypeId : 125
     */

    private String lawsuiId;
    private String title;
    private float amount;
    private String orderNo;
    private int legalAdviserId;
    private int requireTypeId;

    public String getLawsuiId() {
        return lawsuiId;
    }

    public String getTitle() {
        return title;
    }

    public float getAmount() {
        return amount;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public int getLegalAdviserId() {
        return legalAdviserId;
    }

    public int getRequireTypeId() {
        return requireTypeId;
    }
}
