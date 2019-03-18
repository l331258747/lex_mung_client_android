package com.lex_mung.client_android.mvp.model.entity;

import java.util.List;

public class CaseListEntity {

    /**
     * pageNum : 1
     * pageSize : 5
     * size : 5
     * orderBy :
     * startRow : 1
     * endRow : 5
     * total : 38
     * pages : 8
     * list : [{"memberId":2,"id":"b11d3edd-859b-46d9-90b9-a3f639f63d79","casetype":"民事","trialround":"2","judgementtype":"裁定","courtname":"白银市中级人民法院","casenumber":"（2017）甘04民终34号","judgementdate":"2017-01-13","publishdate":"2017-03-22","publishtype":"0","hitresult":"","hashistoricaljudgment":"false","watched":"false","similarjudgement":"false","tiantongcode":"false","hascaseanalysis":"false","haswusongreaderacticle":"false","temporarysearchreport":"false","title":"上诉人苏钰人与被上诉人熊建刚委托合同纠纷一案二审民事裁定书","keywords":"","courtopinion":""},{"memberId":2,"id":"1e7f1e34-7ad4-4e51-97c1-b8ab34b508ed","casetype":"民事","trialround":"2","judgementtype":"裁定","courtname":"甘肃省高级人民法院","casenumber":"（2017）甘民终593号","judgementdate":"2017-11-16","publishdate":"2018-02-23","publishtype":"0","hitresult":"","hashistoricaljudgment":"false","watched":"false","similarjudgement":"false","tiantongcode":"false","hascaseanalysis":"false","haswusongreaderacticle":"false","temporarysearchreport":"false","title":"上诉人巨昶辉与被上诉人中国农业银行股份有限公司兰州城关支行、原审被告甘肃兴生源农业开发有限公司、巨有军二审民事裁定书","keywords":"","courtopinion":"本院认为，上诉人巨昶辉在本院审理期间提出撤回上诉的请求，不违反法律规定，本院予以准许。依照《中华人民共和国民事诉讼法》第一百七十三条规定，裁定如下："},{"memberId":2,"id":"1234b963-1ce1-4675-b7df-630886418c5d","casetype":"民事","trialround":"1","judgementtype":"判决","courtname":"兰州市中级人民法院","casenumber":"（2017）甘01民初186号","judgementdate":"2017-07-20","publishdate":"2017-09-26","publishtype":"0","hitresult":"","hashistoricaljudgment":"false","watched":"false","similarjudgement":"false","tiantongcode":"false","hascaseanalysis":"false","haswusongreaderacticle":"false","temporarysearchreport":"false","title":"中国农业银行股份有限公司兰州高新技术开发区支行和兰州鲁通商贸有限责任公司;徐尔珍;巨有军;永靖县独一处度假村有限公司金融借款合同纠纷一审民事判决书","keywords":"","courtopinion":"综上，原告农行兰州高新区支行通过诉讼主张权利，使其债权得以实现，不违反法律规定，本院予以支持。依照《中华人民共和国合同法》第八条、第一百零七条、第二百零五条、第二百零七条、《中华人民共和国担保法》第六"},{"memberId":2,"id":"2d6fb19a-65cf-4e0c-8b8c-8719a8ff1ab4","casetype":"民事","trialround":"1","judgementtype":"判决","courtname":"兰州市中级人民法院","casenumber":"（2017）甘01民初138号","judgementdate":"2017-08-22","publishdate":"2017-11-17","publishtype":"0","hitresult":"","hashistoricaljudgment":"false","watched":"false","similarjudgement":"false","tiantongcode":"false","hascaseanalysis":"false","haswusongreaderacticle":"false","temporarysearchreport":"false","title":"中国农业银行股份有限公司兰州城关支行和巨昶辉;甘肃兴生源农业开发有限公司;巨有军借款合同纠纷一审民事判决书","keywords":"","courtopinion":"本院认为，原告农行城关支行（贷款人）与被告巨昶辉（借款人）、兴生源公司（抵押人）签订的最高额担保个人借款合同系各方当事人真实意思表示，不违反法律法规的强制性规定，合法有效，各方当事人均应按照合同约定履"},{"memberId":2,"id":"d56d985a-7789-4737-bb6c-fee46655c20c","casetype":"民事","trialround":"2","judgementtype":"判决","courtname":"兰州市中级人民法院","casenumber":"（2016）甘01民终1721号","judgementdate":"2016-10-24","publishdate":"2017-03-28","publishtype":"0","hitresult":"","hashistoricaljudgment":"false","watched":"false","similarjudgement":"false","tiantongcode":"false","hascaseanalysis":"false","haswusongreaderacticle":"false","temporarysearchreport":"false","title":"城关区广场南路皇冠发艺店与豆莲财产损害赔偿纠纷二审民事判决书","keywords":"","courtopinion":"本院认为，根据《最高人民法院关于适用的解释》第九十条之规定，当事人对自己提出的诉讼请求所依据的事实或者反驳对方诉讼请求所依据的事实，应当提供证据加以证明。在作出判决前，当事人未能提供证据或者证据不足以"}]
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
         * memberId : 2
         * id : b11d3edd-859b-46d9-90b9-a3f639f63d79
         * casetype : 民事
         * trialround : 2
         * judgementtype : 裁定
         * courtname : 白银市中级人民法院
         * casenumber : （2017）甘04民终34号
         * judgementdate : 2017-01-13
         * publishdate : 2017-03-22
         * publishtype : 0
         * hitresult :
         * hashistoricaljudgment : false
         * watched : false
         * similarjudgement : false
         * tiantongcode : false
         * hascaseanalysis : false
         * haswusongreaderacticle : false
         * temporarysearchreport : false
         * title : 上诉人苏钰人与被上诉人熊建刚委托合同纠纷一案二审民事裁定书
         * keywords :
         * courtopinion :
         */

