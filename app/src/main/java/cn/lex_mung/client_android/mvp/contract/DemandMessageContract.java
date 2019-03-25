package cn.lex_mung.client_android.mvp.contract;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.DemandMessageEntity;
import cn.lex_mung.client_android.mvp.ui.adapter.DemandMessageAdapter;

import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;

public interface DemandMessageContract {
    interface View extends IView {

        void initRecyclerView(DemandMessageAdapter adapter);

        void setAdapter(DemandMessageAdapter adapter);
    }

    interface Model extends IModel {
        Observable<BaseResponse<DemandMessageEntity>> getDemandMessageList();
    }
}
