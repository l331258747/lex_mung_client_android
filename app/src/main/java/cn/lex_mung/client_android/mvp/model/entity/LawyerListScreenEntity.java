package cn.lex_mung.client_android.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

public class LawyerListScreenEntity implements Serializable, SelectList {

    public LawyerListScreenEntity(String propKey, String text, int isTile) {
        this.propKey = propKey;
        this.text = text;
        this.isTile = isTile;
    }

    public LawyerListScreenEntity() {
    }

    /**
     * propKey : practiceYearId
     * text : 执业年限
     * isTile : 1
     * margin : 35
     * items : [{"id":1,"text":"不限"},{"id":2,"text":"1年以上"},{"id":3,"text":"3年以上"},{"id":4,"text":"5年以上"},{"id":5,"text":"10年以上"},{"id":6,"text":"15年以上"}]
     */

    private String propKey;
    private String text;
    private int isTile;
    private int margin;

    private List<ItemsBean> items;
    private int id;
    private int pos;
    private String content;
    private double minPrice;
    private double maxPrice;

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPropKey() {
        return propKey;
    }

    public void setPropKey(String propKey) {
        this.propKey = propKey;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIsTile() {
        return isTile;
    }

    public void setIsTile(int isTile) {
        this.isTile = isTile;
    }

    public int getMargin() {
        return margin;
    }

    public void setMargin(int margin) {
        this.margin = margin;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean implements Serializable, SelectList {
        /**
         * id : 1
         * text : 不限
         */

        private int id;
        private String text;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
