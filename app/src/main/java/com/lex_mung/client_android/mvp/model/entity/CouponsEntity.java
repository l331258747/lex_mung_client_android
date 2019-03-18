package com.lex_mung.client_android.mvp.model.entity;

import java.util.List;

public class CouponsEntity {

    /**
     * pageNum : 0
     * pageSize : 20
     * size : 0
     * orderBy :
     * startRow : 0
     * endRow : 0
     * total : 0
     * pages : 0
     * list : []
     * firstPage : 0
     * prePage : 0
     * nextPage : 0
     * lastPage : 0
     * isFirstPage : false
     * isLastPage : true
     * hasPreviousPage : false
     * hasNextPage : false
     * navigatePages : 8
     * navigatepageNums : []
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
    private List<?> navigatepageNums;

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

    public List<?> getNavigatepageNums() {
        return navigatepageNums;
    }

    public void setNavigatepageNums(List<?> navigatepageNums) {
        this.navigatepageNums = navigatepageNums;
    }

    public static class ListBean {
        private String couponId;
        private String couponName;
        private String scopeOfUse;
        private String image;
        private String scopeOfUseName;
        private String couponType;
        private String couponTypeName;
        private String effectiveTimeStart;//有效期起始
        private String effectiveTimeEnd; //优惠券有效期终止
        private String showExactTime;
        private String preferentialWay;
        private String amount;//会员卡类型额度
        private String fullNum; //优惠-满多少
        private String reduceNum;//优惠-减多少
        private String preferentialDiscount;
        private String preferentialContent;
        private String useForOrgId;
        private String useForOrgLevelId;
        private String canOverlayUse;
        private String status;
        private String couponDesc;
        private String balance;
        private String consumeMoney;

        public String getCouponId() {
            return couponId;
        }

        public void setCouponId(String couponId) {
            this.couponId = couponId;
        }

        public String getCouponName() {
            return couponName;
        }

        public void setCouponName(String couponName) {
            this.couponName = couponName;
        }

        public String getScopeOfUse() {
            return scopeOfUse;
        }

        public void setScopeOfUse(String scopeOfUse) {
            this.scopeOfUse = scopeOfUse;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getScopeOfUseName() {
            return scopeOfUseName;
        }

        public void setScopeOfUseName(String scopeOfUseName) {
            this.scopeOfUseName = scopeOfUseName;
        }

        public String getCouponType() {
            return couponType;
        }

        public void setCouponType(String couponType) {
            this.couponType = couponType;
        }

        public String getCouponTypeName() {
            return couponTypeName;
        }

        public void setCouponTypeName(String couponTypeName) {
            this.couponTypeName = couponTypeName;
        }

        public String getEffectiveTimeStart() {
            return effectiveTimeStart;
        }

        public void setEffectiveTimeStart(String effectiveTimeStart) {
            this.effectiveTimeStart = effectiveTimeStart;
        }

        public String getEffectiveTimeEnd() {
            return effectiveTimeEnd;
        }

        public void setEffectiveTimeEnd(String effectiveTimeEnd) {
            this.effectiveTimeEnd = effectiveTimeEnd;
        }

        public String getShowExactTime() {
            return showExactTime;
        }

        public void setShowExactTime(String showExactTime) {
            this.showExactTime = showExactTime;
        }

        public String getPreferentialWay() {
            return preferentialWay;
        }

        public void setPreferentialWay(String preferentialWay) {
            this.preferentialWay = preferentialWay;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getFullNum() {
            return fullNum;
        }

        public void setFullNum(String fullNum) {
            this.fullNum = fullNum;
        }

        public String getReduceNum() {
            return reduceNum;
        }

        public void setReduceNum(String reduceNum) {
            this.reduceNum = reduceNum;
        }

        public String getPreferentialDiscount() {
            return preferentialDiscount;
        }

        public void setPreferentialDiscount(String preferentialDiscount) {
            this.preferentialDiscount = preferentialDiscount;
        }

        public String getPreferentialContent() {
            return preferentialContent;
        }

        public void setPreferentialContent(String preferentialContent) {
            this.preferentialContent = preferentialContent;
        }

        public String getUseForOrgId() {
            return useForOrgId;
        }

        public void setUseForOrgId(String useForOrgId) {
            this.useForOrgId = useForOrgId;
        }

        public String getUseForOrgLevelId() {
            return useForOrgLevelId;
        }

        public void setUseForOrgLevelId(String useForOrgLevelId) {
            this.useForOrgLevelId = useForOrgLevelId;
        }

        public String getCanOverlayUse() {
            return canOverlayUse;
        }

        public void setCanOverlayUse(String canOverlayUse) {
            this.canOverlayUse = canOverlayUse;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCouponDesc() {
            return couponDesc;
        }

        public void setCouponDesc(String couponDesc) {
            this.couponDesc = couponDesc;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getConsumeMoney() {
            return consumeMoney;
        }

        public void setConsumeMoney(String consumeMoney) {
            this.consumeMoney = consumeMoney;
        }
    }
}
