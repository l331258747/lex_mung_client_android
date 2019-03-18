package com.lex_mung.client_android.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

public class LawsHomePagerBaseEntity implements Serializable {

    /**
     * memberId : 5022
     * memberName : 杨纯
     * memberRoleId : 5
     * mobile :
     * sex : 0
     * birthday : 1985-01-01 00:00:00
     * age : 34岁
     * regionId : 430102
     * region : 湖南省，长沙市
     * institutionId : 3
     * institutionName : 湖南金州律师事务所
     * memberPositionName : 律师
     * businessDescription :
     * beginPracticeDate : 2018-12-26 00:00:00
     * practice : 执业不足1年
     * isCollect : 0
     * isOnline : 1
     * iconImage : http://oss.lex-mung.com/icon_image_member_1699914879.png
     * backgroundImage :
     * evaluation : {"aspectVitality":{"code":"aspect_vitality","text":"活跃度","description":"补充信息，提升活跃度，让更多用户找到你","tips":"回答免费文字咨询、快速抢单、接听用户电话、接收用户需求都将快速增加您在平台中的活跃度。","score":52,"maxScore":100,"hasFill":1,"child":[{"code":"point_info_integrity","text":"信息完善度","description":"","tips":"","score":2,"maxScore":10,"hasFill":1,"child":[{"code":"point_base","text":"基本信息","description":"","tips":"","score":0,"maxScore":0,"hasFill":1,"child":[]},{"code":"point_business_type","text":"擅长领域","description":"","tips":"","score":0,"maxScore":0,"hasFill":1,"child":[]},{"code":"point_service_price","text":"服务价格","description":"","tips":"","score":0,"maxScore":0,"hasFill":1,"child":[]},{"code":"point_industry","text":"熟悉的行业","description":"","tips":"","score":0,"maxScore":0,"hasFill":1,"child":[]},{"code":"point_court","text":"常去法院","description":"","tips":"","score":0,"maxScore":0,"hasFill":1,"child":[]},{"code":"point_procuratorate","text":"常去检察院","description":"","tips":"","score":0,"maxScore":0,"hasFill":1,"child":[]}]}]},"aspectReliability":{"code":"aspect_reliability","text":"可信度","description":"可信度分数的提高能让您在平台中获得更高价值的订单","tips":"","score":50,"maxScore":100,"hasFill":1,"child":[{"code":"point_qualification","text":"专业证书","description":"","tips":"","score":5,"maxScore":5,"hasFill":1,"child":[]},{"code":"point_social_function","text":"社会职务","description":"","tips":"","score":2,"maxScore":10,"hasFill":1,"child":[]},{"code":"point_honor","text":"所获荣誉","description":"","tips":"","score":3,"maxScore":5,"hasFill":1,"child":[]},{"code":"point_organization","text":"参加的组织","description":"","tips":"","score":0,"maxScore":15,"hasFill":0,"child":[]},{"code":"point_deposit","text":"增信","description":"","tips":"","score":0,"maxScore":10,"hasFill":0,"child":[]}]},"aspectSocial":{"code":"aspect_social","text":"社会资源","description":"社会资源分数的提升能让您在平台中","tips":"","score":79,"maxScore":100,"hasFill":1,"child":[{"code":"point_court","text":"常去法院","description":"","tips":"","score":10,"maxScore":10,"hasFill":1,"child":[]},{"code":"point_procuratorate","text":"常去检察院","description":"","tips":"","score":10,"maxScore":10,"hasFill":1,"child":[]},{"code":"point_education","text":"教育经历","description":"","tips":"","score":5,"maxScore":5,"hasFill":1,"child":[]},{"code":"point_social_social_function","text":"社会职务","description":"","tips":"","score":2,"maxScore":10,"hasFill":1,"child":[]},{"code":"point_social_honor","text":"所获荣誉","description":"","tips":"","score":2,"maxScore":10,"hasFill":1,"child":[]}]},"aspectLitigation":{"code":"aspect_litigation","text":"诉讼经验","description":"诉讼经验是平台根据您在裁判文书网上公开的案件进行获取，如有疑问，请与客服人员进行联系","tips":"","score":52,"maxScore":100,"hasFill":1,"child":[{"code":"point_lawsuit","text":"","description":"","tips":"","score":0,"maxScore":0,"hasFill":0,"child":[]},{"code":"point_litigation_practice_year","text":"执业年限","description":"","tips":"","score":2,"maxScore":10,"hasFill":1,"child":[]}]},"aspectNonLitigation":{"code":"aspect_non_litigation","text":"非诉经验","description":"非诉经验的提升能帮助您在平台中接到大量的企业订单","tips":"","score":52,"maxScore":100,"hasFill":1,"child":[{"code":"point_non_litigation","text":"","description":"","tips":"","score":0,"maxScore":0,"hasFill":0,"child":[]},{"code":"point_corporate_advisory","text":"","description":"","tips":"","score":0,"maxScore":0,"hasFill":0,"child":[]},{"code":"point_practice_year","text":"执业年限","description":"","tips":"","score":2,"maxScore":10,"hasFill":1,"child":[]}]}}
     * baseInfo : {"businessRadar":[{"businessTypeId":5,"businessTypeNo":"A1-01","businessTypeName":"离婚/夫妻财产/抚养权","score":0.92,"avgScore":0.08},{"businessTypeId":51,"businessTypeNo":"A1-15","businessTypeName":"遗嘱继承/分家析产","score":0.85,"avgScore":0.1},{"businessTypeId":28,"businessTypeNo":"A3-03","businessTypeName":"涉外婚姻","score":0.75,"avgScore":0.16},{"businessTypeId":27,"businessTypeNo":"A3-02","businessTypeName":"海外移民/投资","score":0.31,"avgScore":0.21},{"businessTypeId":6,"businessTypeNo":"A1-02","businessTypeName":"人身伤害/造谣诽谤/医疗纠纷","score":0.24,"avgScore":0.07},{"businessTypeId":48,"businessTypeNo":"A1-13","businessTypeName":"欠款催收/执行案件","score":0.23,"avgScore":0.08}],"skill":[{"skillId":1,"skillNo":"F1","skillName":"专业技能","iconImage":"","parentId":0,"children":[{"skillId":4,"skillNo":"F1-01","skillName":"文书写作","iconImage":"http://oss.lex-mung.com/skill_f1_01.png","parentId":1,"children":[]},{"skillId":5,"skillNo":"F1-02","skillName":"交流沟通","iconImage":"http://oss.lex-mung.com/skill_f1_02.png","parentId":1,"children":[]},{"skillId":6,"skillNo":"F1-03","skillName":"谈判/庭审","iconImage":"http://oss.lex-mung.com/skill_f1_03.png","parentId":1,"children":[]},{"skillId":7,"skillNo":"F1-04","skillName":"演讲培训","iconImage":"http://oss.lex-mung.com/skill_f1_04.png","parentId":1,"children":[]},{"skillId":8,"skillNo":"F1-05","skillName":"尽职调查","iconImage":"http://oss.lex-mung.com/skill_f1_05.png","parentId":1,"children":[]}]},{"skillId":2,"skillNo":"F2","skillName":"其他技能","iconImage":"","parentId":0,"children":[{"skillId":9,"skillNo":"F2-01","skillName":"会计师","iconImage":"","parentId":2,"children":[]},{"skillId":10,"skillNo":"F2-02","skillName":"审计师","iconImage":"","parentId":2,"children":[]}]},{"skillId":3,"skillNo":"F3","skillName":"语言技能","iconImage":"","parentId":0,"children":[{"skillId":18,"skillNo":"F3-01","skillName":"英语","iconImage":"","parentId":3,"children":[]},{"skillId":19,"skillNo":"F3-02","skillName":"日语","iconImage":"","parentId":3,"children":[]},{"skillId":20,"skillNo":"F3-03","skillName":"德语","iconImage":"","parentId":3,"children":[]}]}],"industry":["农/林/牧/渔/其他","保险／金融／信托／拍卖","建筑业／房地产","文化/传媒/娱乐/体育／广告"],"qualifi":["金融类"],"memberDescription":"个人简介，qsxhxudnxnk"}
     * reliabilityInfo : {"depositTags":[],"orgTags":[],"socialFunction":["区县级人大代表/政协委员"],"honor":["国家优秀律师"],"workExp":[{"tbMemberExtendWorkExperienceId":317,"institutionName":"法律顾问","positionName":"我的手机1小时1","workExperienceImage":"","startDate":"2018-12-24 00:00:00","endDate":"2018-12-24 00:00:00"},{"tbMemberExtendWorkExperienceId":318,"institutionName":"法律顾问","positionName":"我的手机1小时1","workExperienceImage":"","startDate":"2018-12-24 00:00:00","endDate":"2018-12-24 00:00:00"},{"tbMemberExtendWorkExperienceId":319,"institutionName":"法律顾问","positionName":"我的手机1小时1","workExperienceImage":"","startDate":"2018-12-24 00:00:00","endDate":"2018-12-24 00:00:00"},{"tbMemberExtendWorkExperienceId":320,"institutionName":"法律顾问","positionName":"我的手机1小时1","workExperienceImage":"","startDate":"2018-12-24 00:00:00","endDate":"2018-12-24 00:00:00"},{"tbMemberExtendWorkExperienceId":321,"institutionName":"法律顾问","positionName":"我的手机1小时1","workExperienceImage":"","startDate":"2018-12-24 00:00:00","endDate":"2018-12-24 00:00:00"}],"education":[{"memberExtendEducationId":306,"memberId":5022,"educationId":3,"educationTitle":"我的人生观价值观都","educationImage":"","startDate":"2018-12-24 00:00:00","endDate":"2018-12-24 00:00:00","memberExtendStatusId":1321,"sortOrder":0,"status":1,"dateAdded":"2018-12-24 15:04:00","dateModified":"1970-01-01 08:00:00","verifyUserId":0,"educationDescription":"法学","verifyComment":"","educationName":"本科"}]}
     * socialInfo : {"socialFunction":["区县级人大代表/政协委员"],"honor":["国家优秀律师"],"court":["湖南省长沙市天心区人民法院","长沙市岳麓区人民法院","长沙市芙蓉区人民法院","长沙市雨花区人民法院"],"procuratorate":["长沙市芙蓉区人民检察院","长沙市岳麓区人民检察院","长沙市开福区人民检察院","长沙县人民检察院"]}
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
    private String memberPositionName;
    private String businessDescription;
    private String beginPracticeDate;
    private String practice;
    private int isCollect;
    private int isOnline;
    private String iconImage;
    private String backgroundImage;
    private List<LawyerEvaluationEntity> evaluationList;
    private BaseInfoBean baseInfo;
    private ReliabilityInfoBean reliabilityInfo;
    private SocialInfoBean socialInfo;
    private List<LawyerTagsEntity> lawyerTags;
    private List<BusinessEntity> requireInfo;
    private List<DynamicInfoBean> activityInfo;

    public List<DynamicInfoBean> getActivityInfo() {
        return activityInfo;
    }

    public void setActivityInfo(List<DynamicInfoBean> activityInfo) {
        this.activityInfo = activityInfo;
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

    public String getMemberPositionName() {
        return memberPositionName;
    }

    public void setMemberPositionName(String memberPositionName) {
        this.memberPositionName = memberPositionName;
    }

    public String getBusinessDescription() {
        return businessDescription;
    }

    public void setBusinessDescription(String businessDescription) {
        this.businessDescription = businessDescription;
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

    public List<LawyerEvaluationEntity> getEvaluationList() {
        return evaluationList;
    }

    public void setEvaluationList(List<LawyerEvaluationEntity> evaluationList) {
        this.evaluationList = evaluationList;
    }

    public BaseInfoBean getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(BaseInfoBean baseInfo) {
        this.baseInfo = baseInfo;
    }

    public ReliabilityInfoBean getReliabilityInfo() {
        return reliabilityInfo;
    }

    public void setReliabilityInfo(ReliabilityInfoBean reliabilityInfo) {
        this.reliabilityInfo = reliabilityInfo;
    }

    public SocialInfoBean getSocialInfo() {
        return socialInfo;
    }

    public void setSocialInfo(SocialInfoBean socialInfo) {
        this.socialInfo = socialInfo;
    }

    public List<LawyerTagsEntity> getLawyerTags() {
        return lawyerTags;
    }

    public void setLawyerTags(List<LawyerTagsEntity> lawyerTags) {
        this.lawyerTags = lawyerTags;
    }

    public List<BusinessEntity> getRequireInfo() {
        return requireInfo;
    }

    public void setRequireInfo(List<BusinessEntity> requireInfo) {
        this.requireInfo = requireInfo;
    }

    public static class BaseInfoBean implements Serializable {
        /**
         * businessRadar : [{"businessTypeId":5,"businessTypeNo":"A1-01","businessTypeName":"离婚/夫妻财产/抚养权","score":0.92,"avgScore":0.08},{"businessTypeId":51,"businessTypeNo":"A1-15","businessTypeName":"遗嘱继承/分家析产","score":0.85,"avgScore":0.1},{"businessTypeId":28,"businessTypeNo":"A3-03","businessTypeName":"涉外婚姻","score":0.75,"avgScore":0.16},{"businessTypeId":27,"businessTypeNo":"A3-02","businessTypeName":"海外移民/投资","score":0.31,"avgScore":0.21},{"businessTypeId":6,"businessTypeNo":"A1-02","businessTypeName":"人身伤害/造谣诽谤/医疗纠纷","score":0.24,"avgScore":0.07},{"businessTypeId":48,"businessTypeNo":"A1-13","businessTypeName":"欠款催收/执行案件","score":0.23,"avgScore":0.08}]
         * skill : [{"skillId":1,"skillNo":"F1","skillName":"专业技能","iconImage":"","parentId":0,"children":[{"skillId":4,"skillNo":"F1-01","skillName":"文书写作","iconImage":"http://oss.lex-mung.com/skill_f1_01.png","parentId":1,"children":[]},{"skillId":5,"skillNo":"F1-02","skillName":"交流沟通","iconImage":"http://oss.lex-mung.com/skill_f1_02.png","parentId":1,"children":[]},{"skillId":6,"skillNo":"F1-03","skillName":"谈判/庭审","iconImage":"http://oss.lex-mung.com/skill_f1_03.png","parentId":1,"children":[]},{"skillId":7,"skillNo":"F1-04","skillName":"演讲培训","iconImage":"http://oss.lex-mung.com/skill_f1_04.png","parentId":1,"children":[]},{"skillId":8,"skillNo":"F1-05","skillName":"尽职调查","iconImage":"http://oss.lex-mung.com/skill_f1_05.png","parentId":1,"children":[]}]},{"skillId":2,"skillNo":"F2","skillName":"其他技能","iconImage":"","parentId":0,"children":[{"skillId":9,"skillNo":"F2-01","skillName":"会计师","iconImage":"","parentId":2,"children":[]},{"skillId":10,"skillNo":"F2-02","skillName":"审计师","iconImage":"","parentId":2,"children":[]}]},{"skillId":3,"skillNo":"F3","skillName":"语言技能","iconImage":"","parentId":0,"children":[{"skillId":18,"skillNo":"F3-01","skillName":"英语","iconImage":"","parentId":3,"children":[]},{"skillId":19,"skillNo":"F3-02","skillName":"日语","iconImage":"","parentId":3,"children":[]},{"skillId":20,"skillNo":"F3-03","skillName":"德语","iconImage":"","parentId":3,"children":[]}]}]
         * industry : ["农/林/牧/渔/其他","保险／金融／信托／拍卖","建筑业／房地产","文化/传媒/娱乐/体育／广告"]
         * qualifi : ["金融类"]
         * memberDescription : 个人简介，qsxhxudnxnk
         */

