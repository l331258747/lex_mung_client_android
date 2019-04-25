package cn.lex_mung.client_android.mvp.model.entity.order;

import android.text.TextUtils;

public class DocGetEntity {


    /**
     * size_text : 36.67KB
     * is_read : 0
     * create_member_id : 5807
     * create_member_icon_image : http://oss.lex-mung.com/icon_image_lawyer_15530526634229.png
     * size : 37547
     * create_time : 1556074481
     * create_member_name : 付捍江律师无敌
     * name : 141049399.jpg
     * link : http://drive.lex-office.top/data/user/f/042a213bd02d4e4590489f60ad3e43e5.jpg?e=1556079796&token=5eWlQjLHpeGb4vjwqvfDSviMbGwu0hdSuEGpWewf:IVfec0cAeaLoUJ0eogpA5E-XSHA=
     * repository_id : 042a213bd02d4e4590489f60ad3e43e5
     * create_member_sex : 1
     */

    private String size_text;
    private int is_read;
    private int create_member_id;
    private String create_member_icon_image;
    private int size;
    private int create_time;
    private String create_member_name;
    private String name;
    private String link;
    private String repository_id;
    private int create_member_sex;

    public String getSize_text() {
        return size_text;
    }

    public void setSize_text(String size_text) {
        this.size_text = size_text;
    }

    public int getIs_read() {
        return is_read;
    }

    public void setIs_read(int is_read) {
        this.is_read = is_read;
    }

    public int getCreate_member_id() {
        return create_member_id;
    }

    public void setCreate_member_id(int create_member_id) {
        this.create_member_id = create_member_id;
    }

    public String getCreate_member_icon_image() {
        return create_member_icon_image;
    }

    public void setCreate_member_icon_image(String create_member_icon_image) {
        this.create_member_icon_image = create_member_icon_image;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public String getCreate_member_name() {
        return create_member_name;
    }

    public void setCreate_member_name(String create_member_name) {
        this.create_member_name = create_member_name;
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

    public String getRepository_id() {
        return repository_id;
    }

    public void setRepository_id(String repository_id) {
        this.repository_id = repository_id;
    }

    public int getCreate_member_sex() {
        return create_member_sex;
    }

    public void setCreate_member_sex(int create_member_sex) {
        this.create_member_sex = create_member_sex;
    }

    public String getFileType() {
        if(TextUtils.isEmpty(name)) return "";
        String[] names = name.split(".");
        if(names.length < 2) return "";
        return names[names.length - 1];
    }

    //	0-未读，1-律师已读，2-用户已读，3-都已读
    public String getRead() {
        switch (is_read){
            case 0:
                return "未读";
            case 1:
                return "律师已读";
            case 2:
                return "用户已读";
            case 3:
                return "已读";
        }
        return "未读";
    }
}
