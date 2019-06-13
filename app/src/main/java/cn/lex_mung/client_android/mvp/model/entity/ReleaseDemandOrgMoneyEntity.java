package cn.lex_mung.client_android.mvp.model.entity;

public class ReleaseDemandOrgMoneyEntity {

    /**
     * memberId : 0
     * lmemberId : 0
     * organizationId : 16
     * organizationLevId : 18
     * score : 1
     * dateAdded : 2019-02-27 17:06:06
     * organizationName : 湖南省浙江商会
     * organizationLevelName : fdfdfdf
     * image : http://oss.lex-mung.com/organization_image_15511473047369.png
     * coupon1Count : 1
     * requireTypeId : 0
     * amount : 400
     * amountDis : 1600
     */

    private int memberId;
    private int lmemberId;
    private int organizationId;
    private int organizationLevId;
    private int score;
    private int couponId;
    private String dateAdded;
    private String organizationName;
    private String organizationLevelName;
    private String image;
    private int coupon1Count;
    private int requireTypeId;
    private float amount;
    private float amountDis;
    private double amountNew;

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public double getAmountNew() {
        return amountNew;
    }

    public void setAmountNew(double amountNew) {
        this.amountNew = amountNew;
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

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getAmountDis() {
        return amountDis;
    }

    public void setAmountDis(float amountDis) {
        this.amountDis = amountDis;
    }
}
