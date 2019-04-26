package cn.lex_mung.client_android.mvp.model.entity.order;

import java.util.List;

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
    int payType;
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


    public int getPayType() {
        return payType;
    }

    public float getPayAmount() {
        return payAmount;
    }

    public String getPayAmountStr(){
        return payAmount + "元";
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
}
