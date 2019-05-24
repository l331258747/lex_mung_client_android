package cn.lex_mung.client_android.mvp.contract;

import java.util.List;

import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;


public interface HelpStep2Contract {
    interface View extends IView {
        void setAdapter(List<String> datas);
    }

    interface Model extends IModel {

    }
}
