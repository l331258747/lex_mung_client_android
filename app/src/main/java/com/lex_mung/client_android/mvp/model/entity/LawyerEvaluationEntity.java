package com.lex_mung.client_android.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

public class LawyerEvaluationEntity implements Serializable {
    /**
     * code : aspect_vitality
     * text : 活跃度
     * description : 补充信息，提升活跃度，让更多用户找到你
     * tips : 回答免费文字咨询、快速抢单、接听用户电话、接收用户需求都将快速增加您在平台中的活跃度。
     * score : 52
     * maxScore : 100
     * hasFill : 1
     * child : [{"code":"point_info_integrity","text":"信息完善度","description":"","tips":"","score":2,"maxScore":10,"hasFill":1,"child":[{"code":"point_base","text":"基本信息","description":"","tips":"","score":0,"maxScore":0,"hasFill":1,"child":[]},{"code":"point_business_type","text":"擅长领域","description":"","tips":"","score":0,"maxScore":0,"hasFill":1,"child":[]},{"code":"point_service_price","text":"服务价格","description":"","tips":"","score":0,"maxScore":0,"hasFill":1,"child":[]},{"code":"point_industry","text":"熟悉的行业","description":"","tips":"","score":0,"maxScore":0,"hasFill":1,"child":[]},{"code":"point_court","text":"常去法院","description":"","tips":"","score":0,"maxScore":0,"hasFill":1,"child":[]},{"code":"point_procuratorate","text":"常去检察院","description":"","tips":"","score":0,"maxScore":0,"hasFill":1,"child":[]}]}]
     */

    private String code;
    private String text;
    private String description;
    private String tips;
    private int score;
    private int maxScore;
    private int hasFill;
    private int sortOrder;
    private List<LawyerEvaluationEntity> child;

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public int getHasFill() {
        return hasFill;
    }

    public void setHasFill(int hasFill) {
        this.hasFill = hasFill;
    }

    public List<LawyerEvaluationEntity> getChild() {
        return child;
    }

    public void setChild(List<LawyerEvaluationEntity> child) {
        this.child = child;
    }

}
