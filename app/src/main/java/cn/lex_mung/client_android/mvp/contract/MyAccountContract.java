package cn.lex_mung.client_android.mvp.contract;

import android.app.Activity;

import java.util.List;

import cn.lex_mung.client_android.mvp.model.entity.BalanceEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.PayEntity;
import cn.lex_mung.client_android.mvp.model.entity.mine.RechargeCouponEntity;
import cn.lex_mung.client_android.mvp.model.entity.mine.RechargeEntity;
import cn.lex_mung.client_android.mvp.ui.adapter.MyAccountPayAdapter2;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IModel;
import me.zl.mvp.mvp.IView;
import okhttp3.RequestBody;

public interface MyAccountContract {
    interface View extends IView {
        void setBalance(String balance);

        Activity getActivity();

        void showToAppInfoDialog();

        void setOrderMoney(String format);

        void initRecyclerView(MyAccountPayAdapter2 myAccountPayAdapter);

        void setTip2(String str);

        void showPriceDialog(String str);

        void setGivePrice(boolean isShow, double givePrice);

    }

    interface Model extends IModel {
        Observable<BaseResponse<BalanceEntity>> getUserBalance(int id);

        Observable<BaseResponse<PayEntity>> pay(RequestBody body);

        Observable<BaseResponse<List<RechargeEntity>>> rechargeList(RequestBody body);

        Observable<BaseResponse<List<RechargeCouponEntity>>> rechargeCouponList(int voucherPackId);
    }
}
