package cn.lex_mung.client_android.mvp.model.entity;

import java.io.Serializable;

public class EducationBackgroundEntity implements Serializable {

    /**
     * memberExtendEducationId : 245
     * memberId : 14
     * educationId : 4
     * educationTitle : 中南大学
     * educationImage :
     * startDate : 1990-07-02 00:00:00
     * endDate : 2000-07-02 00:00:00
     * memberExtendStatusId : 1321
     * sortOrder :
     * status :
     * dateAdded : 2018-11-12 10:01:04
     * dateModified : 1970-01-01 08:00:00
     * verifyUserId :
     * educationDescription :
     * verifyComment :
     * educationName :"博士"
     */

    private int memberExtendEducationId;
    private int memberId;
    private int educationId;
    private String educationTitle;
    private String educationImage;
    private String startDate;
    private String endDate;
    private int memberExtendStatusId;
    private String sortOrder;
    private String status;
    private String dateAdded;
    private String dateModified;
    private String verifyUserId;
    private String educationDescription;
    private String verifyComment;
    private String educationName;

    public String getEducationName() {
        return educationName;
    }

    public void setEducationName(String educationName) {
        this.educationName = educationName;
    }

    public int getMemberExtendEducationId() {
        return memberExtendEducationId;
    }

    public void setMemberExtendEducationId(int memberExtendEducationId) {
        this.memberExtendEducationId = memberExtendEducationId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getEducationId() {
        return educationId;
    }

    public void setEducationId(int educationId) {
        this.educationId = educationId;
    }

    public String getEducationTitle() {
        return educationTitle;
    }

    public void setEducationTitle(String educationTitle) {
        this.educationTitle = educationTitle;
    }

    public String getEducationImage() {
        return educationImage;
    }

    public void setEducationImage(String educationImage) {
        this.educationImage = educationImage;
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

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public String getVerifyUserId() {
        return verifyUserId;
    }

    public void setVerifyUserId(String verifyUserId) {
        this.verifyUserId = verifyUserId;
    }

    public String getEducationDescription() {
        return educationDescription;
    }

    public void setEducationDescription(String educationDescription) {
        this.educationDescription = educationDescription;
    }

    public String getVerifyComment() {
        return verifyComment;
    }

    public void setVerifyComment(String verifyComment) {
        this.verifyComment = verifyComment;
    }
}
