package cn.lex_mung.client_android.mvp.contract;

import java.util.List;

import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.home.CommonSolutionEntity;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;


public interface HomeSolutionContract {
    interface View extends IView {
        void initAdapter(List<CommonSolutionEntity> datas);
    }

    interface Model extends IModel {
        Observable<BaseResponse<BaseListEntity<CommonSolutionEntity>>> commonSolutionList();
    }
}
