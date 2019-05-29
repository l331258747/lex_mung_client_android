package cn.lex_mung.client_android.mvp.model.entity.help;

import java.util.List;

public class HirstoryDemandEntity {


    /**
     * pageNum : 1
     * pageSize : 10
     * size : 3
     * orderBy :
     * startRow : 1
     * endRow : 3
     * total : 3
     * pages : 1
     * list : [{"orderNo":"XQ19052801393068932","id":3664,"memberId":6082,"lawyerMemberId":4795,"createDate":"2019-05-28 14:04:57","typeName":"审查合同","requireTypeId":30,"content":"EMS女球迷若果搜索木事木事女人","requirementExtendValue":"200","pageNum":0,"pageSize":0},{"orderNo":"XQ19051000247015747","id":3583,"memberId":6082,"lawyerMemberId":4795,"createDate":"2019-05-10 17:45:30","typeName":"审查合同","requireTypeId":30,"content":"小伙伴呢补偿你想你想你想你想你想你想你想那些","requirementExtendValue":"30","pageNum":0,"pageSize":0},{"orderNo":"XQ19050600441913066","id":3527,"memberId":6082,"lawyerMemberId":5047,"createDate":"2019-05-06 18:42:35","typeName":"审查合同","requireTypeId":30,"content":"卡不哭我我我啊呀呀哇哇哇","requirementExtendValue":"36","pageNum":0,"pageSize":0}]
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
         * orderNo : XQ19052801393068932
         * id : 3664
         * memberId : 6082
         * lawyerMemberId : 4795
         * createDate : 2019-05-28 14:04:57
         * typeName : 审查合同
         * requireTypeId : 30
         * content : EMS女球迷若果搜索木事木事女人
         * requirementExtendValue : 200
         * pageNum : 0
         * pageSize : 0
         */

        private String orderNo;
        private int id;
        private int memberId;
        private int lawyerMemberId;
        private String createDate;
        private String typeName;
        private int requireTypeId;
        private String content;
        private String requirementExtendValue;
        private int pageNum;
        private int pageSize;

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
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

        public int getLawyerMemberId() {
            return lawyerMemberId;
        }

        public void setLawyerMemberId(int lawyerMemberId) {
            this.lawyerMemberId = lawyerMemberId;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public int getRequireTypeId() {
            return requireTypeId;
        }

        public void setRequireTypeId(int requireTypeId) {
            this.requireTypeId = requireTypeId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getRequirementExtendValue() {
            return requirementExtendValue;
        }

        public void setRequirementExtendValue(String requirementExtendValue) {
            this.requirementExtendValue = requirementExtendValue;
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
