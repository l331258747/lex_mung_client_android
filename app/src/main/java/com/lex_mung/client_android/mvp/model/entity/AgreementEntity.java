package com.lex_mung.client_android.mvp.model.entity;

public class AgreementEntity {
    private String userRegisterAgreenmentUrl;
    private String lawyerTariffExplanationUrl;
    private String depositExplain;

    public String getUserRegisterAgreenmentUrl() {
        return userRegisterAgreenmentUrl;
    }

    public void setUserRegisterAgreenmentUrl(String userRegisterAgreenmentUrl) {
        this.userRegisterAgreenmentUrl = userRegisterAgreenmentUrl;
    }

    public String getLawyerTariffExplanationUrl() {
        return lawyerTariffExplanationUrl;
    }

    public void setLawyerTariffExplanationUrl(String lawyerTariffExplanationUrl) {
        this.lawyerTariffExplanationUrl = lawyerTariffExplanationUrl;
    }

    public String getDepositExplain() {
        return depositExplain;
    }

    public void setDepositExplain(String depositExplain) {
        this.depositExplain = depositExplain;
    }
}
