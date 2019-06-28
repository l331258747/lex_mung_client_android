package cn.lex_mung.client_android.mvp.model.entity.help;

import java.io.Serializable;
import java.util.List;

import cn.lex_mung.client_android.mvp.model.entity.SelectList;

public class SolutionTypeBean implements Serializable,SelectList {

    /**
     * solutionTypeId : 2
     * solutionTypeName : 个人类
     * isGroup : 1
     * child : [{"solutionTypeId":2,"solutionTypeName":"婚姻家事","isGroup":0,"child":[],"description":"如：离婚诉讼,夫妻财产/债务,家庭暴力,抚养/收养,财产继承等"},{"solutionTypeId":10,"solutionTypeName":"借贷/抵押/理财","isGroup":0,"child":[],"description":"如：民间借贷,P2P借贷,抵押担保,民间集资,金融理财等"},{"solutionTypeId":3,"solutionTypeName":"交通事故","isGroup":0,"child":[],"description":"如：民间借贷,融资租赁,融资担保,股权融资,保理/票据融资等"},{"solutionTypeId":8,"solutionTypeName":"医疗纠纷","isGroup":0,"child":[],"description":"如：房地产开发,建设施工合同纠纷,征地拆迁补偿,建筑材料欠款,建设项目转让等"},{"solutionTypeId":4,"solutionTypeName":"劳动纠纷","isGroup":0,"child":[],"description":"如：劳资纠纷,薪资追讨,工伤保险,社保纠纷等"},{"solutionTypeId":6,"solutionTypeName":"重大财产处置与保护","isGroup":0,"child":[],"description":"如：房屋销售/买卖,二手房买卖,房屋租赁,房屋抵押,房屋拆迁/补偿等"},{"solutionTypeId":14,"solutionTypeName":"人身伤害/名誉侵权","isGroup":0,"child":[],"description":"如：人身伤害,保险理赔,工伤索赔,交通事故,名誉侵权等"},{"solutionTypeId":15,"solutionTypeName":"损害赔偿","isGroup":0,"child":[],"description":""}]
     * description :
     */

    private int solutionTypeId;
    private String solutionTypeName;
    private int isGroup;
    private String description;
    private List<SolutionTypeChildBean> child;
    private int id;
    private String text;

    public void setId(int id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

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

    public int getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(int isGroup) {
        this.isGroup = isGroup;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SolutionTypeChildBean> getChild() {
        return child;
    }

    public void setChild(List<SolutionTypeChildBean> child) {
        this.child = child;
    }

}