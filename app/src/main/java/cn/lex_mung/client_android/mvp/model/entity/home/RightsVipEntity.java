package cn.lex_mung.client_android.mvp.model.entity.home;

public class RightsVipEntity {


    /**
     * requireTypeId : 127
     * requireTypeNo : S13
     * equityName : 私人律师团
     * equityDesc :
     * image : http://oss.lex-mung.com/organization_image_15722293586529.png
     * iconImage : http://oss.lex-mung.com/organization_image_15722293586529.png
     * isOwn : true
     * roleId : 0
     * legalAdviserUrl : https://h5-test.lex-mung.com/privatelawyer/index.html
     */

    private int requireTypeId;
    private String requireTypeNo;
    private String equityName;
    private String equityDesc;
    private String image;
    private String iconImage;
    private boolean isOwn;
    private int roleId;
    private String legalAdviserUrl;

    public int getRequireTypeId() {
        return requireTypeId;
    }

    public void setRequireTypeId(int requireTypeId) {
        this.requireTypeId = requireTypeId;
    }

    public String getRequireTypeNo() {
        return requireTypeNo;
    }

    public void setRequireTypeNo(String requireTypeNo) {
        this.requireTypeNo = requireTypeNo;
    }

    public String getEquityName() {
        return equityName;
    }

    public void setEquityName(String equityName) {
        this.equityName = equityName;
    }

    public String getEquityDesc() {
        return equityDesc;
    }

    public void setEquityDesc(String equityDesc) {
        this.equityDesc = equityDesc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIconImage() {
        return iconImage;
    }

    public void setIconImage(String iconImage) {
        this.iconImage = iconImage;
    }

    public boolean isIsOwn() {
        return isOwn;
    }

    public void setIsOwn(boolean isOwn) {
        this.isOwn = isOwn;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getLegalAdviserUrl() {
        return legalAdviserUrl;
    }

    public void setLegalAdviserUrl(String legalAdviserUrl) {
        this.legalAdviserUrl = legalAdviserUrl;
    }
}
