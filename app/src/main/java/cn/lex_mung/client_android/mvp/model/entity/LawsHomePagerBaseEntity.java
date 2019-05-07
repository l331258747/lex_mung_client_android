package cn.lex_mung.client_android.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

public class LawsHomePagerBaseEntity implements Serializable {

    /**
     * memberId : 5022
     * memberName : 杨纯
     * memberRoleId : 5
     * mobile :
     * sex : 2
     * birthday : 1985-01-01 00:00:00
     * age : 34岁
     * regionId : 430102
     * region : 湖南省-长沙市
     * institutionId : 3
     * institutionName : 湖南金州律师事务所
     * memberPositionId : 4
     * memberPositionName : 律师
     * lawyerTags : []
     * beginPracticeDate : 2018-12-26 00:00:00
     * practice : 执业不足1年
     * isCollect : 0
     * isOnline : 1
     * iconImage : http://oss.lex-mung.com/icon_image_member_1699914879.png
     * backgroundImage :
     * businessInfo : []
     * socialFunction : ["区县级人大代表/政协委员"]
     * baseInfo : {"memberDescription":"个人简介，qsxhxudnxnk","honor":["国家优秀律师"],"orgTags":[{"tagName":"ceshi","image":"http://oss.lex-mung.com/aa","link":"https://h5-test.lex-mung.com/organization.html?id=18&organizationLevId=0"},{"tagName":"ceshi","image":"http://oss.lex-mung.com/aa","link":"https://h5-test.lex-mung.com/organization.html?id=4&organizationLevId=0"},{"tagName":"长沙市侨商会专属律师团律师","image":"http://oss.lex-mung.com/organization_aa0a4d35b.jpg","link":"https://h5-test.lex-mung.com/organization.html?id=1&organizationLevId=22"},{"tagName":"诉讼融资授信律师200万额度","image":"http://oss.lex-mung.com/organization_a8fcb0d20.jpg","link":"https://h5-test.lex-mung.com/organization.html?id=3&organizationLevId=21"},{"tagName":"湖南省浙江商会","image":"http://oss.lex-mung.com/organization_image_1071512265.png","link":"https://h5-test.lex-mung.com/organization.html?id=16&organizationLevId=18"}],"workExp":[{"tbMemberExtendWorkExperienceId":317,"institutionName":"法律顾问","positionName":"我的手机1小时1","workExperienceImage":"","startDate":"2018-12-24 00:00:00","endDate":"2018-12-24 00:00:00"},{"tbMemberExtendWorkExperienceId":318,"institutionName":"法律顾问","positionName":"我的手机1小时1","workExperienceImage":"","startDate":"2018-12-24 00:00:00","endDate":"2018-12-24 00:00:00"},{"tbMemberExtendWorkExperienceId":319,"institutionName":"法律顾问","positionName":"我的手机1小时1","workExperienceImage":"","startDate":"2018-12-24 00:00:00","endDate":"2018-12-24 00:00:00"},{"tbMemberExtendWorkExperienceId":320,"institutionName":"法律顾问","positionName":"我的手机1小时1","workExperienceImage":"","startDate":"2018-12-24 00:00:00","endDate":"2018-12-24 00:00:00"},{"tbMemberExtendWorkExperienceId":321,"institutionName":"法律顾问","positionName":"我的手机1小时1","workExperienceImage":"","startDate":"2018-12-24 00:00:00","endDate":"2018-12-24 00:00:00"}],"education":[{"memberExtendEducationId":306,"memberId":5022,"educationId":3,"educationTitle":"我的人生观价值观都","educationImage":"","startDate":"2018-12-24 00:00:00","endDate":"2018-12-24 00:00:00","memberExtendStatusId":1321,"sortOrder":0,"status":1,"dateAdded":"2018-12-24 15:04:00","dateModified":"1970-01-01 08:00:00","verifyUserId":0,"educationDescription":"法学","verifyComment":"","educationName":"本科"}]}
     * requireInfo : [{"memberId":0,"requireTypeId":8,"requireTypeSectionId":0,"minAmount":0,"creater":"","dateAdded":"","dateModified":"","requireTypeName":"电话咨询","rstatus":1,"iconImage":"http://oss.lex-mung.com/Lawyers_homepage_icon_13_max.png","image":"http://oss.lex-mung.com/Lawyers_homepage_icon_13_max.png","unit":"小时","parentId":0,"type":1,"requireTypeDescription":"电话咨询","requirementType":2,"requireTypes":[],"requires":[{"memberId":0,"requireTypeId":20,"requireTypeSectionId":0,"minAmount":0,"creater":"","dateAdded":"","dateModified":"","requireTypeName":"直接拨打","rstatus":1,"iconImage":"","image":"","unit":"小时","parentId":8,"type":1,"requireTypeDescription":"","requirementType":1,"requireTypes":[],"requires":[],"rcount":0}],"rcount":0},{"memberId":0,"requireTypeId":3,"requireTypeSectionId":0,"minAmount":0,"creater":"","dateAdded":"","dateModified":"","requireTypeName":"起草合同","rstatus":1,"iconImage":"http://oss.lex-mung.com/organization_image_15514318526639.png","image":"http://oss.lex-mung.com/organization_image_15514318490679.png","unit":"份","parentId":0,"type":1,"requireTypeDescription":"审查或完全撰写一份合同","requirementType":1,"requireTypes":[],"requires":[{"memberId":0,"requireTypeId":10,"requireTypeSectionId":0,"minAmount":0,"creater":"","dateAdded":"","dateModified":"","requireTypeName":"起草简式合同","rstatus":1,"iconImage":"http://oss.lex-mung.com/organization_image_15506535018269.jpg","image":"http://oss.lex-mung.com/organization_image_15506534901419.jpg","unit":"起","parentId":3,"type":1,"requireTypeDescription":"适用于法律关系比较简单，关系人比较熟悉，签订合同主要是证明作用","requirementType":1,"requireTypes":[],"requires":[],"rcount":0},{"memberId":0,"requireTypeId":11,"requireTypeSectionId":0,"minAmount":0,"creater":"","dateAdded":"","dateModified":"","requireTypeName":"起草标准合同","rstatus":1,"iconImage":"http://oss.lex-mung.com/organization_image_15514129700259.png","image":"http://oss.lex-mung.com/organization_image_15514129665789.png","unit":"起","parentId":3,"type":1,"requireTypeDescription":"正常交易，双方当事人不是特别熟悉，需要合同防范主要的风险，明确双方权利义务","requirementType":1,"requireTypes":[],"requires":[],"rcount":0}],"rcount":0},{"memberId":5022,"requireTypeId":2,"requireTypeSectionId":3,"minAmount":75,"creater":"","dateAdded":"2018-12-21 11:30:41","dateModified":"2019-01-06 03:33:02","requireTypeName":"诉讼/仲裁","rstatus":2,"iconImage":"http://oss.lex-mung.com/organization_image_15514442414219.png","image":"http://oss.lex-mung.com/organization_image_15514442377499.png","unit":"起","parentId":0,"type":2,"requireTypeDescription":"展开依法审理或纠纷裁决","requirementType":1,"requireTypes":[],"requires":[{"memberId":5022,"requireTypeId":15,"requireTypeSectionId":0,"minAmount":75,"creater":"ceshi666","dateAdded":"2019-01-28 15:22:25","dateModified":"2019-01-28 15:22:25","requireTypeName":"诉讼/仲裁","rstatus":2,"iconImage":"http://oss.lex-mung.com/organization_image_15513465049119.png","image":"http://oss.lex-mung.com/organization_image_15513465012279.png","unit":"份","parentId":2,"type":1,"requireTypeDescription":"这是诉讼仲裁","requirementType":1,"requireTypes":[],"requires":[],"rcount":0}],"rcount":1},{"memberId":0,"requireTypeId":4,"requireTypeSectionId":0,"minAmount":0,"creater":"","dateAdded":"","dateModified":"","requireTypeName":"律师函","rstatus":1,"iconImage":"http://oss.lex-mung.com/organization_image_15514443640949.png","image":"http://oss.lex-mung.com/organization_image_15514443599809.png","unit":"份","parentId":0,"type":1,"requireTypeDescription":"向某人或机构发送律师函","requirementType":1,"requireTypes":[],"requires":[{"memberId":0,"requireTypeId":14,"requireTypeSectionId":0,"minAmount":0,"creater":"","dateAdded":"","dateModified":"","requireTypeName":"律师函","rstatus":1,"iconImage":"","image":"","unit":"份","parentId":4,"type":1,"requireTypeDescription":"","requirementType":1,"requireTypes":[],"requires":[],"rcount":0}],"rcount":0},{"memberId":0,"requireTypeId":9,"requireTypeSectionId":0,"minAmount":0,"creater":"","dateAdded":"","dateModified":"","requireTypeName":"线下见面","rstatus":1,"iconImage":"http://oss.lex-mung.com/organization_image_15514443857829.png","image":"http://oss.lex-mung.com/organization_image_15514443819229.png","unit":"次","parentId":0,"type":1,"requireTypeDescription":"线下见面","requirementType":1,"requireTypes":[],"requires":[{"memberId":0,"requireTypeId":21,"requireTypeSectionId":0,"minAmount":0,"creater":"","dateAdded":"","dateModified":"","requireTypeName":"1小时内","rstatus":1,"iconImage":"","image":"","unit":"次","parentId":9,"type":1,"requireTypeDescription":"","requirementType":1,"requireTypes":[],"requires":[],"rcount":0},{"memberId":0,"requireTypeId":22,"requireTypeSectionId":0,"minAmount":0,"creater":"","dateAdded":"","dateModified":"","requireTypeName":"3小时内","rstatus":1,"iconImage":"","image":"","unit":"次","parentId":9,"type":1,"requireTypeDescription":"","requirementType":1,"requireTypes":[],"requires":[],"rcount":0},{"memberId":0,"requireTypeId":29,"requireTypeSectionId":0,"minAmount":0,"creater":"","dateAdded":"","dateModified":"","requireTypeName":"2点12分","rstatus":1,"iconImage":"http://oss.lex-mung.com/1111","image":"http://oss.lex-mung.com/1111","unit":"次","parentId":9,"type":2,"requireTypeDescription":"2点12分","requirementType":1,"requireTypes":[],"requires":[],"rcount":0}],"rcount":0},{"memberId":0,"requireTypeId":6,"requireTypeSectionId":0,"minAmount":0,"creater":"","dateAdded":"","dateModified":"","requireTypeName":"企业顾问","rstatus":1,"iconImage":"http://oss.lex-mung.com/organization_image_15514444051089.png","image":"http://oss.lex-mung.com/organization_image_15514443994699.png","unit":"年","parentId":0,"type":2,"requireTypeDescription":"律师担任长期法律顾问","requirementType":1,"requireTypes":[],"requires":[{"memberId":0,"requireTypeId":17,"requireTypeSectionId":0,"minAmount":0,"creater":"","dateAdded":"","dateModified":"","requireTypeName":"常年法律顾问","rstatus":1,"iconImage":"","image":"","unit":"年","parentId":6,"type":2,"requireTypeDescription":"","requirementType":1,"requireTypes":[],"requires":[],"rcount":0},{"memberId":0,"requireTypeId":18,"requireTypeSectionId":0,"minAmount":0,"creater":"","dateAdded":"","dateModified":"","requireTypeName":"专项法律顾问","rstatus":1,"iconImage":"","image":"","unit":"年","parentId":6,"type":2,"requireTypeDescription":"","requirementType":1,"requireTypes":[],"requires":[],"rcount":0}],"rcount":0},{"memberId":0,"requireTypeId":30,"requireTypeSectionId":0,"minAmount":0,"creater":"","dateAdded":"","dateModified":"","requireTypeName":"审查合同","rstatus":1,"iconImage":"http://oss.lex-mung.com/organization_image_15514319543389.png","image":"http://oss.lex-mung.com/organization_image_15514319497209.png","unit":"起","parentId":0,"type":2,"requireTypeDescription":"审查或完全撰写一份合同","requirementType":1,"requireTypes":[],"requires":[{"memberId":0,"requireTypeId":12,"requireTypeSectionId":0,"minAmount":0,"creater":"","dateAdded":"","dateModified":"","requireTypeName":"审查简式合同","rstatus":1,"iconImage":"http://oss.lex-mung.com/organization_image_15514129835449.png","image":"http://oss.lex-mung.com/organization_image_15514129875329.png","unit":"起","parentId":30,"type":1,"requireTypeDescription":"1111111111111111111111","requirementType":1,"requireTypes":[],"requires":[],"rcount":0},{"memberId":0,"requireTypeId":13,"requireTypeSectionId":0,"minAmount":0,"creater":"","dateAdded":"","dateModified":"","requireTypeName":"审查标准合同","rstatus":1,"iconImage":"http://oss.lex-mung.com/organization_image_15514130184759.png","image":"http://oss.lex-mung.com/organization_image_15514130117749.png","unit":"起","parentId":30,"type":1,"requireTypeDescription":"1111111111111","requirementType":1,"requireTypes":[],"requires":[],"rcount":0}],"rcount":0},{"memberId":0,"requireTypeId":5,"requireTypeSectionId":0,"minAmount":0,"creater":"","dateAdded":"","dateModified":"","requireTypeName":"意见书","rstatus":1,"iconImage":"http://oss.lex-mung.com/Lawyers_homepage_icon_08_max.png","image":"http://oss.lex-mung.com/Lawyers_homepage_icon_08_max.png","unit":"份","parentId":0,"type":2,"requireTypeDescription":"律师提供法律指导及建议","requirementType":1,"requireTypes":[],"requires":[{"memberId":0,"requireTypeId":16,"requireTypeSectionId":0,"minAmount":0,"creater":"","dateAdded":"","dateModified":"","requireTypeName":"法律意见书","rstatus":1,"iconImage":"","image":"","unit":"份","parentId":5,"type":2,"requireTypeDescription":"","requirementType":1,"requireTypes":[],"requires":[],"rcount":0},{"memberId":0,"requireTypeId":24,"requireTypeSectionId":0,"minAmount":0,"creater":"","dateAdded":"","dateModified":"","requireTypeName":"测试用的","rstatus":1,"iconImage":"http://oss.lex-mung.com/organization_image_15506535606549.jpg","image":"http://oss.lex-mung.com/organization_image_15506535555699.jpg","unit":"","parentId":5,"type":1,"requireTypeDescription":"1111111","requirementType":1,"requireTypes":[],"requires":[],"rcount":0}],"rcount":0},{"memberId":0,"requireTypeId":7,"requireTypeSectionId":0,"minAmount":0,"creater":"","dateAdded":"","dateModified":"","requireTypeName":"尽职调查","rstatus":1,"iconImage":"http://oss.lex-mung.com/Lawyers_homepage_icon_07_max.png","image":"http://oss.lex-mung.com/Lawyers_homepage_icon_07_max.png","unit":"次","parentId":0,"type":2,"requireTypeDescription":"对公司整体状况进行调查","requirementType":1,"requireTypes":[],"requires":[{"memberId":0,"requireTypeId":19,"requireTypeSectionId":0,"minAmount":0,"creater":"","dateAdded":"","dateModified":"","requireTypeName":"尽职调查","rstatus":1,"iconImage":"","image":"","unit":"次","parentId":7,"type":2,"requireTypeDescription":"","requirementType":1,"requireTypes":[],"requires":[],"rcount":0}],"rcount":0}]
     * practiceInfo : {"served":["中国移动通信股份有限公司湖南分公司","中国联通","测试数据"],"industry":["农/林/牧/渔/其他","保险／金融／信托／拍卖","建筑业／房地产","文化/传媒/娱乐/体育／广告"],"baseSkill":["文书写作","交流沟通","谈判/庭审","演讲培训","尽职调查"],"otherSkill":["会计师","审计师"],"langSkill":["英语","日语","德语"],"court":["湖南省长沙市天心区人民法院","长沙市岳麓区人民法院","长沙市芙蓉区人民法院","长沙市雨花区人民法院"],"procuratorate":["长沙市芙蓉区人民检察院","长沙市岳麓区人民检察院","长沙市开福区人民检察院","长沙县人民检察院"]}
     */

