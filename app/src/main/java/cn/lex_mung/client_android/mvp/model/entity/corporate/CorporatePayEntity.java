package cn.lex_mung.client_android.mvp.model.entity.corporate;

import java.util.List;

public class CorporatePayEntity {

    int corporateServerId;
    String corporateMemberName;
    float amount;
    List<CorporatePayList> productViewList;

    public int getCorporateServerId() {
        return corporateServerId;
    }

    public String getCorporateMemberName() {
        return corporateMemberName;
    }

    public float getAmount() {
        return amount;
    }

    public List<CorporatePayList> getProductViewList() {
        return productViewList;
    }
}
