package cn.lex_mung.client_android.mvp.model.entity;

public class PayEntity {

    /**
     * pkg : Sign=WXPay
     * appid : wxe5178124e8a016af
     * sign : F98677AFB96FFDBA5EED96935CF4F91C
     * partnerId : 1502127031
     * prepayId : wx19101556845486bc4c386b9b2764903005
     * nonceStr : 1015556054
     * timestamp : 1545185756
     */

    private String pkg;
    private String appid;
    private String sign;
    private String partnerId;
    private String prepayId;
    private String nonceStr;
    private String timestamp;
    private String orderInfo;
    private String orderno;

    public String getOrderNo() {
        return orderno;
    }

    public void setOrderNo(String orderno) {
        this.orderno = orderno;
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
