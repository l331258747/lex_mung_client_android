package cn.lex_mung.client_android.mvp.model.entity.help;

import java.io.Serializable;

public class RequireTypeBean implements Serializable {
    /**
     * requireTypeId : 0
     * requireTypeNo :
     * requireTypeName : 不知道当前需要什么样的法律服务
     * requirementType : 0
     */

    private int requireTypeId;
    private String requireTypeNo;
    private String requireTypeName;
    private int requirementType;

    public int getRequireTypeId() {
        return requireTypeId;
    }

    public void setRequireTypeId(int requireTypeId) {
        this.requireTypeId = requireTypeId;
    }

    public String getRequireTypeNo() {
        return requireTypeNo;
    }

    public void setRequireTypeNo(String requireTypeNo) {
        this.requireTypeNo = requireTypeNo;
    }

    public String getRequireTypeName() {
        return requireTypeName;
    }

    public void setRequireTypeName(String requireTypeName) {
        this.requireTypeName = requireTypeName;
    }

    public int getRequirementType() {
        return requirementType;
    }

    public void setRequirementType(int requirementType) {
        this.requirementType = requirementType;
    }
}