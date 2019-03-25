package cn.lex_mung.client_android.mvp.model.entity;

import java.util.List;

public class InstitutionEntity {

    /**
     * topList : [{"institutionId":7401,"institutionName":"广州市人民检察院","image":"","institutionTypeId":14,"regionId":440100,"establishTime":"","address":"","telephone":"","fax":"","email":"","zipcode":"","webstie":"","latitudeNum":"","longitudeNum":"","status":1,"dateAdded":"1970-01-01 00:00:01","dateModified":"1970-01-01 00:00:01","createUserId":0,"updateUserId":0,"isHot":0,"linkValue":"","createMemberId":0,"updateMemberId":0,"institutionDescription":""}]
     * result : {"pageNum":1,"pageSize":50,"size":13,"orderBy":"","startRow":1,"endRow":13,"total":13,"pages":1,"list":[{"institutionId":7402,"institutionName":"广州市荔湾区人民检察院","image":"","institutionTypeId":15,"regionId":440103,"establishTime":"","address":"","telephone":"","fax":"","email":"","zipcode":"","webstie":"","latitudeNum":"","longitudeNum":"","status":1,"dateAdded":"1970-01-01 00:00:01","dateModified":"1970-01-01 00:00:01","createUserId":0,"updateUserId":0,"isHot":0,"linkValue":"","createMemberId":0,"updateMemberId":0,"institutionDescription":""},{"institutionId":7403,"institutionName":"广州市越秀区人民检察院","image":"","institutionTypeId":15,"regionId":440104,"establishTime":"","address":"","telephone":"","fax":"","email":"","zipcode":"","webstie":"","latitudeNum":"","longitudeNum":"","status":1,"dateAdded":"1970-01-01 00:00:01","dateModified":"1970-01-01 00:00:01","createUserId":0,"updateUserId":0,"isHot":0,"linkValue":"","createMemberId":0,"updateMemberId":0,"institutionDescription":""},{"institutionId":7404,"institutionName":"广州市海珠区人民检察院","image":"","institutionTypeId":15,"regionId":440105,"establishTime":"","address":"","telephone":"","fax":"","email":"","zipcode":"","webstie":"","latitudeNum":"","longitudeNum":"","status":1,"dateAdded":"1970-01-01 00:00:01","dateModified":"1970-01-01 00:00:01","createUserId":0,"updateUserId":0,"isHot":0,"linkValue":"","createMemberId":0,"updateMemberId":0,"institutionDescription":""},{"institutionId":7405,"institutionName":"广州市天河区人民检察院","image":"","institutionTypeId":15,"regionId":440106,"establishTime":"","address":"","telephone":"","fax":"","email":"","zipcode":"","webstie":"","latitudeNum":"","longitudeNum":"","status":1,"dateAdded":"1970-01-01 00:00:01","dateModified":"1970-01-01 00:00:01","createUserId":0,"updateUserId":0,"isHot":0,"linkValue":"","createMemberId":0,"updateMemberId":0,"institutionDescription":""},{"institutionId":7406,"institutionName":"广州市白云区人民检察院","image":"","institutionTypeId":15,"regionId":440111,"establishTime":"","address":"","telephone":"","fax":"","email":"","zipcode":"","webstie":"","latitudeNum":"","longitudeNum":"","status":1,"dateAdded":"1970-01-01 00:00:01","dateModified":"1970-01-01 00:00:01","createUserId":0,"updateUserId":0,"isHot":0,"linkValue":"","createMemberId":0,"updateMemberId":0,"institutionDescription":""},{"institutionId":7407,"institutionName":"广州市黄埔区人民检察院","image":"","institutionTypeId":15,"regionId":440112,"establishTime":"","address":"","telephone":"","fax":"","email":"","zipcode":"","webstie":"","latitudeNum":"","longitudeNum":"","status":1,"dateAdded":"1970-01-01 00:00:01","dateModified":"1970-01-01 00:00:01","createUserId":0,"updateUserId":0,"isHot":0,"linkValue":"","createMemberId":0,"updateMemberId":0,"institutionDescription":""},{"institutionId":7408,"institutionName":"广州市番禺区人民检察院","image":"","institutionTypeId":15,"regionId":440113,"establishTime":"","address":"","telephone":"","fax":"","email":"","zipcode":"","webstie":"","latitudeNum":"","longitudeNum":"","status":1,"dateAdded":"1970-01-01 00:00:01","dateModified":"1970-01-01 00:00:01","createUserId":0,"updateUserId":0,"isHot":0,"linkValue":"","createMemberId":0,"updateMemberId":0,"institutionDescription":""},{"institutionId":7409,"institutionName":"广州市花都区人民检察院","image":"","institutionTypeId":15,"regionId":440114,"establishTime":"","address":"","telephone":"","fax":"","email":"","zipcode":"","webstie":"","latitudeNum":"","longitudeNum":"","status":1,"dateAdded":"1970-01-01 00:00:01","dateModified":"1970-01-01 00:00:01","createUserId":0,"updateUserId":0,"isHot":0,"linkValue":"","createMemberId":0,"updateMemberId":0,"institutionDescription":""},{"institutionId":7410,"institutionName":"广州市南沙区人民检察院","image":"","institutionTypeId":15,"regionId":440115,"establishTime":"","address":"","telephone":"","fax":"","email":"","zipcode":"","webstie":"","latitudeNum":"","longitudeNum":"","status":1,"dateAdded":"1970-01-01 00:00:01","dateModified":"1970-01-01 00:00:01","createUserId":0,"updateUserId":0,"isHot":0,"linkValue":"","createMemberId":0,"updateMemberId":0,"institutionDescription":""},{"institutionId":7411,"institutionName":"广州市萝岗区人民检察院","image":"","institutionTypeId":15,"regionId":440100,"establishTime":"","address":"","telephone":"","fax":"","email":"","zipcode":"","webstie":"","latitudeNum":"","longitudeNum":"","status":1,"dateAdded":"1970-01-01 00:00:01","dateModified":"1970-01-01 00:00:01","createUserId":0,"updateUserId":0,"isHot":0,"linkValue":"","createMemberId":0,"updateMemberId":0,"institutionDescription":""},{"institutionId":7412,"institutionName":"广州市增城区人民检察院","image":"","institutionTypeId":15,"regionId":440118,"establishTime":"","address":"","telephone":"","fax":"","email":"","zipcode":"","webstie":"","latitudeNum":"","longitudeNum":"","status":1,"dateAdded":"1970-01-01 00:00:01","dateModified":"1970-01-01 00:00:01","createUserId":0,"updateUserId":0,"isHot":0,"linkValue":"","createMemberId":0,"updateMemberId":0,"institutionDescription":""},{"institutionId":7413,"institutionName":"广州市从化区人民检察院","image":"","institutionTypeId":15,"regionId":440117,"establishTime":"","address":"","telephone":"","fax":"","email":"","zipcode":"","webstie":"","latitudeNum":"","longitudeNum":"","status":1,"dateAdded":"1970-01-01 00:00:01","dateModified":"1970-01-01 00:00:01","createUserId":0,"updateUserId":0,"isHot":0,"linkValue":"","createMemberId":0,"updateMemberId":0,"institutionDescription":""},{"institutionId":8057,"institutionName":"贵阳市白云区人民检察院","image":"","institutionTypeId":15,"regionId":440111,"establishTime":"","address":"","telephone":"","fax":"","email":"","zipcode":"","webstie":"","latitudeNum":"","longitudeNum":"","status":1,"dateAdded":"1970-01-01 00:00:01","dateModified":"1970-01-01 00:00:01","createUserId":0,"updateUserId":0,"isHot":0,"linkValue":"","createMemberId":0,"updateMemberId":0,"institutionDescription":""}],"firstPage":1,"prePage":0,"nextPage":0,"lastPage":1,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1]}
     */

