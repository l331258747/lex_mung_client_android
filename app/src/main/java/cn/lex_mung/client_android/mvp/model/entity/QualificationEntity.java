package cn.lex_mung.client_android.mvp.model.entity;

import android.text.TextUtils;

import java.io.Serializable;

import cn.lex_mung.client_android.app.StateTags;

public class QualificationEntity implements Serializable, SelectList {


    /**
     * memberExtendQualificationId : 2
     * memberId : 5001
     * qualificationTypeId : 1
     * qualificationNumber :
     * qualificationImage : /ceshi
     * startDate : 1970-01-01 08:00:00
     * qualificationStatus : 4
     * sortOrder : 0
     * status : 1
     * dateAdded : 2018-11-17 12:29:25
     * dateModified : 2018-11-17 13:31:11
     * verifyUserId : 42
     * verifyComment : 不予通过
     */

    private int memberExtendQualificationId;
    private int memberId;
    private int qualificationTypeId;
    private String qualificationNumber;
    private String qualificationTypeName;
    private String qualificationImage;
    private String startDate;
    private int qualificationStatus;
    private int sortOrder;
    private int status;
    private String dateAdded;
    private String dateModified;
    private int verifyUserId;
    private String verifyComment;

    int qualificationTypeIdv2;
    String qualificationTypeNamev2;
    int qualificationTagId;
    String qualificationTagName;

    String memberQualificationDescription;

    public String getMemberQualificationDescription() {
        return memberQualificationDescription;
    }

    public int getQualificationTypeIdv2() {
        return qualificationTypeIdv2;
    }

    public int getQualificationTagId() {
        return qualificationTagId;
    }

    //原来 qualificationTypeId qualificationTypeName
    //现在 qualificationTypeIdv2 qualificationTypeNamev2
    //    qualificationTagId qualificationTagName

    public String getTypeName(){
        if(!TextUtils.isEmpty(memberQualificationDescription))
            return memberQualificationDescription;

        if(!TextUtils.isEmpty(qualificationTagName))
            return qualificationTypeNamev2 + qualificationTagName;

        return qualificationTypeName;
    }

    public String getTypeName2(){
        if(!TextUtils.isEmpty(qualificationTagName))
            return qualificationTypeNamev2 + qualificationTagName;

        return qualificationTypeName;
    }

    public String getMemberExtendStatusIdStr(){
        switch (qualificationStatus){
            case StateTags.QUALIFICATION_1:
                return "认证中";
            case StateTags.QUALIFICATION_3:
                return "已认证";
            case StateTags.QUALIFICATION_4:
                return "未通过";
        }
        return "";
    }

    public int getMemberExtendStatusIdInt(){
        switch (qualificationStatus){
            case StateTags.QUALIFICATION_1:
                return 2;
            case StateTags.QUALIFICATION_3:
                return 1;
            case StateTags.QUALIFICATION_4:
                return 3;
        }
        return 0;
    }

    public String getStartDateStr(){
        if(TextUtils.isEmpty(startDate)) return "";
        return startDate.split(" ")[0];
    }

    public String getQualificationTypeName() {
        return qualificationTypeName;
    }

    public void setQualificationTypeName(String qualificationTypeName) {
        this.qualificationTypeName = qualificationTypeName;
    }

    public int getMemberExtendQualificationId() {
        return memberExtendQualificationId;
    }

    public void setMemberExtendQualificationId(int memberExtendQualificationId) {
        this.memberExtendQualificationId = memberExtendQualificationId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getQualificationTypeId() {
        return qualificationTypeId;
    }

    public void setQualificationTypeId(int qualificationTypeId) {
        this.qualificationTypeId = qualificationTypeId;
    }

    public String getQualificationNumber() {
        return qualificationNumber;
    }

    public void setQualificationNumber(String qualificationNumber) {
        this.qualificationNumber = qualificationNumber;
    }

    public String getQualificationImage() {
        return qualificationImage;
    }

    public void setQualificationImage(String qualificationImage) {
        this.qualificationImage = qualificationImage;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getQualificationStatus() {
        return qualificationStatus;
    }

    public void setQualificationStatus(int qualificationStatus) {
        this.qualificationStatus = qualificationStatus;
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

    public String getVerifyComment() {
        return verifyComment;
    }

    public void setVerifyComment(String verifyComment) {
        this.verifyComment = verifyComment;
    }
}
