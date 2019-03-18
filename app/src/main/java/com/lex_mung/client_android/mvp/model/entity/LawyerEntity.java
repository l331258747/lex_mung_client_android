package com.lex_mung.client_android.mvp.model.entity;

import com.lex_mung.client_android.mvp.model.api.Api;

import java.util.List;

public class LawyerEntity {
    private LawyerBean data;
    private int code;
    private String message;
    private long time;

    public long getTime() {
        return time;
    }

    public LawyerBean getData() {
        return data;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return code == Api.RequestSuccess;
    }

    public static class LawyerBean {

        /**
         * pageNum : 1
         * pageSize : 10
         * size : 6
         * orderBy :
         * startRow : 1
         * endRow : 6
         * total : 6
         * pages : 1
         * list : [{"memberId":266,"memberName":"金明政","institutionId":9000,"institutionName":"湖南南天门律师事务所","sex":0,"birthday":"1970-01-01 00:00:01","iconImage":"http://oss.lex-mung.com/icon_image_member_e0a657871.jpg","regionId":430700,"region":"湖南省-常德市","memberPositionId":4,"memberPositionName":"","beginPracticeDate":"2017-10-01 00:00:00","isOnline":1,"description":"擅长领域：境外收购，境外融资，民间借贷/担保/抵押","orgTags":[]},{"memberId":4485,"memberName":"么金玉","institutionId":9301,"institutionName":"河北冀华唐山律师事务所","sex":1,"birthday":"1981-05-28 00:00:00","iconImage":"http://oss.lex-mung.com/icon_image_member_795b14dcd.jpg","regionId":130200,"region":"河北省-唐山市","memberPositionId":4,"memberPositionName":"","beginPracticeDate":"2017-01-01 00:00:00","isOnline":1,"description":"擅长领域：境外收购，境外融资，民间借贷/担保/抵押","orgTags":[]},{"memberId":466,"memberName":"黄金","institutionId":9059,"institutionName":"湖南邦卓律师事务所","sex":0,"birthday":"1970-01-01 00:00:01","iconImage":"http://oss.lex-mung.com/icon_image_member_03de9e731.jpg","regionId":430100,"region":"湖南省-长沙市","memberPositionId":3,"memberPositionName":"","beginPracticeDate":"2014-01-01 00:00:00","isOnline":1,"description":"擅长领域：境外收购，境外融资，民间借贷/担保/抵押","orgTags":[{"tagName":"可接收绿豆券么","image":"http://oss.lex-mung.com/organization_c24b1d208.png"}]},{"memberId":350,"memberName":"金雷","institutionId":9041,"institutionName":"辽宁永字律师事务所","sex":0,"birthday":"1970-01-01 00:00:01","iconImage":"http://oss.lex-mung.com/icon_image_member_08c260285.jpg","regionId":210700,"region":"辽宁省-锦州市","memberPositionId":5,"memberPositionName":"","beginPracticeDate":"2000-03-01 00:00:00","isOnline":1,"description":"擅长领域：境外收购，境外融资，民间借贷/担保/抵押","orgTags":[]},{"memberId":1098,"memberName":"杨金涛","institutionId":9157,"institutionName":"湖南源美律师事务所","sex":0,"birthday":"1970-01-01 00:00:01","iconImage":"http://oss.lex-mung.com/icon_image_member_2f3c66e08.jpg","regionId":430100,"region":"湖南省-长沙市","memberPositionId":5,"memberPositionName":"","beginPracticeDate":"2010-10-01 00:00:00","isOnline":1,"description":"擅长领域：境外收购，境外融资，民间借贷/担保/抵押","orgTags":[]},{"memberId":1252,"memberName":"金伟超","institutionId":22,"institutionName":"湖南元端律师事务所","sex":0,"birthday":"1970-01-01 00:00:01","iconImage":"http://oss.lex-mung.com/icon_image_member_70373a1bc.jpg","regionId":0,"region":"","memberPositionId":4,"memberPositionName":"","beginPracticeDate":"2015-09-01 00:00:00","isOnline":1,"description":"擅长领域：境外收购，境外融资，民间借贷/担保/抵押","orgTags":[]}]
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
             * memberId : 266
             * memberName : 金明政
             * institutionId : 9000
             * institutionName : 湖南南天门律师事务所
             * sex : 0
             * birthday : 1970-01-01 00:00:01
             * iconImage : http://oss.lex-mung.com/icon_image_member_e0a657871.jpg
             * regionId : 430700
             * region : 湖南省-常德市
             * memberPositionId : 4
             * memberPositionName :
             * beginPracticeDate : 2017-10-01 00:00:00
             * isOnline : 1
             * description : 擅长领域：境外收购，境外融资，民间借贷/担保/抵押
             * orgTags : []
             */

            private int memberId;
            private String memberName;
            private int institutionId;
            private String institutionName;
            private int sex;
            private String birthday;
            private String iconImage;
            private int regionId;
            private String region;
            private int memberPositionId;
            private String memberPositionName;
            private String beginPracticeDate;
            private int isOnline;
            private String description;
            private List<OrgTagsEntity> orgTags;
            private String lawyerWeight;
            private String practice;

            public String getPractice() {
                return practice;
            }

            public void setPractice(String practice) {
                this.practice = practice;
            }

            public String getLawyerWeight() {
                return lawyerWeight;
            }

            public void setLawyerWeight(String lawyerWeight) {
                this.lawyerWeight = lawyerWeight;
            }

            public int getMemberId() {
                return memberId;
            }

            public void setMemberId(int memberId) {
                this.memberId = memberId;
            }

            public String getMemberName() {
                return memberName;
            }

            public void setMemberName(String memberName) {
                this.memberName = memberName;
            }

            public int getInstitutionId() {
                return institutionId;
            }

            public void setInstitutionId(int institutionId) {
                this.institutionId = institutionId;
            }

            public String getInstitutionName() {
                return institutionName;
            }

            public void setInstitutionName(String institutionName) {
                this.institutionName = institutionName;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public String getIconImage() {
                return iconImage;
            }

            public void setIconImage(String iconImage) {
                this.iconImage = iconImage;
            }

            public int getRegionId() {
                return regionId;
            }

            public void setRegionId(int regionId) {
                this.regionId = regionId;
            }

            public String getRegion() {
                return region;
            }

            public void setRegion(String region) {
                this.region = region;
            }

            public int getMemberPositionId() {
                return memberPositionId;
            }

            public void setMemberPositionId(int memberPositionId) {
                this.memberPositionId = memberPositionId;
            }

            public String getMemberPositionName() {
                return memberPositionName;
            }

            public void setMemberPositionName(String memberPositionName) {
                this.memberPositionName = memberPositionName;
            }

            public String getBeginPracticeDate() {
                return beginPracticeDate;
            }

            public void setBeginPracticeDate(String beginPracticeDate) {
                this.beginPracticeDate = beginPracticeDate;
            }

            public int getIsOnline() {
                return isOnline;
            }

            public void setIsOnline(int isOnline) {
                this.isOnline = isOnline;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public List<OrgTagsEntity> getOrgTags() {
                return orgTags;
            }

            public void setOrgTags(List<OrgTagsEntity> orgTags) {
                this.orgTags = orgTags;
            }
        }
    }
}
