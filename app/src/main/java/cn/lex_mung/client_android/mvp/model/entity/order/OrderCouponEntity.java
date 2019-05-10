package cn.lex_mung.client_android.mvp.model.entity.order;

import java.util.List;

import me.zl.mvp.utils.StringUtils;

public class OrderCouponEntity {

    /**
     * pageNum : 1
     * pageSize : 5
     * size : 1
     * orderBy :
     * startRow : 1
     * endRow : 1
     * total : 1
     * pages : 1
     * list : [{"memberId":0,"couponId":11,"couponName":"券001","preferentialWay":2,"fullNum":100,"reduceNum":50,"preferentialContent":"满100减50！快速电话专享！","preferentialDiscount":0,"startTime":"2019-05-08 00:00:00","endTime":"2019-05-15 00:00:00","couponStatus":1,"pageNum":0,"pageSize":0}]
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

    public static class ListBean {
        /**
         * memberId : 0
         * couponId : 11
         * couponName : 券001
         * preferentialWay : 2
         * fullNum : 100
         * reduceNum : 50
         * preferentialContent : 满100减50！快速电话专享！
         * preferentialDiscount : 0
         * startTime : 2019-05-08 00:00:00
         * endTime : 2019-05-15 00:00:00
         * couponStatus : 1
         * pageNum : 0
         * pageSize : 0
         */

        private int memberId;
        private int couponId;
        private String couponName;
        private int preferentialWay;//	优惠方式（2满减3折扣）
        private double fullNum;
        private double reduceNum;
        private String preferentialContent;
        private double preferentialDiscount;
        private String startTime;
        private String endTime;
        private int couponStatus;//券状态（1可使用2不可使用）
        private int pageNum;
        private int pageSize;

        public int getMemberId() {
            return memberId;
        }

        public int getCouponId() {
            return couponId;
        }

        public String getCouponName() {
            return couponName;
        }

        public String getRule() {
            return "满" + StringUtils.getStringNum(fullNum) + "减" + StringUtils.getStringNum(reduceNum);
        }

        public int getPreferentialWay() {
            return preferentialWay;
        }

        public double getFullNum() {
            return fullNum;
        }

        public double getReduceNum() {
            return reduceNum;
        }

        public String getReduceNumStr() {
            return StringUtils.getStringNum(reduceNum);
        }

        public String getPreferentialContent() {
            return preferentialContent;
        }

        public double getPreferentialDiscount() {
            return preferentialDiscount;
        }

        public String getPreferentialDiscountStr(){
            return (preferentialDiscount * 10) + "";
        }

        public String getStartTime() {
            return startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public int getCouponStatus() {
            return couponStatus;
        }

        public String getCouponStatusStr() {
            if (couponStatus == 1) {
                return "立\n即\n使\n用";
            } else if (couponStatus == 2) {
                return "不\n可\n用";
            }
            return "不\n可\n用";
        }

        public int getPageNum() {
            return pageNum;
        }

        public int getPageSize() {
            return pageSize;
        }
    }
}
