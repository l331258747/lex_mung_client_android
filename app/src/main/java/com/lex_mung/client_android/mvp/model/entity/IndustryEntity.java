package com.lex_mung.client_android.mvp.model.entity;

public class IndustryEntity {

    /**
     * industryId : 21
     * industryNo : E01
     * industryName : 建筑业／房地产
     * parentId : 0
     * sortOrder : 1
     * status : 1
     * dateAdded : 2017-09-21T07:55:20.000+0000
     * dateModified : 2018-07-18T07:23:56.000+0000
     * industryDescription : null
     */

    private int industryId;
    private String industryNo;
    private String industryName;
    private int parentId;
    private int sortOrder;
    private int status;
    private String dateAdded;
    private String dateModified;
    private Object industryDescription;
    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getIndustryId() {
        return industryId;
    }

    public void setIndustryId(int industryId) {
        this.industryId = industryId;
    }

    public String getIndustryNo() {
        return industryNo;
    }

    public void setIndustryNo(String industryNo) {
        this.industryNo = industryNo;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
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

    public Object getIndustryDescription() {
        return industryDescription;
    }

    public void setIndustryDescription(Object industryDescription) {
        this.industryDescription = industryDescription;
    }
}
