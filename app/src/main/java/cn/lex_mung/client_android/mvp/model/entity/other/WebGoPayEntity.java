package cn.lex_mung.client_android.mvp.model.entity.other;

import android.text.TextUtils;

public class WebGoPayEntity {

    /**
     * money : 300
     * requireTypeId : 3
     * requireTypeName : 离婚|协议书
     */

    private String money;
    private int requireTypeId;
    private String requireTypeName;
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public float getMoney() {
        if(TextUtils.isEmpty(money))
            return 0;
        return Float.valueOf(money);
    }

    public int getRequireTypeId() {
        return requireTypeId;
    }

    public String getRequireTypeName() {
        return requireTypeName;
    }

}
