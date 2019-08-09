package cn.lex_mung.client_android.mvp.model.entity.expert;

import java.io.Serializable;

public class ExpertPriceSolutionChildEntity implements Serializable {
    /**
     * solutionMarkId : 21
     * solutionMarkName : 离婚诉讼
     * score : 85
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