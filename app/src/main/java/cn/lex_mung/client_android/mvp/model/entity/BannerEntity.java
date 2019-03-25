package cn.lex_mung.client_android.mvp.model.entity;

import java.util.List;

public class BannerEntity {
    /**
     * pageNum : 1
     * pageSize : 10
     * size : 10
     * orderBy :
     * startRow : 0
     * endRow : 9
     * total : 10
     * pages : 1
     * list : [{"bannerId":4,"name":"律师端首页广告","status":false,"indexName":"","deviceInfoId":2,"pageNum":0,"pageSize":0,"bannerImageId":1040,"image":"http://oss.lex-mung.com/banner_fd2f9ce14.jpg","title":"征稿","linkRoute":"lawyer_inner_web","linkValue":"http://www.opvo.top/wx_msgs/spview/3787730/3689162/hbKON+MFilfADHbc8zp+gX0KaU5DJGeXDlFohZ1Bi-2F-I=?width=400&c=283085d30e10513624c8cece7993f4de","deviceType":10,"sortOrder":3,"deviceTypeNo":0,"bannerImage":{},"bannerImageDescription":{}},{"bannerId":4,"name":"律师端首页广告","status":false,"indexName":"","deviceInfoId":2,"pageNum":0,"pageSize":0,"bannerImageId":1041,"image":"http://oss.lex-mung.com/banner_489807c00.png","title":"湘江夜话","linkRoute":"lawyer_inner_web","linkValue":"http://www.opvo.top/wx_msgs/spview/3787730/3690302/IXpiQWQws8ahSy6hAmacHuN94SfZuVV0tLdwETQcEJI=?width=400&c=283085d30e10513624c8cece7993f4de","deviceType":10,"sortOrder":5,"deviceTypeNo":0,"bannerImage":{},"bannerImageDescription":{}},{"bannerId":4,"name":"律师端首页广告","status":false,"indexName":"","deviceInfoId":2,"pageNum":0,"pageSize":0,"bannerImageId":1042,"image":"http://oss.lex-mung.com/banner_7e7206301.jpg","title":"湖南浙江总商会","linkRoute":"lawyer_outer_web","linkValue":"https://mp.weixin.qq.com/s/3FYgXbiB7y4TWkydBlk3Rw","deviceType":10,"sortOrder":2,"deviceTypeNo":0,"bannerImage":{},"bannerImageDescription":{}},{"bannerId":4,"name":"律师端首页广告","status":false,"indexName":"","deviceInfoId":2,"pageNum":0,"pageSize":0,"bannerImageId":1043,"image":"http://oss.lex-mung.com/banner_c7858e1ca.jpg","title":"绿豆券合作律师招募","linkRoute":"lawyer_inner_web","linkValue":"https://www.wjx.top/jq/26626787.aspx","deviceType":10,"sortOrder":4,"deviceTypeNo":0,"bannerImage":{},"bannerImageDescription":{}},{"bannerId":4,"name":"律师端首页广告","status":false,"indexName":"","deviceInfoId":2,"pageNum":0,"pageSize":0,"bannerImageId":1044,"image":"http://oss.lex-mung.com/banner_18eeffdbe.jpg","title":"广告位招商","linkRoute":"lawyer_inner_web","linkValue":"http://a2.rabbitpre.com/m2/aUe1ZiIYvZ","deviceType":10,"sortOrder":1,"deviceTypeNo":0,"bannerImage":{},"bannerImageDescription":{}},{"bannerId":4,"name":"律师端首页广告","status":false,"indexName":"","deviceInfoId":2,"pageNum":0,"pageSize":0,"bannerImageId":1045,"image":"http://oss.lex-mung.com/banner_7e4d291fc.jpg","title":"征稿banner","linkRoute":"lawyer_inner_web","linkValue":"http://www.opvo.top/wx_msgs/spview/3787730/3689162/hbKON+MFilfADHbc8zp+gX0KaU5DJGeXDlFohZ1Bi-2F-I=?width=400&c=283085d30e10513624c8cece7993f4de","deviceType":20,"sortOrder":3,"deviceTypeNo":0,"bannerImage":{},"bannerImageDescription":{}},{"bannerId":4,"name":"律师端首页广告","status":false,"indexName":"","deviceInfoId":2,"pageNum":0,"pageSize":0,"bannerImageId":1046,"image":"http://oss.lex-mung.com/banner_772e1f316.png","title":"湘江夜话","linkRoute":"lawyer_inner_web","linkValue":"http://www.opvo.top/wx_msgs/spview/3787730/3690302/IXpiQWQws8ahSy6hAmacHuN94SfZuVV0tLdwETQcEJI=?width=400&c=283085d30e10513624c8cece7993f4de","deviceType":20,"sortOrder":5,"deviceTypeNo":0,"bannerImage":{},"bannerImageDescription":{}},{"bannerId":4,"name":"律师端首页广告","status":false,"indexName":"","deviceInfoId":2,"pageNum":0,"pageSize":0,"bannerImageId":1047,"image":"http://oss.lex-mung.com/banner_127cd1105.jpg","title":"湖南浙江总商会","linkRoute":"lawyer_outer_web","linkValue":"https://mp.weixin.qq.com/s/3FYgXbiB7y4TWkydBlk3Rw","deviceType":20,"sortOrder":2,"deviceTypeNo":0,"bannerImage":{},"bannerImageDescription":{}},{"bannerId":4,"name":"律师端首页广告","status":false,"indexName":"","deviceInfoId":2,"pageNum":0,"pageSize":0,"bannerImageId":1048,"image":"http://oss.lex-mung.com/banner_ff86df874.jpg","title":"绿豆券律师招募","linkRoute":"lawyer_inner_web","linkValue":"https://www.wjx.top/jq/26626787.aspx","deviceType":20,"sortOrder":4,"deviceTypeNo":0,"bannerImage":{},"bannerImageDescription":{}},{"bannerId":4,"name":"律师端首页广告","status":false,"indexName":"","deviceInfoId":2,"pageNum":0,"pageSize":0,"bannerImageId":1049,"image":"http://oss.lex-mung.com/banner_cbac41495.jpg","title":"广告位招商","linkRoute":"lawyer_inner_web","linkValue":"http://a2.rabbitpre.com/m2/aUe1ZiIYvZ","deviceType":20,"sortOrder":1,"deviceTypeNo":0,"bannerImage":{},"bannerImageDescription":{}}]
     * firstPage : 1
     * prePage : 0
     * nextPage : 0
     * lastPage : 1
     * isFirstPage : true
     * isLastPage : true
     * hasPreviousPage : false
     * hasNextPage : false
     * navigatePages : 8
     * navigatepageNums : [1]
     */

    private int pageNum;
    private int pageSize;
    private int size;
    private String orderBy;
    private int startRow;
    private int endRow;
    private int total;
    private int pages;
    private int firstPage;
    private int prePage;
    private int nextPage;
    private int lastPage;
    private boolean isFirstPage;
    private boolean isLastPage;
    private boolean hasPreviousPage;
    private boolean hasNextPage;
    private int navigatePages;
    private List<ListBean> list;
    private List<Integer> navigatepageNums;

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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(int firstPage) {
        this.firstPage = firstPage;
    }

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public boolean isIsFirstPage() {
        return isFirstPage;
    }

    public void setIsFirstPage(boolean isFirstPage) {
        this.isFirstPage = isFirstPage;
    }

    public boolean isIsLastPage() {
        return isLastPage;
    }

    public void setIsLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public int getNavigatePages() {
        return navigatePages;
    }

    public void setNavigatePages(int navigatePages) {
        this.navigatePages = navigatePages;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public List<Integer> getNavigatepageNums() {
        return navigatepageNums;
    }

    public void setNavigatepageNums(List<Integer> navigatepageNums) {
        this.navigatepageNums = navigatepageNums;
    }

    public static class ListBean {
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
}
