package cn.lex_mung.client_android.mvp.model.entity;

public class ReleaseDemandOrgMoneyEntity2 {


    /**
     * optimal : {"pageNum":1,"pageSize":1,"size":1,"orderBy":"","startRow":0,"endRow":0,"total":1,"pages":1,"list":[{"memberId":0,"lmemberId":0,"couponId":0,"organizationId":16,"organizationLevId":18,"score":1,"dateAdded":"2019-02-20 10:56:59","organizationName":"湖南省浙江商会","organizationLevelName":"湖南省浙江商会vip","image":"http://oss.lex-mung.com/organization_image_15507372415359.png","coupon1Count":0,"requireTypeId":0,"type":0,"orgStatus":1,"pageNum":0,"pageSize":0,"amount":0,"amountDis":0,"consumeMoney":0,"amountShip":0,"useForOrgLevelIid":0,"amountNew":0}],"firstPage":1,"prePage":0,"nextPage":0,"lastPage":1,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1]}
     * coupon : {"pageNum":1,"pageSize":30,"size":4,"orderBy":"","startRow":1,"endRow":4,"total":4,"pages":1,"list":[{"memberId":0,"mobile":"","couponId":110,"couponName":"商品券满100减80第二版","couponType":0,"scopeOfUse":0,"preferentialWay":2,"fullNum":100,"reduceNum":80,"preferentialContent":"商品券满100减80第二版内容","preferentialDiscount":0,"deviceId":0,"startTime":"2019-07-08 00:00:00","endTime":"2019-07-20 00:00:00","couponStatus":1,"productId":0,"pageNum":0,"pageSize":0,"orderAmount":0,"payment":0,"deductionAmount":0,"couponDesc":"","useTimeType":0,"useTimeNum":0,"organizationLevelName":"","availableNumber":0,"existingQuantity":0,"oneQuantity":0,"type":0,"linkId":0,"imageId":0,"linkName":"","url":"","imageName":"","image":""},{"memberId":0,"mobile":"","couponId":104,"couponName":"快速咨询优惠券","couponType":0,"scopeOfUse":0,"preferentialWay":2,"fullNum":50,"reduceNum":30,"preferentialContent":"0","preferentialDiscount":0,"deviceId":0,"startTime":"2019-07-01 00:00:00","endTime":"2019-07-31 00:00:00","couponStatus":1,"productId":0,"pageNum":0,"pageSize":0,"orderAmount":0,"payment":0,"deductionAmount":0,"couponDesc":"","useTimeType":0,"useTimeNum":0,"organizationLevelName":"","availableNumber":0,"existingQuantity":0,"oneQuantity":0,"type":0,"linkId":0,"imageId":0,"linkName":"","url":"","imageName":"","image":""},{"memberId":0,"mobile":"","couponId":101,"couponName":"yy","couponType":0,"scopeOfUse":0,"preferentialWay":2,"fullNum":5,"reduceNum":3,"preferentialContent":"","preferentialDiscount":0,"deviceId":0,"startTime":"2019-06-20 00:00:00","endTime":"2019-07-28 00:00:00","couponStatus":1,"productId":0,"pageNum":0,"pageSize":0,"orderAmount":0,"payment":0,"deductionAmount":0,"couponDesc":"","useTimeType":0,"useTimeNum":0,"organizationLevelName":"","availableNumber":0,"existingQuantity":0,"oneQuantity":0,"type":0,"linkId":0,"imageId":0,"linkName":"","url":"","imageName":"","image":""},{"memberId":0,"mobile":"","couponId":102,"couponName":"测试折扣券","couponType":0,"scopeOfUse":0,"preferentialWay":3,"fullNum":0,"reduceNum":0,"preferentialContent":"这是一张8.5折的券","preferentialDiscount":85,"deviceId":0,"startTime":"2019-06-13 00:00:00","endTime":"2019-07-19 00:00:00","couponStatus":1,"productId":0,"pageNum":0,"pageSize":0,"orderAmount":0,"payment":0,"deductionAmount":0,"couponDesc":"","useTimeType":0,"useTimeNum":0,"organizationLevelName":"","availableNumber":0,"existingQuantity":0,"oneQuantity":0,"type":0,"linkId":0,"imageId":0,"linkName":"","url":"","imageName":"","image":""}],"firstPage":1,"prePage":0,"nextPage":0,"lastPage":1,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1]}
     */

    private BaseListEntity<ReleaseDemandOrgMoneyEntityOptimal> optimal;
    private BaseListEntity<ReleaseDemandOrgMoneyEntityCoupon> coupon;

    public BaseListEntity<ReleaseDemandOrgMoneyEntityOptimal> getOptimal() {
        return optimal;
    }

    public BaseListEntity<ReleaseDemandOrgMoneyEntityCoupon> getCoupon() {
        return coupon;
    }
}
