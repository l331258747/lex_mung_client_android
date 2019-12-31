package cn.lex_mung.client_android.mvp.model.entity.payEquity;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.List;

import cn.lex_mung.client_android.mvp.model.entity.OrderDetailsEntity;
import cn.lex_mung.client_android.mvp.model.entity.other.QuickTimeBean;

public class LegalAdviserOrderDetailEntity implements Serializable {


    /**
     * id : 9
     * orderNo : ZJ191021301672415041
     * payOrderNo :
     * orderId : ZJ191022601625920726
     * legalAdviserId : 0
     * requireTypeId : 124
     * lawyerMemberId : 0
     * receiptDate :
     * showAmount : 0
     * lawyerAmount : 0
     * receiptStatus : 2
     * platformAmount : 0
     * createMemberId : 0
     * parentMemberId : 0
     * createDate : 2019-10-23 21:05:04
     * status : 0
     * regionId : 0
     * address :
     * meetingTime :
     * meetNum : 0
     * typeId : 0
     * memberId : 6546
     * memberName : 用户18600
     * typeAliasName : 企业经营
     * requireTypeName : 电话咨询
     * lawyerId : 6097
     * lawyerName : 邓珊
     * iconImage : icon_image_lawyer_15556611822639.jpeg
     * memberRoleid : 5
     * lawyerRegionId : 430102
     * institutionName : 北京浩天信和（长沙）律师事务所
     * evaluateDate :
     * evaluateType : 1
     * generalEvaluation : 5
     * professionalKnowledge : 5
     * responseSpeed : 5
     * serviceAttitude : 5
     * evaluationContent : dsjlkfjlsd
     */

    private int id;
    private String orderNo;
    private String payOrderNo;
    private String orderId;
    private int legalAdviserId;
    private int requireTypeId;
    private int lawyerMemberId;
    private String receiptDate;
    private double showAmount;
    private String lawyerAmount;
    private int receiptStatus;
    private double platformAmount;
    private int createMemberId;
    private int parentMemberId;
    private String createDate;
    private int status;
    private int regionId;
    private String address;
    private String meetingTime;
    private int meetNum;
    private int typeId;
    private int memberId;
    private String memberName;
    private String typeAliasName;
    private String requireTypeName;
    private int lawyerId;
    private String lawyerName;
    private String iconImage;
    private int memberRoleid;
    private int lawyerRegionId;
    private String institutionName;
    private String evaluateDate;
    private int evaluateType;
    private int generalEvaluation;
    private int professionalKnowledge;
    private int responseSpeed;
    private int serviceAttitude;
    private String evaluationContent;
    private String lawyerRegion;
    private String region;
    private List<QuickTimeBean> quickTime;
    private LegalAdviserOrderDetailLawsui lawsui;
    private String serverName;
    private String callTimeStr;

    public String getCallTimeStr() {
        return callTimeStr;
    }

    public String getServerName() {
        return serverName;
    }

    public LegalAdviserOrderDetailLawsui getLawsui() {
        return lawsui;
    }

    public List<QuickTimeBean> getQuickTime() {
        return quickTime;
    }

    public String getRegion() {
        return region;
    }

    public String getLawyerRegion() {
        return lawyerRegion;
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

    public int getId() {
        return id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public String getPayOrderNo() {
        return payOrderNo;
    }

    public String getOrderId() {
        return orderId;
    }

    public int getLegalAdviserId() {
        return legalAdviserId;
    }

    //124电话咨询  125合同文书  126线下见面
    public int getRequireTypeId() {
        return requireTypeId;
    }

    public int getLawyerMemberId() {
        return lawyerMemberId;
    }

    public String getReceiptDate() {
        return receiptDate;
    }

    public double getShowAmount() {
        return showAmount;
    }

    public String getLawyerAmount() {
        return lawyerAmount;
    }

    //订单状态：1	待接单 2	待服务 3	服务中 4	待确认 5	待评价 6	已完成 7	待处理 8	已关闭 9	已取消
    public int getReceiptStatus() {
        return receiptStatus;
    }

    public String getReceiptStatusStr(){
        switch (receiptStatus){
            case 1:
                return "待接单";
            case 2:
                return "待服务";
            case 3:
                return "服务中";
            case 4:
                return "待确认";
            case 5:
                return "待评价";
            case 6:
                return "已完成";
            case 7:
                return "待处理";
            case 8:
                return "已关闭";
            case 9:
                return "已取消";
        }
        return "";
    }

    public double getPlatformAmount() {
        return platformAmount;
    }

    public int getCreateMemberId() {
        return createMemberId;
    }

    public int getParentMemberId() {
        return parentMemberId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public int getStatus() {
        return status;
    }

    public int getRegionId() {
        return regionId;
    }

    public String getAddress() {
        return address;
    }

    public String getMeetingTime() {
        return meetingTime;
    }

    public String getMeetingDuration(){
        if(meetNum <= 0){
            return "";
        }
        switch (meetNum){
            case 1:
                return "3小时（1次）";
            case 2:
                return "6小时（2次）";
            case 3:
                return "1天（3次）";
            case 4:
                return "12小时（4次）";
            case 6:
                return "2天（6次）";
            case 9:
                return "3天（9次）";
        }
        return "";
    }

    public int getMeetNum() {
        return meetNum;
    }

    public int getTypeId() {
        return typeId;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public String getTypeAliasName() {
        return typeAliasName;
    }

    public String getRequireTypeName() {
        return requireTypeName;
    }

    public int getLawyerId() {
        return lawyerId;
    }

    public String getLawyerName() {
        return lawyerName;
    }

    public String getIconImage() {
        return iconImage;
    }

    public int getMemberRoleid() {
        return memberRoleid;
    }

    public int getLawyerRegionId() {
        return lawyerRegionId;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public String getEvaluateDate() {
        return evaluateDate;
    }

    public int getEvaluateType() {
        return evaluateType;
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
}
