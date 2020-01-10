package cn.lex_mung.client_android.mvp.model.entity;

import android.text.TextUtils;

import java.util.List;

import cn.lex_mung.client_android.mvp.model.api.Api;

public class LawyerEntity2 {
    /**
     * memberId : 266
     * memberName : 金明政
     * institutionId : 9000
     * institutionName : 湖南南天门律师事务所
     * sex : 0
     * birthday : 1970-01-01 00:00:01
     * iconImage : http://oss.lex-mung.com/icon_image_member_e0a657871.jpg
     * regionId : 430700
     * region : 湖南省-常德市
     * memberPositionId : 4
     * memberPositionName :
     * beginPracticeDate : 2017-10-01 00:00:00
     * isOnline : 1
     * description : 擅长领域：境外收购，境外融资，民间借贷/担保/抵押
     * orgTags : []
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
    private String lawyerWeight;
    private String practice;
    private String mobile;
    private String tagName;
    private String curriculumContent;

    public String getTagName() {
        return tagName;
    }

    public String getCurriculumContent() {
        return curriculumContent;
    }

    public String getMobile() {
        return mobile;
    }

    public String getPractice() {
        return practice;
    }

    public void setPractice(String practice) {
        this.practice = practice;
    }

    public String getLawyerWeight() {
        return lawyerWeight;
    }

    public void setLawyerWeight(String lawyerWeight) {
        this.lawyerWeight = lawyerWeight;
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

    public String getReginInstitutionName(){
        if(TextUtils.isEmpty(region) && TextUtils.isEmpty(institutionName))
            return "";
        if(TextUtils.isEmpty(region))
            return institutionName;
        if(TextUtils.isEmpty(institutionName))
            return region;
        return region + " | " + institutionName;
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

    public String getMemberPositionNameStr() {
        if(TextUtils.isEmpty(memberPositionName))
            return "";
        return "（"+memberPositionName + ")";
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

    public String getDescriptionStr() {
        if(TextUtils.isEmpty(description)) return "";
        if(description.startsWith("擅长领域：")){
            String string = "<font color=\"#323232\"><b>擅长领域</b></font>&#8195;" + description.substring(("擅长领域：").length());
            return string;
        }
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<OrgTagsEntity> getOrgTags() {
        return orgTags;
    }

    public void setOrgTags(List<OrgTagsEntity> orgTags) {
        this.orgTags = orgTags;
    }
}
