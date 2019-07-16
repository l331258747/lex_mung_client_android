package cn.lex_mung.client_android.mvp.model.entity;

import com.youth.banner.Banner;

import java.util.List;

import me.zl.mvp.utils.StringUtils;

public class CouponsEntity {

    private int couponId;
    private String couponName;
    private String scopeOfUse;
    private String image;
    private String scopeOfUseName;
    private String couponType;
    private String couponTypeName;
    private String effectiveTimeStart;//有效期起始
    private String effectiveTimeEnd; //优惠券有效期终止
    private String showExactTime;
    private String preferentialWay;
    private String amount;//会员卡类型额度
    private String fullNum; //优惠-满多少
    private String reduceNum;//优惠-减多少
    private String preferentialDiscount;
    private String preferentialContent;
    private String useForOrgId;
    private String useForOrgLevelId;
    private String canOverlayUse;
    private String status;
    private String couponDesc;
    private double balance;
    private String consumeMoney;
    private int organizationId;
    private int organizationLevelNameId;
    private List<CouponsChildEntity> couponBalanceList;

    public List<CouponsChildEntity> getCouponBalanceList() {
        return couponBalanceList;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public int getOrganizationLevelNameId() {
        return organizationLevelNameId;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getScopeOfUse() {
        return scopeOfUse;
    }

    public void setScopeOfUse(String scopeOfUse) {
        this.scopeOfUse = scopeOfUse;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getScopeOfUseName() {
        return scopeOfUseName;
    }

    public void setScopeOfUseName(String scopeOfUseName) {
        this.scopeOfUseName = scopeOfUseName;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getCouponTypeName() {
        return couponTypeName;
    }

    public void setCouponTypeName(String couponTypeName) {
        this.couponTypeName = couponTypeName;
    }

    public String getEffectiveTimeStart() {
        return effectiveTimeStart;
    }

    public void setEffectiveTimeStart(String effectiveTimeStart) {
        this.effectiveTimeStart = effectiveTimeStart;
    }

    public String getEffectiveTimeEnd() {
        return effectiveTimeEnd;
    }

    public void setEffectiveTimeEnd(String effectiveTimeEnd) {
        this.effectiveTimeEnd = effectiveTimeEnd;
    }

    public String getShowExactTime() {
        return showExactTime;
    }

    public void setShowExactTime(String showExactTime) {
        this.showExactTime = showExactTime;
    }

    public String getPreferentialWay() {
        return preferentialWay;
    }

    public void setPreferentialWay(String preferentialWay) {
        this.preferentialWay = preferentialWay;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFullNum() {
        return fullNum;
    }

    public void setFullNum(String fullNum) {
        this.fullNum = fullNum;
    }

    public String getReduceNum() {
        return reduceNum;
    }

    public void setReduceNum(String reduceNum) {
        this.reduceNum = reduceNum;
    }

    public String getPreferentialDiscount() {
        return preferentialDiscount;
    }

    public void setPreferentialDiscount(String preferentialDiscount) {
        this.preferentialDiscount = preferentialDiscount;
    }

    public String getPreferentialContent() {
        return preferentialContent;
    }

    public void setPreferentialContent(String preferentialContent) {
        this.preferentialContent = preferentialContent;
    }

    public String getUseForOrgId() {
        return useForOrgId;
    }

    public void setUseForOrgId(String useForOrgId) {
        this.useForOrgId = useForOrgId;
    }

    public String getUseForOrgLevelId() {
        return useForOrgLevelId;
    }

    public void setUseForOrgLevelId(String useForOrgLevelId) {
        this.useForOrgLevelId = useForOrgLevelId;
    }

    public String getCanOverlayUse() {
        return canOverlayUse;
    }

    public void setCanOverlayUse(String canOverlayUse) {
        this.canOverlayUse = canOverlayUse;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCouponDesc() {
        return couponDesc;
    }

    public void setCouponDesc(String couponDesc) {
        this.couponDesc = couponDesc;
    }

    public double getBalance() {
        return balance;
    }

    public String getBalanceStr(){
        if(balance > 0)
            return "余额：" + StringUtils.getStringNum(balance);
        else
            return "";
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getConsumeMoney() {
        return consumeMoney;
    }

    public void setConsumeMoney(String consumeMoney) {
        this.consumeMoney = consumeMoney;
    }

    public class CouponsChildEntity{
        int couponId;
        int couponType;//1.会员卡，2.电子优惠券，3.线下优惠券，4.体验券，5.集团卡
        String couponTypeName;
        String couponName;
        double balance;

        public int getCouponId() {
            return couponId;
        }

        public int getCouponType() {
            return couponType;
        }

        public String getCouponTypeName() {
            return couponTypeName;
        }

        public String getCouponName() {
            return couponName;
        }

        public double getBalance() {
            return balance;
        }

        public String getBalanceStr(){
            if(balance <=0) return "";
            return couponTypeName+"余额：" + StringUtils.getStringNum(balance);
        }
    }
}
