package cn.lex_mung.client_android.mvp.model.entity;

public class EquitiesListEntity {

    /**
     * organizationId : 16
     * organizationLevelName : k1
     * organizationLevelNameId : 18
     * organizationName : 湖南省浙江商会
     * memberId : 0
     * score : 0
     * rightsInterpret : 测试一下1
     * openingQualification : 测试一下2
     * exclusiveRights : 测试一下3
     * image : http://oss.lex-mung.com/organization_image_15505578104849.jpg
     */

    private int organizationId;
    private String organizationLevelName;
    private int organizationLevelNameId;
    private String organizationName;
    private int memberId;
    private int score;
    private String rightsInterpret;
    private String openingQualification;
    private String exclusiveRights;
    private String image;
    private String smallImage;
    private int joinStatus;

    public String getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }

    public int getJoinStatus() {
        return joinStatus;
    }

    public void setJoinStatus(int joinStatus) {
        this.joinStatus = joinStatus;
    }

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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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
}
