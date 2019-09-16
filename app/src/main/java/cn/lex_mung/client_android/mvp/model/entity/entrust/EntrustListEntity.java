package cn.lex_mung.client_android.mvp.model.entity.entrust;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class EntrustListEntity {

    /**
     * id : 2
     * type : 0
     * orderNo : 123456
     * memberName : 用户我我我
     * mobile : 15367889204
     * deviceTypeId : 0
     * regionId : 430100
     * title : 回火
     * orderStatus : 4
     * amount : 10000.0
     * lawyerAmount : 1000.0
     * platformAmount : 100.0
     * authentication : 1
     * procuration : 1
     * solutionTypeId : 11
     * isRecommend : 1
     * recommendTime :
     * enrollEffectiveNum : 30
     * enrollEffectiveTime : 2019-08-22 23:25:05
     * enrollMemberNum : 20
     * lawyerMemberId : 0
     * createUserId : 0
     * createDate : 2019-08-21 18:25:39
     * updateDate :
     * publishTime : 2019-08-21 18:25:45
     * publishUserId : 0
     * verifyUserId : 0
     * verifyComment :
     * verifyDate :
     * caseEffectiveTime : 2019-08-21 23:26:10
     * description : 等级客户端USDHDUHNJDFJDJ那份
     * pageNum : 0
     * pageSize : 0
     * startTime :
     * endTime :
     * rname : 湖南省 长沙市
     * iconImage : http://oss.lex-mung.com/icon_image_member_505463558.jpg
     * liconImage :
     * caseTypeName :
     * createUserName :
     * verifyUserName :
     * publishUserName :
     * lmemberName :
     * lmemberId : 0
     * lawyerStatus : 0
     * files : []
     * lawyers : [{"id":2,"orderNo":"123456","memberId":5020,"status":1,"createDate":"2019-08-22 18:08:07","beginPrDate":4,"regionId":430100,"rname":"湖南省 长沙市","memberName":"律师我我我","iconImage":"http://oss.lex-mung.com/icon_image_member_15467318541129.png","mobile":"13135110211","business":[{"solutionTypeId":9,"solutionTypeName":"不动产销售与租赁","titleIconImage":"http://oss.lex-mung.com/solution_type_title_19.png","child":[{"solutionMarkId":95,"solutionMarkName":"物业管理纠纷","score":30},{"solutionMarkId":96,"solutionMarkName":"共有物业","score":100}]},{"solutionTypeId":8,"solutionTypeName":"基础项目建设","titleIconImage":"http://oss.lex-mung.com/solution_type_title_25.png","child":[{"solutionMarkId":42,"solutionMarkName":"建设施工合同纠纷","score":30}]}]}]
     */

    private int id;
    private int type;
    private String orderNo;
    private String memberName;
    private String mobile;
    private int deviceTypeId;
    private int regionId;
    private String title;
    private int orderStatus;
    private double amount;
    private double lawyerAmount;
    private double platformAmount;
    private int authentication;
    private int procuration;
    private int solutionTypeId;
    private int isRecommend;
    private String recommendTime;
    private int enrollEffectiveNum;
    private String enrollEffectiveTime;
    private int enrollMemberNum;
    private int lawyerMemberId;
    private int createUserId;
    private String createDate;
    private String updateDate;
    private String publishTime;
    private int publishUserId;
    private int verifyUserId;
    private String verifyComment;
    private String verifyDate;
    private String caseEffectiveTime;
    private String description;
    private int pageNum;
    private int pageSize;
    private String startTime;
    private String endTime;
    private String rname;
    private String iconImage;
    private String liconImage;
    private String caseTypeName;
    private String createUserName;
    private String verifyUserName;
    private String publishUserName;
    private String lmemberName;
    private int lmemberId;
    private int lawyerStatus;
    private String h5Url;
    private List<?> files;
    private List<LawyersBean> lawyers;
    private String amountStr;
    private String lawyerAmountStr;
    private String platformAmountStr;
    private int showAdvantage;
    private String institutionName;//TODO

    public int getShowAdvantage() {//显示律师优势（1是0否）
        return showAdvantage;
    }

    public String getH5Url() {
        return h5Url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(int deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public String getOrderStatusStr() {
        //	4待确认，6已确认，7待打款，8已打款，9已完成，10已关闭，11已删除
        switch (orderStatus) {
            case 4:
                return "待确认";
            case 6:
                return "已确认";
            case 7:
                return "待打款";
            case 8:
                return "已打款";
            case 9:
                return "已完成";
            case 10:
                return "已关闭";
            case 11:
                return "已删除";
        }
        return "";
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public double getAmount() {
        return amount;
    }

    public String getAmountStr2() {
        return amountStr;
    }

    public String getAmountStr() {
        if (!TextUtils.isEmpty(amountStr)) {
            return "涉案标的额:" + amountStr;
        }
        return "";
    }


    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getLawyerAmount() {
        return lawyerAmount;
    }

    public String getLawyerAmountStr2() {
        return lawyerAmountStr;
    }

    public String getLawyerAmountStr() {
        if (!TextUtils.isEmpty(lawyerAmountStr)) {
            return "律师代理费:" + lawyerAmountStr;
        }
        return "";
    }

    public void setLawyerAmount(double lawyerAmount) {
        this.lawyerAmount = lawyerAmount;
    }

    public double getPlatformAmount() {
        return platformAmount;
    }

    public String getPlatformAmountStr2() {
        if (!TextUtils.isEmpty(platformAmountStr)) {
            return platformAmountStr;
        }
        return "";
    }

    public void setPlatformAmount(double platformAmount) {
        this.platformAmount = platformAmount;
    }

    public int getAuthentication() {
        return authentication;
    }

    public void setAuthentication(int authentication) {
        this.authentication = authentication;
    }

    public int getProcuration() {
        return procuration;
    }

    public String getProcurationStr() {
        if (procuration == 1)
            return "一般代理";
        if (procuration == 2)
            return "风险代理";
        if(procuration == 3)
            return "半风险代理";
        return "";
    }

    public void setProcuration(int procuration) {
        this.procuration = procuration;
    }

    public int getSolutionTypeId() {
        return solutionTypeId;
    }

    public void setSolutionTypeId(int solutionTypeId) {
        this.solutionTypeId = solutionTypeId;
    }

    public int getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(int isRecommend) {
        this.isRecommend = isRecommend;
    }

    public String getRecommendTime() {
        return recommendTime;
    }

    public void setRecommendTime(String recommendTime) {
        this.recommendTime = recommendTime;
    }

    public int getEnrollEffectiveNum() {
        return enrollEffectiveNum;
    }

    public void setEnrollEffectiveNum(int enrollEffectiveNum) {
        this.enrollEffectiveNum = enrollEffectiveNum;
    }

    public String getEnrollEffectiveTime() {
        return enrollEffectiveTime;
    }

    public void setEnrollEffectiveTime(String enrollEffectiveTime) {
        this.enrollEffectiveTime = enrollEffectiveTime;
    }

    public int getEnrollMemberNum() {
        return enrollMemberNum;
    }

    public List<String> getTabs() {
        List<String> tabs = new ArrayList<>();
        if (!TextUtils.isEmpty(getProcurationStr())) {
            tabs.add(getProcurationStr());
        }
        if (!TextUtils.isEmpty(getAmountStr())) {
            tabs.add(getAmountStr());
        }
        if (!TextUtils.isEmpty(getLawyerAmountStr())) {
            tabs.add(getLawyerAmountStr());
        }
        return tabs;
    }

    public void setEnrollMemberNum(int enrollMemberNum) {
        this.enrollMemberNum = enrollMemberNum;
    }

    public int getLawyerMemberId() {
        return lawyerMemberId;
    }

    public void setLawyerMemberId(int lawyerMemberId) {
        this.lawyerMemberId = lawyerMemberId;
    }

    public int getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(int createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public int getPublishUserId() {
        return publishUserId;
    }

    public void setPublishUserId(int publishUserId) {
        this.publishUserId = publishUserId;
    }

    public int getVerifyUserId() {
        return verifyUserId;
    }

    public void setVerifyUserId(int verifyUserId) {
        this.verifyUserId = verifyUserId;
    }

    public String getVerifyComment() {
        return verifyComment;
    }

    public void setVerifyComment(String verifyComment) {
        this.verifyComment = verifyComment;
    }

    public String getVerifyDate() {
        return verifyDate;
    }

    public void setVerifyDate(String verifyDate) {
        this.verifyDate = verifyDate;
    }

    public String getCaseEffectiveTime() {
        return caseEffectiveTime;
    }

    public void setCaseEffectiveTime(String caseEffectiveTime) {
        this.caseEffectiveTime = caseEffectiveTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public String getIconImage() {
        if (TextUtils.isEmpty(iconImage)) return "";
        if (!iconImage.startsWith("http"))
            return "";
        return iconImage;
    }

    public void setIconImage(String iconImage) {
        this.iconImage = iconImage;
    }

    public String getLiconImage() {
        return liconImage;
    }

    public void setLiconImage(String liconImage) {
        this.liconImage = liconImage;
    }

    public String getCaseTypeName() {
        return caseTypeName;
    }

    public void setCaseTypeName(String caseTypeName) {
        this.caseTypeName = caseTypeName;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getVerifyUserName() {
        return verifyUserName;
    }

    public void setVerifyUserName(String verifyUserName) {
        this.verifyUserName = verifyUserName;
    }

    public String getPublishUserName() {
        return publishUserName;
    }

    public void setPublishUserName(String publishUserName) {
        this.publishUserName = publishUserName;
    }

    public String getLMemeberName2(){//TODO
        if(TextUtils.isEmpty(institutionName)){
            return rname;
        }
        return rname + " | " + institutionName;
    }

    public String getLmemberName() {
        return lmemberName;
    }

    public void setLmemberName(String lmemberName) {
        this.lmemberName = lmemberName;
    }

    public int getLmemberId() {
        return lmemberId;
    }

    public void setLmemberId(int lmemberId) {
        this.lmemberId = lmemberId;
    }

    public int getLawyerStatus() {
        return lawyerStatus;
    }

    public void setLawyerStatus(int lawyerStatus) {
        this.lawyerStatus = lawyerStatus;
    }

    public List<?> getFiles() {
        return files;
    }

    public void setFiles(List<?> files) {
        this.files = files;
    }

    public List<LawyersBean> getLawyers() {
        return lawyers;
    }

    public void setLawyers(List<LawyersBean> lawyers) {
        this.lawyers = lawyers;
    }

    public static class LawyersBean {
        /**
         * id : 2
         * orderNo : 123456
         * memberId : 5020
         * status : 1
         * createDate : 2019-08-22 18:08:07
         * beginPrDate : 4
         * regionId : 430100
         * rname : 湖南省 长沙市
         * memberName : 律师我我我
         * iconImage : http://oss.lex-mung.com/icon_image_member_15467318541129.png
         * mobile : 13135110211
         * business : [{"solutionTypeId":9,"solutionTypeName":"不动产销售与租赁","titleIconImage":"http://oss.lex-mung.com/solution_type_title_19.png","child":[{"solutionMarkId":95,"solutionMarkName":"物业管理纠纷","score":30},{"solutionMarkId":96,"solutionMarkName":"共有物业","score":100}]},{"solutionTypeId":8,"solutionTypeName":"基础项目建设","titleIconImage":"http://oss.lex-mung.com/solution_type_title_25.png","child":[{"solutionMarkId":42,"solutionMarkName":"建设施工合同纠纷","score":30}]}]
         */

        private int id;
        private String orderNo;
        private int memberId;
        private int status;
        private String createDate;
        private int beginPrDate;
        private int regionId;
        private String rname;
        private String memberName;
        private String iconImage;
        private String mobile;
        private String institutionName;
        private String memberPositionName;
        private List<BusinessBean> business;

        public String getMemberPositionNameStr() {
            if (TextUtils.isEmpty(memberPositionName)) return "";
            return "（" + memberPositionName + "）";
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public int getMemberId() {
            return memberId;
        }

        public String getDescriptionStr() {
            StringBuffer sb = new StringBuffer("");
            if (business == null || business.size() == 0) {
                return sb.toString();
            }
            sb.append("擅长领域：");
            for (int i = 0; i < business.size(); i++) {
                if (business.get(i).getChild() != null && business.get(i).getChild().size() > 0) {
                    for (int j = 0; j < business.get(i).getChild().size(); j++) {
                        sb.append(business.get(i).getChild().get(j).getSolutionMarkName() + "、");
                    }
                }
            }
            String str = sb.toString();
            if (TextUtils.isEmpty(str)) return "";
            if (str.endsWith("、")) {
                str = str.substring(0, str.length() - 1);
            }

            return str;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public int getBeginPrDate() {
            return beginPrDate;
        }

        public void setBeginPrDate(int beginPrDate) {
            this.beginPrDate = beginPrDate;
        }

        public int getRegionId() {
            return regionId;
        }

        public void setRegionId(int regionId) {
            this.regionId = regionId;
        }

        public String getRname() {
            return rname;
        }

        public void setRname(String rname) {
            this.rname = rname;
        }

        public String getMemberName() {
            return memberName;
        }

        public String getReginInstitutionName() {
            if (TextUtils.isEmpty(rname) && TextUtils.isEmpty(institutionName))
                return "";
            if (TextUtils.isEmpty(rname))
                return institutionName;
            if (TextUtils.isEmpty(institutionName))
                return rname;
            return rname + " | " + institutionName;
        }

        public void setMemberName(String memberName) {
            this.memberName = memberName;
        }

        public String getIconImage() {
            return iconImage;
        }

        public void setIconImage(String iconImage) {
            this.iconImage = iconImage;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public List<BusinessBean> getBusiness() {
            return business;
        }

        public void setBusiness(List<BusinessBean> business) {
            this.business = business;
        }

        public static class BusinessBean {
            /**
             * solutionTypeId : 9
             * solutionTypeName : 不动产销售与租赁
             * titleIconImage : http://oss.lex-mung.com/solution_type_title_19.png
             * child : [{"solutionMarkId":95,"solutionMarkName":"物业管理纠纷","score":30},{"solutionMarkId":96,"solutionMarkName":"共有物业","score":100}]
             */

            private int solutionTypeId;
            private String solutionTypeName;
            private String titleIconImage;
            private List<ChildBean> child;

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

            public String getTitleIconImage() {
                return titleIconImage;
            }

            public void setTitleIconImage(String titleIconImage) {
                this.titleIconImage = titleIconImage;
            }

            public List<ChildBean> getChild() {
                return child;
            }

            public void setChild(List<ChildBean> child) {
                this.child = child;
            }

            public static class ChildBean {
                /**
                 * solutionMarkId : 95
                 * solutionMarkName : 物业管理纠纷
                 * score : 30
                 */

                private int solutionMarkId;
                private String solutionMarkName;
                private int score;

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
    }
}
