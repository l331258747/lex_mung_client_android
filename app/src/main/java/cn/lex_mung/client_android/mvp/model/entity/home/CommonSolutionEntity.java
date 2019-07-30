package cn.lex_mung.client_android.mvp.model.entity.home;

public class CommonSolutionEntity {


    /**
     * id : 14
     * parentType : 个人类
     * typeName : 人身伤害/名誉侵权
     * alias : 人身伤害/名誉侵权
     * creater : 李海东
     * sort : 25
     * createTime : 2018-12-14 09:43:55
     * updateTime : 2019-07-29 15:43:52
     * quick : 1
     * expert : 1
     * freeSolution : 1
     * freeText : 1
     * freeCall : 1
     * price : 100
     * titleIconImage : http://oss.lex-mung.com/solution_type_title_14.png
     * webImage :
     * h5Show : 0
     * appIcon : http://oss.lex-mung.com/solution_type_title_2.png
     */

    private int id;
    private String parentType;
    private String typeName;
    private String alias;
    private String creater;
    private int sort;
    private String createTime;
    private String updateTime;
    private int quick;
    private int expert;
    private int freeSolution;
    private int freeText;
    private int freeCall;
    private double price;
    private String titleIconImage;
    private String webImage;
    private int h5Show;
    private String appIcon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getParentType() {
        return parentType;
    }

    public void setParentType(String parentType) {
        this.parentType = parentType;
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

    public int getFreeSolution() {
        return freeSolution;
    }

    public void setFreeSolution(int freeSolution) {
        this.freeSolution = freeSolution;
    }

    public int getFreeText() {
        return freeText;
    }

    public void setFreeText(int freeText) {
        this.freeText = freeText;
    }

    public int getFreeCall() {
        return freeCall;
    }

    public void setFreeCall(int freeCall) {
        this.freeCall = freeCall;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTitleIconImage() {
        return titleIconImage;
    }

    public void setTitleIconImage(String titleIconImage) {
        this.titleIconImage = titleIconImage;
    }

    public String getWebImage() {
        return webImage;
    }

    public void setWebImage(String webImage) {
        this.webImage = webImage;
    }

    public int getH5Show() {
        return h5Show;
    }

    public void setH5Show(int h5Show) {
        this.h5Show = h5Show;
    }

    public String getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(String appIcon) {
        this.appIcon = appIcon;
    }
}
