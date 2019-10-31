package cn.lex_mung.client_android.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

import me.zl.mvp.utils.StringUtils;

public class OrderEntity implements Serializable {

    /**
     * id : 2098
     * memberId : 5006
     * orderStatus : 0
     * talkTime :
     * talkTime1s : 0
     * talkTime1m : 0
     * talkTime1h : 0
     * orderNo : 201812261159405006
     * payStatus :
     * orderType : 需求
     * payType :
     * budget :
     * withdrawAccount :
     * statusValue : 未接单
     * typeName : 诉讼/仲裁
     * content : 123
     * memberName :
     * lmemberName : 汤坤
     * iconImage : http://oss.lex-mung.com/icon_image_member_f825a93a7.jpg
     * lawyerMemberId : 55
     * beginTime : 1970-01-01 08:00:00
     * endTime : 1970-01-01 08:00:00
     * createDate : 2018-12-26 11:59:40
     * buyerPayAmount : 10000
     * pageNum : 0
     * pageSize : 0
     */

    private int id;
    private int memberId;
    private int orderStatus;
    private String talkTime;
    private int talkTime1s;
    private int talkTime1m;
    private int talkTime1h;
    private String orderNo;
    private String payStatus;
    private String orderType;
    private String payType;
    private String budget;
    private String withdrawAccount;
    private String statusValue;
    private String typeName;
    private String content;
    private String memberName;
    private String lmemberName;
    private String iconImage;
    private int lawyerMemberId;
    private String beginTime;
    private String endTime;
    private String createDate;
    private double buyerPayAmount;
    private int pageNum;
    private int pageSize;
    private int typeId;
    private int isHot;
    private int replyCount;
    private int status;
    private String lawSuiId;

    public String getLawSuiId() {
        return lawSuiId;
    }

    public int getStatus() {
        return status;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public String getReplyCountStr() {
        return replyCount+"条回复";
    }

    public int getIsHot() {
        return isHot;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getTalkTime() {
        return talkTime;
    }

    public void setTalkTime(String talkTime) {
        this.talkTime = talkTime;
    }

    public int getTalkTime1s() {
        return talkTime1s;
    }

    public void setTalkTime1s(int talkTime1s) {
        this.talkTime1s = talkTime1s;
    }

    public int getTalkTime1m() {
        return talkTime1m;
    }

    public void setTalkTime1m(int talkTime1m) {
        this.talkTime1m = talkTime1m;
    }

    public int getTalkTime1h() {
        return talkTime1h;
    }

    public void setTalkTime1h(int talkTime1h) {
        this.talkTime1h = talkTime1h;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getWithdrawAccount() {
        return withdrawAccount;
    }

    public void setWithdrawAccount(String withdrawAccount) {
        this.withdrawAccount = withdrawAccount;
    }

    public String getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(String statusValue) {
        this.statusValue = statusValue;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getLmemberName() {
        return lmemberName;
    }

    public String getLmemberNameStr() {
        return lmemberName + "律师";
    }

    public void setLmemberName(String lmemberName) {
        this.lmemberName = lmemberName;
    }

    public String getIconImage() {
        return iconImage;
    }

    public void setIconImage(String iconImage) {
        this.iconImage = iconImage;
    }

    public int getLawyerMemberId() {
        return lawyerMemberId;
    }

    public void setLawyerMemberId(int lawyerMemberId) {
        this.lawyerMemberId = lawyerMemberId;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public double getBuyerPayAmount() {
        return buyerPayAmount;
    }

    public String getBuyerPayAmountStr() {
        return StringUtils.getStringNum(buyerPayAmount);
    }

    public void setBuyerPayAmount(double buyerPayAmount) {
        this.buyerPayAmount = buyerPayAmount;
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
}
