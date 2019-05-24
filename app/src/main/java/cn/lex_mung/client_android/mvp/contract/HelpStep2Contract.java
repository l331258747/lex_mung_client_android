package cn.lex_mung.client_android.mvp.contract;

import java.util.List;

import cn.lex_mung.client_android.mvp.model.entity.help.SolutionTypeChildBean;
import me.zl.mvp.mvp.IModel;
import me.zl.mvp.mvp.IView;


public interface HelpStep2Contract {
    interface View extends IView {
        void setAdapter(List<SolutionTypeChildBean> datas);
    }

    interface Model extends IModel {

    }
}
