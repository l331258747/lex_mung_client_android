package cn.lex_mung.client_android.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

import me.zl.mvp.utils.StringUtils;

public class BusinessEntity implements Serializable {

    /**
     * memberId : 5006
     * requireTypeId : 1
     * requireTypeSectionId : 2
     * minAmount : 50
     * creater : ceshi
     * dateAdded : 2018-12-13 10:28:04
     * dateModified : 2018-12-13 10:28:04
     * requireTypeName : 法律咨询
     * rstatus : 2
     * requireTypes : []
     */

    private int memberId;
    private int requireTypeId;
    private int requireTypeSectionId;
    private double minAmount;
    private String creater;
    private String dateAdded;
    private String dateModified;
    private String requireTypeName;
    private String image;
    private String unit;
    private int rstatus;
    private int parentId;
    private int type;
    private List<BusinessEntity> requires;
    private String iconImage;
    private String requireTypeDescription;
    private int requirementType;
    private int rcount;
    private String miniDuration;

    public String getMiniDuration() {
        return miniDuration;
    }

    public String getIconImage() {
        return iconImage;
    }

    public void setIconImage(String iconImage) {
        this.iconImage = iconImage;
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

    public int getRcount() {
        return rcount;
    }

    public void setRcount(int rcount) {
        this.rcount = rcount;
    }

    public List<BusinessEntity> getRequires() {
        return requires;
    }

    public void setRequires(List<BusinessEntity> requires) {
        this.requires = requires;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

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

    public String getMinAmountStr(){
        return StringUtils.getStringNum(minAmount);
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
}
