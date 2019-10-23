package cn.lex_mung.client_android.mvp.model.entity.payEquity;

public class LegalAdviserOrderConfirmChildEntity {

    /**
     * legalAdviserId : 2
     * typeName : 企业日常经营管理专项法律服务
     * typeAliasName : 企业日常|经营管理
     * priceTotal : 2000
     * meetNum : 0
     * unit : 元
     * edition : 2
     */

    private int legalAdviserId;
    private String typeName;
    private String typeAliasName;
    private int priceTotal;
    private int meetNum;
    private String unit;
    private int edition;

    public int getLegalAdviserId() {
        return legalAdviserId;
    }

    public void setLegalAdviserId(int legalAdviserId) {
        this.legalAdviserId = legalAdviserId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeAliasName() {
        return typeAliasName;
    }

    public void setTypeAliasName(String typeAliasName) {
        this.typeAliasName = typeAliasName;
    }

    public int getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(int priceTotal) {
        this.priceTotal = priceTotal;
    }

    public int getMeetNum() {
        return meetNum;
    }

    public void setMeetNum(int meetNum) {
        this.meetNum = meetNum;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getEdition() {
        return edition;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }
}
