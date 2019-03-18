package com.lex_mung.client_android.mvp.contract;

import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;


public interface SocialResourcesContract {
    interface View extends IView {
        void setSocialFunction(String s);

        void setCourt(String s);

        void setP(String s);

        void hidePLayout();

        void hideCourtLayout();

        void hideSocialFunctionLayout();

        void showNoDataLayout();

        void showNoDataLayout1();
    }

    interface Model extends IModel {

    }
}
