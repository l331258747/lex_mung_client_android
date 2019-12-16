package cn.lex_mung.client_android.mvp.model.entity.home;

public class PagesSecondEntity {


    /**
     * litigationUrl : https://h5-test.lex-mung.com/feature.html
     * retrialUrl : https://h5-test.lex-mung.com/retrial.html
     * caseRiskUrl : https://h5-test.lex-mung.com/risk.html
     * caseEntrustedUrl : https://h5-test.lex-mung.com/caseEntrustment.html
     * memberCard : https://h5.lex-mung.com/member/
     * examinUrl : https://h5-test.lex-mung.com/riskCheckUp.html
     */

    private String hotContractUrl;
    private String litigationUrl;
    private String retrialUrl;
    private String caseRiskUrl;
    private String caseEntrustedUrl;
    private String memberCard;
    private String examinUrl;
    private String quickConsultationUrl;
    private String onlineLegalUrl;
    private String lawyerGroupUrl;

    public String getHotContractUrl() {
        return hotContractUrl;
    }

    public String getLawyerGroupUrl() {
        return lawyerGroupUrl;
    }

    public String getQuickConsultationUrl() {
        return quickConsultationUrl;
    }

    public String getOnlineLegalUrl() {
        return onlineLegalUrl;
    }

    public String getLitigationUrl() {
        return litigationUrl;
    }

    public void setLitigationUrl(String litigationUrl) {
        this.litigationUrl = litigationUrl;
    }

    public String getRetrialUrl() {
        return retrialUrl;
    }

    public void setRetrialUrl(String retrialUrl) {
        this.retrialUrl = retrialUrl;
    }

    public String getCaseRiskUrl() {
        return caseRiskUrl;
    }

    public void setCaseRiskUrl(String caseRiskUrl) {
        this.caseRiskUrl = caseRiskUrl;
    }

    public String getCaseEntrustedUrl() {
        return caseEntrustedUrl;
    }

    public void setCaseEntrustedUrl(String caseEntrustedUrl) {
        this.caseEntrustedUrl = caseEntrustedUrl;
    }

    public String getMemberCard() {
        return memberCard;
    }

    public void setMemberCard(String memberCard) {
        this.memberCard = memberCard;
    }

    public String getExaminUrl() {
        return examinUrl;
    }

    public void setExaminUrl(String examinUrl) {
        this.examinUrl = examinUrl;
    }
}
