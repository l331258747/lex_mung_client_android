package cn.lex_mung.client_android.mvp.model.entity.other;

import android.text.TextUtils;

public class LaunchLocationEntity {


    /**
     * status : 1
     * info : OK
     * infocode : 10000
     * province : 湖南省
     * city : 长沙市
     * adcode : 430100
     * rectangle : 112.6534116,27.96920845;113.3946776,28.42655248
     */

    private String status;
    private String info;
    private String infocode;
    private String province;
    private String city;
    private String adcode;
    private String rectangle;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfocode() {
        return infocode;
    }

    public void setInfocode(String infocode) {
        this.infocode = infocode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAdcode() {
        return adcode;
    }

    public int getAdcodeInt(){
        if(TextUtils.isEmpty(adcode))
            return  0;
        int result;
        try{
            result = Integer.valueOf(adcode);
        }catch (Exception e){
            result = 0;
        }
        return result;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    public String getRectangle() {
        return rectangle;
    }

    public void setRectangle(String rectangle) {
        this.rectangle = rectangle;
    }
}
