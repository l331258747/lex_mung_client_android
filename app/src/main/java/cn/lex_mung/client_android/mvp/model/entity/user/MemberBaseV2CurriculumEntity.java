package cn.lex_mung.client_android.mvp.model.entity.user;

public class MemberBaseV2CurriculumEntity {


    /**
     * id : 1
     * memberId : 5020
     * authImage :
     * content : 闪光来了
     * authStatus : 2
     * status : 1
     * createDate : 2019-12-03 14:42:21
     * updateDate : 2019-12-03 14:43:47
     * verifyUserId : 121
     * reasonId : 0
     * verifyDate : 2019-12-03 14:43:48
     * memberName :
     * mobile :
     * type :
     * style : 0
     * verifyComment : 仅支持前法院、检察院等公职人员进行认证，通过认证后，用户可以在您的个人主页查看该信息，您的证件照仅用于平台审核，用户无法查看.
     * firstname :
     * pageNum : 0
     * pageSize : 0
     * startTime :
     * endTime :
     */

    private int id;
    private int memberId;
    private String authImage;
    private String content;
    private int authStatus;
    private int status;
    private String createDate;
    private String updateDate;
    private int verifyUserId;
    private int reasonId;
    private String verifyDate;
    private String memberName;
    private String mobile;
    private String type;
    private int style;
    private String verifyComment;
    private String firstname;
    private int pageNum;
    private int pageSize;
    private String startTime;
    private String endTime;

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

    public String getAuthImage() {
        return authImage;
    }

    public void setAuthImage(String authImage) {
        this.authImage = authImage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAuthStatus() {
        return authStatus;
    }

    public int getAuthStatusInt() {
        switch (authStatus){
            case 1:
                return 2;
            case 2:
                return 1;
            case 3:
                return 3;
        }
        return 0;
    }

    public String getAuthStatusStr() {
        switch (authStatus){
            case 1:
                return "认证中";
            case 2:
                return "已认证";
            case 3:
                return "未通过";
        }
        return "";
    }

    public void setAuthStatus(int authStatus) {
        this.authStatus = authStatus;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public int getVerifyUserId() {
        return verifyUserId;
    }

    public void setVerifyUserId(int verifyUserId) {
        this.verifyUserId = verifyUserId;
    }

    public int getReasonId() {
        return reasonId;
    }

    public void setReasonId(int reasonId) {
        this.reasonId = reasonId;
    }

    public String getVerifyDate() {
        return verifyDate;
    }

    public void setVerifyDate(String verifyDate) {
        this.verifyDate = verifyDate;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public String getVerifyComment() {
        return verifyComment;
    }

    public void setVerifyComment(String verifyComment) {
        this.verifyComment = verifyComment;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
