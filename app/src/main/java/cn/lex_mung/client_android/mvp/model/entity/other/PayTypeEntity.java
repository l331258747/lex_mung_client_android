package cn.lex_mung.client_android.mvp.model.entity.other;

import me.zl.mvp.utils.StringUtils;

public class PayTypeEntity {

    int icon;

    String title;

    double balance;

    boolean isSelected;

    int type;

    int id;

    int groupId;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getBalance() {
        return balance;
    }

    public String getBalanceStr() {
        return "(余额" + StringUtils.getStringNum(balance) + "元)";
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
