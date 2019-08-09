package cn.lex_mung.client_android.mvp.model.entity.expert;

import java.io.Serializable;
import java.util.List;

public class ExpertPriceSolutionEntity implements Serializable {

    /**
     * solutionTypeId : 2
     * solutionTypeName : 婚姻及家事
     * titleIconImage : http://oss.lex-mung.com/solution_type_title_2.png
     * child : [{"solutionMarkId":21,"solutionMarkName":"离婚诉讼","score":85},{"solutionMarkId":22,"solutionMarkName":"夫妻财产/债务","score":85},{"solutionMarkId":24,"solutionMarkName":"抚养/收养","score":60},{"solutionMarkId":65,"solutionMarkName":"财产继承","score":60}]
     */

    private int solutionTypeId;
    private String solutionTypeName;
    private String titleIconImage;
    private List<ExpertPriceSolutionChildEntity> child;

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

    public String getTitleIconImage() {
        return titleIconImage;
    }

    public void setTitleIconImage(String titleIconImage) {
        this.titleIconImage = titleIconImage;
    }

    public List<ExpertPriceSolutionChildEntity> getChild() {
        return child;
    }

    public void setChild(List<ExpertPriceSolutionChildEntity> child) {
        this.child = child;
    }


}