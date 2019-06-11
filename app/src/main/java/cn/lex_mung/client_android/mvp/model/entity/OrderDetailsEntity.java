package cn.lex_mung.client_android.mvp.model.entity;

import java.util.List;

import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.StringUtils;

public class OrderDetailsEntity {


    /**
     * pageNum : 1
     * pageSize : 1
     * size : 1
     * orderBy :
     * startRow : 0
     * endRow : 0
     * total : 1
     * pages : 1
     * list : [{"id":480,"memberId":4691,"orderStatus":7,"talkTime":"","talkTime1s":0,"talkTime1m":0,"talkTime1h":0,"callTime":189,"orderNo":"QC19031501661765554","payStatus":"2","orderType":"快速电话咨询","payType":"","budget":"","withdrawAccount":"","statusValue":"已完成","typeName":"海外投资","typeId":4,"content":"","memberName":"用户我我我","lmemberName":"律师我我我","iconImage":"http://oss.lex-mung.com/icon_image_member_15467318541129.png","lawyerMemberId":5020,"really":1,"beginTime":"","endTime":"","createDate":"2019-03-15 10:27:50","buyerPayAmount":0.01,"pageNum":0,"pageSize":0,"remainingTime":0,"grabTime":"2019-03-15 10:29:06","regionId":0,"rname":"","couponName":"","scopeOfUse":0,"couponType":0,"couponDeductionAmount":0,"useCoupon":0,"payStatusValue":"支付成功","couponId":0,"payAmount":-0.01,"orderAmount":"","quickTime":[{"id":480,"beginTime":"2019-03-15 15:03:29","endTime":"2019-03-15 15:06:38"}]}]
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
         * id : 480
         * memberId : 4691
         * orderStatus : 7
         * talkTime :
         * talkTime1s : 0
         * talkTime1m : 0
         * talkTime1h : 0
         * callTime : 189
         * orderNo : QC19031501661765554
         * payStatus : 2
         * orderType : 快速电话咨询
         * payType :
         * budget :
         * withdrawAccount :
         * statusValue : 已完成
         * typeName : 海外投资
         * typeId : 4
         * content :
         * memberName : 用户我我我
         * lmemberName : 律师我我我
         * iconImage : http://oss.lex-mung.com/icon_image_member_15467318541129.png
         * lawyerMemberId : 5020
         * really : 1
         * beginTime :
         * endTime :
         * createDate : 2019-03-15 10:27:50
         * buyerPayAmount : 0.01
         * pageNum : 0
         * pageSize : 0
         * remainingTime : 0
         * grabTime : 2019-03-15 10:29:06
         * regionId : 0
         * rname :
         * couponName :
         * scopeOfUse : 0
         * couponType : 0
         * couponDeductionAmount : 0
         * useCoupon : 0
         * payStatusValue : 支付成功
         * couponId : 0
         * payAmount : -0.01
         * orderAmount :
         * quickTime : [{"id":480,"beginTime":"2019-03-15 15:03:29","endTime":"2019-03-15 15:06:38"}]
         */

        private int id;
        private int memberId;
        private int orderStatus;
        private String talkTime;
        private int talkTime1s;
        private int talkTime1m;
        private int talkTime1h;
        private int callTime;
        private String orderNo;
        private String payStatus;
        private String orderType;
        private String payType;
        private String budget;
        private String withdrawAccount;
        private String statusValue;
        private String typeName;
        private int typeId;
        private String content;
        private String memberName;
        private String lmemberName;
        private String iconImage;
        private int lawyerMemberId;
        private int really;
        private String beginTime;
        private String endTime;
        private String createDate;
        private double buyerPayAmount;
        private int pageNum;
        private int pageSize;
        private int remainingTime;
        private String grabTime;
        private int regionId;
        private String rname;
        private String couponName;
        private int scopeOfUse;
        private int couponType;
        private double couponDeductionAmount;
        private int useCoupon;
        private String payStatusValue;
        private int couponId;
        private double payAmount;
        private String orderAmount;
        private List<QuickTimeBean> quickTime;
        private int callback;
        private String institutionName;
        private String lmobile;

