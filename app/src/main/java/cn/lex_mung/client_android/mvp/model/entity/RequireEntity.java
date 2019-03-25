package cn.lex_mung.client_android.mvp.model.entity;

public class RequireEntity {
    public RequireEntity(int requireTypeId, int clientAffordMinAmount, int clientAffordMaxAmount) {
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
    private int clientAffordMinAmount;
    private int clientAffordMaxAmount;

    public int getRequireTypeId() {
        return requireTypeId;
    }

    public void setRequireTypeId(int requireTypeId) {
        this.requireTypeId = requireTypeId;
    }

    public int getClientAffordMinAmount() {
        return clientAffordMinAmount;
    }

    public void setClientAffordMinAmount(int clientAffordMinAmount) {
        this.clientAffordMinAmount = clientAffordMinAmount;
    }

    public int getClientAffordMaxAmount() {
        return clientAffordMaxAmount;
    }

    public void setClientAffordMaxAmount(int clientAffordMaxAmount) {
        this.clientAffordMaxAmount = clientAffordMaxAmount;
    }
}
