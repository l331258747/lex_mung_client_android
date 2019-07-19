package cn.lex_mung.client_android.mvp.contract;

import java.util.List;

import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.TradingListEntity;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.RequestBody;


public interface MyTradingListContract {
    interface View extends IView {
        void setAdapter(List<TradingListEntity> list, boolean isAdd);
    }

    interface Model extends IModel {
        Observable<BaseResponse<BaseListEntity<TradingListEntity>>> amountDetail(RequestBody body);
    }
}
