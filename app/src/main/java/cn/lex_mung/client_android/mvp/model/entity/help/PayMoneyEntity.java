package cn.lex_mung.client_android.mvp.model.entity.help;

import java.io.Serializable;

public class PayMoneyEntity implements Serializable {
    /**
     * affordFeeId : 1
     * name : 2万元以内
     * minAmount : 0.0
     * maxAmount : 20000.0
     */

    private int affordFeeId;
    private String name;
    private double minAmount;
    private double maxAmount;

    public int getId() {
        return affordFeeId;
    }


    public String getText() {
        return name;
    }


}
