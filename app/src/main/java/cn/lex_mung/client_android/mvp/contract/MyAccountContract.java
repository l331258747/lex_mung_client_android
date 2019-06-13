package cn.lex_mung.client_android.mvp.contract;

import android.app.Activity;

import cn.lex_mung.client_android.mvp.model.entity.BalanceEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;

import cn.lex_mung.client_android.mvp.model.entity.PayEntity;
import cn.lex_mung.client_android.mvp.ui.adapter.MyAccountPayAdapter;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.RequestBody;

public interface MyAccountContract {
    interface View extends IView {
        void setBalance(String balance);

        Activity getActivity();

        void showToAppInfoDialog();

        void setOrderMoney(String format);

        void initRecyclerView(MyAccountPayAdapter myAccountPayAdapter);

        void setTip2(String str);
    }

    interface Model extends IModel {
        Observable<BaseResponse<BalanceEntity>> getUserBalance(int id);

        Observable<BaseResponse<PayEntity>> pay(RequestBody body);
    }
}
