package com.lex_mung.client_android.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

public class UserInfoDetailsEntity implements Serializable {

    /**
     * memberId : 5431
     * memberName :
     * mobile : 13272454470
     * email :
     * memberRoleId : 12
     * institutionId : 0
     * institutionName :
     * realName :
     * sex : 0
     * birthday : 1970-01-01 00:00:01
     * astroId : 0
     * bakPractisingCertificateNumber :
     * idNumberImage :
     * iconImage :
     * ip :
     * code :
     * password :
     * salt :
     * token :
     * tokenExpireTime : 0
     * webToken :
     * webTokenExpireTime : 0
     * appToken :
     * appTokenExpireTime : 0
     * status : true
     * dateAdded : 2019-02-22 14:45:48
     * dateModified : 2019-02-22 14:45:48
     * createUserId : 0
     * updateUserId : 0
     * regionId : 0
     * industryId : 0
     * isCalculate : 0
     * memberPositionId : 0
     * memberPositionName :
     * backgroundImage :
     * device : 1
     * source : 0
     * channel : default
     * memberDescription :
     * rname :
     * mobileNew :
     * vcode :
     * birthday1 :
     * institutionTypeId : 0
     * institutionTypeName :
     * insTypeName :
     * businessTypeId : 0
     * businessTypeName :
     * score : 0
     * lawsRegulationsId : 0
     * lawsRegulationsName :
     * memberAuthStatus : 0
     * isOnline : 0
     * lawyerMemberDepositLevelName :
     * coupon : 0
     * organizationLevId : 0
     * pageNum : 0
     * pageSize : 0
     * organizationId : 0
     * dateAdded1 :
     * dateAdded2 :
     * dateAdded3 :
     * dateAdded4 :
     * mdateAdded :
     * rids : []
     * jids : []
     * dids : []
     * addressExtend : {"province":"","provinceId":0,"city":"","cityId":0,"area":"","areaId":0}
     * organizations : []
     * oids : []
     * ybeginPDate : 0
     */

    private int memberId;
    private String memberName;
    private String mobile;
    private String email;
    private int memberRoleId;
    private int institutionId;
    private String institutionName;
    private String realName;
    private int sex;
    private String age;
    private String birthday;
    private int astroId;
    private String bakPractisingCertificateNumber;
    private String idNumberImage;
    private String iconImage;
    private String ip;
    private String code;
    private String password;
    private String salt;
    private String token;
    private int tokenExpireTime;
    private String webToken;
    private int webTokenExpireTime;
    private String appToken;
    private int appTokenExpireTime;
    private boolean status;
    private String dateAdded;
    private String dateModified;
    private int createUserId;
    private int updateUserId;
    private int regionId;
    private int industryId;
    private String industryName;
    private int isCalculate;
    private int memberPositionId;
    private String memberPositionName;
    private String backgroundImage;
    private int device;
    private int source;
    private String channel;
    private String memberDescription;
    private String rname;
    private String mobileNew;
    private String vcode;
    private String birthday1;
    private int institutionTypeId;
    private String institutionTypeName;
    private String insTypeName;
    private int businessTypeId;
    private String businessTypeName;
    private int score;
    private int lawsRegulationsId;
    private String lawsRegulationsName;
    private int memberAuthStatus;
    private int isOnline;
    private String lawyerMemberDepositLevelName;
    private int coupon;
    private int organizationLevId;
    private int pageNum;
    private int pageSize;
    private int organizationId;
    private String dateAdded1;
    private String dateAdded2;
    private String dateAdded3;
    private String dateAdded4;
    private String mdateAdded;
    private AddressExtendBean addressExtend;
    private int ybeginPDate;
    private List<OrganizationsBean> organizations;

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getMemberRoleId() {
        return memberRoleId;
    }

