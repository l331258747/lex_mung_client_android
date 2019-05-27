package cn.lex_mung.client_android.mvp.model.entity.help;

import java.util.List;

public class HelpStepLawyerEntity {


    /**
     * filter : {"region":"长沙市","solutionType":"个人类-婚姻家事","requireType":"起草合同","amount":"不涉及金额"}
     * recommendLawyer : [{"memberId":60,"memberName":"黄婧","institutionId":3,"institutionName":"湖南金州律师事务所","sex":0,"birthday":"1970-01-01 00:00:01","iconImage":"http://oss.lex-mung.com/icon_image_member_2430e3439.jpg","regionId":430100,"region":"湖南省-长沙市","memberPositionId":4,"memberPositionName":"律师","beginPracticeDate":"2016-03-01 00:00:00","practice":"执业3年","isOnline":1,"description":"抚养/收养、财产继承、买卖合同纠纷、二手房买卖、房屋赠与/继承、家庭暴力","orgTags":[{"tagName":"长沙市侨商会专属律师团律师","image":"http://oss.lex-mung.com/organization_aa0a4d35b.jpg","link":"https://h5-test.lex-mung.com/organization.html?id=1&organizationLevId=22"}],"matchDegree":"99%","businessInfo":[{"solutionTypeId":2,"solutionTypeName":"婚姻家事","titleIconImage":"http://oss.lex-mung.com/solution_type_title_2.png","child":[{"solutionMarkId":21,"solutionMarkName":"离婚诉讼","score":20},{"solutionMarkId":22,"solutionMarkName":"夫妻财产/债务","score":10},{"solutionMarkId":23,"solutionMarkName":"家庭暴力","score":40},{"solutionMarkId":24,"solutionMarkName":"抚养/收养","score":70},{"solutionMarkId":65,"solutionMarkName":"财产继承","score":60}]},{"solutionTypeId":6,"solutionTypeName":"重大财产处置与保护","titleIconImage":"http://oss.lex-mung.com/solution_type_title_6.png","child":[{"solutionMarkId":35,"solutionMarkName":"房屋销售/买卖","score":30},{"solutionMarkId":36,"solutionMarkName":"二手房买卖","score":50},{"solutionMarkId":128,"solutionMarkName":"房屋赠与/继承","score":50}]},{"solutionTypeId":7,"solutionTypeName":"一般合同纠纷","titleIconImage":"http://oss.lex-mung.com/solution_type_title_7.png","child":[{"solutionMarkId":38,"solutionMarkName":"买卖合同纠纷","score":50},{"solutionMarkId":39,"solutionMarkName":"施工合同纠纷","score":30}]},{"solutionTypeId":18,"solutionTypeName":"重大基础项目建设","titleIconImage":"http://oss.lex-mung.com/solution_type_title_25.png","child":[{"solutionMarkId":105,"solutionMarkName":"公司治理","score":10},{"solutionMarkId":106,"solutionMarkName":"股东保护和股权架构","score":20},{"solutionMarkId":148,"solutionMarkName":"员工股权激励","score":10}]},{"solutionTypeId":17,"solutionTypeName":"海外投资","titleIconImage":"http://oss.lex-mung.com/solution_type_title_17.png","child":[{"solutionMarkId":111,"solutionMarkName":"境内IPO","score":10},{"solutionMarkId":142,"solutionMarkName":"股权投资VC/PE","score":10}]}],"socialFunction":[],"requireInfo":[{"memberId":60,"requireTypeId":3,"requireTypeSectionId":35,"minAmount":1000,"creater":"","dateAdded":"2018-03-06 10:03:58","dateModified":"2018-07-12 10:24:02","requireTypeName":"起草合同","rstatus":2,"iconImage":"http://oss.lex-mung.com/organization_image_15514318526639.png","image":"http://oss.lex-mung.com/organization_image_15514318490679.png","unit":"份","parentId":0,"type":1,"requireTypeDescription":"审查或完全撰写一份合同","requirementType":1,"status":1,"requireTypes":[],"requires":[{"memberId":60,"requireTypeId":10,"requireTypeSectionId":181,"minAmount":1000,"creater":"ceshi666","dateAdded":"2019-01-28 15:08:02","dateModified":"2019-01-28 15:08:02","requireTypeName":"起草简式合同","rstatus":2,"iconImage":"http://oss.lex-mung.com/organization_image_15506535018269.jpg","image":"http://oss.lex-mung.com/organization_image_15506534901419.jpg","unit":"起","parentId":3,"type":1,"requireTypeDescription":"适用于法律关系比较简单，关系人比较熟悉，签订合同主要是证明作用","requirementType":1,"status":1,"requireTypes":[],"requires":[],"rcount":0}],"rcount":1}]}]
     */

    private FilterBean filter;
    private List<RecommendLawyerBean> recommendLawyer;

    public FilterBean getFilter() {
        return filter;
    }

    public void setFilter(FilterBean filter) {
        this.filter = filter;
    }

    public List<RecommendLawyerBean> getRecommendLawyer() {
        return recommendLawyer;
    }

    public void setRecommendLawyer(List<RecommendLawyerBean> recommendLawyer) {
        this.recommendLawyer = recommendLawyer;
    }
}
