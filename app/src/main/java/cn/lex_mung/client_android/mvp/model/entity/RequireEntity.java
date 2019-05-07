package cn.lex_mung.client_android.mvp.model.entity;

public class RequireEntity {
    public RequireEntity(int requireTypeId, double clientAffordMinAmount, double clientAffordMaxAmount) {
        this.requireTypeId = requireTypeId;
        this.clientAffordMinAmount = clientAffordMinAmount;
        this.clientAffordMaxAmount = clientAffordMaxAmount;
    }

    public RequireEntity() {
    }

    /**
     * requireTypeId : 10
     * clientAffordMinAmount : 1000
     * clientAffordMaxAmount : 5000
     */

    private int requireTypeId;
    private double clientAffordMinAmount;
    private double clientAffordMaxAmount;

    public int getRequireTypeId() {
        return requireTypeId;
    }

    public void setRequireTypeId(int requireTypeId) {
        this.requireTypeId = requireTypeId;
    }

    public double getClientAffordMinAmount() {
        return clientAffordMinAmount;
    }

    public void setClientAffordMinAmount(double clientAffordMinAmount) {
        this.clientAffordMinAmount = clientAffordMinAmount;
    }

    public double getClientAffordMaxAmount() {
        return clientAffordMaxAmount;
    }

    public void setClientAffordMaxAmount(double clientAffordMaxAmount) {
        this.clientAffordMaxAmount = clientAffordMaxAmount;
    }
}
