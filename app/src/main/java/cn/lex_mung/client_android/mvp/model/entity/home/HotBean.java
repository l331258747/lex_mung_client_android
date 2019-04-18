package cn.lex_mung.client_android.mvp.model.entity.home;

import android.text.TextUtils;

public class HotBean {
    /**
     * requireTypeId : 36
     * requireTypeName : 民间借贷|起草审查合同
     */

    private int requireTypeId;
    private String requireTypeName;

    public int getRequireTypeId() {
        return requireTypeId;
    }

    public void setRequireTypeId(int requireTypeId) {
        this.requireTypeId = requireTypeId;
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