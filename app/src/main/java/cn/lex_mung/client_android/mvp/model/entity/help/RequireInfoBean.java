package cn.lex_mung.client_android.mvp.model.entity.help;

import java.util.List;

public class RequireInfoBean {
    /**
     * memberId : 60
     * requireTypeId : 3
     * requireTypeSectionId : 35
     * minAmount : 1000
     * creater :
     * dateAdded : 2018-03-06 10:03:58
     * dateModified : 2018-07-12 10:24:02
     * requireTypeName : 起草合同
     * rstatus : 2
     * iconImage : http://oss.lex-mung.com/organization_image_15514318526639.png
     * image : http://oss.lex-mung.com/organization_image_15514318490679.png
     * unit : 份
     * parentId : 0
     * type : 1
     * requireTypeDescription : 审查或完全撰写一份合同
     * requirementType : 1
     * status : 1
     * requireTypes : []
     * requires : [{"memberId":60,"requireTypeId":10,"requireTypeSectionId":181,"minAmount":1000,"creater":"ceshi666","dateAdded":"2019-01-28 15:08:02","dateModified":"2019-01-28 15:08:02","requireTypeName":"起草简式合同","rstatus":2,"iconImage":"http://oss.lex-mung.com/organization_image_15506535018269.jpg","image":"http://oss.lex-mung.com/organization_image_15506534901419.jpg","unit":"起","parentId":3,"type":1,"requireTypeDescription":"适用于法律关系比较简单，关系人比较熟悉，签订合同主要是证明作用","requirementType":1,"status":1,"requireTypes":[],"requires":[],"rcount":0}]
     * rcount : 1
     */

    private int memberId;
    private int requireTypeId;
    private int requireTypeSectionId;
    private double minAmount;
    private String creater;
    private String dateAdded;
    private String dateModified;
    private String requireTypeName;
    private int rstatus;
    private String iconImage;
    private String image;
    private String unit;
    private int parentId;
    private int type;
    private String requireTypeDescription;
    private int requirementType;
    private int status;
    private int rcount;
    private List<?> requireTypes;
    private List<RequireInfoChildBean> requires;

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getRequireTypeId() {
        return requireTypeId;
    }

    public void setRequireTypeId(int requireTypeId) {
        this.requireTypeId = requireTypeId;
    }

    public int getRequireTypeSectionId() {
        return requireTypeSectionId;
    }

    public void setRequireTypeSectionId(int requireTypeSectionId) {
        this.requireTypeSectionId = requireTypeSectionId;
    }

    public double getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(double minAmount) {
        this.minAmount = minAmount;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
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

    public String getRequireTypeName() {
        return requireTypeName;
    }

    public void setRequireTypeName(String requireTypeName) {
        this.requireTypeName = requireTypeName;
    }

    public int getRstatus() {
        return rstatus;
    }

    public void setRstatus(int rstatus) {
        this.rstatus = rstatus;
    }

    public String getIconImage() {
        return iconImage;
    }

    public void setIconImage(String iconImage) {
        this.iconImage = iconImage;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRequireTypeDescription() {
        return requireTypeDescription;
    }

    public void setRequireTypeDescription(String requireTypeDescription) {
        this.requireTypeDescription = requireTypeDescription;
    }

    public int getRequirementType() {
        return requirementType;
    }

    public void setRequirementType(int requirementType) {
        this.requirementType = requirementType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getRcount() {
        return rcount;
    }

    public void setRcount(int rcount) {
        this.rcount = rcount;
    }

    public List<?> getRequireTypes() {
        return requireTypes;
    }

    public void setRequireTypes(List<?> requireTypes) {
        this.requireTypes = requireTypes;
    }

    public List<RequireInfoChildBean> getRequires() {
        return requires;
    }

    public void setRequires(List<RequireInfoChildBean> requires) {
        this.requires = requires;
    }


}