package cn.lex_mung.client_android.mvp.model.entity;

import java.util.List;

public class FreeConsultReplyEntity {

    /**
     * pageNum : 1
     * pageSize : 3
     * size : 3
     * orderBy :
     * startRow : 1
     * endRow : 3
     * total : 4
     * pages : 2
     * list : [{"consultationReplyId":8,"consultationNumber":"Q18112910000002","lawyerId":17,"memberId":20,"type":1,"dateAdded":"2018-11-29 16:49:06","content":"收到","lawyerName":"何律师","lawyerIconImage":"http://oss.lex-mung.com/icon_image_member_280d9729f.jpg","lawyerFirm":"","lawyerSex":0},{"consultationReplyId":9,"consultationNumber":"Q18112910000002","lawyerId":17,"memberId":20,"type":1,"dateAdded":"2018-11-29 16:49:45","content":"收到2","lawyerName":"何律师","lawyerIconImage":"http://oss.lex-mung.com/icon_image_member_280d9729f.jpg","lawyerFirm":"","lawyerSex":0},{"consultationReplyId":10,"consultationNumber":"Q18112910000002","lawyerId":17,"memberId":20,"type":1,"dateAdded":"2018-11-29 16:49:47","content":"收到3","lawyerName":"何律师","lawyerIconImage":"http://oss.lex-mung.com/icon_image_member_280d9729f.jpg","lawyerFirm":"","lawyerSex":0}]
     * firstPage : 1
     * prePage : 0
     * nextPage : 2
     * lastPage : 2
     * isFirstPage : true
     * isLastPage : false
     * hasPreviousPage : false
     * hasNextPage : true
     * navigatePages : 8
     * navigatepageNums : [1,2]
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
    private List<FreeConsultReplyListEntity> list;
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

    public List<FreeConsultReplyListEntity> getList() {
        return list;
    }

    public void setList(List<FreeConsultReplyListEntity> list) {
        this.list = list;
    }

    public List<Integer> getNavigatepageNums() {
        return navigatepageNums;
    }

    public void setNavigatepageNums(List<Integer> navigatepageNums) {
        this.navigatepageNums = navigatepageNums;
    }
}
