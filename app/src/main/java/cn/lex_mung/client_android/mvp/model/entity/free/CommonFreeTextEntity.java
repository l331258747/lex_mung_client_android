package cn.lex_mung.client_android.mvp.model.entity.free;

import java.util.List;

import cn.lex_mung.client_android.app.TimeFormat;

public class CommonFreeTextEntity {


    /**
     * pageNum : 1
     * pageSize : 5
     * size : 5
     * orderBy :
     * startRow : 1
     * endRow : 5
     * total : 166
     * pages : 34
     * list : [{"consultationId":541,"consultationNumber":"FT190417600311214320","memberId":5006,"consultationStatus":4,"consultationTypeId":18,"source":5,"regionId":430100,"dateAdded":"2019-04-17 17:04:14","dateModified":"2019-04-17 17:04:50","isHide":0,"content":"阿弗莱克沙发里框架啦是激发了加","replyCount":6,"region":"湖南省长沙市","memberName":"我是大魔王666呀","memberIconImage":"http://oss.lex-mung.com/icon_image_member_15548954201179.jpg","categoryName":"重大基础项目建设"},{"consultationId":525,"consultationNumber":"FT190325400596307593","memberId":5186,"consultationStatus":4,"consultationTypeId":3,"source":2,"regionId":450900,"dateAdded":"2019-03-25 16:21:02","dateModified":"2019-03-25 16:21:12","isHide":0,"content":"阿尔特塔方法是人生发反反复复","replyCount":17,"region":"广西壮族自治区玉林市","memberName":"微风","memberIconImage":"http://oss.lex-mung.com/icon_image_member_15571369828879.png","categoryName":"交通事故"},{"consultationId":502,"consultationNumber":"FT190320602004639209","memberId":5186,"consultationStatus":4,"consultationTypeId":3,"source":2,"regionId":430102,"dateAdded":"2019-03-20 15:23:45","dateModified":"2019-03-20 15:24:22","isHide":1,"content":"不重复的推特地铁地铁地铁","replyCount":11,"region":"湖南省长沙市","memberName":"匿名用户","memberIconImage":"","categoryName":"交通事故"},{"consultationId":498,"consultationNumber":"FT190320701558147318","memberId":5186,"consultationStatus":4,"consultationTypeId":3,"source":2,"regionId":450921,"dateAdded":"2019-03-20 11:15:10","dateModified":"2019-03-20 11:16:09","isHide":1,"content":"姐姐家鸡都会好起来哈哈哈哈过","replyCount":9,"region":"广西壮族自治区玉林市","memberName":"匿名用户","memberIconImage":"","categoryName":"交通事故"},{"consultationId":497,"consultationNumber":"FT190319801532013306","memberId":5003,"consultationStatus":4,"consultationTypeId":2,"source":0,"regionId":430100,"dateAdded":"2019-03-19 10:36:32","dateModified":"2019-03-20 17:27:30","isHide":0,"content":"dbxjxbzbsnznznndjb隔热机子认证","replyCount":6,"region":"湖南省长沙市","memberName":"我到底是谁啊？","memberIconImage":"http://oss.lex-mung.com/icon_image_member_15531400893599.jpeg","categoryName":"婚姻家事"}]
     * firstPage : 1
     * prePage : 0
     * nextPage : 2
     * lastPage : 8
     * isFirstPage : true
     * isLastPage : false
     * hasPreviousPage : false
     * hasNextPage : true
     * navigatePages : 8
     * navigatepageNums : [1,2,3,4,5,6,7,8]
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
         * consultationId : 541
         * consultationNumber : FT190417600311214320
         * memberId : 5006
         * consultationStatus : 4
         * consultationTypeId : 18
         * source : 5
         * regionId : 430100
         * dateAdded : 2019-04-17 17:04:14
         * dateModified : 2019-04-17 17:04:50
         * isHide : 0
         * content : 阿弗莱克沙发里框架啦是激发了加
         * replyCount : 6
         * region : 湖南省长沙市
         * memberName : 我是大魔王666呀
         * memberIconImage : http://oss.lex-mung.com/icon_image_member_15548954201179.jpg
         * categoryName : 重大基础项目建设
         */

        private int consultationId;
        private String consultationNumber;
        private int memberId;
        private int consultationStatus;
        private int consultationTypeId;
        private int source;
        private int regionId;
        private String dateAdded;
        private String dateModified;
        private int isHide;
        private String content;
        private int replyCount;
        private String region;
        private String memberName;
        private String memberIconImage;
        private String categoryName;

        public int getConsultationId() {
            return consultationId;
        }

        public void setConsultationId(int consultationId) {
            this.consultationId = consultationId;
        }

        public String getConsultationNumber() {
            return consultationNumber;
        }

        public void setConsultationNumber(String consultationNumber) {
            this.consultationNumber = consultationNumber;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public int getConsultationStatus() {
            return consultationStatus;
        }

        public void setConsultationStatus(int consultationStatus) {
            this.consultationStatus = consultationStatus;
        }

        //审核状态（1已发布2已通过3未通过4已回复5已完
        public String getConsultationStatusStr(){
            switch (consultationStatus){
                case 1:
                    return "已发布";
                case 2:
                    return "已通过";
                case 3:
                    return "未通过";
                case 4:
                    return "已回复";
                case 5:
                    return "已完成";
                case 6:
                    return "已删除";
            }
            return "";
        }

        public int getConsultationTypeId() {
            return consultationTypeId;
        }

        public void setConsultationTypeId(int consultationTypeId) {
            this.consultationTypeId = consultationTypeId;
        }

        public int getSource() {
            return source;
        }

        public void setSource(int source) {
            this.source = source;
        }

        public int getRegionId() {
            return regionId;
        }

        public void setRegionId(int regionId) {
            this.regionId = regionId;
        }

        public String getDateAdded() {
            return dateAdded;
        }

        public String getDateAddedStr(){
            return TimeFormat.getTime(TimeFormat.strToLong(dateAdded,TimeFormat.s1));
        }

        public void setDateAdded(String dateAdded) {
            this.dateAdded = dateAdded;
        }

        public String getDateModified() {
            return dateModified;
        }

        public void setDateModified(String dateModified) {
            this.dateModified = dateModified;
        }

        public int getIsHide() {
            return isHide;
        }

        public void setIsHide(int isHide) {
            this.isHide = isHide;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getReplyCount() {
            return replyCount;
        }
        public String getReplyCountStr() {
            return replyCount+"条回复";
        }

        public void setReplyCount(int replyCount) {
            this.replyCount = replyCount;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getMemberName() {
            if(isHide == 1){
                return "匿名用户";
            }
            return memberName;
        }

        public void setMemberName(String memberName) {
            this.memberName = memberName;
        }

        public String getMemberIconImage() {
            return memberIconImage;
        }

        public void setMemberIconImage(String memberIconImage) {
            this.memberIconImage = memberIconImage;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }
    }
}
