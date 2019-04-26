package cn.lex_mung.client_android.mvp.model.entity.order;

public class RushOrderLawyerEntity {


    /**
     * member_id : 5020
     * member_name : fdf
     * require_type_id : 32
     * icon_image : http://oss.lex-mung.com/icon_image_member_15467318541129.png
     * lawyer_group_id : 1
     * institution_name : 湖南芙蓉律师事务所
     */

    private int member_id;
    private String member_name;
    private int require_type_id;
    private String icon_image;
    private int lawyer_group_id;
    private String institution_name;

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public int getRequire_type_id() {
        return require_type_id;
    }

    public void setRequire_type_id(int require_type_id) {
        this.require_type_id = require_type_id;
    }

    public String getIcon_image() {
        return icon_image;
    }

    public void setIcon_image(String icon_image) {
        this.icon_image = icon_image;
    }

    public int getLawyer_group_id() {
        return lawyer_group_id;
    }

    public void setLawyer_group_id(int lawyer_group_id) {
        this.lawyer_group_id = lawyer_group_id;
    }

    public String getInstitution_name() {
        return institution_name;
    }

    public void setInstitution_name(String institution_name) {
        this.institution_name = institution_name;
    }
}
