package cn.lex_mung.client_android.mvp.model.entity;

import java.io.Serializable;

import me.zl.mvp.utils.StringUtils;

public class ExpertPriceEntity implements Serializable {

    /**
     * priceUnit : 小时
     * lawyerPrice : 300
     * balance : 999.62
     * balanceUnit : 元
     * freeTime : 15秒
     * callCenterNo : 073112345678
     */

    private String priceUnit;
    private float lawyerPrice;
    private float balance;
    private String balanceUnit;
    private String freeTime;
    private String callCenterNo;
    private String orgnizationName;
    private float favorablePrice;
    private String favorableTimeLen;
    private String originTimeLen;
    private int minimumDuration;
    private float minimumBalance;
    private float minimumRecharge;
    private String lawyerName;
    private String agreementUrl;

    public String getAgreementUrl() {
        return agreementUrl;
    }

    public String getLawyerName() {
        return lawyerName;
    }

    public String getPriceStr(){
        return StringUtils.getStringNum(lawyerPrice) + balanceUnit + "/" + priceUnit;
    }

    public String getOrgnizationPriceStr(){
        return StringUtils.getStringNum(favorablePrice) + balanceUnit + "/" + priceUnit;
    }

    public void setLawyerName(String lawyerName) {
        this.lawyerName = lawyerName;
    }

    public float getMinimumRecharge() {
        return minimumRecharge;
    }

    public float getMinimumBalance() {
        return minimumBalance;
    }

    public int getMinimumDuration() {
        return minimumDuration;
    }

    public String getOrgnizationName() {
        return orgnizationName;
    }

    public void setOrgnizationName(String orgnizationName) {
        this.orgnizationName = orgnizationName;
    }

    public float getFavorablePrice() {
        return favorablePrice;
    }

    public String getFavorablePriceInt(){
        return ((int) favorablePrice ) + "";
    }

    public String getFavorablePriceStr(){
        return StringUtils.getStringNum(favorablePrice);
    }

    public void setFavorablePrice(float favorablePrice) {
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

    public float getLawyerPrice() {
        return lawyerPrice;
    }

    public String getLawyerPriceStr(){
        return StringUtils.getStringNum(lawyerPrice);
    }

    public void setLawyerPrice(float lawyerPrice) {
        this.lawyerPrice = lawyerPrice;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
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