        private String memberDescription;
        private List<BusinessRadarBean> businessRadar;
        private List<JobSkillsEntity> skill;
        private List<String> industry;
        private List<String> qualifi;

        public String getMemberDescription() {
            return memberDescription;
        }

        public void setMemberDescription(String memberDescription) {
            this.memberDescription = memberDescription;
        }

        public List<BusinessRadarBean> getBusinessRadar() {
            return businessRadar;
        }

        public void setBusinessRadar(List<BusinessRadarBean> businessRadar) {
            this.businessRadar = businessRadar;
        }

        public List<JobSkillsEntity> getSkill() {
            return skill;
        }

        public void setSkill(List<JobSkillsEntity> skill) {
            this.skill = skill;
        }

        public List<String> getIndustry() {
            return industry;
        }

        public void setIndustry(List<String> industry) {
            this.industry = industry;
        }

        public List<String> getQualifi() {
            return qualifi;
        }

        public void setQualifi(List<String> qualifi) {
            this.qualifi = qualifi;
        }

        public static class BusinessRadarBean implements Serializable {
            /**
             * businessTypeId : 5
             * businessTypeNo : A1-01
             * businessTypeName : 离婚/夫妻财产/抚养权
             * score : 0.92
             * avgScore : 0.08
             */

            private int businessTypeId;
            private String businessTypeNo;
            private String businessTypeName;
            private double score;
            private double avgScore;

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

