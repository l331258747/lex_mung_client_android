package cn.lex_mung.client_android.mvp.model.entity;

import java.util.List;

public class MyLikeEntity {

    /**
     * memberId : 4
     * memberName : 盛浩
     * institutionId : 9228
     * institutionName : 湖南芙蓉出水律师事务所
     * sex : 1
     * birthday : 1973-07-07 00:00:00
     * iconImage : http://oss.lex-mung.com/icon_image_member_ae496a8fd.jpg
     * regionId : 430102
     * region : 湖南省-长沙市-芙蓉区
     * memberPositionId : 2
     * memberPositionName : 高级合伙人
     * beginPracticeDate : 2010-10-01 00:00:00
     * isOnline : 1
     * description : 擅长领域：境外收购，境外融资，民间借贷/担保/抵押
     * orgTags : [{"tagName":"长沙市侨商会专属律师团律师","image":"http://oss.lex-mung.com/organization_aa0a4d35b.jpg"},{"tagName":"可接收绿豆券","image":"http://oss.lex-mung.com/organization_c24b1d208.png"}]
     */

    private int memberId;
    private String memberName;
    private int institutionId;
    private String institutionName;
    private int sex;
    private String birthday;
    private String iconImage;
    private int regionId;
    private String region;
    private int memberPositionId;
    private String memberPositionName;
    private String beginPracticeDate;
    private int isOnline;
    private String description;
    private List<OrgTagsEntity> orgTags;

    public List<OrgTagsEntity> getOrgTags() {
        return orgTags;
    }

    public void setOrgTags(List<OrgTagsEntity> orgTags) {
        this.orgTags = orgTags;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public int getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(int institutionId) {
        this.institutionId = institutionId;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getIconImage() {
        return iconImage;
    }

    public void setIconImage(String iconImage) {
        this.iconImage = iconImage;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getMemberPositionId() {
        return memberPositionId;
    }

    public void setMemberPositionId(int memberPositionId) {
        this.memberPositionId = memberPositionId;
    }

    public String getMemberPositionName() {
        return memberPositionName;
    }

    public void setMemberPositionName(String memberPositionName) {
        this.memberPositionName = memberPositionName;
    }

    public String getBeginPracticeDate() {
        return beginPracticeDate;
    }

    public void setBeginPracticeDate(String beginPracticeDate) {
        this.beginPracticeDate = beginPracticeDate;
    }

    public int getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(int isOnline) {
        this.isOnline = isOnline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