        private int memberId;
        private String id;
        private String casetype;
        private String trialround;
        private String judgementtype;
        private String courtname;
        private String casenumber;
        private String judgementdate;
        private String publishdate;
        private String publishtype;
        private String hitresult;
        private String hashistoricaljudgment;
        private String watched;
        private String similarjudgement;
        private String tiantongcode;
        private String hascaseanalysis;
        private String haswusongreaderacticle;
        private String temporarysearchreport;
        private String title;
        private String keywords;
        private String courtopinion;
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCasetype() {
            return casetype;
        }

        public void setCasetype(String casetype) {
            this.casetype = casetype;
        }

        public String getTrialround() {
            return trialround;
        }

        public void setTrialround(String trialround) {
            this.trialround = trialround;
        }

        public String getJudgementtype() {
            return judgementtype;
        }

        public void setJudgementtype(String judgementtype) {
            this.judgementtype = judgementtype;
        }

        public String getCourtname() {
            return courtname;
        }

        public void setCourtname(String courtname) {
            this.courtname = courtname;
        }

        public String getCasenumber() {
            return casenumber;
        }

        public void setCasenumber(String casenumber) {
            this.casenumber = casenumber;
        }

        public String getJudgementdate() {
            return judgementdate;
        }

        public void setJudgementdate(String judgementdate) {
            this.judgementdate = judgementdate;
        }

        public String getPublishdate() {
            return publishdate;
        }

        public void setPublishdate(String publishdate) {
            this.publishdate = publishdate;
        }

        public String getPublishtype() {
            return publishtype;
        }

        public void setPublishtype(String publishtype) {
            this.publishtype = publishtype;
        }

        public String getHitresult() {
            return hitresult;
        }

        public void setHitresult(String hitresult) {
            this.hitresult = hitresult;
        }

        public String getHashistoricaljudgment() {
            return hashistoricaljudgment;
        }

        public void setHashistoricaljudgment(String hashistoricaljudgment) {
            this.hashistoricaljudgment = hashistoricaljudgment;
        }

        public String getWatched() {
            return watched;
        }

        public void setWatched(String watched) {
            this.watched = watched;
        }

        public String getSimilarjudgement() {
            return similarjudgement;
        }

        public void setSimilarjudgement(String similarjudgement) {
            this.similarjudgement = similarjudgement;
        }

        public String getTiantongcode() {
            return tiantongcode;
        }

        public void setTiantongcode(String tiantongcode) {
            this.tiantongcode = tiantongcode;
        }

        public String getHascaseanalysis() {
            return hascaseanalysis;
        }

        public void setHascaseanalysis(String hascaseanalysis) {
            this.hascaseanalysis = hascaseanalysis;
        }

        public String getHaswusongreaderacticle() {
            return haswusongreaderacticle;
        }

        public void setHaswusongreaderacticle(String haswusongreaderacticle) {
            this.haswusongreaderacticle = haswusongreaderacticle;
        }

        public String getTemporarysearchreport() {
            return temporarysearchreport;
        }

        public void setTemporarysearchreport(String temporarysearchreport) {
            this.temporarysearchreport = temporarysearchreport;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public String getCourtopinion() {
            return courtopinion;
        }

        public void setCourtopinion(String courtopinion) {
            this.courtopinion = courtopinion;
        }
    }
}
