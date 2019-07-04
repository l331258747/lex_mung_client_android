package cn.lex_mung.client_android.mvp.contract;

import android.app.Activity;
import android.support.v4.app.Fragment;

import me.zl.mvp.mvp.IModel;
import me.zl.mvp.mvp.IView;


public interface HelpStep1Contract {
    interface View extends IView {
        Fragment getFragment();

        void showToAppInfoDialog();

        void getLocation();
    }

    interface Model extends IModel {

    }
}
