package cn.lex_mung.client_android.mvp.model.entity.order;

public class LawyerBean {
    /**
     * member_id : 5020
     * practice_year : 4
     * member_position_name : 律师
     * mobile : 13135110211
     * icon_image : http://oss.lex-mung.com/icon_image_member_15467318541129.png
     * satisfaction_degree : 20
     * contract_count : 20
     * member_name : 律师我我我
     * institution_name : 湖南芙蓉律师事务所
     * finish_time : 120000
     * response_time : 21213
     */

    private int member_id;
    private String practice_year;
    private String member_position_name;
    private String mobile;
    private String icon_image;
    private int satisfaction_degree;
    private int contract_count;
    private String member_name;
    private String institution_name;
    private String finish_time;
    private String response_time;

    public int getMember_id() {
        return member_id;
    }

    public String getPractice_year() {
        return practice_year;
    }

    public String getMember_position_name() {
        return member_position_name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getIcon_image() {
        return icon_image;
    }

    public int getSatisfaction_degree() {
        return satisfaction_degree;
    }

    public String getContract_count() {
        return contract_count + "";
    }

    public String getMember_name() {
        return member_name;
    }

    public String getInstitution_name() {
        return institution_name;
    }

    public String getFinish_time() {
        return finish_time;
    }

    public String getResponse_time() {
        return response_time;
    }

    public String getContent2() {
        return institution_name + "|" + practice_year;
    }

    public String getMobile2() {
        return "律师电话:" + mobile;
    }
}