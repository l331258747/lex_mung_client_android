package cn.lex_mung.client_android.mvp.model.entity;

public class LocationEntity {

    String city;
    String cityId;
    String cityParent;
    String error;

    public LocationEntity(String city, String cityParent, String cityId) {
        this.city = city;
        this.cityParent = cityParent;
        this.cityId = cityId;
    }

    public LocationEntity(String error) {
        this.error = error;
    }

    public String getCity() {
        return city;
    }


    public String getCityId() {
        return cityId;
    }

    public int getCityIdInt(){
        int cityIdInt = 0;
        try {
            cityIdInt = Integer.valueOf(cityId);
        }catch (Exception e){
            return cityIdInt;
        }
        return cityIdInt;
    }

    public String getCityParent() {
        return cityParent;
    }

    public String getError() {
        return error;
    }

}
