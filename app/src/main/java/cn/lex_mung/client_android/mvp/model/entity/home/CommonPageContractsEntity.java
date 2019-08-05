package cn.lex_mung.client_android.mvp.model.entity.home;

import android.text.TextUtils;

import me.zl.mvp.utils.StringUtils;

public class CommonPageContractsEntity {


    /**
     * id : 1
     * content : 民间借贷|起草审查合同
     * url : https://h5-test.lex-mung.com/contractDetail.html?groupId=1
     * amount : 1
     * unit : 份
     */

    private int id;
    private String content;
    private String url;
    private double amount;
    private String unit;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public String getUnitStr(){
        if(TextUtils.isEmpty(unit))
            return "";
        String str = unit.replace("/","");
        return str;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }



    public String getPriceStr() {
        return StringUtils.getStringNum(amount) + "元/" + getUnitStr();
    }
}
