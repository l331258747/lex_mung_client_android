package cn.lex_mung.client_android.mvp.model.entity.other;

import android.text.TextUtils;

public class WebGoOrderDetailEntity {

    /**
     * money : 300
     * requireTypeId : 3
     * requireTypeName : 离婚|协议书
     */

    private int id;
    private String orderNo;
    private int typeId;

    public int getTypeId() {
        return typeId;
    }

    public int getOrderId() {
        return id;
    }

    public String getOrderNo() {
        return orderNo;
    }
}
