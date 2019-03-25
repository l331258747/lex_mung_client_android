package cn.lex_mung.client_android.mvp.model.entity;

import java.io.Serializable;

public class FreeConsultEntity implements Serializable {

    /**
     * consultationId : 10
     * consultationNumber : Q1811307050341
     * memberId : 20
     * consultationStatus : 1
     * consultationTypeId : 1
     * source : 0
     * regionId : 0
     * status : 1
     * verifyUserId : 0
     * verifyReason :
     * dateAdded : 2018-11-30 17:37:54
     * dateModified : 1970-01-01 08:00:00
     * isHide : 1
     * content : 测试咨询内容
     * verifyComment :
     * replyCount : 0
     * region :
     * memberName : fly2
     * memberIconImage :
     * categoryName : 婚姻家庭
     */

    private int consultationId;
    private String consultationNumber;
    private int memberId;
    private int consultationStatus;
    private int consultationTypeId;
    private int source;
    private int regionId;
    private int status;
    private int verifyUserId;
    private String verifyReason;
    private String dateAdded;
    private String dateModified;
    private int isHide;
    private String content;
    private String verifyComment;
    private int replyCount;
    private String region;
    private String memberName;
    private String memberIconImage;
    private String categoryName;

    public int getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(int consultationId) {
        this.consultationId = consultationId;
    }

    public String getConsultationNumber() {
        return consultationNumber;
    }

    public void setConsultationNumber(String consultationNumber) {
        this.consultationNumber = consultationNumber;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getConsultationStatus() {
        return consultationStatus;
    }

    public void setConsultationStatus(int consultationStatus) {
        this.consultationStatus = consultationStatus;
    }

    public int getConsultationTypeId() {
        return consultationTypeId;
    }

    public void setConsultationTypeId(int consultationTypeId) {
        this.consultationTypeId = consultationTypeId;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getVerifyUserId() {
        return verifyUserId;
    }

    public void setVerifyUserId(int verifyUserId) {
        this.verifyUserId = verifyUserId;
    }

    public String getVerifyReason() {
        return verifyReason;
    }

    public void setVerifyReason(String verifyReason) {
        this.verifyReason = verifyReason;
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

    public int getIsHide() {
        return isHide;
    }

    public void setIsHide(int isHide) {
        this.isHide = isHide;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getVerifyComment() {
        return verifyComment;
    }

    public void setVerifyComment(String verifyComment) {
        this.verifyComment = verifyComment;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberIconImage() {
        return memberIconImage;
    }

    public void setMemberIconImage(String memberIconImage) {
        this.memberIconImage = memberIconImage;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
