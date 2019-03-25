package cn.lex_mung.client_android.mvp.model.entity;

import java.io.Serializable;

public class OrgTagsEntity implements Serializable {
    /**
     * tagName : 长沙市侨商会专属律师团律师
     * image : http://oss.lex-mung.com/organization_aa0a4d35b.jpg
     */

    private String tagName;
    private String image;
    private String link;

    public OrgTagsEntity(String image, String tagName) {
        this.tagName = tagName;
        this.image = image;
    }

    public OrgTagsEntity() {
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

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
