package com.lex_mung.client_android.mvp.model.entity;

import java.util.List;

public class SolutionListEntity {

    /**
     * pageNum : 1
     * pageSize : 2
     * size : 2
     * orderBy :
     * startRow : 0
     * endRow : 1
     * total : 2
     * pages : 1
     * list : [{"id":7,"image":"catalog/article/article_image_-1537665808","title":"xxxxx","contentType":1,"url":"","typeId":1,"deviceInfoId":1,"isTop":1,"virtualViewCount":100,"markId":"2","status":1,"createDate":"2018-12-04 16:04:51","updateDate":"2018-12-06 14:04:26","createName":"","helpNumber":3,"content":"","createDate1":"","createDate2":"","updateDate1":"","updateDate2":"","typeName":"","name":"","pageNum":"","pageSize":"","solutionUrl":"http://h5-test.lex-mung.com/solution?id=7","ids":[],"deviceInfoIds":[]},{"id":9,"image":"catalog/article/article_image_-1999284542","title":"aaaaaaaaaa","contentType":1,"url":"","typeId":1,"deviceInfoId":1,"isTop":1,"virtualViewCount":20,"markId":"10","status":1,"createDate":"2018-12-04 16:07:44","updateDate":"2018-12-04 16:16:30","createName":"","helpNumber":0,"content":"","createDate1":"","createDate2":"","updateDate1":"","updateDate2":"","typeName":"","name":"","pageNum":"","pageSize":"","solutionUrl":"http://h5-test.lex-mung.com/solution?id=9","ids":[],"deviceInfoIds":[]}]
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
         * id : 7
         * image : catalog/article/article_image_-1537665808
         * title : xxxxx
         * contentType : 1
         * url :
         * typeId : 1
         * deviceInfoId : 1
         * isTop : 1
         * virtualViewCount : 100
         * markId : 2
         * status : 1
         * createDate : 2018-12-04 16:04:51
         * updateDate : 2018-12-06 14:04:26
         * createName :
         * helpNumber : 3
         * content :
         * createDate1 :
         * createDate2 :
         * updateDate1 :
         * updateDate2 :
         * typeName :
         * name :
         * pageNum :
         * pageSize :
         * solutionUrl : http://h5-test.lex-mung.com/solution?id=7
         * ids : []
         * deviceInfoIds : []
         */

        private int id;
        private String image;
        private String title;
        private int contentType;
        private String url;
        private int typeId;
        private int deviceInfoId;
        private int isTop;
        private int virtualViewCount;
        private String markId;
        private int status;
        private String createDate;
        private String updateDate;
        private String createName;
        private int helpNumber;
        private String content;
        private String createDate1;
        private String createDate2;
        private String updateDate1;
        private String updateDate2;
        private String typeName;
        private String name;
        private String pageNum;
        private String pageSize;
        private String solutionUrl;
        private List<?> ids;
        private List<?> deviceInfoIds;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public int getContentType() {
            return contentType;
        }

        public void setContentType(int contentType) {
            this.contentType = contentType;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public int getDeviceInfoId() {
            return deviceInfoId;
        }

        public void setDeviceInfoId(int deviceInfoId) {
            this.deviceInfoId = deviceInfoId;
        }

        public int getIsTop() {
            return isTop;
        }

        public void setIsTop(int isTop) {
            this.isTop = isTop;
        }

        public int getVirtualViewCount() {
            return virtualViewCount;
        }

        public void setVirtualViewCount(int virtualViewCount) {
            this.virtualViewCount = virtualViewCount;
        }

        public String getMarkId() {
            return markId;
        }

        public void setMarkId(String markId) {
            this.markId = markId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
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

        public String getCreateName() {
            return createName;
        }

        public void setCreateName(String createName) {
            this.createName = createName;
        }

        public int getHelpNumber() {
            return helpNumber;
        }

        public void setHelpNumber(int helpNumber) {
            this.helpNumber = helpNumber;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreateDate1() {
            return createDate1;
        }

        public void setCreateDate1(String createDate1) {
            this.createDate1 = createDate1;
        }

        public String getCreateDate2() {
            return createDate2;
        }

        public void setCreateDate2(String createDate2) {
            this.createDate2 = createDate2;
        }

        public String getUpdateDate1() {
            return updateDate1;
        }

        public void setUpdateDate1(String updateDate1) {
            this.updateDate1 = updateDate1;
        }

        public String getUpdateDate2() {
            return updateDate2;
        }

        public void setUpdateDate2(String updateDate2) {
            this.updateDate2 = updateDate2;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPageNum() {
            return pageNum;
        }

        public void setPageNum(String pageNum) {
            this.pageNum = pageNum;
        }

        public String getPageSize() {
            return pageSize;
        }

        public void setPageSize(String pageSize) {
            this.pageSize = pageSize;
        }

        public String getSolutionUrl() {
            return solutionUrl;
        }

        public void setSolutionUrl(String solutionUrl) {
            this.solutionUrl = solutionUrl;
        }

        public List<?> getIds() {
            return ids;
        }

        public void setIds(List<?> ids) {
            this.ids = ids;
        }

        public List<?> getDeviceInfoIds() {
            return deviceInfoIds;
        }

        public void setDeviceInfoIds(List<?> deviceInfoIds) {
            this.deviceInfoIds = deviceInfoIds;
        }
    }
}
