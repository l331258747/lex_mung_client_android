package cn.lex_mung.client_android.mvp.contract;

import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.MessageEntity;
import cn.lex_mung.client_android.mvp.ui.adapter.SystemMessageAdapter;

import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;

public interface SystemMessageContract {
    interface View extends IView {
        void initRecyclerView(SystemMessageAdapter adapter);

        void setEmptyView(SystemMessageAdapter adapter);
    }

    interface Model extends IModel {
        Observable<BaseResponse> allSetRead(int type);

        Observable<BaseResponse<BaseListEntity<MessageEntity>>> getSystemMessageList(int pageNum);

        Observable<BaseResponse> setRead(int id);
    }
}
