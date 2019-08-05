package cn.lex_mung.client_android.mvp.model.entity.home;

public class HomeChildEntity {
    /**
     * icon : http://oss.lex-mung.com/
     * title : 快速律师咨询
     * desc1 : 5分钟内响应
     * desc2 : 律师专业领域对口
     * tag : http://oss.lex-mung.com/
     * jumptype : h5
     * jumpurl : http://h5.lex-mung.com/quick.html
     */

    private String icon;
    private String title;
    private String desc1;
    private String desc2;
    private String tag;
    private String jumptype;
    private String jumpurl;
    /**
     * elementId : 0
     * containerId : 0
     * type : button
     * sortOrder : 0
     * status : 0
     * createTime :
     * updateTime :
     */

    private int elementId;
    private int containerId;
    private String type;
    private int sortOrder;
    private int status;
    private String createTime;
    private String updateTime;
    private int showShare;
    private String shareUrl;
    private String shareTitle;
    private String shareDescription;
    private String shareImg;

    public int getShowShare() {
        return showShare;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public String getShareDescription() {
        return shareDescription;
    }

    public String getShareImg() {
        return shareImg;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc1() {
        return desc1;
    }

    public void setDesc1(String desc1) {
        this.desc1 = desc1;
    }

    public String getDesc2() {
        return desc2;
    }

    public void setDesc2(String desc2) {
        this.desc2 = desc2;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getJumptype() {
        return jumptype;
    }

    public void setJumptype(String jumptype) {
        this.jumptype = jumptype;
    }

    public String getJumpurl() {
        return jumpurl;
    }

    public void setJumpurl(String jumpurl) {
        this.jumpurl = jumpurl;
    }

    public int getElementId() {
        return elementId;
    }

    public void setElementId(int elementId) {
        this.elementId = elementId;
    }

    public int getContainerId() {
        return containerId;
    }

    public void setContainerId(int containerId) {
        this.containerId = containerId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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