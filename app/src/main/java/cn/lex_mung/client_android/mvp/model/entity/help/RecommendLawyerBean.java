package cn.lex_mung.client_android.mvp.model.entity.help;

import android.text.TextUtils;
import android.view.TextureView;

import java.util.List;
import java.util.Random;

public class RecommendLawyerBean {
    /**
     * memberId : 60
     * memberName : 黄婧
     * institutionId : 3
     * institutionName : 湖南金州律师事务所
     * sex : 0
     * birthday : 1970-01-01 00:00:01
     * iconImage : http://oss.lex-mung.com/icon_image_member_2430e3439.jpg
     * regionId : 430100
     * region : 湖南省-长沙市
     * memberPositionId : 4
     * memberPositionName : 律师
     * beginPracticeDate : 2016-03-01 00:00:00
     * practice : 执业3年
     * isOnline : 1
     * description : 抚养/收养、财产继承、买卖合同纠纷、二手房买卖、房屋赠与/继承、家庭暴力
     * orgTags : [{"tagName":"长沙市侨商会专属律师团律师","image":"http://oss.lex-mung.com/organization_aa0a4d35b.jpg","link":"https://h5-test.lex-mung.com/organization.html?id=1&organizationLevId=22"}]
     * matchDegree : 99%
     * businessInfo : [{"solutionTypeId":2,"solutionTypeName":"婚姻家事","titleIconImage":"http://oss.lex-mung.com/solution_type_title_2.png","child":[{"solutionMarkId":21,"solutionMarkName":"离婚诉讼","score":20},{"solutionMarkId":22,"solutionMarkName":"夫妻财产/债务","score":10},{"solutionMarkId":23,"solutionMarkName":"家庭暴力","score":40},{"solutionMarkId":24,"solutionMarkName":"抚养/收养","score":70},{"solutionMarkId":65,"solutionMarkName":"财产继承","score":60}]},{"solutionTypeId":6,"solutionTypeName":"重大财产处置与保护","titleIconImage":"http://oss.lex-mung.com/solution_type_title_6.png","child":[{"solutionMarkId":35,"solutionMarkName":"房屋销售/买卖","score":30},{"solutionMarkId":36,"solutionMarkName":"二手房买卖","score":50},{"solutionMarkId":128,"solutionMarkName":"房屋赠与/继承","score":50}]},{"solutionTypeId":7,"solutionTypeName":"一般合同纠纷","titleIconImage":"http://oss.lex-mung.com/solution_type_title_7.png","child":[{"solutionMarkId":38,"solutionMarkName":"买卖合同纠纷","score":50},{"solutionMarkId":39,"solutionMarkName":"施工合同纠纷","score":30}]},{"solutionTypeId":18,"solutionTypeName":"重大基础项目建设","titleIconImage":"http://oss.lex-mung.com/solution_type_title_25.png","child":[{"solutionMarkId":105,"solutionMarkName":"公司治理","score":10},{"solutionMarkId":106,"solutionMarkName":"股东保护和股权架构","score":20},{"solutionMarkId":148,"solutionMarkName":"员工股权激励","score":10}]},{"solutionTypeId":17,"solutionTypeName":"海外投资","titleIconImage":"http://oss.lex-mung.com/solution_type_title_17.png","child":[{"solutionMarkId":111,"solutionMarkName":"境内IPO","score":10},{"solutionMarkId":142,"solutionMarkName":"股权投资VC/PE","score":10}]}]
     * socialFunction : []
     * requireInfo : [{"memberId":60,"requireTypeId":3,"requireTypeSectionId":35,"minAmount":1000,"creater":"","dateAdded":"2018-03-06 10:03:58","dateModified":"2018-07-12 10:24:02","requireTypeName":"起草合同","rstatus":2,"iconImage":"http://oss.lex-mung.com/organization_image_15514318526639.png","image":"http://oss.lex-mung.com/organization_image_15514318490679.png","unit":"份","parentId":0,"type":1,"requireTypeDescription":"审查或完全撰写一份合同","requirementType":1,"status":1,"requireTypes":[],"requires":[{"memberId":60,"requireTypeId":10,"requireTypeSectionId":181,"minAmount":1000,"creater":"ceshi666","dateAdded":"2019-01-28 15:08:02","dateModified":"2019-01-28 15:08:02","requireTypeName":"起草简式合同","rstatus":2,"iconImage":"http://oss.lex-mung.com/organization_image_15506535018269.jpg","image":"http://oss.lex-mung.com/organization_image_15506534901419.jpg","unit":"起","parentId":3,"type":1,"requireTypeDescription":"适用于法律关系比较简单，关系人比较熟悉，签订合同主要是证明作用","requirementType":1,"status":1,"requireTypes":[],"requires":[],"rcount":0}],"rcount":1}]
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
    private String practice;
    private int isOnline;
    private String description;
    private String matchDegree;
    private List<OrgTagsBean> orgTags;
    private List<BusinessInfoBean> businessInfo;
    private List<String> socialFunction;
    private List<RequireInfoBean> requireInfo;

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public String getName(){
        if(TextUtils.isEmpty(memberPositionName)){
            return memberName;
        }else{
            return memberName + "  (" + memberPositionName + ")";
        }
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

    public String getPractice() {
        return practice;
    }

    public String getName2(){
        return practice + " | " + institutionName;
    }

    public void setPractice(String practice) {
        this.practice = practice;
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

    public String getMatchDegree() {
        return matchDegree;
    }

    public String getMatchDegreeStr(){
        return "优选律师匹配度" + matchDegree;
    }

    public void setMatchDegree(String matchDegree) {
        this.matchDegree = matchDegree;
    }

    public List<OrgTagsBean> getOrgTags() {
        return orgTags;
    }

    public void setOrgTags(List<OrgTagsBean> orgTags) {
        this.orgTags = orgTags;
    }

    public List<BusinessInfoBean> getBusinessInfo() {
        return businessInfo;
    }

    public void setBusinessInfo(List<BusinessInfoBean> businessInfo) {
        this.businessInfo = businessInfo;
    }

    public List<String> getSocialFunction() {
        return socialFunction;
    }

    public void setSocialFunction(List<String> socialFunction) {
        this.socialFunction = socialFunction;
    }

    public List<RequireInfoBean> getRequireInfo() {
        return requireInfo;
    }

    public void setRequireInfo(List<RequireInfoBean> requireInfo) {
        this.requireInfo = requireInfo;
    }






}