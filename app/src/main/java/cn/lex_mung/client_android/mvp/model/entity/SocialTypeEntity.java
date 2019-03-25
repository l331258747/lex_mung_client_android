package cn.lex_mung.client_android.mvp.model.entity;

import java.io.Serializable;

public class SocialTypeEntity implements Serializable, SelectList {

    /**
     * memberSocialFunctionId : 2
     * memberSocialFunctionNo : J1-02
     * memberSocialFunctionName : 区县级人大代表/政协委员
     * memberRoleId : 5
     * sortOrder : 1
     * status : 1
     * dateAdded : 2017-10-18T07:19:35.000+0000
     * dateModified : 2018-02-28T07:30:53.000+0000
     */

    private int memberSocialFunctionId;
    private String memberSocialFunctionNo;
    private String memberSocialFunctionName;
    private int memberRoleId;
    private int sortOrder;
    private int status;
    private String dateAdded;
    private String dateModified;

    public int getMemberSocialFunctionId() {
        return memberSocialFunctionId;
    }

    public void setMemberSocialFunctionId(int memberSocialFunctionId) {
        this.memberSocialFunctionId = memberSocialFunctionId;
    }

    public String getMemberSocialFunctionNo() {
        return memberSocialFunctionNo;
    }

    public void setMemberSocialFunctionNo(String memberSocialFunctionNo) {
        this.memberSocialFunctionNo = memberSocialFunctionNo;
    }

    public String getMemberSocialFunctionName() {
        return memberSocialFunctionName;
    }

    public void setMemberSocialFunctionName(String memberSocialFunctionName) {
        this.memberSocialFunctionName = memberSocialFunctionName;
    }

    public int getMemberRoleId() {
        return memberRoleId;
    }

    public void setMemberRoleId(int memberRoleId) {
        this.memberRoleId = memberRoleId;
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
}