package cn.lex_mung.client_android.mvp.model.entity.payEquity;

import java.util.List;

public class LegalAdviserOrderConfirmEntity {


    /**
     * requireTypeId : 123
     * requireTypeName : 在线法律顾问
     * priceTotal : 2611
     * unit :
     * serverDetails : [{"legalAdviserId":2,"typeName":"企业日常经营管理专项法律服务","typeAliasName":"企业日常|经营管理","priceTotal":2000,"meetNum":0,"unit":"元","edition":2},{"legalAdviserId":5,"typeName":"企业法人股权","typeAliasName":"企业法人|股权","priceTotal":11,"meetNum":0,"unit":"元","edition":2},{"legalAdviserId":0,"typeName":"线下见面","typeAliasName":"线下见面","priceTotal":600,"meetNum":2,"unit":"次","edition":0}]
     */

    private int requireTypeId;
    private String requireTypeName;
    private float priceTotal;
    private String unit;
    private List<LegalAdviserOrderConfirmChildEntity> serverDetails;

    public int getRequireTypeId() {
        return requireTypeId;
    }

    public void setRequireTypeId(int requireTypeId) {
        this.requireTypeId = requireTypeId;
    }

    public String getRequireTypeName() {
        return requireTypeName;
    }

    public void setRequireTypeName(String requireTypeName) {
        this.requireTypeName = requireTypeName;
    }

    public float getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(float priceTotal) {
        this.priceTotal = priceTotal;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }


    public List<LegalAdviserOrderConfirmChildEntity> getServerDetails() {
        return serverDetails;
    }

    public void setServerDetails(List<LegalAdviserOrderConfirmChildEntity> serverDetails) {
        this.serverDetails = serverDetails;
    }
}
