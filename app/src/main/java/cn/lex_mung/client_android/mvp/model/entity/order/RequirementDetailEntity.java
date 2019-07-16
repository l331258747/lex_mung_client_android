package cn.lex_mung.client_android.mvp.model.entity.order;

import java.util.List;

import me.zl.mvp.utils.StringUtils;

public class RequirementDetailEntity {

    int id;
    String orderNo;
    String payOrderNo;
    int memberId;
    int lawyerMemberId;
    String grabTime;
    int version;
    int source;
    String regionName;
    int typeId;
    int orderStatus;
    String payType;
    float payAmount;
    int hasPay;
    String payFailReason;
    String orderComment;
    String phone;
    String bindPhone;
    String bindId;
    int status;
    int createUserId;
    String createDate;
    int updateUserId;
    String updateDate;
    int isDefaultEvaluation;
    int really;
    int pageNum;
    int pageSize;
    String orderSort;
    int followCount;

    int talkTime1s;
    int talkTime1m;
    int talkTime1h;

    String talkTime;
    String memberName;
    String mobile;
    String typeName;
    String categoryName;
    String type;
    String hiddenNo;
    String recordUrl;
    String lmemberName;
    String lmobile;
    String startTime;
    String endTime;

    boolean isBlack;
    int remainingTime;
    int requireType;
    int isReceipt;
    int requirementId;
    double buyerPayAmount;
    int couponType;
    String couponName;

    int useCoupon;
    double couponDeductionAmount;
    String description;
    String rname;
    String iconImage;
    String institutionName;

    double userPayAmount;

    public double getUserPayAmount() {
        return userPayAmount;
    }

    public String getUserPayAmountStr(){
        return "¥ "+StringUtils.getStringNum(userPayAmount) + "元";
    }

    public String getIconImage() {
        return iconImage;
    }

    public String getLMemeberName2(){
        return rname + " | " + institutionName;
    }

    public String getDescription() {
        return description;
    }

    public double getCouponDeductionAmount() {
        return couponDeductionAmount;
    }

    public String getCouponNameStr() {//优惠券类型,1.会员卡，2.电子优惠券，3.线下优惠券，4.体验券，目前只做1和2
        return couponName;
    }

    public int getUseCoupon() {
        return useCoupon;
    }

    public String getCouponDeductionAmountStr() {
        return "¥ "+StringUtils.getStringNum(couponDeductionAmount) + "元";
    }

    public double getBuyerPayAmount() {
        return buyerPayAmount;
    }

    public String getBuyerPayAmountStr() {
        return "¥ "+StringUtils.getStringNum(buyerPayAmount) + "元";
    }

    public String getLmobile() {
        return lmobile;
    }

    public int getId() {
        return id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public String getPayOrderNo() {
        return payOrderNo;
    }

    public int getMemberId() {
        return memberId;
    }

    public int getLawyerMemberId() {
        return lawyerMemberId;
    }

    public String getGrabTime() {
        return grabTime;
    }

    public int getVersion() {
        return version;
    }

    public int getSource() {
        return source;
    }

    public String getRegionName() {
        return regionName;
    }

    public int getTypeId() {
        return typeId;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public String getOrderStatusStr(){
        if(orderStatus == 1){
            return "有效";
        }else{
            return "无效";
        }
    }

    public String getIsReceiptStr(){//需求订单状态（0未接单1已接单2已拒单3已关闭
        if(isReceipt == 0){
            return "未接单";
        }else if(isReceipt == 1){
            return "已接单";
        }else if(isReceipt == 2){
            return "已拒单";
        }else if(isReceipt == 3){
            return "已关闭";
        }else{
            return "";
        }
    }


    public String getPayType() {
        return payType;
    }

    public String getPayTypeStr() {//1.微信，2支付宝，3余额，4会员卡，5百度）
        switch (payType) {
            case "1":
                return "微信支付";
            case "2":
                return "支付宝支付";
            case "3":
                return "余额支付";
            case "4":
                return "会员卡支付";
            case "5":
                return "百度支付";
            case "6":
                return "集团卡支付";
        }
        return payType + "支付";
    }

    public float getPayAmount() {
        return payAmount;
    }

    public String getPayAmountStr(){
        return "¥ "+payAmount + "元";
    }

    public int getHasPay() {
        return hasPay;
    }

    public String getPayFailReason() {
        return payFailReason;
    }

    public String getOrderComment() {
        return orderComment;
    }

    public String getPhone() {
        return phone;
    }

    public String getBindPhone() {
        return bindPhone;
    }

    public String getBindId() {
        return bindId;
    }

    public int getStatus() {
        return status;
    }

    public int getCreateUserId() {
        return createUserId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public int getUpdateUserId() {
        return updateUserId;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public int getIsDefaultEvaluation() {
        return isDefaultEvaluation;
    }

    public int getReally() {
        return really;
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public String getOrderSort() {
        return orderSort;
    }

    public int getFollowCount() {
        return followCount;
    }

    public int getTalkTime1s() {
        return talkTime1s;
    }

    public int getTalkTime1m() {
        return talkTime1m;
    }

    public int getTalkTime1h() {
        return talkTime1h;
    }

    public String getTalkTime() {
        return talkTime;
    }

    public String getMemberName() {
        return memberName;
    }

    public String getMobile() {
        return mobile;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getType() {
        return type;
    }

    public String getHiddenNo() {
        return hiddenNo;
    }

    public String getRecordUrl() {
        return recordUrl;
    }

    public String getLmemberName() {
        return lmemberName;
    }

    public String getLmemberNameStr() {
        return lmemberName + "律师";
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public boolean isBlack() {
        return isBlack;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public int getRequireType() {
        return requireType;
    }

    public int getIsReceipt() {
        return isReceipt;
    }

    public int getRequirementId() {
        return requirementId;
    }

    public String getStatusValue() {
        if(status == -1)
            return "支付未成功";
        if(status == 1 && isReceipt == 0)
            return "待接单";
        if(isReceipt == 1)
            return "已接单";
        if(isReceipt == 1)
            return "已拒单";
        if(isReceipt == 3)
            return "已完成";
        return "";
    }
}
