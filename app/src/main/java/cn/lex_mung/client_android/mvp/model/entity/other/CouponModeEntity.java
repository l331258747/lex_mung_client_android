package cn.lex_mung.client_android.mvp.model.entity.other;

public class CouponModeEntity {
    int type;
    int couponId;
    int orgId;
    int orgLevId;

    public int getOrgLevId() {
        return orgLevId;
    }

    public void setOrgLevId(int orgLevId) {
        this.orgLevId = orgLevId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }
}
