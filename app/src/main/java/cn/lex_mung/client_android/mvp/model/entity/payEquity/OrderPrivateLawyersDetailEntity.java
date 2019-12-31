package cn.lex_mung.client_android.mvp.model.entity.payEquity;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.List;

import cn.lex_mung.client_android.mvp.model.entity.other.QuickTimeBean;
import me.zl.mvp.utils.StringUtils;

public class OrderPrivateLawyersDetailEntity implements Serializable {

    /**
     * id : 17
     * orderId : PB19112502028144347
     * memberId : 4691
     * lmemberId : 6087
     * memberName : 用户我我我
     * iconImage : http://oss.lex-mung.com/icon_image_member_505463558.jpg
     * requireTypeName : 私人律师团-电话咨询
     * configTypeName : 私人律师团
     * solutionTypeName : 不动产销售与租赁
     * lmemberName : 今天
     * lmobile : 13222222222
     * liconImage : http://oss.lex-mung.com/icon_image_lawyer_15570475435269.png
     * institutionName : 湖南一星律师事务所
     * rname : 北京市
     * createDate : 2019-11-25 11:12:51
     * orderType : 私人律师团
     * receiptStatus : 20
     * statusValue : 待服务
     * callTime : 0
     * callTimeStr :
     * evaluate : {}
     * quickTime : []
     */

    private int id;
    private String orderId;
    private int memberId;
    private int lmemberId;
    private String memberName;
    private String iconImage;
    private String requireTypeName;
    private String configTypeName;
    private String solutionTypeName;
    private String lmemberName;
    private String lmobile;
    private String liconImage;
    private String institutionName;
    private String rname;
    private String createDate;
    private String orderType;
    private int receiptStatus;
    private String statusValue;
    private int callTime;
    private String callTimeStr;
    private EvaluateBean evaluate;
    private List<QuickTimeBean> quickTime;
    private String lawyerAmount;

    public String getLawyerArea(){
        if(!TextUtils.isEmpty(rname) && !TextUtils.isEmpty(institutionName))
            return rname + " | " + institutionName;
        if(TextUtils.isEmpty(rname))
            return institutionName;
        if(TextUtils.isEmpty(institutionName))
            return rname;
        return "";
    }

    public String getReceiptStatusStr(){
        switch (receiptStatus){
            case 10:
                return "待接单";
            case 20:
                return "待服务";
            case 30:
                return "服务中";
            case 40:
                return "待确认";
            case 50:
                return "待评价";
            case 60:
                return "已完成";
            case 70:
                return "待处理";
            case 80:
                return "已关闭";
            case 90:
                return "已取消";
        }
        return "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getLmemberId() {
        return lmemberId;
    }

    public void setLmemberId(int lmemberId) {
        this.lmemberId = lmemberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getIconImage() {
        return iconImage;
    }

    public void setIconImage(String iconImage) {
        this.iconImage = iconImage;
    }

    public String getRequireTypeName() {
        return requireTypeName;
    }

    public void setRequireTypeName(String requireTypeName) {
        this.requireTypeName = requireTypeName;
    }

    public String getConfigTypeName() {
        return configTypeName;
    }

    public void setConfigTypeName(String configTypeName) {
        this.configTypeName = configTypeName;
    }

    public String getSolutionTypeName() {
        return solutionTypeName;
    }

    public void setSolutionTypeName(String solutionTypeName) {
        this.solutionTypeName = solutionTypeName;
    }

    public String getLmemberName() {
        return lmemberName;
    }

    public void setLmemberName(String lmemberName) {
        this.lmemberName = lmemberName;
    }

    public String getLmobile() {
        return lmobile;
    }

    public void setLmobile(String lmobile) {
        this.lmobile = lmobile;
    }

    public String getLiconImage() {
        return liconImage;
    }

    public void setLiconImage(String liconImage) {
        this.liconImage = liconImage;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public int getReceiptStatus() {
        return receiptStatus;
    }

    public void setReceiptStatus(int receiptStatus) {
        this.receiptStatus = receiptStatus;
    }

    public String getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(String statusValue) {
        this.statusValue = statusValue;
    }

    public int getCallTime() {
        return callTime;
    }

    public void setCallTime(int callTime) {
        this.callTime = callTime;
    }

    public String getCallTimeStr() {
        return callTimeStr;
    }

    public void setCallTimeStr(String callTimeStr) {
        this.callTimeStr = callTimeStr;
    }

    public EvaluateBean getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(EvaluateBean evaluate) {
        this.evaluate = evaluate;
    }

    public List<QuickTimeBean> getQuickTime() {
        return quickTime;
    }

    public void setQuickTime(List<QuickTimeBean> quickTime) {
        this.quickTime = quickTime;
    }

}
