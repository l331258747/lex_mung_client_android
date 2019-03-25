package cn.lex_mung.client_android.mvp.model.entity;

import java.io.Serializable;

public class LawsJobEntity implements Serializable, SelectList {

    /**
     * memberPositionId : 1
     * memberPositionNo : H1-01
     * memberPositionName : 律所主任
     * memberRoleId : 5
     * sortOrder : 1
     * status : 1
     * dateAdded : 2017-10-16T06:40:43.000+0000
     * dateModified : 2017-10-16T06:40:45.000+0000
     */

    private int memberPositionId;
    private String memberPositionNo;
    private String memberPositionName;
    private int memberRoleId;
    private int sortOrder;
    private int status;
    private String dateAdded;
    private String dateModified;

    public int getMemberPositionId() {
        return memberPositionId;
    }

    public void setMemberPositionId(int memberPositionId) {
        this.memberPositionId = memberPositionId;
    }

    public String getMemberPositionNo() {
        return memberPositionNo;
    }

    public void setMemberPositionNo(String memberPositionNo) {
        this.memberPositionNo = memberPositionNo;
    }

    public String getMemberPositionName() {
        return memberPositionName;
    }

    public void setMemberPositionName(String memberPositionName) {
        this.memberPositionName = memberPositionName;
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
