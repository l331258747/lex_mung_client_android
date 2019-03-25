package cn.lex_mung.client_android.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

public class TradingListEntity implements Serializable {


    /**
     * pageNum : 1
     * pageSize : 10
     * size : 3
     * orderBy :
     * startRow : 1
     * endRow : 3
     * total : 3
     * pages : 1
     * list : [{"memberId":4,"orderStatus":2,"talkTime":"2:08","talkTime1s":128,"talkTime1m":2,"talkTime1h":0,"orderNo":"31354315","payStatus":"已完成","orderType":"专家咨询","payType":"1","budget":"收入","withdrawAccount":"","statusValue":"","typeName":"","content":"","memberName":"佩","lmemberName":"盛浩","beginTime":"2018-12-17 20:18:48","endTime":"2018-12-17 20:20:56","createDate":"2018-12-19 17:29:17","buyerPayAmount":300,"pageNum":0,"pageSize":0},{"memberId":4,"orderStatus":2,"talkTime":"","talkTime1s":0,"talkTime1m":0,"talkTime1h":0,"orderNo":"PA18121901658756675","payStatus":"已完成","orderType":"增信保证金","payType":"2","budget":"收入","withdrawAccount":"","statusValue":"","typeName":"","content":"","memberName":"","lmemberName":"","beginTime":"1970-01-01 08:00:00","endTime":"1970-01-01 08:00:00","createDate":"2018-12-19 12:04:43","buyerPayAmount":1000,"pageNum":0,"pageSize":0},{"memberId":4,"orderStatus":2,"talkTime":"","talkTime1s":0,"talkTime1m":0,"talkTime1h":0,"orderNo":"CZ124389","payStatus":"已完成","orderType":"充值","payType":"支付宝","budget":"收入","withdrawAccount":"","statusValue":"","typeName":"","content":"","memberName":"","lmemberName":"","beginTime":"1970-01-01 08:00:00","endTime":"1970-01-01 08:00:00","createDate":"2018-12-18 15:25:16","buyerPayAmount":5000,"pageNum":0,"pageSize":0}]
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

    public static class ListBean implements Serializable {
        /**
         * memberId : 4
         * orderStatus : 2
         * talkTime : 2:08
         * talkTime1s : 128
         * talkTime1m : 2
         * talkTime1h : 0
         * orderNo : 31354315
         * payStatus : 已完成
         * orderType : 专家咨询
         * payType : 1
         * budget : 收入
         * withdrawAccount :
         * statusValue :
         * typeName :
         * content :
         * memberName : 佩
         * lmemberName : 盛浩
         * beginTime : 2018-12-17 20:18:48
         * endTime : 2018-12-17 20:20:56
         * createDate : 2018-12-19 17:29:17
         * buyerPayAmount : 300
         * pageNum : 0
         * pageSize : 0
         */

        private int memberId;
        private int orderStatus;
        private String talkTime;
        private int talkTime1s;
        private int talkTime1m;
        private int talkTime1h;
        private String orderNo;
        private String payStatus;
        private String orderType;
        private String payType;
        private String budget;
        private String withdrawAccount;
        private String statusValue;
        private String typeName;
        private String content;
        private String memberName;
        private String lmemberName;
        private String beginTime;
        private String endTime;
        private String createDate;
        private double buyerPayAmount;
        private int pageNum;
        private int pageSize;

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public int getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(int orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getTalkTime() {
            return talkTime;
        }

        public void setTalkTime(String talkTime) {
            this.talkTime = talkTime;
        }

        public int getTalkTime1s() {
            return talkTime1s;
        }

        public void setTalkTime1s(int talkTime1s) {
            this.talkTime1s = talkTime1s;
        }

        public int getTalkTime1m() {
            return talkTime1m;
        }

        public void setTalkTime1m(int talkTime1m) {
            this.talkTime1m = talkTime1m;
        }

        public int getTalkTime1h() {
            return talkTime1h;
        }

        public void setTalkTime1h(int talkTime1h) {
            this.talkTime1h = talkTime1h;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getPayStatus() {
            return payStatus;
        }

        public void setPayStatus(String payStatus) {
            this.payStatus = payStatus;
        }

        public String getOrderType() {
            return orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public String getBudget() {
            return budget;
        }

        public void setBudget(String budget) {
            this.budget = budget;
        }

        public String getWithdrawAccount() {
            return withdrawAccount;
        }

        public void setWithdrawAccount(String withdrawAccount) {
            this.withdrawAccount = withdrawAccount;
        }

        public String getStatusValue() {
            return statusValue;
        }

        public void setStatusValue(String statusValue) {
            this.statusValue = statusValue;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getMemberName() {
            return memberName;
        }

        public void setMemberName(String memberName) {
            this.memberName = memberName;
        }

        public String getLmemberName() {
            return lmemberName;
        }

        public void setLmemberName(String lmemberName) {
            this.lmemberName = lmemberName;
        }

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public double getBuyerPayAmount() {
            return buyerPayAmount;
        }

        public void setBuyerPayAmount(double buyerPayAmount) {
            this.buyerPayAmount = buyerPayAmount;
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
    }
}