    private ResultBean result;
    private List<InstitutionListEntity> topList;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public List<InstitutionListEntity> getTopList() {
        return topList;
    }

    public void setTopList(List<InstitutionListEntity> topList) {
        this.topList = topList;
    }

    public static class ResultBean {
        /**
         * pageNum : 1
         * pageSize : 50
         * size : 13
         * orderBy :
         * startRow : 1
         * endRow : 13
         * total : 13
         * pages : 1
         * list : [{"institutionId":7402,"institutionName":"广州市荔湾区人民检察院","image":"","institutionTypeId":15,"regionId":440103,"establishTime":"","address":"","telephone":"","fax":"","email":"","zipcode":"","webstie":"","latitudeNum":"","longitudeNum":"","status":1,"dateAdded":"1970-01-01 00:00:01","dateModified":"1970-01-01 00:00:01","createUserId":0,"updateUserId":0,"isHot":0,"linkValue":"","createMemberId":0,"updateMemberId":0,"institutionDescription":""},{"institutionId":7403,"institutionName":"广州市越秀区人民检察院","image":"","institutionTypeId":15,"regionId":440104,"establishTime":"","address":"","telephone":"","fax":"","email":"","zipcode":"","webstie":"","latitudeNum":"","longitudeNum":"","status":1,"dateAdded":"1970-01-01 00:00:01","dateModified":"1970-01-01 00:00:01","createUserId":0,"updateUserId":0,"isHot":0,"linkValue":"","createMemberId":0,"updateMemberId":0,"institutionDescription":""},{"institutionId":7404,"institutionName":"广州市海珠区人民检察院","image":"","institutionTypeId":15,"regionId":440105,"establishTime":"","address":"","telephone":"","fax":"","email":"","zipcode":"","webstie":"","latitudeNum":"","longitudeNum":"","status":1,"dateAdded":"1970-01-01 00:00:01","dateModified":"1970-01-01 00:00:01","createUserId":0,"updateUserId":0,"isHot":0,"linkValue":"","createMemberId":0,"updateMemberId":0,"institutionDescription":""},{"institutionId":7405,"institutionName":"广州市天河区人民检察院","image":"","institutionTypeId":15,"regionId":440106,"establishTime":"","address":"","telephone":"","fax":"","email":"","zipcode":"","webstie":"","latitudeNum":"","longitudeNum":"","status":1,"dateAdded":"1970-01-01 00:00:01","dateModified":"1970-01-01 00:00:01","createUserId":0,"updateUserId":0,"isHot":0,"linkValue":"","createMemberId":0,"updateMemberId":0,"institutionDescription":""},{"institutionId":7406,"institutionName":"广州市白云区人民检察院","image":"","institutionTypeId":15,"regionId":440111,"establishTime":"","address":"","telephone":"","fax":"","email":"","zipcode":"","webstie":"","latitudeNum":"","longitudeNum":"","status":1,"dateAdded":"1970-01-01 00:00:01","dateModified":"1970-01-01 00:00:01","createUserId":0,"updateUserId":0,"isHot":0,"linkValue":"","createMemberId":0,"updateMemberId":0,"institutionDescription":""},{"institutionId":7407,"institutionName":"广州市黄埔区人民检察院","image":"","institutionTypeId":15,"regionId":440112,"establishTime":"","address":"","telephone":"","fax":"","email":"","zipcode":"","webstie":"","latitudeNum":"","longitudeNum":"","status":1,"dateAdded":"1970-01-01 00:00:01","dateModified":"1970-01-01 00:00:01","createUserId":0,"updateUserId":0,"isHot":0,"linkValue":"","createMemberId":0,"updateMemberId":0,"institutionDescription":""},{"institutionId":7408,"institutionName":"广州市番禺区人民检察院","image":"","institutionTypeId":15,"regionId":440113,"establishTime":"","address":"","telephone":"","fax":"","email":"","zipcode":"","webstie":"","latitudeNum":"","longitudeNum":"","status":1,"dateAdded":"1970-01-01 00:00:01","dateModified":"1970-01-01 00:00:01","createUserId":0,"updateUserId":0,"isHot":0,"linkValue":"","createMemberId":0,"updateMemberId":0,"institutionDescription":""},{"institutionId":7409,"institutionName":"广州市花都区人民检察院","image":"","institutionTypeId":15,"regionId":440114,"establishTime":"","address":"","telephone":"","fax":"","email":"","zipcode":"","webstie":"","latitudeNum":"","longitudeNum":"","status":1,"dateAdded":"1970-01-01 00:00:01","dateModified":"1970-01-01 00:00:01","createUserId":0,"updateUserId":0,"isHot":0,"linkValue":"","createMemberId":0,"updateMemberId":0,"institutionDescription":""},{"institutionId":7410,"institutionName":"广州市南沙区人民检察院","image":"","institutionTypeId":15,"regionId":440115,"establishTime":"","address":"","telephone":"","fax":"","email":"","zipcode":"","webstie":"","latitudeNum":"","longitudeNum":"","status":1,"dateAdded":"1970-01-01 00:00:01","dateModified":"1970-01-01 00:00:01","createUserId":0,"updateUserId":0,"isHot":0,"linkValue":"","createMemberId":0,"updateMemberId":0,"institutionDescription":""},{"institutionId":7411,"institutionName":"广州市萝岗区人民检察院","image":"","institutionTypeId":15,"regionId":440100,"establishTime":"","address":"","telephone":"","fax":"","email":"","zipcode":"","webstie":"","latitudeNum":"","longitudeNum":"","status":1,"dateAdded":"1970-01-01 00:00:01","dateModified":"1970-01-01 00:00:01","createUserId":0,"updateUserId":0,"isHot":0,"linkValue":"","createMemberId":0,"updateMemberId":0,"institutionDescription":""},{"institutionId":7412,"institutionName":"广州市增城区人民检察院","image":"","institutionTypeId":15,"regionId":440118,"establishTime":"","address":"","telephone":"","fax":"","email":"","zipcode":"","webstie":"","latitudeNum":"","longitudeNum":"","status":1,"dateAdded":"1970-01-01 00:00:01","dateModified":"1970-01-01 00:00:01","createUserId":0,"updateUserId":0,"isHot":0,"linkValue":"","createMemberId":0,"updateMemberId":0,"institutionDescription":""},{"institutionId":7413,"institutionName":"广州市从化区人民检察院","image":"","institutionTypeId":15,"regionId":440117,"establishTime":"","address":"","telephone":"","fax":"","email":"","zipcode":"","webstie":"","latitudeNum":"","longitudeNum":"","status":1,"dateAdded":"1970-01-01 00:00:01","dateModified":"1970-01-01 00:00:01","createUserId":0,"updateUserId":0,"isHot":0,"linkValue":"","createMemberId":0,"updateMemberId":0,"institutionDescription":""},{"institutionId":8057,"institutionName":"贵阳市白云区人民检察院","image":"","institutionTypeId":15,"regionId":440111,"establishTime":"","address":"","telephone":"","fax":"","email":"","zipcode":"","webstie":"","latitudeNum":"","longitudeNum":"","status":1,"dateAdded":"1970-01-01 00:00:01","dateModified":"1970-01-01 00:00:01","createUserId":0,"updateUserId":0,"isHot":0,"linkValue":"","createMemberId":0,"updateMemberId":0,"institutionDescription":""}]
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
        private List<InstitutionListEntity> list;
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

        public List<InstitutionListEntity> getList() {
            return list;
        }

        public void setList(List<InstitutionListEntity> list) {
            this.list = list;
        }

        public List<Integer> getNavigatepageNums() {
            return navigatepageNums;
        }

        public void setNavigatepageNums(List<Integer> navigatepageNums) {
            this.navigatepageNums = navigatepageNums;
        }
    }
}
