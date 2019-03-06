package com.lex_mung.client_android.mvp.model.entity;

import java.io.Serializable;

public class WorkexpEntity implements Serializable {

    /**
     * tbMemberExtendWorkExperienceId : 248
     * memberId : 14
     * institutionName : 中国移动通信股份有限公司湖南分公司
     * positionName : 运维
     * workExperienceImage : http://oss.lex-mung.com/bd_logo1.png?where=super
     * startDate : 1990-07-02 00:00:00
     * endDate : 2000-07-02 00:00:00
     * memberExtendStatusId : 1421
     * sortOrder : 0
     * status : 1
     * dateAdded : 2018-11-15 17:26:43
     * dateModified : 1970-01-01 08:00:00
     * verifyUserId : 0
     * workExperienceDescription : 打酱油
     * verifyComment :
     */

    private int tbMemberExtendWorkExperienceId;
    private int memberId;
    private String institutionName;
    private String positionName;
    private String workExperienceImage;
    private String startDate;
    private String endDate;
    private int memberExtendStatusId;
    private int sortOrder;
    private int status;
    private String dateAdded;
    private String dateModified;
    private int verifyUserId;
    private String workExperienceDescription;
    private String verifyComment;

    public int getTbMemberExtendWorkExperienceId() {
        return tbMemberExtendWorkExperienceId;
    }

    public void setTbMemberExtendWorkExperienceId(int tbMemberExtendWorkExperienceId) {
        this.tbMemberExtendWorkExperienceId = tbMemberExtendWorkExperienceId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getWorkExperienceImage() {
        return workExperienceImage;
    }

    public void setWorkExperienceImage(String workExperienceImage) {
        this.workExperienceImage = workExperienceImage;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getMemberExtendStatusId() {
        return memberExtendStatusId;
    }

    public void setMemberExtendStatusId(int memberExtendStatusId) {
        this.memberExtendStatusId = memberExtendStatusId;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public int getVerifyUserId() {
        return verifyUserId;
    }

    public void setVerifyUserId(int verifyUserId) {
        this.verifyUserId = verifyUserId;
    }

    public String getWorkExperienceDescription() {
        return workExperienceDescription;
    }

    public void setWorkExperienceDescription(String workExperienceDescription) {
        this.workExperienceDescription = workExperienceDescription;
    }

    public String getVerifyComment() {
        return verifyComment;
    }

    public void setVerifyComment(String verifyComment) {
        this.verifyComment = verifyComment;
    }
}
