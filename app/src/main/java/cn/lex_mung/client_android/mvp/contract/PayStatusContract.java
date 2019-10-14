package cn.lex_mung.client_android.mvp.contract;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.OrderStatusEntity;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IModel;
import me.zl.mvp.mvp.IView;
import okhttp3.RequestBody;

public interface PayStatusContract {
    interface View extends IView {
        void showSuccessLayout(String string);

        void showSuccessLayout(String string,String btnStr);

        void showSuccessLayout(String s,String btnStr,String btnStr2);

        void showFailLayout(String s);

        void showFailLayout(String s,String btnStr);

        void showFailLayout(String s,String btnStr,String btnStr2);

        void setContentLayout(String s, android.view.View.OnClickListener onClickListener);

        void setImg(int imageId);

        void showReleaseDemandLayout(String tip, String orderNo, String payTime, String stringSF, String sf);
    }

    interface Model extends IModel {
        Observable<BaseResponse<OrderStatusEntity>> checkOrderStatus(RequestBody body);
    }
}
