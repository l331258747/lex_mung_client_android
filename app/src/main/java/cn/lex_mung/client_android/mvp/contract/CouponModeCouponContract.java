package cn.lex_mung.client_android.mvp.contract;

import android.app.Activity;
import android.support.v4.app.Fragment;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.ReleaseDemandOrgMoneyEntity2;
import cn.lex_mung.client_android.mvp.ui.activity.CouponModeActivity;
import cn.lex_mung.client_android.mvp.ui.adapter.ComponModeAdapter1;
import cn.lex_mung.client_android.mvp.ui.adapter.MyCardAdapter;
import cn.lex_mung.client_android.mvp.ui.adapter.OrderCouponAdapter;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.RequestBody;


public interface CouponModeCouponContract {
    interface View extends IView {
        void initRecyclerView(ComponModeAdapter1 adapter);

        void setEmptyView(ComponModeAdapter1 adapter);

        CouponModeActivity getCouponModeActivity();

    }

    interface Model extends IModel {
        Observable<BaseResponse<ReleaseDemandOrgMoneyEntity2>> getOrgList2(RequestBody body);
    }
}
