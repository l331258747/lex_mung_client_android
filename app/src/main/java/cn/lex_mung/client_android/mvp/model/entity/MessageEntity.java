package cn.lex_mung.client_android.mvp.model.entity;

import java.util.List;

public class MessageEntity {

    /**
     * pushMessageId : 3
     * memberId : 4
     * type : 2
     * subType : 200
     * busiId : 1
     * fromId : 46
     * title : 免费咨询被回复
     * content : 这个 这个 那个 回复 回复 这是回复测试
     * isRead : 0
     * createTime : 2018-11-28 12:49:51
     * updateTime :
     * icon : http://oss.lex-mung.com/reply_message.png
     * url :
     */

    private int pushMessageId;
    private int memberId;
    private int type;
    private int subType;
    private int busiId;
    private int fromId;
    private String title;
    private String content;
    private int isRead;
    private String createTime;
    private String updateTime;
    private String icon;
    private String url;

    public int getPushMessageId() {
        return pushMessageId;
    }

    public void setPushMessageId(int pushMessageId) {
        this.pushMessageId = pushMessageId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSubType() {
        return subType;
    }

    public void setSubType(int subType) {
        this.subType = subType;
    }

    public int getBusiId() {
        return busiId;
    }

    public void setBusiId(int busiId) {
        this.busiId = busiId;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