            public double getScore() {
                return score;
            }

            public void setScore(double score) {
                this.score = score;
            }

            public double getAvgScore() {
                return avgScore;
            }

            public void setAvgScore(double avgScore) {
                this.avgScore = avgScore;
            }
        }

        public static class SkillBean implements Serializable {
            /**
             * skillId : 1
             * skillNo : F1
             * skillName : 专业技能
             * iconImage :
             * parentId : 0
             * children : [{"skillId":4,"skillNo":"F1-01","skillName":"文书写作","iconImage":"http://oss.lex-mung.com/skill_f1_01.png","parentId":1,"children":[]},{"skillId":5,"skillNo":"F1-02","skillName":"交流沟通","iconImage":"http://oss.lex-mung.com/skill_f1_02.png","parentId":1,"children":[]},{"skillId":6,"skillNo":"F1-03","skillName":"谈判/庭审","iconImage":"http://oss.lex-mung.com/skill_f1_03.png","parentId":1,"children":[]},{"skillId":7,"skillNo":"F1-04","skillName":"演讲培训","iconImage":"http://oss.lex-mung.com/skill_f1_04.png","parentId":1,"children":[]},{"skillId":8,"skillNo":"F1-05","skillName":"尽职调查","iconImage":"http://oss.lex-mung.com/skill_f1_05.png","parentId":1,"children":[]}]
             */

