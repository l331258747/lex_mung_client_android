package cn.lex_mung.client_android.mvp.contract;

import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.MessageEntity;
import cn.lex_mung.client_android.mvp.ui.adapter.OrderMessageAdapter;

import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;

public interface OrderMessageContract {
    interface View extends IView {
        void initRecyclerView(OrderMessageAdapter adapter);

        void setEmptyView(OrderMessageAdapter adapter);
    }

    interface Model extends IModel {
        Observable<BaseResponse> allSetRead(int type);

        Observable<BaseResponse<BaseListEntity<MessageEntity>>> getOrderMessageList(int pageNum);

        Observable<BaseResponse> setRead(int id);
    }
}
