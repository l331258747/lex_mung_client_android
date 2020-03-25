package cn.lex_mung.client_android.mvp.model.entity.corporate;

import android.text.TextUtils;

import java.util.List;

import cn.lex_mung.client_android.mvp.model.entity.other.QuickTimeBean;

public class CorporateDetailEntity {
    private int memberId;
    private String memberName;
    private int lawyerId;
    private String lawyerName;
    private String iconImage;
    private String lawyerRegion;
    private String institutionName;
    private String memberPositionName;
    private int evaluateType;
    private String evaluateDate;
    private int generalEvaluation;
    private int professionalKnowledge;
    private int responseSpeed;
    private int serviceAttitude;
    private String evaluationContent;
    private int lawyerAmount;
    private String amountDescribe;
    private String showAmount;
    private String orderNo;
    private String createDate;
    private int receiptStatus;
    private String receiptStatusName;
    private String requireTypeName;
    private String requireTypeParentName;
    private String serverName;
    private String solutionTypeName;
    private List<QuickTimeBean> quickTime;
    private int callTime;
    private String callTimeStr;
    private int requireTypeId;

    public int getRequireTypeId() {
        return requireTypeId;
    }

    public String getLawyerArea(){
        if(!TextUtils.isEmpty(lawyerRegion) && !TextUtils.isEmpty(institutionName))
            return lawyerRegion + " | " + institutionName;
        if(TextUtils.isEmpty(lawyerRegion))
            return institutionName;
        if(TextUtils.isEmpty(institutionName))
            return lawyerRegion;
        return "";
    }

    public int getMemberId() {
        return memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public int getLawyerId() {
        return lawyerId;
    }

    public String getLawyerName() {
        return lawyerName;
    }

    public String getEvaluateLawyerName(){
        if(TextUtils.isEmpty(memberPositionName))
            return lawyerName;
        else{
            return lawyerName + "（" + memberPositionName + "）";
        }
    }

    public String getIconImage() {
        return iconImage;
    }

    public String getLawyerRegion() {
        return lawyerRegion;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public String getMemberPositionName() {
        return memberPositionName;
    }

    public int getEvaluateType() {
        return evaluateType;
    }

    public String getEvaluateDate() {
        return evaluateDate;
    }

    public int getGeneralEvaluation() {
        return generalEvaluation;
    }

    public int getProfessionalKnowledge() {
        return professionalKnowledge;
    }

    public int getResponseSpeed() {
        return responseSpeed;
    }

    public int getServiceAttitude() {
        return serviceAttitude;
    }

    public String getEvaluationContent() {
        return evaluationContent;
    }

    public int getLawyerAmount() {
        return lawyerAmount;
    }

    public String getAmountDescribe() {
        return amountDescribe;
    }

    public String getShowAmount() {
        return showAmount;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public String getCreateDate() {
        return createDate;
    }

    public int getReceiptStatus() {
        return receiptStatus;
    }

    public String getReceiptStatusName() {
        return receiptStatusName;
    }

    public String getRequireTypeName() {
        return requireTypeName;
    }

    public String getRequireTypeParentName() {
        return requireTypeParentName;
    }

    public String getServerName() {
        return serverName;
    }

    public String getSolutionTypeName() {
        return solutionTypeName;
    }

    public List<QuickTimeBean> getQuickTime() {
        return quickTime;
    }

    public int getCallTime() {
        return callTime;
    }

    public String getCallTimeStr() {
        return callTimeStr;
    }

}
