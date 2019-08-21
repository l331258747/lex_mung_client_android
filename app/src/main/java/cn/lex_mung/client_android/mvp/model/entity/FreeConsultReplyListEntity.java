package cn.lex_mung.client_android.mvp.model.entity;

import android.text.TextUtils;

import cn.lex_mung.client_android.app.TimeFormat;
import me.zl.mvp.utils.StringUtils;

public class FreeConsultReplyListEntity {


    /**
     * consultationReplyId : 159
     * consultationNumber : Q1811307050341
     * lawyerId : 5050
     * memberId : 20
     * type : 1
     * dateAdded : 2018-12-28 10:20:04
     * content : 聚聚古驰咕咕咕姑姑哔哔哔hug聚聚沟沟壑壑
     * consultationId : 10
     * lawyerName : 测试2
     * lawyerIconImage : http://oss.lex-mung.com/icon_image_member_2011564777.png
     * lawyerFirm : 湖南金州律师事务所
     * lawyerSex : 1
     * memberName :
     * memberIconImage :
     * memberRegion :
     * memberSex : 0
     * replyCount : 0
     */




    private int consultationReplyId;
    private String consultationNumber;
    private int lawyerId;
    private int memberId;
    private int type;
    private String dateAdded;
    private String content;
    private int consultationId;
    private String lawyerName;
    private String lawyerIconImage;
    private String lawyerFirm;
    private int lawyerSex;
    private String memberName;
    private String memberIconImage;
    private String memberRegion;
    private int memberSex;
    private int replyCount;
    /**
     * memberHonorTitle : 全国人大代表/政协委员
     * minAmount : 0
     * lawyerPositionName : 律所主任
     */

    private String memberHonorTitle;
    private String minAmount;
    private String lawyerPositionName;

    public int getConsultationReplyId() {
        return consultationReplyId;
    }

    public void setConsultationReplyId(int consultationReplyId) {
        this.consultationReplyId = consultationReplyId;
    }

    public String getConsultationNumber() {
        return consultationNumber;
    }

    public void setConsultationNumber(String consultationNumber) {
        this.consultationNumber = consultationNumber;
    }

    public int getLawyerId() {
        return lawyerId;
    }

    public void setLawyerId(int lawyerId) {
        this.lawyerId = lawyerId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(int consultationId) {
        this.consultationId = consultationId;
    }

    public String getLawyerName() {
        return lawyerName;
    }

    public void setLawyerName(String lawyerName) {
        this.lawyerName = lawyerName;
    }

    public String getLawyerIconImage() {
        if(TextUtils.isEmpty(lawyerIconImage)) return "";
        if(!lawyerIconImage.startsWith("http")) return "";
        return lawyerIconImage;
    }

    public String getTypeImage(){//1律师，2用户
        if(type == 1){
            return getLawyerIconImage();
        }else{
            return getMemberIconImage();
        }
    }

    public void setLawyerIconImage(String lawyerIconImage) {
        this.lawyerIconImage = lawyerIconImage;
    }

    public String getLawyerFirm() {
        return lawyerFirm;
    }

    public String getArea(){
        if(type == 1){
            return lawyerFirm;
        }else{
            return memberRegion;
        }
    }

    public void setLawyerFirm(String lawyerFirm) {
        this.lawyerFirm = lawyerFirm;
    }

    public int getLawyerSex() {
        return lawyerSex;
    }

    public void setLawyerSex(int lawyerSex) {
        this.lawyerSex = lawyerSex;
    }

    public String getMemberName() {
        return memberName;
    }

    public String getTypeName(){
        if(type == 1){
            return lawyerName;
        }else{
            return memberName;
        }
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberIconImage() {
        if(TextUtils.isEmpty(memberIconImage)) return "";
        if(!memberIconImage.startsWith("http")) return "";
        return memberIconImage;
    }

    public void setMemberIconImage(String memberIconImage) {
        this.memberIconImage = memberIconImage;
    }

    public String getMemberRegion() {
        return memberRegion;
    }

    public void setMemberRegion(String memberRegion) {
        this.memberRegion = memberRegion;
    }

    public int getMemberSex() {
        return memberSex;
    }

    public void setMemberSex(int memberSex) {
        this.memberSex = memberSex;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public String getMemberHonorTitle() {
        return memberHonorTitle;
    }

    public void setMemberHonorTitle(String memberHonorTitle) {
        this.memberHonorTitle = memberHonorTitle;
    }

    public String getMinAmount() {
        return minAmount;
    }

    public String getMinAmountStr(){
        return "电话咨询:"+ minAmount +"元/分钟>";
    }

    public void setMinAmount(String minAmount) {
        this.minAmount = minAmount;
    }

    public String getLawyerPositionName() {
        return lawyerPositionName;
    }

    public String getTitle2(){
        if(type == 1){
            return lawyerPositionName;
        }else{
            return "";
        }
    }

    public void setLawyerPositionName(String lawyerPositionName) {
        this.lawyerPositionName = lawyerPositionName;
    }

    public String getReplyCountStr() {
        return replyCount+"条回复";
    }

    public String getDateAddedStr(){
        return TimeFormat.getTime(TimeFormat.strToLong(dateAdded,TimeFormat.s1));
    }
}
