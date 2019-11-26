package cn.lex_mung.client_android.mvp.model.entity.payEquity;

import java.io.Serializable;

public class EvaluateBean implements Serializable {
    int id;
    String orderId;
    String createDate;
    int evaluateType;
    int generalEvaluation;
    int professionalKnowledge;
    int responseSpeed;
    int serviceAttitude;
    String evaluationContent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getEvaluateType() {
        return evaluateType;
    }

    public void setEvaluateType(int evaluateType) {
        this.evaluateType = evaluateType;
    }

    public int getGeneralEvaluation() {
        return generalEvaluation;
    }

    public void setGeneralEvaluation(int generalEvaluation) {
        this.generalEvaluation = generalEvaluation;
    }

    public int getProfessionalKnowledge() {
        return professionalKnowledge;
    }

    public void setProfessionalKnowledge(int professionalKnowledge) {
        this.professionalKnowledge = professionalKnowledge;
    }

    public int getResponseSpeed() {
        return responseSpeed;
    }

    public void setResponseSpeed(int responseSpeed) {
        this.responseSpeed = responseSpeed;
    }

    public int getServiceAttitude() {
        return serviceAttitude;
    }

    public void setServiceAttitude(int serviceAttitude) {
        this.serviceAttitude = serviceAttitude;
    }

    public String getEvaluationContent() {
        return evaluationContent;
    }

    public void setEvaluationContent(String evaluationContent) {
        this.evaluationContent = evaluationContent;
    }
}