package cn.lex_mung.client_android.mvp.model.entity;

import java.io.Serializable;

public class QualificationTypeEntity implements Serializable, SelectList {

    /**
     * qualificationTypeId : 1
     * qualificationTypeName : 会计证
     * parentId : 0
     * dateAdded : 2018-11-15 18:01:18
     * dateModified : 2018-11-15 18:01:20
     * creater : 测试
     * sortOrder : 0
     * status : 1
     */

    private int qualificationTypeId;
    private String qualificationTypeName;
    private int parentId;
    private String dateAdded;
    private String dateModified;
    private String creater;
    private int sortOrder;
    private int status;

    public int getQualificationTypeId() {
        return qualificationTypeId;
    }

    public void setQualificationTypeId(int qualificationTypeId) {
        this.qualificationTypeId = qualificationTypeId;
    }

    public String getQualificationTypeName() {
        return qualificationTypeName;
    }

    public void setQualificationTypeName(String qualificationTypeName) {
        this.qualificationTypeName = qualificationTypeName;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
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

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
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
}
