package cn.lex_mung.client_android.mvp.model.entity.home;

import android.text.TextUtils;

public class NormalBean {
    /**
     * requireTypeIcon : http://oss.lex-mung.com/organization_image_15514442414219.png
     * requireTypeId : 2
     * jumpUrl :
     * jumptype : 1
     * requireTypeName : 诉讼/仲裁
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
        if(TextUtils.isEmpty(requireTypeName))
            return "";
        return requireTypeName.replace("|","\n");
    }

    public void setRequireTypeName(String requireTypeName) {
        this.requireTypeName = requireTypeName;
    }
}