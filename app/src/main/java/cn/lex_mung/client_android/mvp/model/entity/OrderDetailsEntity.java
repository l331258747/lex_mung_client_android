package cn.lex_mung.client_android.mvp.model.entity;

import android.text.TextUtils;

import java.util.List;

import cn.lex_mung.client_android.app.TimeFormat;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.StringUtils;

public class OrderDetailsEntity {

    /**
     * id : 480
     * memberId : 4691
     * orderStatus : 7
     * talkTime :
     * talkTime1s : 0
     * talkTime1m : 0
     * talkTime1h : 0
     * callTime : 189
     * orderNo : QC19031501661765554
     * payStatus : 2
     * orderType : 快速电话咨询
     * payType :
     * budget :
     * withdrawAccount :
     * statusValue : 已完成
     * typeName : 海外投资
     * typeId : 4
     * content :
     * memberName : 用户我我我
     * lmemberName : 律师我我我
     * iconImage : http://oss.lex-mung.com/icon_image_member_15467318541129.png
     * lawyerMemberId : 5020
     * really : 1
     * beginTime :
     * endTime :
     * createDate : 2019-03-15 10:27:50
     * buyerPayAmount : 0.01
     * pageNum : 0
     * pageSize : 0
     * remainingTime : 0
     * grabTime : 2019-03-15 10:29:06
     * regionId : 0
     * rname :
     * couponName :
     * scopeOfUse : 0
     * couponType : 0
     * couponDeductionAmount : 0
     * useCoupon : 0
     * payStatusValue : 支付成功
     * couponId : 0
     * payAmount : -0.01
     * orderAmount :
     * quickTime : [{"id":480,"beginTime":"2019-03-15 15:03:29","endTime":"2019-03-15 15:06:38"}]
     */

    private int id;
    private int memberId;
    private int orderStatus;
    private String talkTime;
    private int talkTime1s;
    private int talkTime1m;
    private int talkTime1h;
    private int callTime;
    private String orderNo;
    private String payStatus;
    private String orderType;
    private String payType;
    private String budget;
    private String withdrawAccount;
    private String statusValue;
    private String typeName;
    private int typeId;
    private String content;
    private String memberName;
    private String lmemberName;
    private String iconImage;
    private int lawyerMemberId;
    private int really;
    private String beginTime;
    private String endTime;
    private String createDate;
    private double buyerPayAmount;
    private String grabTime;
    private int regionId;
    private String rname;
    private String couponName;
    private int scopeOfUse;
    private int couponType;
    private double couponDeductionAmount;
    private int useCoupon;
    private String payStatusValue;
    private int couponId;
    private double payAmount;
    private String orderAmount;
    private List<QuickTimeBean> quickTime;
    private int callback;
    private String institutionName;
    private String lmobile;


    private int appointmentLength;//咨询时长
    private int remainingTime;//订单剩余时长
    private CallOrderTimeBean callOrderTime;

    private double minAmount;//单价（20元/分钟）
    private int minimumDurationForExpert;//专家电话保底时长
    private double clientExpertAmount;//客户保底费用，保底时长*律师单价

    public double getMinAmount() {
        return minAmount;
    }

    public String getMinAmountStr(){
        return StringUtils.getStringNum(minAmount)+"元/分钟";
    }

    public String getExperAmoutStr(){
        //0-2       已冻结120.00元，暂未扣费（120：实付金额）
        //2-保底时长 保底时长价格
        //保底时长以上 实付金额
        if(callTime < 120){
            return "已冻结"+StringUtils.getStringNum(payAmount)+"元，暂未扣费";
        }else if(120 < callTime && callTime < minimumDurationForExpert * 60){
            return StringUtils.getStringNum(clientExpertAmount) + "元";
        }else{
            return StringUtils.getStringNum(payAmount) + "元";
        }
    }

    public CallOrderTimeBean getCallOrderTime() {
        return callOrderTime;
    }

    public int getAppointmentLength() {
        return appointmentLength;
    }

