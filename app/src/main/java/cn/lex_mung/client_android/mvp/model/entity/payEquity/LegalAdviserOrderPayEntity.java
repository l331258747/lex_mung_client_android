package cn.lex_mung.client_android.mvp.model.entity.payEquity;

public class LegalAdviserOrderPayEntity {

    /**
     * orderNo : ZJ191022700203659552
     * money : 190000
     * deduction : 20000
     * couponId : 215
     * type : 1
     */

    private String legalAdviserOrderNo;
    private int money;
    private int deduction;
    private int couponId;
    private int type;

    public String getLegalAdviserOrderNo() {
        return legalAdviserOrderNo;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getDeduction() {
        return deduction;
    }

    public void setDeduction(int deduction) {
        this.deduction = deduction;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
