package com.lex_mung.client_android.mvp.model.entity;

public class DeviceEntity {
    private int device_type;
    private String app_version;
    private int app_version_code;
    private String channel;
    private String device_model;
    private String device_os_version;
    private String device_uuid;

    public DeviceEntity(int device_type, String app_version, int app_version_code, String channel, String device_model, String device_os_version,String device_uuid) {
        this.device_type = device_type;
        this.app_version = app_version;
        this.app_version_code = app_version_code;
        this.channel = channel;
        this.device_model = device_model;
        this.device_os_version = device_os_version;
        this.device_uuid = device_uuid;
    }

    public String getDevice_uuid() {
        return device_uuid;
    }

    public void setDevice_uuid(String device_uuid) {
        this.device_uuid = device_uuid;
    }


    public int getDevice_type() {
        return device_type;
    }

    public void setDevice_type(int device_type) {
        this.device_type = device_type;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public int getApp_version_code() {
        return app_version_code;
    }

    public void setApp_version_code(int app_version_code) {
        this.app_version_code = app_version_code;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getDevice_model() {
        return device_model;
    }

    public void setDevice_model(String device_model) {
        this.device_model = device_model;
    }

    public String getDevice_os_version() {
        return device_os_version;
    }

    public void setDevice_os_version(String device_os_version) {
        this.device_os_version = device_os_version;
    }
}
