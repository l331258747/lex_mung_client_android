package cn.lex_mung.client_android.mvp.model.entity.home;

import java.util.List;

public class RequirementTypeV3Entity {


    private List<NormalBean> normal;
    private List<HotBean> hot;

    public List<NormalBean> getNormal() {
        return normal;
    }

    public void setNormal(List<NormalBean> normal) {
        this.normal = normal;
    }

    public List<HotBean> getHot() {
        return hot;
    }

    public void setHot(List<HotBean> hot) {
        this.hot = hot;
    }

}
