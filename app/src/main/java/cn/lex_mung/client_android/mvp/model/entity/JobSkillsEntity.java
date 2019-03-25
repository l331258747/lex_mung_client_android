package cn.lex_mung.client_android.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

public class JobSkillsEntity implements Serializable {

    /**
     * skillId : 1
     * skillNo : F1
     * skillName : 专业技能
     * iconImage :
     * parentId : 0
     * children : [{"skillId":5,"skillNo":"F1-02","skillName":"交流沟通","iconImage":"http://oss.lex-mung.com/skill_f1_02.png","parentId":1,"children":[]},{"skillId":7,"skillNo":"F1-04","skillName":"演讲培训","iconImage":"http://oss.lex-mung.com/skill_f1_04.png","parentId":1,"children":[]}]
     */

    private int skillId;
    private String skillNo;
    private String skillName;
    private String iconImage;
    private int parentId;
    private List<JobSkillsEntity> children;
    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public String getSkillNo() {
        return skillNo;
    }

    public void setSkillNo(String skillNo) {
        this.skillNo = skillNo;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getIconImage() {
        return iconImage;
    }

    public void setIconImage(String iconImage) {
        this.iconImage = iconImage;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public List<JobSkillsEntity> getChildren() {
        return children;
    }

    public void setChildren(List<JobSkillsEntity> children) {
        this.children = children;
    }
}
