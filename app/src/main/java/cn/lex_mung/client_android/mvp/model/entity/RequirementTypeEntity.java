package cn.lex_mung.client_android.mvp.model.entity;

public class RequirementTypeEntity {

    /**
     * requireTypeIcon : http://oss.lex-mung.com/organization_image_15514318526639.png
     * requireTypeId : 3
     * jumpUrl :
     * jumptype : 1
     * requireTypeName : 起草合同
     */

    private String requireTypeIcon;
    private int requireTypeId;
    private String jumpUrl;
    private int jumptype;
    private String requireTypeName;

    public String getRequireTypeIcon() {
        return requireTypeIcon;
    }

    public void setRequireTypeIcon(String requireTypeIcon) {
        this.requireTypeIcon = requireTypeIcon;
    }

    public int getRequireTypeId() {
        return requireTypeId;
    }

    public void setRequireTypeId(int requireTypeId) {
        this.requireTypeId = requireTypeId;
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    public int getJumptype() {
        return jumptype;
    }

    public void setJumptype(int jumptype) {
        this.jumptype = jumptype;
    }

    public String getRequireTypeName() {
        return requireTypeName;
    }

    public void setRequireTypeName(String requireTypeName) {
        this.requireTypeName = requireTypeName;
    }
}
