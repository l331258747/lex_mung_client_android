package cn.lex_mung.client_android.mvp.contract;

import android.app.Activity;

import java.util.List;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.order.LawyerBean;
import cn.lex_mung.client_android.mvp.model.entity.order.RushOrderLawyerEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.RushOrderStatusEntity;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.RequestBody;


public interface RushOrdersContract {
    interface View extends IView {
        void setOrderStatus(int position);//订单状态

        void initTextBanner();//初始化律师轮播

        void setTotalProgress(int totalProgress);//设置倒计时总时长

        void setCountdown(int position);//设置倒计时时间

        void stopFlipping();//关闭律师轮播

        void removeAllViews();

        void addFlippingView(android.view.View view);

        void startFlipping();

        void setRushOrdersView(int i);

        Activity getActivity();

        void setStatusSuccess(LawyerBean lawyerBean);

    }

    interface Model extends IModel {
        Observable<BaseResponse<List<RushOrderLawyerEntity>>> requirementGrablawyers(RequestBody body);

        Observable<BaseResponse<RushOrderStatusEntity>> requirementStatusCheck(RequestBody body);
    }
}
