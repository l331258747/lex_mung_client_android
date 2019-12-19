package cn.lex_mung.client_android.mvp.model.entity.entrust;

import android.text.TextUtils;

import java.util.List;

public class EntrustListLawyersBean {
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
    private String advantage;
    private List<BusinessBean> business;
    private EntrustListLawyersTag tag;
    private String curriculumContent;

    public String getCurriculumContent() {
        return curriculumContent;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public String getMemberPositionName() {
        return memberPositionName;
    }

    public EntrustListLawyersTag getTag() {
        return tag;
    }

    public String getAdvantage() {
        return advantage;
    }

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