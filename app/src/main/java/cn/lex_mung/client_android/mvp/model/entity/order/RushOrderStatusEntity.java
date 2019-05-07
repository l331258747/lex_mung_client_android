package cn.lex_mung.client_android.mvp.model.entity.order;

public class RushOrderStatusEntity {


    /**
     * lawyer : {"member_id":5020,"practice_year":4,"member_position_name":"律师","mobile":"13135110211","icon_image":"http://oss.lex-mung.com/icon_image_member_15467318541129.png","satisfaction_degree":20,"contract_count":20,"member_name":"律师我我我","institution_name":"湖南芙蓉律师事务所","finish_time":120000,"response_time":21213}
     * status : 1
     */

    private LawyerBean lawyer;
    private int status;
    private OrderBean order;

    public LawyerBean getLawyer() {
        return lawyer;
    }

    public void setLawyer(LawyerBean lawyer) {
        this.lawyer = lawyer;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public OrderBean getOrder() {
        return order;
    }
}
