package cn.lex_mung.client_android.mvp.model.entity.home;

public class CommonMarkEntity {


    /**
     * id : 21
     * typeId : 2
     * name : 离婚诉讼
     * sort : 1
     * creater : haidong
     * createTime : 2018-12-14 09:45:17
     * updateTime : 2019-01-23 13:52:32
     */

    private int id;
    private int typeId;
    private String name;
    private int sort;
    private String creater;
    private String createTime;
    private String updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
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
}
