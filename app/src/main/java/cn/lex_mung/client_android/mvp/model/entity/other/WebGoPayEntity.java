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

    public float getMoney() {
        if(TextUtils.isEmpty(money))
            return 0;
        return Float.valueOf(money);
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getRequireTypeId() {
        return requireTypeId;
    }

    public void setRequireTypeId(int requireTypeId) {
        this.requireTypeId = requireTypeId;
    }

    public String getRequireTypeName() {
        return requireTypeName;
    }

    public void setRequireTypeName(String requireTypeName) {
        this.requireTypeName = requireTypeName;
    }
}
