package cn.lex_mung.client_android.mvp.model.entity.other;

import android.text.TextUtils;

public class WebGoOrderDetailEntity {

    /**
     * money : 300
     * requireTypeId : 3
     * requireTypeName : 离婚|协议书
     */

    private int orderId;
    private String orderNo;

    public int getOrderId() {
        return orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }
}
