package cn.lex_mung.client_android.mvp.model.entity;

public class LocationEntity {

    String city;
    String cityParent;
    String error;
    double longitude;
    double latitude;

    public LocationEntity(String city, String cityParent, double longitude, double latitude) {
        this.city = city;
        this.cityParent = cityParent;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public LocationEntity(String error) {
        this.error = error;
    }

    public String getCity() {
        return city;
    }


    public String getCityParent() {
        return cityParent;
    }

    public String getError() {
        return error;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