            private int skillId;
            private String skillNo;
            private String skillName;
            private String iconImage;
            private int parentId;
            private List<ChildrenBean> children;

            public int getSkillId() {
                return skillId;
            }

            public void setSkillId(int skillId) {
                this.skillId = skillId;
            }

            public String getSkillNo() {
                return skillNo;
            }

            public void setSkillNo(String skillNo) {
                this.skillNo = skillNo;
            }

            public String getSkillName() {
                return skillName;
            }

            public void setSkillName(String skillName) {
                this.skillName = skillName;
            }

            public String getIconImage() {
                return iconImage;
            }

            public void setIconImage(String iconImage) {
                this.iconImage = iconImage;
            }

            public int getParentId() {
                return parentId;
            }

            public void setParentId(int parentId) {
                this.parentId = parentId;
            }

            public List<ChildrenBean> getChildren() {
                return children;
            }

            public void setChildren(List<ChildrenBean> children) {
                this.children = children;
            }

            public static class ChildrenBean implements Serializable {
                /**
                 * skillId : 4
                 * skillNo : F1-01
                 * skillName : 文书写作
                 * iconImage : http://oss.lex-mung.com/skill_f1_01.png
                 * parentId : 1
                 * children : []
                 */

                private int skillId;
                private String skillNo;
                private String skillName;
                private String iconImage;
                private int parentId;

