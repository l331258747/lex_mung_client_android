package cn.lex_mung.client_android.mvp.model.entity.other;

import android.text.TextUtils;

import java.util.List;

public class ActivityEntity {


    /**
     * id : 3
     * typeId : 1
     * name : 弹窗3
     * url : baidu
     * buttonName : 按钮3
     * sortOrder : 1
     * targetUsers : 1
     * targetUsersGroup : 0
     * purpose : 用途大了
     * beginDate : 2019-09-04 00:00:00
     * endDate : 2019-09-24 00:00:00
     * status : 1
     * iconImage : http://oss.lex-mung.com/image
     * createUserId : 0
     * createDate : 2019-09-04 11:56:56
     * updateDate :
     * pageNum : 0
     * pageSize : 0
     * startTime :
     * endTime :
     * device : 0
     * solutionTypeId : 0
     * beginDateStr :
     * endDateStr :
     * popupDevice : []
     * popupPage : []
     */

    private int id;
    private int typeId;
    private String name;
    private String url;
    private String buttonName;
    private int sortOrder;
    private int targetUsers;
    private int targetUsersGroup;
    private String purpose;
    private String beginDate;
    private String endDate;
    private int status;
    private String iconImage;
    private int createUserId;
    private String createDate;
    private String updateDate;
    private int pageNum;
    private int pageSize;
    private String startTime;
    private String endTime;
    private int device;
    private int solutionTypeId;
    private String beginDateStr;
    private String endDateStr;
    private List<?> popupDevice;
    private List<?> popupPage;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getButtonName() {
        return buttonName;
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public int getTargetUsers() {
        return targetUsers;
    }

    public void setTargetUsers(int targetUsers) {
        this.targetUsers = targetUsers;
    }

    public int getTargetUsersGroup() {
        return targetUsersGroup;
    }

    public void setTargetUsersGroup(int targetUsersGroup) {
        this.targetUsersGroup = targetUsersGroup;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getIconImage() {
        if(TextUtils.isEmpty(iconImage)) return "";
        if(!iconImage.startsWith("http")) return "";
        return iconImage;
    }

    public void setIconImage(String iconImage) {
        this.iconImage = iconImage;
    }

    public int getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(int createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getDevice() {
        return device;
    }

    public void setDevice(int device) {
        this.device = device;
    }

    public int getSolutionTypeId() {
        return solutionTypeId;
    }

    public void setSolutionTypeId(int solutionTypeId) {
        this.solutionTypeId = solutionTypeId;
    }

    public String getBeginDateStr() {
        return beginDateStr;
    }

    public void setBeginDateStr(String beginDateStr) {
        this.beginDateStr = beginDateStr;
    }

    public String getEndDateStr() {
        return endDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }

    public List<?> getPopupDevice() {
        return popupDevice;
    }

    public void setPopupDevice(List<?> popupDevice) {
        this.popupDevice = popupDevice;
    }

    public List<?> getPopupPage() {
        return popupPage;
    }

    public void setPopupPage(List<?> popupPage) {
        this.popupPage = popupPage;
    }
}