    private int memberId;
    private String memberName;
    private int memberRoleId;
    private String mobile;
    private int sex;
    private String birthday;
    private String age;
    private int regionId;
    private String region;
    private int institutionId;
    private String institutionName;
    private int memberPositionId;
    private String memberPositionName;
    private String beginPracticeDate;
    private String practice;
    private int isCollect;
    private int isOnline;
    private List<OrgTagsEntity> lawyerTags;
    private List<ChildBean> businessInfo;
    private String iconImage;
    private String backgroundImage;
    private BaseInfoBean baseInfo;
    private PracticeInfoBean practiceInfo;
    private List<String> socialFunction;
    private List<BusinessEntity> requireInfo;

    public boolean showCall(){
        if(requireInfo == null) return false;
        for(BusinessEntity item : requireInfo){
            if(item.getRequirementType() == 2)
                return true;
        }
        return false;
    }

    public List<ChildBean> getBusinessInfo() {
        return businessInfo;
    }

    public void setBusinessInfo(List<ChildBean> businessInfo) {
        this.businessInfo = businessInfo;
    }

    public List<OrgTagsEntity> getLawyerTags() {
        return lawyerTags;
    }

    public void setLawyerTags(List<OrgTagsEntity> lawyerTags) {
        this.lawyerTags = lawyerTags;
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

    public int getMemberRoleId() {
        return memberRoleId;
    }

    public void setMemberRoleId(int memberRoleId) {
        this.memberRoleId = memberRoleId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
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

    public void setPractice(String practice) {
        this.practice = practice;
    }

    public int getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(int isCollect) {
        this.isCollect = isCollect;
    }

    public int getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(int isOnline) {
        this.isOnline = isOnline;
    }

    public String getIconImage() {
        return iconImage;
    }

    public void setIconImage(String iconImage) {
        this.iconImage = iconImage;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public BaseInfoBean getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(BaseInfoBean baseInfo) {
        this.baseInfo = baseInfo;
    }

    public PracticeInfoBean getPracticeInfo() {
        return practiceInfo;
    }

    public void setPracticeInfo(PracticeInfoBean practiceInfo) {
        this.practiceInfo = practiceInfo;
    }

    public List<String> getSocialFunction() {
        return socialFunction;
    }

    public void setSocialFunction(List<String> socialFunction) {
        this.socialFunction = socialFunction;
    }

    public List<BusinessEntity> getRequireInfo() {
        return requireInfo;
    }

    public void setRequireInfo(List<BusinessEntity> requireInfo) {
        this.requireInfo = requireInfo;
    }

    public static class BaseInfoBean implements Serializable {
        /**
         * memberDescription : 个人简介，qsxhxudnxnk
         * honor : ["国家优秀律师"]
         * orgTags : [{"tagName":"ceshi","image":"http://oss.lex-mung.com/aa","link":"https://h5-test.lex-mung.com/organization.html?id=18&organizationLevId=0"},{"tagName":"ceshi","image":"http://oss.lex-mung.com/aa","link":"https://h5-test.lex-mung.com/organization.html?id=4&organizationLevId=0"},{"tagName":"长沙市侨商会专属律师团律师","image":"http://oss.lex-mung.com/organization_aa0a4d35b.jpg","link":"https://h5-test.lex-mung.com/organization.html?id=1&organizationLevId=22"},{"tagName":"诉讼融资授信律师200万额度","image":"http://oss.lex-mung.com/organization_a8fcb0d20.jpg","link":"https://h5-test.lex-mung.com/organization.html?id=3&organizationLevId=21"},{"tagName":"湖南省浙江商会","image":"http://oss.lex-mung.com/organization_image_1071512265.png","link":"https://h5-test.lex-mung.com/organization.html?id=16&organizationLevId=18"}]
         * workExp : [{"tbMemberExtendWorkExperienceId":317,"institutionName":"法律顾问","positionName":"我的手机1小时1","workExperienceImage":"","startDate":"2018-12-24 00:00:00","endDate":"2018-12-24 00:00:00"},{"tbMemberExtendWorkExperienceId":318,"institutionName":"法律顾问","positionName":"我的手机1小时1","workExperienceImage":"","startDate":"2018-12-24 00:00:00","endDate":"2018-12-24 00:00:00"},{"tbMemberExtendWorkExperienceId":319,"institutionName":"法律顾问","positionName":"我的手机1小时1","workExperienceImage":"","startDate":"2018-12-24 00:00:00","endDate":"2018-12-24 00:00:00"},{"tbMemberExtendWorkExperienceId":320,"institutionName":"法律顾问","positionName":"我的手机1小时1","workExperienceImage":"","startDate":"2018-12-24 00:00:00","endDate":"2018-12-24 00:00:00"},{"tbMemberExtendWorkExperienceId":321,"institutionName":"法律顾问","positionName":"我的手机1小时1","workExperienceImage":"","startDate":"2018-12-24 00:00:00","endDate":"2018-12-24 00:00:00"}]
         * education : [{"memberExtendEducationId":306,"memberId":5022,"educationId":3,"educationTitle":"我的人生观价值观都","educationImage":"","startDate":"2018-12-24 00:00:00","endDate":"2018-12-24 00:00:00","memberExtendStatusId":1321,"sortOrder":0,"status":1,"dateAdded":"2018-12-24 15:04:00","dateModified":"1970-01-01 08:00:00","verifyUserId":0,"educationDescription":"法学","verifyComment":"","educationName":"本科"}]
         */

        private String memberDescription;
        private List<String> honor;
        private List<OrgTagsEntity> orgTags;
        private List<WorkexpEntity> workExp;
        private List<EducationBackgroundEntity> education;

        public String getMemberDescription() {
            return memberDescription;
        }

        public void setMemberDescription(String memberDescription) {
            this.memberDescription = memberDescription;
        }

        public List<String> getHonor() {
            return honor;
        }

        public void setHonor(List<String> honor) {
            this.honor = honor;
        }

        public List<OrgTagsEntity> getOrgTags() {
            return orgTags;
        }

        public void setOrgTags(List<OrgTagsEntity> orgTags) {
            this.orgTags = orgTags;
        }

        public List<WorkexpEntity> getWorkExp() {
            return workExp;
        }

        public void setWorkExp(List<WorkexpEntity> workExp) {
            this.workExp = workExp;
        }

        public List<EducationBackgroundEntity> getEducation() {
            return education;
        }

        public void setEducation(List<EducationBackgroundEntity> education) {
            this.education = education;
        }
    }

    public static class PracticeInfoBean implements Serializable {
        private List<String> served;
        private List<String> industry;
        private List<String> baseSkill;
        private List<String> otherSkill;
        private List<String> langSkill;
        private List<String> court;
        private List<String> procuratorate;

        public List<String> getServed() {
            return served;
        }

        public void setServed(List<String> served) {
            this.served = served;
        }

        public List<String> getIndustry() {
            return industry;
        }

        public void setIndustry(List<String> industry) {
            this.industry = industry;
        }

        public List<String> getBaseSkill() {
            return baseSkill;
        }

        public void setBaseSkill(List<String> baseSkill) {
            this.baseSkill = baseSkill;
        }

        public List<String> getOtherSkill() {
            return otherSkill;
        }

        public void setOtherSkill(List<String> otherSkill) {
            this.otherSkill = otherSkill;
        }

        public List<String> getLangSkill() {
            return langSkill;
        }

        public void setLangSkill(List<String> langSkill) {
            this.langSkill = langSkill;
        }

        public List<String> getCourt() {
            return court;
        }

        public void setCourt(List<String> court) {
            this.court = court;
        }

        public List<String> getProcuratorate() {
            return procuratorate;
        }

        public void setProcuratorate(List<String> procuratorate) {
            this.procuratorate = procuratorate;
        }
    }

    public static class ChildBean implements Serializable {
        /**
         * solutionMarkId : 21
         * solutionMarkName : 离婚诉讼
         * score : 45
         */

        private int solutionTypeId;
        private int solutionMarkId;
        private String solutionTypeName;
        private String solutionMarkName;
        private String titleIconImage;
        private int score;
        private List<ChildBean> child;

        public String getTitleIconImage() {
            return titleIconImage;
        }

        public void setTitleIconImage(String titleIconImage) {
            this.titleIconImage = titleIconImage;
        }

        public int getSolutionTypeId() {
            return solutionTypeId;
        }

        public void setSolutionTypeId(int solutionTypeId) {
            this.solutionTypeId = solutionTypeId;
        }

        public String getSolutionTypeName() {
            return solutionTypeName;
        }

        public void setSolutionTypeName(String solutionTypeName) {
            this.solutionTypeName = solutionTypeName;
        }

        public List<ChildBean> getChild() {
            return child;
        }

        public void setChild(List<ChildBean> child) {
            this.child = child;
        }

        public int getSolutionMarkId() {
            return solutionMarkId;
        }

        public void setSolutionMarkId(int solutionMarkId) {
            this.solutionMarkId = solutionMarkId;
        }

        public String getSolutionMarkName() {
            return solutionMarkName;
        }

        public void setSolutionMarkName(String solutionMarkName) {
            this.solutionMarkName = solutionMarkName;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }
    }
}