                public int getSkillId() {
                    return skillId;
                }

                public void setSkillId(int skillId) {
                    this.skillId = skillId;
                }

                public String getSkillNo() {
                    return skillNo;
                }

                public void setSkillNo(String skillNo) {
                    this.skillNo = skillNo;
                }

                public String getSkillName() {
                    return skillName;
                }

                public void setSkillName(String skillName) {
                    this.skillName = skillName;
                }

                public String getIconImage() {
                    return iconImage;
                }

                public void setIconImage(String iconImage) {
                    this.iconImage = iconImage;
                }

                public int getParentId() {
                    return parentId;
                }

                public void setParentId(int parentId) {
                    this.parentId = parentId;
                }
            }
        }
    }

    public static class ReliabilityInfoBean implements Serializable {
        private List<OrgTagsEntity> depositTags;
        private List<OrgTagsEntity> orgTags;
        private List<String> socialFunction;
        private List<String> honor;
        private List<WorkexpEntity> workExp;
        private List<EducationBackgroundEntity> education;

        public List<OrgTagsEntity> getDepositTags() {
            return depositTags;
        }

        public void setDepositTags(List<OrgTagsEntity> depositTags) {
            this.depositTags = depositTags;
        }

        public List<OrgTagsEntity> getOrgTags() {
            return orgTags;
        }

        public void setOrgTags(List<OrgTagsEntity> orgTags) {
            this.orgTags = orgTags;
        }

        public List<String> getSocialFunction() {
            return socialFunction;
        }

        public void setSocialFunction(List<String> socialFunction) {
            this.socialFunction = socialFunction;
        }

        public List<String> getHonor() {
            return honor;
        }

        public void setHonor(List<String> honor) {
            this.honor = honor;
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

    public static class SocialInfoBean implements Serializable {
        private List<String> socialFunction;
        private List<String> honor;
        private List<String> court;
        private List<String> procuratorate;

        public List<String> getSocialFunction() {
            return socialFunction;
        }

        public void setSocialFunction(List<String> socialFunction) {
            this.socialFunction = socialFunction;
        }

        public List<String> getHonor() {
            return honor;
        }

        public void setHonor(List<String> honor) {
            this.honor = honor;
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

    public static class DynamicInfoBean implements Serializable {

        /**
         * lawyerDynamicId : 0
         * lawyerDynamicType : 1
         * relatedId : 0
         * createTime : 2019-01-25 17:57:36
         * lawyerDynamicContent : 完成了一个合同方面的快速咨询
         * relatedSummary : {"icon":"","text":""}
         */

        private int lawyerDynamicId;
        private int lawyerDynamicType;
        private int relatedId;
        private String createTime;
        private String lawyerDynamicContent;
        private RelatedSummaryBean relatedSummary;

        public int getLawyerDynamicId() {
            return lawyerDynamicId;
        }

        public void setLawyerDynamicId(int lawyerDynamicId) {
            this.lawyerDynamicId = lawyerDynamicId;
        }

        public int getLawyerDynamicType() {
            return lawyerDynamicType;
        }

        public void setLawyerDynamicType(int lawyerDynamicType) {
            this.lawyerDynamicType = lawyerDynamicType;
        }

        public int getRelatedId() {
            return relatedId;
        }

        public void setRelatedId(int relatedId) {
            this.relatedId = relatedId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getLawyerDynamicContent() {
            return lawyerDynamicContent;
        }

        public void setLawyerDynamicContent(String lawyerDynamicContent) {
            this.lawyerDynamicContent = lawyerDynamicContent;
        }

        public RelatedSummaryBean getRelatedSummary() {
            return relatedSummary;
        }

        public void setRelatedSummary(RelatedSummaryBean relatedSummary) {
            this.relatedSummary = relatedSummary;
        }

        public static class RelatedSummaryBean implements Serializable {
            /**
             * icon :
             * text :
             */

            private String icon;
            private String text;

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }
        }
    }


}
