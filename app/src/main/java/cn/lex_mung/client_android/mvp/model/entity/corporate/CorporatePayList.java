package cn.lex_mung.client_android.mvp.model.entity.corporate;

public class CorporatePayList {
    int corporateProductId;
    int configRestrict;
    int restrictNum;
    int requireTypeId;
    String requireTypeName;
    String unit;

    public int getCorporateProductId() {
        return corporateProductId;
    }

    public int getConfigRestrict() {
        return configRestrict;
    }

    public int getRestrictNum() {
        return restrictNum;
    }

    public int getRequireTypeId() {
        return requireTypeId;
    }

    public String getRequireTypeName() {
        return requireTypeName;
    }

    public String getUnit() {
        return unit;
    }
}
