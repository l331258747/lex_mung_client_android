package cn.lex_mung.client_android.mvp.contract;

import android.app.Activity;

import java.util.List;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.ReleaseDemandOrgMoneyEntity;
import cn.lex_mung.client_android.mvp.model.entity.ReleaseDemandOrgMoneyEntity2;
import cn.lex_mung.client_android.mvp.ui.activity.CouponModeActivity;
import cn.lex_mung.client_android.mvp.ui.adapter.ComponModeAdapter2;
import cn.lex_mung.client_android.mvp.ui.adapter.MyCardAdapter;
import cn.lex_mung.client_android.mvp.ui.adapter.OrderCouponAdapter;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.RequestBody;


public interface CouponModeCardContract {
    interface View extends IView {

        void initRecyclerView(ComponModeAdapter2 adapter);

        void setEmptyView(ComponModeAdapter2 adapter);

        CouponModeActivity getCouponModeActivity();
    }

    interface Model extends IModel {

        Observable<BaseResponse<ReleaseDemandOrgMoneyEntity2>> getOrgList2(RequestBody body);
    }
}
