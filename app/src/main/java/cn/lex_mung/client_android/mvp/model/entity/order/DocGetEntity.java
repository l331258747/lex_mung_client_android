package cn.lex_mung.client_android.mvp.model.entity.order;

import java.util.List;

public class DocGetEntity {


    /**
     * paginated : {"pages":1,"end_row_num":1,"has_previous":0,"total_rows":1,"start_row_num":1,"page_num":1,"has_next":0,"page_size":10}
     * help_link :
     * list : [{"size_text":"21.65KB","is_read":0,"create_member_id":5807,"create_member_icon_image":"http://oss.lex-mung.com/icon_image_lawyer_15530526634229.png","size":22171,"create_time":1556265189,"create_member_name":"付捍江律师无敌","name":"app43401329.jpg","link":"http://drive.lex-office.top/data/user/f/fd63e0459f434015b95051e823aea718.jpg?e=1556268862&token=5eWlQjLHpeGb4vjwqvfDSviMbGwu0hdSuEGpWewf:AShP9QQCYaqKjcdpR7u4hrdgEz0=","repository_id":"fd63e0459f434015b95051e823aea718","create_member_sex":1}]
     */

    private PaginatedBean paginated;
    private String help_link;
    private List<ListBean> list;

    public PaginatedBean getPaginated() {
        return paginated;
    }

    public void setPaginated(PaginatedBean paginated) {
        this.paginated = paginated;
    }

    public String getHelp_link() {
        return help_link;
    }

    public void setHelp_link(String help_link) {
        this.help_link = help_link;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }
}
