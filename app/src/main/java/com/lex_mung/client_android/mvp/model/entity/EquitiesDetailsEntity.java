package com.lex_mung.client_android.mvp.model.entity;

public class EquitiesDetailsEntity {

    /**
     * organizationId : 16
     * organizationLevelName : fdfdfdf
     * organizationLevelNameId : 18
     * organizationName :
     * memberId : 0
     * rightsInterpret : 全品类9折
     * openingQualification : 加入lex
     * exclusiveRights : 全品类9折
     * image : catalog/organization/organization_image_15511473047369.png
     * memberCount : 6
     * lawyerCount : 90
     */

    private int organizationId;
    private String organizationLevelName;
    private int organizationLevelNameId;
    private String organizationName;
    private int memberId;
    private String rightsInterpret;
    private String openingQualification;
    private String exclusiveRights;
    private String image;
    private int memberCount;
    private int lawyerCount;

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationLevelName() {
        return organizationLevelName;
    }

    public void setOrganizationLevelName(String organizationLevelName) {
        this.organizationLevelName = organizationLevelName;
    }

    public int getOrganizationLevelNameId() {
        return organizationLevelNameId;
    }

    public void setOrganizationLevelNameId(int organizationLevelNameId) {
        this.organizationLevelNameId = organizationLevelNameId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getRightsInterpret() {
        return rightsInterpret;
    }

    public void setRightsInterpret(String rightsInterpret) {
        this.rightsInterpret = rightsInterpret;
    }

    public String getOpeningQualification() {
        return openingQualification;
    }

    public void setOpeningQualification(String openingQualification) {
        this.openingQualification = openingQualification;
    }

    public String getExclusiveRights() {
        return exclusiveRights;
    }

    public void setExclusiveRights(String exclusiveRights) {
        this.exclusiveRights = exclusiveRights;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public int getLawyerCount() {
        return lawyerCount;
    }

    public void setLawyerCount(int lawyerCount) {
        this.lawyerCount = lawyerCount;
    }
}
