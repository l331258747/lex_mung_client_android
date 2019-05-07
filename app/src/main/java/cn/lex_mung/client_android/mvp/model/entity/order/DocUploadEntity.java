package cn.lex_mung.client_android.mvp.model.entity.order;

public class DocUploadEntity {

    String name;
    String link;
    String repository_size;
    String repository_id;

    public String getRepository_size() {
        return repository_size;
    }

    public String getRepository_id() {
        return repository_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
