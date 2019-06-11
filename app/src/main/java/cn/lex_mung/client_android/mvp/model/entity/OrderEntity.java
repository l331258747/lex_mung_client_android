package cn.lex_mung.client_android.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

import me.zl.mvp.utils.StringUtils;

public class OrderEntity implements Serializable {

    /**
     * pageNum : 1
     * pageSize : 5
     * size : 5
     * orderBy :
     * startRow : 1
     * endRow : 5
     * total : 5
     * pages : 1
     * list : [{"id":2098,"memberId":5006,"orderStatus":0,"talkTime":"","talkTime1s":0,"talkTime1m":0,"talkTime1h":0,"orderNo":"201812261159405006","payStatus":"","orderType":"需求","payType":"","budget":"","withdrawAccount":"","statusValue":"未接单","typeName":"诉讼/仲裁","content":"123","memberName":"","lmemberName":"汤坤","iconImage":"http://oss.lex-mung.com/icon_image_member_f825a93a7.jpg","lawyerMemberId":55,"beginTime":"1970-01-01 08:00:00","endTime":"1970-01-01 08:00:00","createDate":"2018-12-26 11:59:40","buyerPayAmount":10000,"pageNum":0,"pageSize":0},{"id":2090,"memberId":5006,"orderStatus":0,"talkTime":"","talkTime1s":0,"talkTime1m":0,"talkTime1h":0,"orderNo":"201812251611395006","payStatus":"","orderType":"需求","payType":"","budget":"","withdrawAccount":"","statusValue":"未接单","typeName":"诉讼/仲裁","content":"123","memberName":"","lmemberName":"LEX盛晓涵","iconImage":"http://oss.lex-mung.com/icon_image_member_ab62da4fe.jpg","lawyerMemberId":174,"beginTime":"1970-01-01 08:00:00","endTime":"1970-01-01 08:00:00","createDate":"2018-12-25 16:11:40","buyerPayAmount":0,"pageNum":0,"pageSize":0},{"id":2083,"memberId":5006,"orderStatus":0,"talkTime":"","talkTime1s":0,"talkTime1m":0,"talkTime1h":0,"orderNo":"201812221400425006","payStatus":"","orderType":"需求","payType":"","budget":"","withdrawAccount":"","statusValue":"未接单","typeName":"诉讼/仲裁","content":"aaa","memberName":"","lmemberName":"黄奕","iconImage":"http://oss.lex-mung.com/icon_image_member_bf287a541.jpg","lawyerMemberId":2443,"beginTime":"1970-01-01 08:00:00","endTime":"1970-01-01 08:00:00","createDate":"2018-12-22 14:00:42","buyerPayAmount":0,"pageNum":0,"pageSize":0},{"id":2082,"memberId":5006,"orderStatus":0,"talkTime":"","talkTime1s":0,"talkTime1m":0,"talkTime1h":0,"orderNo":"201812221355585006","payStatus":"","orderType":"需求","payType":"","budget":"","withdrawAccount":"","statusValue":"未接单","typeName":"律师函","content":"呵呵哒","memberName":"","lmemberName":"詹俊妮","iconImage":"http://oss.lex-mung.com/icon_image_member_0dac8d22c.jpg","lawyerMemberId":4750,"beginTime":"1970-01-01 08:00:00","endTime":"1970-01-01 08:00:00","createDate":"2018-12-22 13:55:58","buyerPayAmount":0,"pageNum":0,"pageSize":0},{"id":2081,"memberId":5006,"orderStatus":0,"talkTime":"","talkTime1s":0,"talkTime1m":0,"talkTime1h":0,"orderNo":"201812221316045006","payStatus":"","orderType":"需求","payType":"","budget":"","withdrawAccount":"","statusValue":"未接单","typeName":"诉讼/仲裁","content":"qwe","memberName":"","lmemberName":"LEX盛晓涵","iconImage":"http://oss.lex-mung.com/icon_image_member_ab62da4fe.jpg","lawyerMemberId":174,"beginTime":"1970-01-01 08:00:00","endTime":"1970-01-01 08:00:00","createDate":"2018-12-22 13:16:04","buyerPayAmount":0,"pageNum":0,"pageSize":0}]
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
         * id : 2098
         * memberId : 5006
         * orderStatus : 0
         * talkTime :
         * talkTime1s : 0
         * talkTime1m : 0
         * talkTime1h : 0
         * orderNo : 201812261159405006
         * payStatus :
         * orderType : 需求
         * payType :
         * budget :
         * withdrawAccount :
         * statusValue : 未接单
         * typeName : 诉讼/仲裁
         * content : 123
         * memberName :
         * lmemberName : 汤坤
         * iconImage : http://oss.lex-mung.com/icon_image_member_f825a93a7.jpg
         * lawyerMemberId : 55
         * beginTime : 1970-01-01 08:00:00
         * endTime : 1970-01-01 08:00:00
         * createDate : 2018-12-26 11:59:40
         * buyerPayAmount : 10000
         * pageNum : 0
         * pageSize : 0
         */

        private int id;
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
        private String iconImage;
        private int lawyerMemberId;
        private String beginTime;
        private String endTime;
        private String createDate;
        private double buyerPayAmount;
        private int pageNum;
        private int pageSize;
        private int typeId;
        private int isHot;
        private int replyCount;
        private int status;

        public int getStatus() {
            return status;
        }

        public int getReplyCount() {
            return replyCount;
        }

        public String getReplyCountStr() {
            return replyCount+"条回复";
        }

        public int getIsHot() {
            return isHot;
        }

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

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

        public String getLmemberNameStr() {
            return lmemberName + "律师";
        }

        public void setLmemberName(String lmemberName) {
            this.lmemberName = lmemberName;
        }

        public String getIconImage() {
            return iconImage;
        }

        public void setIconImage(String iconImage) {
            this.iconImage = iconImage;
        }

        public int getLawyerMemberId() {
            return lawyerMemberId;
        }

        public void setLawyerMemberId(int lawyerMemberId) {
            this.lawyerMemberId = lawyerMemberId;
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

        public String getBuyerPayAmountStr() {
            return StringUtils.getStringNum(buyerPayAmount);
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
