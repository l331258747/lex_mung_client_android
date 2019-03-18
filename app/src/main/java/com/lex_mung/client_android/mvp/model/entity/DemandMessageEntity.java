package com.lex_mung.client_android.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

public class DemandMessageEntity implements Serializable {
    private List<RequirementBean> requirement;

    public List<RequirementBean> getRequirement() {
        return requirement;
    }

    public void setRequirement(List<RequirementBean> requirement) {
        this.requirement = requirement;
    }

    public static class RequirementBean implements Serializable {
        /**
         * requirementId : 2535
         * sourceMemberId : 0
         * lawyerMemberId : 4
         * memberName : 盛浩
         * iconImage : http://oss.lex-mung.com/icon_image_member_ae496a8fd.jpg
         * requirementName : 诉讼/仲裁需求
         * requirementTypeName : 诉讼/仲裁
         * requirementDescriptionValue : 弟弟大家觉得
         * requirementDescriptionTypeId : 11
         * isReceipt : 3
         * refuseReasonId : 0
         * refuseReason :
         * sendTime : 2019-03-08 10:03:06
         * orderStartTime :
         * regionId : 0
         * regionName :
         * businessTypeName :
         * maxCost :
         * comment :
         * createTime :
         * isRead : 1
         * skillId : 0
         * skillName :
         * isFlag : 0
         */

        private int requirementId;
        private int sourceMemberId;
        private int lawyerMemberId;
        private String memberName;
        private String iconImage;
        private String requirementName;
        private String requirementTypeName;
        private String requirementDescriptionValue;
        private int requirementDescriptionTypeId;
        private int isReceipt;
        private int refuseReasonId;
        private String refuseReason;
        private String sendTime;
        private String orderStartTime;
        private int regionId;
        private String regionName;
        private String businessTypeName;
        private String maxCost;
        private String comment;
        private String createTime;
        private int isRead;
        private int skillId;
        private String skillName;
        private int isFlag;

        public int getRequirementId() {
            return requirementId;
        }

        public void setRequirementId(int requirementId) {
            this.requirementId = requirementId;
        }

        public int getSourceMemberId() {
            return sourceMemberId;
        }

        public void setSourceMemberId(int sourceMemberId) {
            this.sourceMemberId = sourceMemberId;
        }

        public int getLawyerMemberId() {
            return lawyerMemberId;
        }

        public void setLawyerMemberId(int lawyerMemberId) {
            this.lawyerMemberId = lawyerMemberId;
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

        public String getRequirementName() {
            return requirementName;
        }

        public void setRequirementName(String requirementName) {
            this.requirementName = requirementName;
        }

        public String getRequirementTypeName() {
            return requirementTypeName;
        }

        public void setRequirementTypeName(String requirementTypeName) {
            this.requirementTypeName = requirementTypeName;
        }

        public String getRequirementDescriptionValue() {
            return requirementDescriptionValue;
        }

        public void setRequirementDescriptionValue(String requirementDescriptionValue) {
            this.requirementDescriptionValue = requirementDescriptionValue;
        }

        public int getRequirementDescriptionTypeId() {
            return requirementDescriptionTypeId;
        }

        public void setRequirementDescriptionTypeId(int requirementDescriptionTypeId) {
            this.requirementDescriptionTypeId = requirementDescriptionTypeId;
        }

        public int getIsReceipt() {
            return isReceipt;
        }

        public void setIsReceipt(int isReceipt) {
            this.isReceipt = isReceipt;
        }

        public int getRefuseReasonId() {
            return refuseReasonId;
        }

        public void setRefuseReasonId(int refuseReasonId) {
            this.refuseReasonId = refuseReasonId;
        }

        public String getRefuseReason() {
            return refuseReason;
        }

        public void setRefuseReason(String refuseReason) {
            this.refuseReason = refuseReason;
        }

        public String getSendTime() {
            return sendTime;
        }

        public void setSendTime(String sendTime) {
            this.sendTime = sendTime;
        }

        public String getOrderStartTime() {
            return orderStartTime;
        }

        public void setOrderStartTime(String orderStartTime) {
            this.orderStartTime = orderStartTime;
        }

        public int getRegionId() {
            return regionId;
        }

        public void setRegionId(int regionId) {
            this.regionId = regionId;
        }

        public String getRegionName() {
            return regionName;
        }

        public void setRegionName(String regionName) {
            this.regionName = regionName;
        }

        public String getBusinessTypeName() {
            return businessTypeName;
        }

        public void setBusinessTypeName(String businessTypeName) {
            this.businessTypeName = businessTypeName;
        }

        public String getMaxCost() {
            return maxCost;
        }

        public void setMaxCost(String maxCost) {
            this.maxCost = maxCost;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getIsRead() {
            return isRead;
        }

        public void setIsRead(int isRead) {
            this.isRead = isRead;
        }

        public int getSkillId() {
            return skillId;
        }

        public void setSkillId(int skillId) {
            this.skillId = skillId;
        }

        public String getSkillName() {
            return skillName;
        }

        public void setSkillName(String skillName) {
            this.skillName = skillName;
        }

        public int getIsFlag() {
            return isFlag;
        }

        public void setIsFlag(int isFlag) {
            this.isFlag = isFlag;
        }
    }
}
