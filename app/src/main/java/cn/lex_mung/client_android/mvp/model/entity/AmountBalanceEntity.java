package cn.lex_mung.client_android.mvp.model.entity;

import java.util.List;

public class AmountBalanceEntity {

    /**
     * amount : {"memberId":6082,"totalConsumeAmount":0,"balanceAmount":7055.81,"frozenAmount":0,"depositAmount":0,"frozenDepositAmount":0,"advanceCollectionAmount":0,"dateModified":"","version":131}
     * orgAmounts : [{"couponId":111,"orgLevelId":18,"amount":199016.9,"couponName":"集团卡1"},{"couponId":123,"orgLevelId":22,"amount":0,"couponName":"集团卡0717"}]
     * memberCard : {"memberId":0,"lmemberId":0,"couponId":120,"organizationId":0,"organizationLevId":18,"score":0,"dateAdded":"","organizationName":"","organizationLevelName":"","exclusiveRights":"","image":"","coupon1Count":0,"requireTypeId":0,"type":0,"orgStatus":0,"pageNum":0,"pageSize":0,"amount":0,"amountDis":0,"consumeMoney":0,"amountShip":200000,"useForOrgLevelIid":0,"amountNew":200000}
     */

    private AmountBean amount;
    private MemberCardBean memberCard;
    private List<OrgAmountsBean> orgAmounts;

    public AmountBean getAmount() {
        return amount;
    }

    public void setAmount(AmountBean amount) {
        this.amount = amount;
    }

    public MemberCardBean getMemberCard() {
        return memberCard;
    }

    public void setMemberCard(MemberCardBean memberCard) {
        this.memberCard = memberCard;
    }

    public List<OrgAmountsBean> getOrgAmounts() {
        return orgAmounts;
    }

    public void setOrgAmounts(List<OrgAmountsBean> orgAmounts) {
        this.orgAmounts = orgAmounts;
    }

    public static class AmountBean {
        /**
         * memberId : 6082
         * totalConsumeAmount : 0
         * balanceAmount : 7055.81
         * frozenAmount : 0
         * depositAmount : 0
         * frozenDepositAmount : 0.0
         * advanceCollectionAmount : 0.0
         * dateModified :
         * version : 131
         */

        private int memberId;
        private int totalConsumeAmount;
        private double balanceAmount;
        private int frozenAmount;
        private int depositAmount;
        private double frozenDepositAmount;
        private double advanceCollectionAmount;
        private String dateModified;
        private int version;

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public int getTotalConsumeAmount() {
            return totalConsumeAmount;
        }

        public void setTotalConsumeAmount(int totalConsumeAmount) {
            this.totalConsumeAmount = totalConsumeAmount;
        }

        public double getBalanceAmount() {
            return balanceAmount;
        }

        public void setBalanceAmount(double balanceAmount) {
            this.balanceAmount = balanceAmount;
        }

        public int getFrozenAmount() {
            return frozenAmount;
        }

        public void setFrozenAmount(int frozenAmount) {
            this.frozenAmount = frozenAmount;
        }

        public int getDepositAmount() {
            return depositAmount;
        }

        public void setDepositAmount(int depositAmount) {
            this.depositAmount = depositAmount;
        }

        public double getFrozenDepositAmount() {
            return frozenDepositAmount;
        }

        public void setFrozenDepositAmount(double frozenDepositAmount) {
            this.frozenDepositAmount = frozenDepositAmount;
        }

        public double getAdvanceCollectionAmount() {
            return advanceCollectionAmount;
        }

        public void setAdvanceCollectionAmount(double advanceCollectionAmount) {
            this.advanceCollectionAmount = advanceCollectionAmount;
        }

        public String getDateModified() {
            return dateModified;
        }

        public void setDateModified(String dateModified) {
            this.dateModified = dateModified;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }
    }

    public static class MemberCardBean {
        /**
         * memberId : 0
         * lmemberId : 0
         * couponId : 120
         * organizationId : 0
         * organizationLevId : 18
         * score : 0
         * dateAdded :
         * organizationName :
         * organizationLevelName :
         * exclusiveRights :
         * image :
         * coupon1Count : 0
         * requireTypeId : 0
         * type : 0
         * orgStatus : 0
         * pageNum : 0
         * pageSize : 0
         * amount : 0
         * amountDis : 0
         * consumeMoney : 0.0
         * amountShip : 200000.0
         * useForOrgLevelIid : 0
         * amountNew : 200000.0
         */

