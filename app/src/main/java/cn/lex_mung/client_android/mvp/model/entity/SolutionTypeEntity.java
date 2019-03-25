package cn.lex_mung.client_android.mvp.model.entity;

public class SolutionTypeEntity {

    /**
     * id : 2
     * typeName : 婚姻家事
     * alias : 婚姻家事
     * parentType : 个人类
     * creater : 李海东
     * sort : 1
     * createTime : 2018-12-14 09:41:18
     * updateTime : 2019-01-31 00:02:44
     * quick : 1
     * expert : 0
     * price : 0.01
     */

    private int id;
    private String typeName;
    private String alias;
    private String parentType;
    private String creater;
    private int sort;
    private String createTime;
    private String updateTime;
    private int quick;
    private int expert;
    private double price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getParentType() {
        return parentType;
    }

    public void setParentType(String parentType) {
        this.parentType = parentType;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getQuick() {
        return quick;
    }

    public void setQuick(int quick) {
        this.quick = quick;
    }

    public int getExpert() {
        return expert;
    }

    public void setExpert(int expert) {
        this.expert = expert;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
