package cn.lex_mung.client_android.mvp.model.entity.home;

import java.util.List;

import cn.lex_mung.client_android.mvp.model.entity.LawyerEntity2;
import cn.lex_mung.client_android.mvp.model.entity.free.CommonFreeTextEntity;

public class HomeEntity {

    /**
     * headline :
     * subheadline :
     * type : buttonheadline
     * btns : [{"icon":"http://oss.lex-mung.com/","title":"快速律师咨询","desc1":"5分钟内响应","desc2":"律师专业领域对口","tag":"http://oss.lex-mung.com/","jumptype":"h5","jumpurl":"http://h5.lex-mung.com/quick.html"},{"icon":"http://oss.lex-mung.com/","title":"权威律师服务","desc1":"全方位护航","desc2":"量身定制解决方案","tag":"http://oss.lex-mung.com/","jumptype":"inner","jumpurl":"lex://pages/expert"},{"icon":"http://oss.lex-mung.com/","title":"围观他人提问","desc1":"律师互动答疑","desc2":"简要梳理思路","tag":"http://oss.lex-mung.com/","jumptype":"inner","jumpurl":"lex://pages/free"}]
     */
    private int containerId;
    private String headline;
    private String subheadline;
    private String type;
    private List<HomeChildEntity> btns;
    private LawyerEntity2 lawyerEntity2;
    private CommonFreeTextEntity freeTextEntity;

    public CommonFreeTextEntity getFreeTextEntity() {
        return freeTextEntity;
    }

    public void setFreeTextEntity(CommonFreeTextEntity freeTextEntity) {
        this.freeTextEntity = freeTextEntity;
    }

    public LawyerEntity2 getLawyerEntity2() {
        return lawyerEntity2;
    }

    public void setLawyerEntity2(LawyerEntity2 lawyerEntity2) {
        this.lawyerEntity2 = lawyerEntity2;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getSubheadline() {
        return subheadline;
    }

    public void setSubheadline(String subheadline) {
        this.subheadline = subheadline;
    }

    public String getType() {
        return type;
    }

    public int getTypeId() {
        switch (type) {
            case "button":
                return 1;
            case "banner":
                return 2;
            case " two_two_menu":
            case "two_two_menu":
                return 3;
            case "three_card":
                return 4;
            case "four_button":
                return 5;
            case "eight_button":
                return 6;
            case "single_image":
                return 7;
            case "vertical_navigate_list":
                return 8;
            case "swipe_card":
                return 9;
            case "home_lawyer":
                return 20;
            case "home_lawyer_title":
                return 21;
            case "home_free":
                return 30;
            case "home_free_title":
                return 31;
        }
        return 0;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<HomeChildEntity> getBtns() {
        return btns;
    }

    public void setBtns(List<HomeChildEntity> btns) {
        this.btns = btns;
    }


}
