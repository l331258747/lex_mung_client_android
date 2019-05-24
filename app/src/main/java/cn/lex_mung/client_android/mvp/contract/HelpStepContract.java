package cn.lex_mung.client_android.mvp.contract;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.help.HelpStepEntity;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IModel;
import me.zl.mvp.mvp.IView;


public interface HelpStepContract {
    interface View extends IView {
        void setFragment(HelpStepEntity entity);
    }

    interface Model extends IModel {
        Observable<BaseResponse<HelpStepEntity>> assistantFilters();
    }
}
