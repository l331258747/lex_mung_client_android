package cn.lex_mung.client_android.mvp.contract;

import cn.lex_mung.client_android.mvp.model.entity.OrgTagsEntity;

import java.util.List;

import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;

public interface IntegrityContract {
    interface View extends IView {
        void setOrgAdapter2(List<OrgTagsEntity> orgTags);

        void setSocialFunction(String s);

        void setPersonalHonor(String s);

        void hideOrgLayout2();

        void hideSocialFunctionLayout();

        void hidePersonalHonorLayout();

        void showNoDataLayout();
    }

    interface Model extends IModel {

    }
}
