package cn.lex_mung.client_android.mvp.model.entity.order;

public class QuickPayEntity {


    /**
     * memberId : 0
     * couponId : 11
     * couponName : 券001
     * preferentialWay : 3
     * fullNum : 99
     * reduceNum : 50
     * preferentialContent : 满100减50！快速电话专享！
     * preferentialDiscount : 8
     * startTime : 2019-05-08 00:00:00
     * endTime : 2019-05-15 00:00:00
     * couponStatus : 1
     * pageNum : 0
     * pageSize : 0
     * orderAmount : 99
     * payment : 7.92
     * deductionAmount : 91.08
     */

    private int memberId;
    private int couponId;
    private String couponName;
    private int preferentialWay;
    private double fullNum;
    private double reduceNum;
    private String preferentialContent;
    private double preferentialDiscount;
    private String startTime;
    private String endTime;
    private int couponStatus;
    private int pageNum;
    private int pageSize;
    private double orderAmount;
    private float payment;
    private float deductionAmount;

    public int getMemberId() {
        return memberId;
    }

    public int getCouponId() {
        return couponId;
    }

    public String getCouponName() {
        return couponName;
    }

    public int getPreferentialWay() {
        return preferentialWay;
    }

    public double getFullNum() {
        return fullNum;
    }

    public double getReduceNum() {
        return reduceNum;
    }

    public String getPreferentialContent() {
        return preferentialContent;
    }

    public double getPreferentialDiscount() {
        return preferentialDiscount;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public int getCouponStatus() {
        return couponStatus;
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public float getPayment() {
        return payment;
    }

    public float getDeductionAmount() {
        return deductionAmount;
    }
}
