package cn.lex_mung.client_android.mvp.model.entity;

import me.zl.mvp.utils.StringUtils;

public class ExpertPriceEntity {

    /**
     * priceUnit : 小时
     * lawyerPrice : 300
     * balance : 999.62
     * balanceUnit : 元
     * freeTime : 15秒
     * callCenterNo : 073112345678
     */

    private String priceUnit;
    private double lawyerPrice;
    private double balance;
    private String balanceUnit;
    private String freeTime;
    private String callCenterNo;
    private String orgnizationName;
    private double favorablePrice;
    private String favorableTimeLen;
    private String originTimeLen;

    public String getOrgnizationName() {
        return orgnizationName;
    }

    public void setOrgnizationName(String orgnizationName) {
        this.orgnizationName = orgnizationName;
    }

    public double getFavorablePrice() {
        return favorablePrice;
    }

    public String getFavorablePriceInt(){
        return ((int) favorablePrice ) + "";
    }

    public void setFavorablePrice(double favorablePrice) {
        this.favorablePrice = favorablePrice;
    }

    public String getFavorableTimeLen() {
        return favorableTimeLen;
    }

    public void setFavorableTimeLen(String favorableTimeLen) {
        this.favorableTimeLen = favorableTimeLen;
    }

    public String getOriginTimeLen() {
        return originTimeLen;
    }

    public void setOriginTimeLen(String originTimeLen) {
        this.originTimeLen = originTimeLen;
    }

    public String getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }

    public double getLawyerPrice() {
        return lawyerPrice;
    }

    public String getLawyerPriceStr(){
        return StringUtils.getStringNum(lawyerPrice);
    }

    public void setLawyerPrice(double lawyerPrice) {
        this.lawyerPrice = lawyerPrice;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getBalanceUnit() {
        return balanceUnit;
    }

    public void setBalanceUnit(String balanceUnit) {
        this.balanceUnit = balanceUnit;
    }

    public String getFreeTime() {
        return freeTime;
    }

    public void setFreeTime(String freeTime) {
        this.freeTime = freeTime;
    }

    public String getCallCenterNo() {
        return callCenterNo;
    }

    public void setCallCenterNo(String callCenterNo) {
        this.callCenterNo = callCenterNo;
    }
}
