package cn.lex_mung.client_android.mvp.model.entity;

public class EquitiesBuyListEntity {

    String requireTypeId;
    String requireTypeNo;
    String equityName;
    String equityDesc;
    String image;
    String iconImage;
    boolean isOwn;
    int roleId;
    String legalAdviserUrl;

    public String getLegalAdviserUrl() {
        return legalAdviserUrl;
    }

    public String getRequireTypeId() {
        return requireTypeId;
    }

    public String getRequireTypeNo() {
        return requireTypeNo;
    }

    public String getEquityName() {
        return equityName;
    }

    public String getEquityDesc() {
        return equityDesc;
    }

    public String getImage() {
        return image;
    }

    public String getIconImage() {
        return iconImage;
    }

    public boolean isOwn() {
        return isOwn;
    }

    public int getRoleId() {
        return roleId;
    }
}
