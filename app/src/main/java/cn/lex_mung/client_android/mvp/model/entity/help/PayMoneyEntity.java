package cn.lex_mung.client_android.mvp.model.entity.help;

import java.io.Serializable;

public class PayMoneyEntity implements Serializable {
    int id;
    String text;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
