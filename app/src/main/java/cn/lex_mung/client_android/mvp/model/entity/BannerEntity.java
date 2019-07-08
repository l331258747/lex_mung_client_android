package cn.lex_mung.client_android.mvp.model.entity;

import java.util.List;

public class BannerEntity {
    /**
     * bannerId : 4
     * name : 律师端首页广告
     * status : false
     * indexName :
     * deviceInfoId : 2
     * pageNum : 0
     * pageSize : 0
     * bannerImageId : 1040
     * image : http://oss.lex-mung.com/banner_fd2f9ce14.jpg
     * title : 征稿
     * linkRoute : lawyer_inner_web
     * linkValue : http://www.opvo.top/wx_msgs/spview/3787730/3689162/hbKON+MFilfADHbc8zp+gX0KaU5DJGeXDlFohZ1Bi-2F-I=?width=400&c=283085d30e10513624c8cece7993f4de
     * deviceType : 10
     * sortOrder : 3
     * deviceTypeNo : 0
     * bannerImage : {}
     * bannerImageDescription : {}
     */

    private int bannerId;
    private String name;
    private int status;
    private String indexName;
    private int deviceInfoId;
    private int pageNum;
    private int pageSize;
    private int bannerImageId;
    private String image;
    private String title;
    private String linkRoute;
    private String linkValue;
    private int deviceType;
    private int sortOrder;
    private int deviceTypeNo;
    private BannerImageBean bannerImage;
    private BannerImageDescriptionBean bannerImageDescription;

    public int getBannerId() {
        return bannerId;
    }

    public void setBannerId(int bannerId) {
        this.bannerId = bannerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int isStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public int getDeviceInfoId() {
        return deviceInfoId;
    }

    public void setDeviceInfoId(int deviceInfoId) {
        this.deviceInfoId = deviceInfoId;
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

    public int getBannerImageId() {
        return bannerImageId;
    }

    public void setBannerImageId(int bannerImageId) {
        this.bannerImageId = bannerImageId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLinkRoute() {
        return linkRoute;
    }

    public void setLinkRoute(String linkRoute) {
        this.linkRoute = linkRoute;
    }

    public String getLinkValue() {
        return linkValue;
    }

    public void setLinkValue(String linkValue) {
        this.linkValue = linkValue;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public int getDeviceTypeNo() {
        return deviceTypeNo;
    }

    public void setDeviceTypeNo(int deviceTypeNo) {
        this.deviceTypeNo = deviceTypeNo;
    }

    public BannerImageBean getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(BannerImageBean bannerImage) {
        this.bannerImage = bannerImage;
    }

    public BannerImageDescriptionBean getBannerImageDescription() {
        return bannerImageDescription;
    }

    public void setBannerImageDescription(BannerImageDescriptionBean bannerImageDescription) {
        this.bannerImageDescription = bannerImageDescription;
    }

    public static class BannerImageBean {
    }

    public static class BannerImageDescriptionBean {
    }
}
