package com.lex_mung.client_android.mvp.model.entity;

import java.util.List;

public class BusinessTypeEntity {

    /**
     * businessTypeId : 1
     * businessTypeNo : A1
     * businessTypeName : 个人及家事类
     * parentId : 0
     * children :
     */

    private int businessTypeId;
    private String businessTypeNo;
    private String businessTypeName;
    private int parentId;
    private List<BusinessTypeEntity> children;

    public BusinessTypeEntity(int businessTypeId, String businessTypeName) {
        this.businessTypeId = businessTypeId;
        this.businessTypeName = businessTypeName;
    }

    public BusinessTypeEntity() {
    }

    public int getBusinessTypeId() {
        return businessTypeId;
    }

    public void setBusinessTypeId(int businessTypeId) {
        this.businessTypeId = businessTypeId;
    }

    public String getBusinessTypeNo() {
        return businessTypeNo;
    }

    public void setBusinessTypeNo(String businessTypeNo) {
        this.businessTypeNo = businessTypeNo;
    }

    public String getBusinessTypeName() {
        return businessTypeName;
    }

    public void setBusinessTypeName(String businessTypeName) {
        this.businessTypeName = businessTypeName;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public List<BusinessTypeEntity> getChildren() {
        return children;
    }

    public void setChildren(List<BusinessTypeEntity> children) {
        this.children = children;
    }
}
