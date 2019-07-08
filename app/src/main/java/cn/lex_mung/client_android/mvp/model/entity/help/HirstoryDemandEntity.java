package cn.lex_mung.client_android.mvp.model.entity.help;

import java.util.List;

public class HirstoryDemandEntity {

    /**
     * orderNo : XQ19052801393068932
     * id : 3664
     * memberId : 6082
     * lawyerMemberId : 4795
     * createDate : 2019-05-28 14:04:57
     * typeName : 审查合同
     * requireTypeId : 30
     * content : EMS女球迷若果搜索木事木事女人
     * requirementExtendValue : 200
     * pageNum : 0
     * pageSize : 0
     */

    private String orderNo;
    private int id;
    private int memberId;
    private int lawyerMemberId;
    private String createDate;
    private String typeName;
    private int requireTypeId;
    private String content;
    private String requirementExtendValue;
    private int pageNum;
    private int pageSize;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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

    public int getLawyerMemberId() {
        return lawyerMemberId;
    }

    public void setLawyerMemberId(int lawyerMemberId) {
        this.lawyerMemberId = lawyerMemberId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getRequireTypeId() {
        return requireTypeId;
    }

    public void setRequireTypeId(int requireTypeId) {
        this.requireTypeId = requireTypeId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRequirementExtendValue() {
        return requirementExtendValue;
    }

    public String getRequirementExtendValueStr() {
        return requirementExtendValue + "元";
    }

    public void setRequirementExtendValue(String requirementExtendValue) {
        this.requirementExtendValue = requirementExtendValue;
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
