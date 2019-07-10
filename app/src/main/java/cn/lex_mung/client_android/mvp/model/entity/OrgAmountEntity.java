package cn.lex_mung.client_android.mvp.model.entity;

public class OrgAmountEntity {


    /**
     * couponId : 111
     * orgLevelId : 18
     * amount : 2000000
     */

    private int couponId;
    private int orgLevelId;
    private int amount;

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public int getOrgLevelId() {
        return orgLevelId;
    }

    public void setOrgLevelId(int orgLevelId) {
        this.orgLevelId = orgLevelId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
