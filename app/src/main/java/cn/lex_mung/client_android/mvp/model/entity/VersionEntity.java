package cn.lex_mung.client_android.mvp.model.entity;

public class VersionEntity {

    /**
     * hasUpdate : 1
     * downloadUrl : http://www.baidu.com/
     * forceUpdate : 0
     * updateTips : 1
     */

    private int hasUpdate;
    private String downloadUrl;
    private int forceUpdate;
    private String updateTips;
    private int versionCode;
    private String version;

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return version;
    }

    public void setVersionName(String versionName) {
        this.version = versionName;
    }

    public int getHasUpdate() {
        return hasUpdate;
    }

    public void setHasUpdate(int hasUpdate) {
        this.hasUpdate = hasUpdate;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public int getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(int forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public String getUpdateTips() {
        return updateTips;
    }

    public void setUpdateTips(String updateTips) {
        this.updateTips = updateTips;
    }
}
