package cn.lex_mung.client_android.mvp.model.entity.help;

import java.io.Serializable;

public class RequirementInvolveAmountBean implements Serializable {
    /**
     * amountId : 1
     * amountName : 不涉及金额
     */

    private int amountId;
    private String amountName;

    public int getAmountId() {
        return amountId;
    }

    public void setAmountId(int amountId) {
        this.amountId = amountId;
    }

    public String getAmountName() {
        return amountName;
    }

    public void setAmountName(String amountName) {
        this.amountName = amountName;
    }
}