    public void setMemberRoleId(int memberRoleId) {
        this.memberRoleId = memberRoleId;
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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
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

    public int getAstroId() {
        return astroId;
    }

    public void setAstroId(int astroId) {
        this.astroId = astroId;
    }

    public String getBakPractisingCertificateNumber() {
        return bakPractisingCertificateNumber;
    }

    public void setBakPractisingCertificateNumber(String bakPractisingCertificateNumber) {
        this.bakPractisingCertificateNumber = bakPractisingCertificateNumber;
    }

    public String getIdNumberImage() {
        return idNumberImage;
    }

    public void setIdNumberImage(String idNumberImage) {
        this.idNumberImage = idNumberImage;
    }

    public String getIconImage() {
        return iconImage;
    }

    public void setIconImage(String iconImage) {
        this.iconImage = iconImage;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getTokenExpireTime() {
        return tokenExpireTime;
    }

    public void setTokenExpireTime(int tokenExpireTime) {
        this.tokenExpireTime = tokenExpireTime;
    }

    public String getWebToken() {
        return webToken;
    }

    public void setWebToken(String webToken) {
        this.webToken = webToken;
    }

    public int getWebTokenExpireTime() {
        return webTokenExpireTime;
    }

    public void setWebTokenExpireTime(int webTokenExpireTime) {
        this.webTokenExpireTime = webTokenExpireTime;
    }

    public String getAppToken() {
        return appToken;
    }

    public void setAppToken(String appToken) {
        this.appToken = appToken;
    }

    public int getAppTokenExpireTime() {
        return appTokenExpireTime;
    }

    public void setAppTokenExpireTime(int appTokenExpireTime) {
        this.appTokenExpireTime = appTokenExpireTime;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDateAdded() {
        return dateAdded;
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

    public int getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(int createUserId) {
        this.createUserId = createUserId;
    }

    public int getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(int updateUserId) {
        this.updateUserId = updateUserId;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public int getIndustryId() {
        return industryId;
    }

    public void setIndustryId(int industryId) {
        this.industryId = industryId;
    }

    public int getIsCalculate() {
        return isCalculate;
    }

    public void setIsCalculate(int isCalculate) {
        this.isCalculate = isCalculate;
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

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public int getDevice() {
        return device;
    }

    public void setDevice(int device) {
        this.device = device;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getMemberDescription() {
        return memberDescription;
    }

    public void setMemberDescription(String memberDescription) {
        this.memberDescription = memberDescription;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public String getMobileNew() {
        return mobileNew;
    }

    public void setMobileNew(String mobileNew) {
        this.mobileNew = mobileNew;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

    public String getBirthday1() {
        return birthday1;
    }

    public void setBirthday1(String birthday1) {
        this.birthday1 = birthday1;
    }

    public int getInstitutionTypeId() {
        return institutionTypeId;
    }

    public void setInstitutionTypeId(int institutionTypeId) {
        this.institutionTypeId = institutionTypeId;
    }

    public String getInstitutionTypeName() {
        return institutionTypeName;
    }

    public void setInstitutionTypeName(String institutionTypeName) {
        this.institutionTypeName = institutionTypeName;
    }

    public String getInsTypeName() {
        return insTypeName;
    }

    public void setInsTypeName(String insTypeName) {
        this.insTypeName = insTypeName;
    }

    public int getBusinessTypeId() {
        return businessTypeId;
    }

    public void setBusinessTypeId(int businessTypeId) {
        this.businessTypeId = businessTypeId;
    }

    public String getBusinessTypeName() {
        return businessTypeName;
    }

    public void setBusinessTypeName(String businessTypeName) {
        this.businessTypeName = businessTypeName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLawsRegulationsId() {
        return lawsRegulationsId;
    }

    public void setLawsRegulationsId(int lawsRegulationsId) {
        this.lawsRegulationsId = lawsRegulationsId;
    }

    public String getLawsRegulationsName() {
        return lawsRegulationsName;
    }

    public void setLawsRegulationsName(String lawsRegulationsName) {
        this.lawsRegulationsName = lawsRegulationsName;
    }

    public int getMemberAuthStatus() {
        return memberAuthStatus;
    }

    public void setMemberAuthStatus(int memberAuthStatus) {
        this.memberAuthStatus = memberAuthStatus;
    }

    public int getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(int isOnline) {
        this.isOnline = isOnline;
    }

    public String getLawyerMemberDepositLevelName() {
        return lawyerMemberDepositLevelName;
    }

    public void setLawyerMemberDepositLevelName(String lawyerMemberDepositLevelName) {
        this.lawyerMemberDepositLevelName = lawyerMemberDepositLevelName;
    }

    public int getCoupon() {
        return coupon;
    }

    public void setCoupon(int coupon) {
        this.coupon = coupon;
    }

    public int getOrganizationLevId() {
        return organizationLevId;
    }

    public void setOrganizationLevId(int organizationLevId) {
        this.organizationLevId = organizationLevId;
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

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public String getDateAdded1() {
        return dateAdded1;
    }

    public void setDateAdded1(String dateAdded1) {
        this.dateAdded1 = dateAdded1;
    }

    public String getDateAdded2() {
        return dateAdded2;
    }

    public void setDateAdded2(String dateAdded2) {
        this.dateAdded2 = dateAdded2;
    }

    public String getDateAdded3() {
        return dateAdded3;
    }

    public void setDateAdded3(String dateAdded3) {
        this.dateAdded3 = dateAdded3;
    }

    public String getDateAdded4() {
        return dateAdded4;
    }

    public void setDateAdded4(String dateAdded4) {
        this.dateAdded4 = dateAdded4;
    }

    public String getMdateAdded() {
        return mdateAdded;
    }

    public void setMdateAdded(String mdateAdded) {
        this.mdateAdded = mdateAdded;
    }

    public AddressExtendBean getAddressExtend() {
        return addressExtend;
    }

    public void setAddressExtend(AddressExtendBean addressExtend) {
        this.addressExtend = addressExtend;
    }

    public int getYbeginPDate() {
        return ybeginPDate;
    }

    public void setYbeginPDate(int ybeginPDate) {
        this.ybeginPDate = ybeginPDate;
    }

    public List<OrganizationsBean> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<OrganizationsBean> organizations) {
        this.organizations = organizations;
    }

    public static class AddressExtendBean implements Serializable {
        /**
         * province :
         * provinceId : 0
         * city :
         * cityId : 0
         * area :
         * areaId : 0
         */

        private String province;
        private int provinceId;
        private String city;
        private int cityId;
        private String area;
        private int areaId;

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public int getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(int provinceId) {
            this.provinceId = provinceId;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public int getCityId() {
            return cityId;
        }

        public void setCityId(int cityId) {
            this.cityId = cityId;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public int getAreaId() {
            return areaId;
        }

        public void setAreaId(int areaId) {
            this.areaId = areaId;
        }
    }

    public static class OrganizationsBean implements Serializable {

        private String organizationId;
        private String organizationName;

        public String getOrganizationId() {
            return organizationId;
        }

        public void setOrganizationId(String organizationId) {
            this.organizationId = organizationId;
        }

        public String getOrganizationName() {
            return organizationName;
        }

        public void setOrganizationName(String organizationName) {
            this.organizationName = organizationName;
        }
    }
}
