package cn.lex_mung.client_android.mvp.model.entity.help;

import java.util.List;

public class HelpStepEntity {


    private List<RequireTypeBean> requireType;
    private List<RequirementInvolveAmountBean> requirementInvolveAmount;
    private List<SolutionTypeBean> solutionType;
    private List<IndustryEntity> industry;
    private List<PayMoneyEntity> afford;

    public List<IndustryEntity> getIndustryEntities() {
        return industry;
    }

    public void setIndustryEntities(List<IndustryEntity> industry) {
        this.industry = industry;
    }

    public List<PayMoneyEntity> getPayMoneyEntities() {
        return afford;
    }

    public void setPayMoneyEntities(List<PayMoneyEntity> afford) {
        this.afford = afford;
    }

    public List<RequireTypeBean> getRequireType() {
        return requireType;
    }

    public void setRequireType(List<RequireTypeBean> requireType) {
        this.requireType = requireType;
    }

    public List<RequirementInvolveAmountBean> getRequirementInvolveAmount() {
        return requirementInvolveAmount;
    }

    public void setRequirementInvolveAmount(List<RequirementInvolveAmountBean> requirementInvolveAmount) {
        this.requirementInvolveAmount = requirementInvolveAmount;
    }

    public List<SolutionTypeBean> getSolutionType() {
        return solutionType;
    }

    public void setSolutionType(List<SolutionTypeBean> solutionType) {
        this.solutionType = solutionType;
    }






}

