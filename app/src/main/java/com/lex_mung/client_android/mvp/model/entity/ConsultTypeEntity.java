package com.lex_mung.client_android.mvp.model.entity;

public class ConsultTypeEntity {


    /**
     * id : 1
     * typeName : 测试类型
     * creater : aa
     * sort : 0
     * createTime : 2018-11-01 11:01:13
     * updateTime : 2018-11-05 09:56:18
     * quick : 0
     * expert : 0
     */
    public ConsultTypeEntity(int id, String typeName) {
        this.id = id;
        this.typeName = typeName;
    }

    public ConsultTypeEntity() {
    }

    private int id;
    private String typeName;
    private String creater;
    private int sort;
    private String createTime;
    private String updateTime;
    private int quick;
    private int expert;

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
}
