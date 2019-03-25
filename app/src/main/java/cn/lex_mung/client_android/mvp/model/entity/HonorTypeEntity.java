package cn.lex_mung.client_android.mvp.model.entity;

import java.io.Serializable;

public class HonorTypeEntity implements Serializable, SelectList {

    /**
     * memberHonorId : 1
     * memberHonorNo : I1-01
     * memberHonorName : 国家优秀律师
     * memberRoleId : 5
     * sortOrder : 1
     * status : 1
     * dateAdded : 2017-10-18T06:51:30.000+0000
     * dateModified : 2017-10-18T06:51:31.000+0000
     */

    private int memberHonorId;
    private String memberHonorNo;
    private String memberHonorName;
    private int memberRoleId;
    private int sortOrder;
    private int status;
    private String dateAdded;
    private String dateModified;

    public int getMemberHonorId() {
        return memberHonorId;
    }

    public void setMemberHonorId(int memberHonorId) {
        this.memberHonorId = memberHonorId;
    }

    public String getMemberHonorNo() {
        return memberHonorNo;
    }

    public void setMemberHonorNo(String memberHonorNo) {
        this.memberHonorNo = memberHonorNo;
    }

    public String getMemberHonorName() {
        return memberHonorName;
    }

    public void setMemberHonorName(String memberHonorName) {
        this.memberHonorName = memberHonorName;
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
