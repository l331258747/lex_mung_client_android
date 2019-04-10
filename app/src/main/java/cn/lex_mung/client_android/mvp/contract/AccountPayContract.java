package cn.lex_mung.client_android.mvp.contract;

import android.app.Activity;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.PayEntity;

import java.util.List;

import cn.lex_mung.client_android.mvp.ui.adapter.MyAccountPayAdapter;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.RequestBody;

public interface AccountPayContract {
    interface View extends IView {
        Activity getActivity();

        void showToAppInfoDialog();

        void setOrderMoney(String format);

        void initRecyclerView(MyAccountPayAdapter myAccountPayAdapter);
    }

    interface Model extends IModel {
        Observable<BaseResponse<PayEntity>> pay(RequestBody body);
    }
}
