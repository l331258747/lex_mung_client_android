package cn.lex_mung.client_android.mvp.model.entity.free;

public class FreeTextBizinfoEntity {


    /**
     * today_free_text_count : 18
     * today_free_text_reply_count : 24
     */

    private int today_free_text_count;
    private int today_free_text_reply_count;

    public int getToday_free_text_count() {
        return today_free_text_count;
    }

    public void setToday_free_text_count(int today_free_text_count) {
        this.today_free_text_count = today_free_text_count;
    }

    public int getToday_free_text_reply_count() {
        return today_free_text_reply_count;
    }

    public void setToday_free_text_reply_count(int today_free_text_reply_count) {
        this.today_free_text_reply_count = today_free_text_reply_count;
    }
}
