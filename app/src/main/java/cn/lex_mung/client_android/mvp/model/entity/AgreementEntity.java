package cn.lex_mung.client_android.mvp.model.entity;

public class AgreementEntity {
    private String userRegisterAgreenmentUrl;
    private String lawyerTariffExplanationUrl;
    private String depositExplain;
    private String tariffExplanationUrl;
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTariffExplanationUrl() {
        return tariffExplanationUrl;
    }

    public void setTariffExplanationUrl(String tariffExplanationUrl) {
        this.tariffExplanationUrl = tariffExplanationUrl;
    }

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
