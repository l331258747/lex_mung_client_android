package cn.lex_mung.client_android.mvp.contract;

import java.util.List;

import cn.lex_mung.client_android.mvp.model.entity.help.SolutionTypeChildBean;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;


public interface BusinessTypeSelectContract {
    interface View extends IView {
        void setAdapter(List<SolutionTypeChildBean> datas);
    }

    interface Model extends IModel {

    }
}
