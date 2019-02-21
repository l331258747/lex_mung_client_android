package com.lex_mung.client_android.mvp.model.entity;

import java.io.Serializable;

public class FeedbackTypeEntity implements Serializable, SelectList {

    /**
     * feedbackTypeId : 4
     * feedbackTypeDescription : 无法登录，切换，登出等问题
     * feedbackTypeName : 账号登录，切换，登录
     */

    private int feedbackTypeId;
    private String feedbackTypeDescription;
    private String feedbackTypeName;

    public int getFeedbackTypeId() {
        return feedbackTypeId;
    }

    public void setFeedbackTypeId(int feedbackTypeId) {
        this.feedbackTypeId = feedbackTypeId;
    }

    public String getFeedbackTypeDescription() {
        return feedbackTypeDescription;
    }

    public void setFeedbackTypeDescription(String feedbackTypeDescription) {
        this.feedbackTypeDescription = feedbackTypeDescription;
    }

    public String getFeedbackTypeName() {
        return feedbackTypeName;
    }

    public void setFeedbackTypeName(String feedbackTypeName) {
        this.feedbackTypeName = feedbackTypeName;
    }
}
