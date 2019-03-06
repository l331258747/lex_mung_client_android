package com.lex_mung.client_android.mvp.model.entity;

import java.io.Serializable;

public class LawyerTagsEntity implements Serializable {
    /**
     * tagName :
     * image :
     */

    private String tagName;
    private String image;

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
