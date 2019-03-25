package cn.lex_mung.client_android.mvp.model.entity;

public class BalanceEntity {

    /**
     * memberId : 1
     * totalConsumeAmount : 0
     * balanceAmount : 0.01
     * frozenAmount : 0
     * depositAmount : 0
     * dateModified :
     */

    private int memberId;
    private int totalConsumeAmount;
    private double balanceAmount;
    private int frozenAmount;
    private int depositAmount;
    private String dateModified;

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getTotalConsumeAmount() {
        return totalConsumeAmount;
    }

    public void setTotalConsumeAmount(int totalConsumeAmount) {
        this.totalConsumeAmount = totalConsumeAmount;
    }

    public double getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public int getFrozenAmount() {
        return frozenAmount;
    }

    public void setFrozenAmount(int frozenAmount) {
        this.frozenAmount = frozenAmount;
    }

    public int getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(int depositAmount) {
        this.depositAmount = depositAmount;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }
}
