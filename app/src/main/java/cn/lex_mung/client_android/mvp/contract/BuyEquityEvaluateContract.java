package cn.lex_mung.client_android.mvp.contract;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IModel;
import me.zl.mvp.mvp.IView;
import okhttp3.RequestBody;


public interface BuyEquityEvaluateContract {
    interface View extends IView {

    }

    interface Model extends IModel {
        Observable<BaseResponse> legalAdviserOrderEvaluate(RequestBody body);

        Observable<BaseResponse> privateLawyersEvaluateAdd(RequestBody body);
    }
}
