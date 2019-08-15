package cn.lex_mung.client_android.mvp.contract;

import android.app.Activity;

import java.util.List;

import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.OrderDetailsEntity;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IModel;
import me.zl.mvp.mvp.IView;
import okhttp3.RequestBody;


public interface OrderDetailsExpertContract {
    interface View extends IView {
        void setOrderDetailView(int index);
        Activity getActivity();
        void setBottomStatus(boolean isLayoutShow, String btnTipName, boolean isBtnShow,String btnName, android.view.View.OnClickListener onClick);
        void setLawyer(String s);
        void setTime(String s);
        void setPrice(String s);
        void setOrderPrice(String s);
        void setCouponType(String s);
        void setOrderNo(String s);
        void setTalkTime(String s);
        void showCancelDialog();
        void showFinishDialog();

        void setTalkRecordList(List<OrderDetailsEntity.QuickTimeBean> lists);

    }

    interface Model extends IModel {
        Observable<BaseResponse<BaseListEntity<OrderDetailsEntity>>> getOrderDetail(RequestBody body);
        Observable<BaseResponse> expertCancel(RequestBody body);
        Observable<BaseResponse> expertFinish(RequestBody body);

    }
}