        private int memberId;
        private int lmemberId;
        private int couponId;
        private int organizationId;
        private int organizationLevId;
        private int score;
        private String dateAdded;
        private String organizationName;
        private String organizationLevelName;
        private String exclusiveRights;
        private String image;
        private int coupon1Count;
        private int requireTypeId;
        private int type;
        private int orgStatus;
        private int pageNum;
        private int pageSize;
        private int amount;
        private int amountDis;
        private double consumeMoney;
        private double amountShip;
        private int useForOrgLevelIid;
        private double amountNew;

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public int getLmemberId() {
            return lmemberId;
        }

        public void setLmemberId(int lmemberId) {
            this.lmemberId = lmemberId;
        }

        public int getCouponId() {
            return couponId;
        }

        public void setCouponId(int couponId) {
            this.couponId = couponId;
        }

        public int getOrganizationId() {
            return organizationId;
        }

        public void setOrganizationId(int organizationId) {
            this.organizationId = organizationId;
        }

        public int getOrganizationLevId() {
            return organizationLevId;
        }

        public void setOrganizationLevId(int organizationLevId) {
            this.organizationLevId = organizationLevId;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getDateAdded() {
            return dateAdded;
        }

        public void setDateAdded(String dateAdded) {
            this.dateAdded = dateAdded;
        }

        public String getOrganizationName() {
            return organizationName;
        }

        public void setOrganizationName(String organizationName) {
            this.organizationName = organizationName;
        }

        public String getOrganizationLevelName() {
            return organizationLevelName;
        }

        public void setOrganizationLevelName(String organizationLevelName) {
            this.organizationLevelName = organizationLevelName;
        }

        public String getExclusiveRights() {
            return exclusiveRights;
        }

        public void setExclusiveRights(String exclusiveRights) {
            this.exclusiveRights = exclusiveRights;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getCoupon1Count() {
            return coupon1Count;
        }

        public void setCoupon1Count(int coupon1Count) {
            this.coupon1Count = coupon1Count;
        }

        public int getRequireTypeId() {
            return requireTypeId;
        }

        public void setRequireTypeId(int requireTypeId) {
            this.requireTypeId = requireTypeId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getOrgStatus() {
            return orgStatus;
        }

        public void setOrgStatus(int orgStatus) {
            this.orgStatus = orgStatus;
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

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getAmountDis() {
            return amountDis;
        }

        public void setAmountDis(int amountDis) {
            this.amountDis = amountDis;
        }

        public double getConsumeMoney() {
            return consumeMoney;
        }

        public void setConsumeMoney(double consumeMoney) {
            this.consumeMoney = consumeMoney;
        }

        public double getAmountShip() {
            return amountShip;
        }

        public void setAmountShip(double amountShip) {
            this.amountShip = amountShip;
        }

        public int getUseForOrgLevelIid() {
            return useForOrgLevelIid;
        }

        public void setUseForOrgLevelIid(int useForOrgLevelIid) {
            this.useForOrgLevelIid = useForOrgLevelIid;
        }

        public double getAmountNew() {
            return amountNew;
        }

        public void setAmountNew(double amountNew) {
            this.amountNew = amountNew;
        }
    }

    public static class OrgAmountsBean {
        /**
         * couponId : 111
         * orgLevelId : 18
         * amount : 199016.9
         * couponName : 集团卡1
         */

        private int couponId;
        private int orgLevelId;
        private double amount;
        private String couponName;

        public int getCouponId() {
            return couponId;
        }

        public void setCouponId(int couponId) {
            this.couponId = couponId;
        }

        public int getOrgLevelId() {
            return orgLevelId;
        }

        public void setOrgLevelId(int orgLevelId) {
            this.orgLevelId = orgLevelId;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getCouponName() {
            return couponName;
        }

        public void setCouponName(String couponName) {
            this.couponName = couponName;
        }
    }
}
