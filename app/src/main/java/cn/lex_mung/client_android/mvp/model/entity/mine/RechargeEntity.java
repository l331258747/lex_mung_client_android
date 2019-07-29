package cn.lex_mung.client_android.mvp.model.entity.mine;

import me.zl.mvp.utils.StringUtils;

public class RechargeEntity {


    /**
     * id : 1
     * amount : 200.0
     * activity : 0
     * activityDescription : 活动描述
     * give : 0
     * giveAmount : 0.0
     * giveCoupon : 0
     * couponPackId : 0
     * isShow : 1
     * createUserId : 0
     * createDate : 2019-07-25 18:47:20
     * status : 1
     * sort : 1
     * startTime :
     * endTime :
     * pageNum : 0
     * pageSize : 0
     * firstname :
     */

    private int id;
    private double amount;
    private int activity;
    private String activityDescription;
    private int give;
    private double giveAmount;
    private int giveCoupon;
    private int couponPackId;
    private int isShow;
    private int createUserId;
    private String createDate;
    private int status;
    private int sort;
    private String startTime;
    private String endTime;
    private int pageNum;
    private int pageSize;
    private String firstname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public String getAmountStr2() {
        return StringUtils.getStringNum(amount) + "元";
    }

    public String getAmountStr(){
        return StringUtils.getStringNum(amount);
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getActivity() {
        return activity;
    }

    public void setActivity(int activity) {
        this.activity = activity;
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }

    public int getGive() {
        return give;
    }

    public void setGive(int give) {
        this.give = give;
    }

    public double getGiveAmount() {
        return giveAmount;
    }

    public String getGiveAmountStr(){
        if(giveAmount <= 0){
            return "";
        }
        return StringUtils.getStringNum(giveAmount) + "元";
    }

    public void setGiveAmount(double giveAmount) {
        this.giveAmount = giveAmount;
    }

    public int getGiveCoupon() {
        return giveCoupon;
    }

    public void setGiveCoupon(int giveCoupon) {
        this.giveCoupon = giveCoupon;
    }

    public int getCouponPackId() {
        return couponPackId;
    }

    public void setCouponPackId(int couponPackId) {
        this.couponPackId = couponPackId;
    }

    public int getIsShow() {
        return isShow;
    }

    public void setIsShow(int isShow) {
        this.isShow = isShow;
    }

    public int getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(int createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
}
