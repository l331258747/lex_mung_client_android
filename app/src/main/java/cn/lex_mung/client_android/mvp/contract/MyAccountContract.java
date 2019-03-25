package cn.lex_mung.client_android.mvp.contract;

import cn.lex_mung.client_android.mvp.model.entity.BalanceEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;

import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;

public interface MyAccountContract {
    interface View extends IView {
        void setBalance(String balance);
    }

    interface Model extends IModel {
        Observable<BaseResponse<BalanceEntity>> getUserBalance(int id);
    }
}
