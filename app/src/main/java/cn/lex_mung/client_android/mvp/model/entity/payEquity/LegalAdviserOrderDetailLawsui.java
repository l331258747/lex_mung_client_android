package cn.lex_mung.client_android.mvp.model.entity.payEquity;

import java.io.Serializable;

import me.zl.mvp.utils.StringUtils;

public class LegalAdviserOrderDetailLawsui implements Serializable {

    double amount;
    String unit;
    String lawsuiId;
    String describe;
    String title;
    String descLink;

    public String getDescLink() {
        return descLink;
    }

    public double getAmount() {
        return amount;
    }

    public String getAmountStr(){
        return "¥ "+StringUtils.getStringNum(amount);
    }

    public String getUnit() {
        return unit;
    }

    public String getLawsuiId() {
        return lawsuiId;
    }

    public String getDescribe() {
        return describe;
    }

    public String getTitle() {
        return title;
    }
}
