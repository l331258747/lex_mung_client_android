package cn.lex_mung.client_android.mvp.contract;

import java.util.List;

import cn.lex_mung.client_android.mvp.model.entity.help.RequireTypeBean;
import me.zl.mvp.mvp.IModel;
import me.zl.mvp.mvp.IView;


public interface HelpStep4Contract {
    interface View extends IView {
        void setAdapter(List<RequireTypeBean> datas);
    }

    interface Model extends IModel {

    }
}
