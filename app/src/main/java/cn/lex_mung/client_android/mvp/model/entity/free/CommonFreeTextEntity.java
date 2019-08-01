package cn.lex_mung.client_android.mvp.model.entity.free;

import java.util.ArrayList;
import java.util.List;

import cn.lex_mung.client_android.app.TimeFormat;

public class CommonFreeTextEntity {

    /**
     * consultationId : 541
     * consultationNumber : FT190417600311214320
     * memberId : 5006
     * consultationStatus : 4
     * consultationTypeId : 18
     * source : 5
     * regionId : 430100
     * dateAdded : 2019-04-17 17:04:14
     * dateModified : 2019-04-17 17:04:50
     * isHide : 0
     * content : 阿弗莱克沙发里框架啦是激发了加
     * replyCount : 6
     * region : 湖南省长沙市
     * memberName : 我是大魔王666呀
     * memberIconImage : http://oss.lex-mung.com/icon_image_member_15548954201179.jpg
     * categoryName : 重大基础项目建设
     */

    private int consultationId;
    private String consultationNumber;
    private int memberId;
    private int consultationStatus;
    private int consultationTypeId;
    private int source;
    private int regionId;
    private String dateAdded;
    private String dateModified;
    private int isHide;
    private String content;
    private int replyCount;
    private String region;
    private String memberName;
    private String memberIconImage;
    private String categoryName;
    private List<HeadEntity> lawyerMemberImages;

    public List<HeadEntity> getLawyerMemberImages() {
        return lawyerMemberImages;
    }

    public List<String> getLawyerMemberImagesStr(){
        List<String> lists = new ArrayList<>();
        if(lawyerMemberImages == null || lawyerMemberImages.size() == 0){
            return lists;
        }
        for (int i=0;i<lawyerMemberImages.size();i++){
            String str = lawyerMemberImages.get(i).getIconImage();
            lists.add(str);
        }
        return lists;
    }

    public int getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(int consultationId) {
        this.consultationId = consultationId;
    }

    public String getConsultationNumber() {
        return consultationNumber;
    }

    public void setConsultationNumber(String consultationNumber) {
        this.consultationNumber = consultationNumber;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getConsultationStatus() {
        return consultationStatus;
    }

    public void setConsultationStatus(int consultationStatus) {
        this.consultationStatus = consultationStatus;
    }

    //审核状态（1已发布2已通过3未通过4已回复5已完
    public String getConsultationStatusStr(){
        switch (consultationStatus){
            case 1:
                return "已发布";
            case 2:
                return "已通过";
            case 3:
                return "未通过";
            case 4:
                return "已回复";
            case 5:
                return "已完成";
            case 6:
                return "已删除";
        }
        return "";
    }

    public int getConsultationTypeId() {
        return consultationTypeId;
    }

    public void setConsultationTypeId(int consultationTypeId) {
        this.consultationTypeId = consultationTypeId;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public String getDateAddedStr(){
        return TimeFormat.getTime(TimeFormat.strToLong(dateAdded,TimeFormat.s1));
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public int getIsHide() {
        return isHide;
    }

    public void setIsHide(int isHide) {
        this.isHide = isHide;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getReplyCount() {
        return replyCount;
    }
    public String getReplyCountStr() {
        return replyCount+"条回复";
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getMemberName() {
        if(isHide == 1){
            return "匿名用户";
        }
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberIconImage() {
        return memberIconImage;
    }

    public void setMemberIconImage(String memberIconImage) {
        this.memberIconImage = memberIconImage;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public static class HeadEntity{
        String iconImage;
        int memberId;
        String memberName;

        public int getMemberId() {
            return memberId;
        }

        public String getMemberName() {
            return memberName;
        }

        public String getIconImage() {
            return iconImage;
        }

    }
}
