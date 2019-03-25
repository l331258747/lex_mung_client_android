package cn.lex_mung.client_android.mvp.model.entity;

import java.io.Serializable;

public class AboutEntity implements Serializable {

    /**
     * aboutUsUrl : http://h5-test.lex-mung.com/about_us.html
     * cooperationUrl : http://h5-test.lex-mung.com/cooperation.html
     * kefuPhone : 4008113060
     * share : {"title":"LEX 绿豆圈-律师端","url":"http://www.e-lex.cn","desc":"做最精准的匹配，找最合适的律师"}
     * guideUrl : http://h5-test.lex-mung.com/guidance.html
     */

    private String aboutUsUrl;
    private String cooperationUrl;
    private String kefuPhone;
    private ShareBean share;
    private String guideUrl;

    public String getAboutUsUrl() {
        return aboutUsUrl;
    }

    public void setAboutUsUrl(String aboutUsUrl) {
        this.aboutUsUrl = aboutUsUrl;
    }

    public String getCooperationUrl() {
        return cooperationUrl;
    }

    public void setCooperationUrl(String cooperationUrl) {
        this.cooperationUrl = cooperationUrl;
    }

    public String getKefuPhone() {
        return kefuPhone;
    }

    public void setKefuPhone(String kefuPhone) {
        this.kefuPhone = kefuPhone;
    }

    public ShareBean getShare() {
        return share;
    }

    public void setShare(ShareBean share) {
        this.share = share;
    }

    public String getGuideUrl() {
        return guideUrl;
    }

    public void setGuideUrl(String guideUrl) {
        this.guideUrl = guideUrl;
    }

    public static class ShareBean implements Serializable {
        /**
         * title : LEX 绿豆圈-律师端
         * url : http://www.e-lex.cn
         * desc : 做最精准的匹配，找最合适的律师
         */

        private String title;
        private String url;
        private String desc;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
