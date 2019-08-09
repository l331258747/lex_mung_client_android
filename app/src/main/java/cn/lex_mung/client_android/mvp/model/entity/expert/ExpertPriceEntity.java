package cn.lex_mung.client_android.mvp.model.entity.expert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.lex_mung.client_android.utils.DecimalUtil;
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
    private double lawyerPrice;
    private double balance;
    private String balanceUnit;
    private String freeTime;
    private String callCenterNo;
    private String orgnizationName;
    private double favorablePrice;
    private String favorableTimeLen;
    private String originTimeLen;
    private int minimumDuration;
    private float minimumBalance;
    private float minimumRecharge;
    private String lawyerName;
    private String agreementUrl;
    private int orderStatus;
    private String city;
    private String icon;
    private int lawyerId;

    private List<ExpertPriceSolutionEntity> solution;
    private List<ExpertPriceTimeEntity> timeSection;
    private List<ExpertPriceTime2Entity> TalkTimes = new ArrayList<>();

    public int getLawyerId() {
        return lawyerId;
    }

    public void setLawyerId(int lawyerId) {
        this.lawyerId = lawyerId;
    }

    public List<ExpertPriceTime2Entity> getTalkTimes() {
        return TalkTimes;
    }

    public List<String> getTalkTimeStrs(){
        List<String> lists = new ArrayList<>();
        for (int i = 0; i< TalkTimes.size(); i++){
            lists.add(TalkTimes.get(i).getTimestr());
        }
        return lists;
    }

    public void setTalkTimes() {
        for (int i = 0; i < 5; i++) {
            ExpertPriceTime2Entity time2 = new ExpertPriceTime2Entity();
            int minute = 0;
            switch (i){
                case 0:
                    minute = minimumDuration;
                    break;
                case 1:
                    minute = (int) (Math.ceil((getMinimumDurationDouble() + 10) / 10) * 10);
                    break;
                case 2:
                    minute = (int) (Math.ceil((getMinimumDurationDouble() + 20) / 10) * 10);
                    break;
                case 3:
                    minute = (int) (Math.ceil((getMinimumDurationDouble() + 30) / 10) * 10);
                    break;
                case 4:
                    minute = (int) (Math.ceil((getMinimumDurationDouble() + 40) / 10) * 10);
                    break;
            }
            time2.setTime(minute);
            time2.setTimestr("冻结" + minute + "分钟咨询费用（" + StringUtils.getStringNum(DecimalUtil.multiply(minute, lawyerPrice)) + "元）");
            TalkTimes.add(time2);
        }
    }

    public List<ExpertPriceSolutionEntity> getSolution() {
        return solution;
    }

    public List<ExpertPriceTimeEntity> getTimeSection() {
        return timeSection;
    }

    public String getIcon() {
        return icon;
    }

    public String getCity() {
        return city;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public String getAgreementUrl() {
        return agreementUrl;
    }

    public String getLawyerName() {
        return lawyerName;
    }

    public String getPriceStr() {
        return StringUtils.getStringNum(lawyerPrice) + balanceUnit + "/" + priceUnit;
    }

    public String getPriceStr2() {
        String str2 = "%1$s，不足%2$s分钟按%3$s分钟计算，超过%4$s分钟按实际通话分钟数计算。";
        return String.format(str2, getPriceStr(), getMinimumDurationStr(), getMinimumDurationStr(), getMinimumDurationStr());
    }

    public String getOrgnizationPriceStr() {
        return StringUtils.getStringNum(favorablePrice) + balanceUnit + "/" + priceUnit;
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

    public double getMinimumDurationDouble(){
        return Double.valueOf(minimumDuration);
    }

    public String getMinimumDurationStr() {
        return minimumDuration + "";
    }

    public String getOrgnizationName() {
        return orgnizationName;
    }

    public void setOrgnizationName(String orgnizationName) {
        this.orgnizationName = orgnizationName;
    }

    public double getFavorablePrice() {
        return favorablePrice;
    }

    public String getFavorablePriceInt() {
        return ((int) favorablePrice) + "";
    }

    public String getFavorablePriceStr() {
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

    public double getLawyerPrice() {
        return lawyerPrice;
    }

    public String getLawyerPriceStr() {
        return StringUtils.getStringNum(lawyerPrice);
    }

    public void setLawyerPrice(float lawyerPrice) {
        this.lawyerPrice = lawyerPrice;
    }

    public double getBalance() {
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

    public void getTimeList() {

    }

}