    public String getLmobile() {
        return lmobile;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public int getCallback() {
        return callback;
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

    public int getCallTime() {
        return callTime;
    }

    public String getCallTimeStr() {
        if(callTime == 0) return "";
        long hours = callTime / 3600;//转换小时数
        callTime = callTime % 3600;//剩余秒数
        long minutes = callTime / 60;//转换分钟
        callTime = callTime % 60;//剩余秒数
        if(hours > 0){
            return hours + "小时" + minutes + "分" + callTime + "秒";
        }
        if(minutes > 0){
            return minutes + "分" + callTime + "秒";
        }
        return callTime + "秒";
    }

    public void setCallTime(int callTime) {
        this.callTime = callTime;
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

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
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

    public String getLMemeberName2() {
        return rname + " | " + institutionName;
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

    public int getReally() {
        return really;
    }

    public void setReally(int really) {
        this.really = really;
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
        return "¥ " + StringUtils.getStringNum(buyerPayAmount) + "元";
    }

    public void setBuyerPayAmount(double buyerPayAmount) {
        this.buyerPayAmount = buyerPayAmount;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public String getRemainingTimeStr(){
       return TimeFormat.countDownToStr(Long.valueOf(remainingTime) * 1000, 0);
    }


    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public String getGrabTime() {
        return grabTime;
    }

    public void setGrabTime(String grabTime) {
        this.grabTime = grabTime;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public int getScopeOfUse() {
        return scopeOfUse;
    }

    public void setScopeOfUse(int scopeOfUse) {
        this.scopeOfUse = scopeOfUse;
    }

    public int getCouponType() {
        return couponType;
    }

    public String getCouponNameStr() {//优惠券类型,1.会员卡，2.电子优惠券，3.线下优惠券，4.体验券，目前只做1和2
        return couponName;
    }

    public void setCouponType(int couponType) {
        this.couponType = couponType;
    }

    public double getCouponDeductionAmount() {
        return couponDeductionAmount;
    }

    public String getCouponDeductionAmountStr() {
        return "¥ " + StringUtils.getStringNum(couponDeductionAmount) + "元";
    }

    public void setCouponDeductionAmount(double couponDeductionAmount) {
        this.couponDeductionAmount = couponDeductionAmount;
    }

    public int getUseCoupon() {
        return useCoupon;
    }

    public void setUseCoupon(int useCoupon) {
        this.useCoupon = useCoupon;
    }

    public String getPayStatusValue() {
        return payStatusValue;
    }

    public void setPayStatusValue(String payStatusValue) {
        this.payStatusValue = payStatusValue;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public double getPayAmount() {
        return payAmount;
    }

    public String getPayAmountStr() {
        return "¥ " + StringUtils.getStringNum(payAmount) + "元";
    }

    public void setPayAmount(double payAmount) {
        this.payAmount = payAmount;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public List<QuickTimeBean> getQuickTime() {
        return quickTime;
    }

    public void setQuickTime(List<QuickTimeBean> quickTime) {
        this.quickTime = quickTime;
    }

    public static class QuickTimeBean {
        /**
         * id : 480
         * beginTime : 2019-03-15 15:03:29
         * endTime : 2019-03-15 15:06:38
         */

        private int id;
        private String beginTime;
        private String endTime;
        private int callLength;

        public String getCalllength() {
            long hours = callLength / 3600;//转换小时数
            callLength = callLength % 3600;//剩余秒数
            long minutes = callLength / 60;//转换分钟
            callLength = callLength % 60;//剩余秒数
            if(hours > 0){
                return hours + "小时" + minutes + "分" + callLength + "秒";
            }
            if(minutes > 0){
                return minutes + "分" + callLength + "秒";
            }
            return callLength + "秒";
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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
    }

    public static class CallOrderTimeBean{
        String callOrderNo;
        int type;//类型
        String clientTimeStart;//用户预约起始时间
        String clientTimeEnd;//用户预约结束时间
        String lawyerTimeStart;//律师履约起始时间
        String acceptTime;//律师接单时间
        String createTime;
        String updateTime;

        public String getCallOrderNo() {
            return callOrderNo;
        }

        public int getType() {
            return type;
        }

        public String getClientTimeStart() {
            return clientTimeStart;
        }

        public String getClientTimeEnd() {
            return clientTimeEnd;
        }

        public String getLawyerTimeStart() {
            return lawyerTimeStart;
        }

        public String getAcceptTime() {
            return acceptTime;
        }

        public String getCreateTime() {
            return createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public String getClientTimeStr(){
            long lcurrent = TimeFormat.strToLong(TimeFormat.getCurrentTime(),TimeFormat.s1);

            long lstart = TimeFormat.strToLong(clientTimeStart,TimeFormat.s1);
            long lend = TimeFormat.strToLong(clientTimeEnd,TimeFormat.s1);

            if(lstart == 0 || lend == 0)
                return "----";

            if(TimeFormat.longToStr(lcurrent,"yyyy-MM-dd").equals(TimeFormat.longToStr(lstart,"yyyy-MM-dd"))){
                if(TimeFormat.longToStr(lcurrent,"yyyy-MM-dd").equals(TimeFormat.longToStr(lend,"yyyy-MM-dd"))){
                    return "今天" + TimeFormat.longToStr(lstart, "HH:mm") + " - " + TimeFormat.longToStr(lend, "HH:mm");
                }else{
                    return "今天" + TimeFormat.longToStr(lstart, "HH:mm") + " - 明天" + TimeFormat.longToStr(lend, "HH:mm");
                }
            }else{
                return "明天" + TimeFormat.longToStr(lstart, "HH:mm") + " - " + TimeFormat.longToStr(lend, "HH:mm");
            }
        }

        public String getLawyerTimeStr(){
            long lcurrent = TimeFormat.strToLong(TimeFormat.getCurrentTime(),TimeFormat.s1);
            long lstart = TimeFormat.strToLong(lawyerTimeStart,TimeFormat.s1);

            if(lstart == 0 )
                return "----";

            if(TimeFormat.longToStr(lcurrent,"yyyy-MM-dd").equals(TimeFormat.longToStr(lstart,"yyyy-MM-dd"))){
                return "今天" + TimeFormat.longToStr(lstart, "HH:mm");
            }else{
                return "明天" + TimeFormat.longToStr(lstart, "HH:mm");
            }
        }
    }
}
