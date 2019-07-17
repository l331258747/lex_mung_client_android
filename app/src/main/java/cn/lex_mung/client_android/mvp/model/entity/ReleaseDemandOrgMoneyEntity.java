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
    private float amountNew;
    private int type;
    private int orgStatus;
    private int pageNum;
    private int pageSize;
    private int consumeMoney;
    private int amountShip;
    private int useForOrgLevelIid;
    /**
     * mobile :
     * couponName : 商品券满100减80第二版
     * couponType : 0
     * scopeOfUse : 0
     * preferentialWay : 2
     * fullNum : 100
     * reduceNum : 80
     * preferentialContent : 商品券满100减80第二版内容
     * preferentialDiscount : 0
     * deviceId : 0
     * startTime : 2019-07-08 00:00:00
     * endTime : 2019-07-20 00:00:00
     * couponStatus : 1
     * productId : 0
     * orderAmount : 1000.0
     * payment : 920.0
     * deductionAmount : 80.0
     * couponDesc :
     * useTimeType : 0
     * useTimeNum : 0
     * availableNumber : 0
     * existingQuantity : 0
     * oneQuantity : 0
     * linkId : 0
     * imageId : 0
     * linkName :
     * url :
     * imageName :
     */

    private String mobile;
    private String couponName;
    private int couponType;
    private int scopeOfUse;
    private int preferentialWay;
    private int fullNum;
    private int reduceNum;
    private String preferentialContent;
    private int preferentialDiscount;
    private int deviceId;
    private String startTime;
    private String endTime;
    private int couponStatus;
    private int productId;
    private float orderAmount;
    private float payment;
    private float deductionAmount;
    private String couponDesc;
    private int useTimeType;
    private int useTimeNum;
    private int availableNumber;
    private int existingQuantity;
    private int oneQuantity;
    private int linkId;
    private int imageId;
    private String linkName;
    private String url;
    private String imageName;

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public float getAmountNew() {
        return amountNew;
    }

    public void setAmountNew(float amountNew) {
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public int getCouponType() {
        return couponType;
    }

    public void setCouponType(int couponType) {
        this.couponType = couponType;
    }

    public int getScopeOfUse() {
        return scopeOfUse;
    }

    public void setScopeOfUse(int scopeOfUse) {
        this.scopeOfUse = scopeOfUse;
    }

    public int getPreferentialWay() {
        return preferentialWay;
    }

    public void setPreferentialWay(int preferentialWay) {
        this.preferentialWay = preferentialWay;
    }

    public int getFullNum() {
        return fullNum;
    }

    public void setFullNum(int fullNum) {
        this.fullNum = fullNum;
    }

    public int getReduceNum() {
        return reduceNum;
    }

    public void setReduceNum(int reduceNum) {
        this.reduceNum = reduceNum;
    }

    public String getPreferentialContent() {
        return preferentialContent;
    }

    public void setPreferentialContent(String preferentialContent) {
        this.preferentialContent = preferentialContent;
    }

    public int getPreferentialDiscount() {
        return preferentialDiscount;
    }

    public void setPreferentialDiscount(int preferentialDiscount) {
        this.preferentialDiscount = preferentialDiscount;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getCouponStatus() {
        return couponStatus;
    }

    public void setCouponStatus(int couponStatus) {
        this.couponStatus = couponStatus;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public float getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(float orderAmount) {
        this.orderAmount = orderAmount;
    }

    public float getPayment() {
        return payment;
    }

    public void setPayment(float payment) {
        this.payment = payment;
    }

    public float getDeductionAmount() {
        return deductionAmount;
    }

    public void setDeductionAmount(float deductionAmount) {
        this.deductionAmount = deductionAmount;
    }

    public String getCouponDesc() {
        return couponDesc;
    }

    public void setCouponDesc(String couponDesc) {
        this.couponDesc = couponDesc;
    }

    public int getUseTimeType() {
        return useTimeType;
    }

    public void setUseTimeType(int useTimeType) {
        this.useTimeType = useTimeType;
    }

    public int getUseTimeNum() {
        return useTimeNum;
    }

    public void setUseTimeNum(int useTimeNum) {
        this.useTimeNum = useTimeNum;
    }

    public int getAvailableNumber() {
        return availableNumber;
    }

    public void setAvailableNumber(int availableNumber) {
        this.availableNumber = availableNumber;
    }

    public int getExistingQuantity() {
        return existingQuantity;
    }

    public void setExistingQuantity(int existingQuantity) {
        this.existingQuantity = existingQuantity;
    }

    public int getOneQuantity() {
        return oneQuantity;
    }

    public void setOneQuantity(int oneQuantity) {
        this.oneQuantity = oneQuantity;
    }

    public int getLinkId() {
        return linkId;
    }

    public void setLinkId(int linkId) {
        this.linkId = linkId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
