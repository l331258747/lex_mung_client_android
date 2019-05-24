package cn.lex_mung.client_android.mvp.model.entity.help;

import java.util.List;

public class SolutionTypeChildBean {
    /**
     * solutionTypeId : 2
     * solutionTypeName : 婚姻家事
     * isGroup : 0
     * child : []
     * description : 如：离婚诉讼,夫妻财产/债务,家庭暴力,抚养/收养,财产继承等
     */

    private int solutionTypeId;
    private String solutionTypeName;
    private int isGroup;
    private String description;
    private List<?> child;

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

    public List<?> getChild() {
        return child;
    }

    public void setChild(List<?> child) {
        this.child = child;
    }
}