        public String getLmobile() {
            return lmobile;
        }

        public String getInstitutionName() {
            return institutionName;
        }

        public int getCallback() {
            return callback;
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

        public int getCallTime() {
            return callTime;
        }

        public void setCallTime(int callTime) {
            this.callTime = callTime;
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

        public String getPayTypeStr() {//1.微信，2支付宝，3余额，4会员卡，5百度）
            switch (payType) {
                case "1":
                    return "微信支付";
                case "2":
                    return "支付宝支付";
                case "3":
                    return "余额支付";
                case "4":
                    return "会员卡支付";
                case "5":
                    return "百度支付";
            }
            return payType + "支付";
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

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
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

        public String getLMemeberName2(){
            return rname + " | " + institutionName;
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

        public int getReally() {
            return really;
        }

        public void setReally(int really) {
            this.really = really;
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
            return StringUtils.getStringNum(buyerPayAmount) + "元";
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

        public int getRemainingTime() {
            return remainingTime;
        }

        public void setRemainingTime(int remainingTime) {
            this.remainingTime = remainingTime;
        }

        public String getGrabTime() {
            return grabTime;
        }

        public void setGrabTime(String grabTime) {
            this.grabTime = grabTime;
        }

        public int getRegionId() {
            return regionId;
        }

        public void setRegionId(int regionId) {
            this.regionId = regionId;
        }

        public String getRname() {
            return rname;
        }

        public void setRname(String rname) {
            this.rname = rname;
        }

        public String getCouponName() {
            return couponName;
        }

        public void setCouponName(String couponName) {
            this.couponName = couponName;
        }

        public int getScopeOfUse() {
            return scopeOfUse;
        }

        public void setScopeOfUse(int scopeOfUse) {
            this.scopeOfUse = scopeOfUse;
        }

        public int getCouponType() {
            return couponType;
        }

        public String getCouponTypeStr() {//优惠券类型,1.会员卡，2.电子优惠券，3.线下优惠券，4.体验券，目前只做1和2
            switch (couponType) {
                case 1:
                    return "会员卡-" + couponName;
                case 2:
                    return "电子优惠券-" + couponName;
                case 3:
                    return "线下优惠券-" + couponName;
                case 4:
                    return "体验券-" + couponName;
            }
            return "";
        }

        public void setCouponType(int couponType) {
            this.couponType = couponType;
        }

        public double getCouponDeductionAmount() {
            return couponDeductionAmount;
        }

        public String getCouponDeductionAmountStr() {
            return StringUtils.getStringNum(couponDeductionAmount) + "元";
        }

        public void setCouponDeductionAmount(double couponDeductionAmount) {
            this.couponDeductionAmount = couponDeductionAmount;
        }

        public int getUseCoupon() {
            return useCoupon;
        }

        public void setUseCoupon(int useCoupon) {
            this.useCoupon = useCoupon;
        }

        public String getPayStatusValue() {
            return payStatusValue;
        }

        public void setPayStatusValue(String payStatusValue) {
            this.payStatusValue = payStatusValue;
        }

        public int getCouponId() {
            return couponId;
        }

        public void setCouponId(int couponId) {
            this.couponId = couponId;
        }

        public double getPayAmount() {
            return payAmount;
        }

        public String getPayAmountStr() {
            return StringUtils.getStringNum(payAmount) + "元";
        }

        public void setPayAmount(double payAmount) {
            this.payAmount = payAmount;
        }

        public String getOrderAmount() {
            return orderAmount;
        }

        public void setOrderAmount(String orderAmount) {
            this.orderAmount = orderAmount;
        }

        public List<QuickTimeBean> getQuickTime() {
            return quickTime;
        }

        public void setQuickTime(List<QuickTimeBean> quickTime) {
            this.quickTime = quickTime;
        }

        public static class QuickTimeBean {
            /**
             * id : 480
             * beginTime : 2019-03-15 15:03:29
             * endTime : 2019-03-15 15:06:38
             */

            private int id;
            private String beginTime;
            private String endTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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
        }
    }
}
