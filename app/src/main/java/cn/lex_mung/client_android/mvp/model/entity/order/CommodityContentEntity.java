package cn.lex_mung.client_android.mvp.model.entity.order;

import me.zl.mvp.utils.StringUtils;

public class CommodityContentEntity {

    String title;
    float price;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getPriceStr(){
        if(price == 0)
            return "";
        return StringUtils.getStringNum(price) + "元";
    }
}
