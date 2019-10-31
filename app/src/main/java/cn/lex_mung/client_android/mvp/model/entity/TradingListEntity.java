package cn.lex_mung.client_android.mvp.model.entity;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.List;

import cn.lex_mung.client_android.mvp.model.entity.other.QuickTimeBean;

public class TradingListEntity implements Serializable {

    /**
     * memberId : 4
     * orderStatus : 2
     * talkTime : 2:08
     * talkTime1s : 128
     * talkTime1m : 2
     * talkTime1h : 0
     * orderNo : 31354315
     * payStatus : 已完成
     * orderType : 专家咨询
     * payType : 1
     * budget : 收入
     * withdrawAccount :
     * statusValue :
     * typeName :
     * content :
     * memberName : 佩
     * lmemberName : 盛浩
     * beginTime : 2018-12-17 20:18:48
     * endTime : 2018-12-17 20:20:56
     * createDate : 2018-12-19 17:29:17
     * buyerPayAmount : 300
     * pageNum : 0
     * pageSize : 0
     */

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
    private String beginTime;
    private String endTime;
    private String createDate;
    private double buyerPayAmount;
    private int pageNum;
    private int pageSize;
    private String typeAliasName;

    public String getTypeAliasName() {
        if(!orderType.equals("在线法律顾问"))
            return "";
        return typeAliasName;
    }

    private String callTimeStr;
    private int callTime;
    private List<QuickTimeBean> quickTime;

    public String getCallTimeStr() {
        return callTimeStr;
    }

    public int getCallTime() {
        return callTime;
    }

    public List<QuickTimeBean> getQuickTime() {
        return quickTime;
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

    public String getOrderType2() {
        if(orderType.equals("在线法律顾问") && !TextUtils.isEmpty(typeAliasName)){
            return orderType + "\n" + typeAliasName;
        }
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

    public void setLmemberName(String lmemberName) {
        this.lmemberName = lmemberName;
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
