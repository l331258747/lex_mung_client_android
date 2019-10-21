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
    private int isPublic;

    String requireTypeId;
    String requireTypeNo;
    String equityName;
    String equityDesc;
    String iconImage;
    boolean isOwn;
    int roleId;
    String legalAdviserUrl;
    private boolean isBuyEquity;

    public String getLegalAdviserUrl() {
        return legalAdviserUrl;
    }

    public void setLegalAdviserUrl(String legalAdviserUrl) {
        this.legalAdviserUrl = legalAdviserUrl;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public String getOrganizationLevelName() {
        return organizationLevelName;
    }

    public int getOrganizationLevelNameId() {
        return organizationLevelNameId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public int getMemberId() {
        return memberId;
    }

    public int getScore() {
        return score;
    }

    public String getRightsInterpret() {
        return rightsInterpret;
    }

    public String getOpeningQualification() {
        return openingQualification;
    }

    public String getExclusiveRights() {
        return exclusiveRights;
    }

    public String getImage() {
        return image;
    }

    public String getSmallImage() {
        return smallImage;
    }

    public int getJoinStatus() {
        return joinStatus;
    }

    public int getIsPublic() {
        return isPublic;
    }

    public void setRequireTypeId(String requireTypeId) {
        this.requireTypeId = requireTypeId;
    }

    public void setRequireTypeNo(String requireTypeNo) {
        this.requireTypeNo = requireTypeNo;
    }

    public void setEquityName(String equityName) {
        this.equityName = equityName;
    }

    public void setEquityDesc(String equityDesc) {
        this.equityDesc = equityDesc;
    }

    public void setIconImage(String iconImage) {
        this.iconImage = iconImage;
    }

    public void setOwn(boolean own) {
        isOwn = own;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public void setIsBuyEquity(boolean isBuyEquity) {
        this.isBuyEquity = isBuyEquity;
    }

    public String getRequireTypeId() {
        return requireTypeId;
    }

    public String getRequireTypeNo() {
        return requireTypeNo;
    }

    public String getEquityName() {
        return equityName;
    }

    public String getEquityDesc() {
        return equityDesc;
    }

    public String getIconImage() {
        return iconImage;
    }

    public boolean isOwn() {
        return isOwn;
    }

    public int getRoleId() {
        return roleId;
    }

    public boolean getIsBuyEquity() {
        return isBuyEquity;
    }
}
