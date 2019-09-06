package cn.lex_mung.client_android.mvp.model.entity.help;

public class FilterBean {
    /**
     * region : 长沙市
     * solutionType : 个人类-婚姻家事
     * requireType : 起草合同
     * amount : 不涉及金额
     */

    private String region;
    private String solutionType;
    private String requireType;
    private String amount;
    private String afford;
    private String industry;

    public String getAfford() {
        if(afford.equals("不限")) return "";
        return afford;
    }

    public String getIndustry() {
        if(industry.equals("不限")) return "";
        return industry;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSolutionType() {
        if(solutionType.equals("不限")) return "";
        return solutionType;
    }

    public void setSolutionType(String solutionType) {
        this.solutionType = solutionType;
    }

    public String getRequireType() {
        return requireType;
    }

    public void setRequireType(String requireType) {
        this.requireType = requireType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}