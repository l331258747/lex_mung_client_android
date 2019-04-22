package cn.lex_mung.client_android.mvp.model.entity.home;

import java.util.List;

public class RequirementTypeV3Entity {


    private List<NormalBean> normal;
    private List<NormalBean> hot;
    private List<NormalBean> more;

    public List<NormalBean> getNormal() {
        return normal;
    }

    public void setNormal(List<NormalBean> normal) {
        this.normal = normal;
    }

    public List<NormalBean> getHot() {
        return hot;
    }

    public void setHot(List<NormalBean> hot) {
        this.hot = hot;
    }

    public List<NormalBean> getMore() {
        return more;
    }

    public void setMore(List<NormalBean> more) {
        this.more = more;
    }
}
