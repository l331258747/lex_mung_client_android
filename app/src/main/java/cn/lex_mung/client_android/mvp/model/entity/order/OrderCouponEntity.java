package cn.lex_mung.client_android.mvp.model.entity.order;

import android.text.TextUtils;

import java.util.List;

import me.zl.mvp.utils.StringUtils;

public class OrderCouponEntity {

    /**
     * memberId : 0
     * couponId : 11
     * couponName : 券001
     * preferentialWay : 2
     * fullNum : 100
     * reduceNum : 50
     * preferentialContent : 满100减50！快速电话专享！
     * preferentialDiscount : 0
     * startTime : 2019-05-08 00:00:00
     * endTime : 2019-05-15 00:00:00
     * couponStatus : 1
     * pageNum : 0
     * pageSize : 0
     */

    private int memberId;
    private int couponId;
    private String couponName;
    private int preferentialWay;//	优惠方式（2满减3折扣）
    private double fullNum;
    private double reduceNum;
    private String preferentialContent;
    private double preferentialDiscount;
    private String startTime;
    private String endTime;
    private int couponStatus;//券状态（1可使用2不可使用）
    private int pageNum;
    private int pageSize;



    public int getMemberId() {
        return memberId;
    }

    public int getCouponId() {
        return couponId;
    }

    public String getCouponName() {
        return couponName;
    }

    public String getRule() {
        if(preferentialWay == 2){
            return "满" + StringUtils.getStringNum(fullNum) + "减" + StringUtils.getStringNum(reduceNum);
        }else{
            return "满" + StringUtils.getStringNum(fullNum) + "打" + getPreferentialDiscountStr() + "折";
        }
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

    public String getReduceNumStr() {
        return StringUtils.getStringNum(reduceNum);
    }

    public String getPreferentialContent() {
        return preferentialContent;
    }

    public double getPreferentialDiscount() {
        return preferentialDiscount;
    }

    public String getPreferentialDiscountStr(){
        return (preferentialDiscount / 10) + "";
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getTimeStr() {
        if(TextUtils.isEmpty(startTime) || TextUtils.isEmpty(endTime)) return "";
        return "限"+startTime.split(" ")[0] + "至" + endTime.split(" ")[0];
    }

    public int getCouponStatus() {
        return couponStatus;
    }

    public String getCouponStatusStr() {
//            if (couponStatus == 1) {
//                return "立\n即\n使\n用";
//            } else if (couponStatus == 2) {
//                return "不\n可\n用";
//            }
//            return "不\n可\n用";
        return "立\n即\n使\n用";
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public void setPreferentialWay(int preferentialWay) {
        this.preferentialWay = preferentialWay;
    }

    public void setFullNum(double fullNum) {
        this.fullNum = fullNum;
    }

    public void setReduceNum(double reduceNum) {
        this.reduceNum = reduceNum;
    }

    public void setPreferentialContent(String preferentialContent) {
        this.preferentialContent = preferentialContent;
    }

    public void setPreferentialDiscount(double preferentialDiscount) {
        this.preferentialDiscount = preferentialDiscount;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setCouponStatus(int couponStatus) {
        this.couponStatus = couponStatus;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
