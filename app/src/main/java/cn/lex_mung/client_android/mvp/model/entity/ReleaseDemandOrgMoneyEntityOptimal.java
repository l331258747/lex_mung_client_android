package cn.lex_mung.client_android.mvp.model.entity;

import android.text.TextUtils;

public class ReleaseDemandOrgMoneyEntityOptimal {


    /**
     * memberId : 0
     * lmemberId : 0
     * couponId : 0
     * organizationId : 16
     * organizationLevId : 18
     * score : 1
     * dateAdded : 2019-02-20 10:56:59
     * organizationName : 湖南省浙江商会
     * organizationLevelName : 湖南省浙江商会vip
     * image : http://oss.lex-mung.com/organization_image_15507372415359.png
     * coupon1Count : 0
     * requireTypeId : 0
     * type : 0
     * orgStatus : 1
     * pageNum : 0
     * pageSize : 0
     * amount : 0
     * amountDis : 0
     * consumeMoney : 0
     * amountShip : 0
     * useForOrgLevelIid : 0
     * amountNew : 0
     */

    private int memberId;
    private int lmemberId;
    private int couponId;
    private int organizationId;
    private int organizationLevId;
    private int score;
    private String dateAdded;
    private String organizationName;
    private String organizationLevelName;
    private String image;
    private int coupon1Count;
    private int requireTypeId;
    private int type;
    private int orgStatus;
    private int pageNum;
    private int pageSize;
    private int amount;
    private int amountDis;
    private int consumeMoney;
    private int amountShip;
    private int useForOrgLevelIid;
    private int amountNew;
    private String exclusiveRights;

    public String getExclusiveRights() {
        if(TextUtils.isEmpty(exclusiveRights))
            return "无";
        return exclusiveRights;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getLmemberId() {
        return lmemberId;
    }

    public void setLmemberId(int lmemberId) {
        this.lmemberId = lmemberId;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public int getOrganizationLevId() {
        return organizationLevId;
    }

    public void setOrganizationLevId(int organizationLevId) {
        this.organizationLevId = organizationLevId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationLevelName() {
        return organizationLevelName;
    }

    public void setOrganizationLevelName(String organizationLevelName) {
        this.organizationLevelName = organizationLevelName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCoupon1Count() {
        return coupon1Count;
    }

    public void setCoupon1Count(int coupon1Count) {
        this.coupon1Count = coupon1Count;
    }

    public int getRequireTypeId() {
        return requireTypeId;
    }

    public void setRequireTypeId(int requireTypeId) {
        this.requireTypeId = requireTypeId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getOrgStatus() {
        return orgStatus;
    }

    public void setOrgStatus(int orgStatus) {
        this.orgStatus = orgStatus;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmountDis() {
        return amountDis;
    }

    public void setAmountDis(int amountDis) {
        this.amountDis = amountDis;
    }

    public int getConsumeMoney() {
        return consumeMoney;
    }

    public void setConsumeMoney(int consumeMoney) {
        this.consumeMoney = consumeMoney;
    }

    public int getAmountShip() {
        return amountShip;
    }

    public void setAmountShip(int amountShip) {
        this.amountShip = amountShip;
    }

    public int getUseForOrgLevelIid() {
        return useForOrgLevelIid;
    }

    public void setUseForOrgLevelIid(int useForOrgLevelIid) {
        this.useForOrgLevelIid = useForOrgLevelIid;
    }

    public int getAmountNew() {
        return amountNew;
    }

    public void setAmountNew(int amountNew) {
        this.amountNew = amountNew;
    }
}
