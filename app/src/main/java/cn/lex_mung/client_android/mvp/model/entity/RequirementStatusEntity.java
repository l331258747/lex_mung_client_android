package cn.lex_mung.client_android.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

public class RequirementStatusEntity implements Serializable {


    /**
     * requirementName : 诉讼/仲裁需求
     * amount : 525154
     * requirementBusiTypeName : 个人及家事类
     * requirementDescriptionValue : 鸡柳咕噜噜挺愧疚了体贴拒绝
     * remain : 258581957
     * tags : [{"tagId":8,"tagName":"有婚前协议"},{"tagId":9,"tagName":"家庭暴力"}]
     * skillName : 离婚/夫妻财产/抚养权
     * skillId : 5
     * requirementDescriptionTypeId : 11
     * requirementTypeName : 诉讼/仲裁
     * createTime : 2018-12-27 16:59:31
     * comment : null
     * region : 长沙市
     * requirementId : 2129
     * status : 0
     */

    private String requirementName;
    private String amount;
    private String requirementBusiTypeName;
    private String requirementDescriptionValue;
    private int remain;
    private String skillName;
    private int skillId;
    private int requirementDescriptionTypeId;
    private String requirementTypeName;
    private String createTime;
    private Object comment;
    private String region;
    private int requirementId;
    private int status;
    private List<TagsBean> tags;

    public String getRequirementName() {
        return requirementName;
    }

    public void setRequirementName(String requirementName) {
        this.requirementName = requirementName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRequirementBusiTypeName() {
        return requirementBusiTypeName;
    }

    public void setRequirementBusiTypeName(String requirementBusiTypeName) {
        this.requirementBusiTypeName = requirementBusiTypeName;
    }

    public String getRequirementDescriptionValue() {
        return requirementDescriptionValue;
    }

    public void setRequirementDescriptionValue(String requirementDescriptionValue) {
        this.requirementDescriptionValue = requirementDescriptionValue;
    }

    public int getRemain() {
        return remain;
    }

    public void setRemain(int remain) {
        this.remain = remain;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public int getRequirementDescriptionTypeId() {
        return requirementDescriptionTypeId;
    }

    public void setRequirementDescriptionTypeId(int requirementDescriptionTypeId) {
        this.requirementDescriptionTypeId = requirementDescriptionTypeId;
    }

    public String getRequirementTypeName() {
        return requirementTypeName;
    }

    public void setRequirementTypeName(String requirementTypeName) {
        this.requirementTypeName = requirementTypeName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Object getComment() {
        return comment;
    }

    public void setComment(Object comment) {
        this.comment = comment;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getRequirementId() {
        return requirementId;
    }

    public void setRequirementId(int requirementId) {
        this.requirementId = requirementId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<TagsBean> getTags() {
        return tags;
    }

    public void setTags(List<TagsBean> tags) {
        this.tags = tags;
    }

    public static class TagsBean implements Serializable{
        /**
         * tagId : 8
         * tagName : 有婚前协议
         */

        private int tagId;
        private String tagName;

        public int getTagId() {
            return tagId;
        }

        public void setTagId(int tagId) {
            this.tagId = tagId;
        }

        public String getTagName() {
            return tagName;
        }

        public void setTagName(String tagName) {
            this.tagName = tagName;
        }
    }
}
