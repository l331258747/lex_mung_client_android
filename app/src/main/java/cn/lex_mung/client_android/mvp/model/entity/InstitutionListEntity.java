package cn.lex_mung.client_android.mvp.model.entity;

import java.io.Serializable;

public class InstitutionListEntity implements Serializable {
    /**
     * institutionId : 3311
     * institutionName : 湖南省长沙市中级人民法院
     * image :
     * institutionTypeId : 8
     * regionId : 430111
     * establishTime : 1949-01-01 00:00:00
     * address : 湖南省长沙市雨花区曙光中路289号
     * telephone : 0731-85798333
     * fax :
     * email :
     * zipcode : 410007
     * webstie : http://cszy.chinacourt.org/index.shtml
     * latitudeNum : 28.17944
     * longitudeNum : 113.00815
     * status : 1
     * dateAdded : 2017-09-21 18:10:58
     * dateModified : 2017-09-21 18:10:58
     * createUserId : 0
     * updateUserId : 0
     * isHot : 0
     * linkValue : http://baike.baidu.com/item/%E6%B9%96%E5%8D%97%E7%9C%81%E9%95%BF%E6%B2%99%E5%B8%82%E4%B8%AD%E7%BA%A7%E4%BA%BA%E6%B0%91%E6%B3%95%E9%99%A2
     * createMemberId : 0
     * updateMemberId : 0
     * institutionDescription :
     * memberId : 4
     */

    private int institutionId;
    private String institutionName;
    private String image;
    private int institutionTypeId;
    private int regionId;
    private String establishTime;
    private String address;
    private String telephone;
    private String fax;
    private String email;
    private String zipcode;
    private String webstie;
    private String latitudeNum;
    private String longitudeNum;
    private int status;
    private String dateAdded;
    private String dateModified;
    private int createUserId;
    private int updateUserId;
    private int isHot;
    private String linkValue;
    private int createMemberId;
    private int updateMemberId;
    private String institutionDescription;
    private int memberId;
    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getInstitutionTypeId() {
        return institutionTypeId;
    }

    public void setInstitutionTypeId(int institutionTypeId) {
        this.institutionTypeId = institutionTypeId;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public String getEstablishTime() {
        return establishTime;
    }

    public void setEstablishTime(String establishTime) {
        this.establishTime = establishTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getWebstie() {
        return webstie;
    }

    public void setWebstie(String webstie) {
        this.webstie = webstie;
    }

    public String getLatitudeNum() {
        return latitudeNum;
    }

    public void setLatitudeNum(String latitudeNum) {
        this.latitudeNum = latitudeNum;
    }

    public String getLongitudeNum() {
        return longitudeNum;
    }

    public void setLongitudeNum(String longitudeNum) {
        this.longitudeNum = longitudeNum;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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

    public int getIsHot() {
        return isHot;
    }

    public void setIsHot(int isHot) {
        this.isHot = isHot;
    }

    public String getLinkValue() {
        return linkValue;
    }

    public void setLinkValue(String linkValue) {
        this.linkValue = linkValue;
    }

    public int getCreateMemberId() {
        return createMemberId;
    }

    public void setCreateMemberId(int createMemberId) {
        this.createMemberId = createMemberId;
    }

    public int getUpdateMemberId() {
        return updateMemberId;
    }

    public void setUpdateMemberId(int updateMemberId) {
        this.updateMemberId = updateMemberId;
    }

    public String getInstitutionDescription() {
        return institutionDescription;
    }

    public void setInstitutionDescription(String institutionDescription) {
        this.institutionDescription = institutionDescription;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
}
