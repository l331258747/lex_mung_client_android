package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.order.RushOrderLawyerEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.RushOrderStatusEntity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.di.scope.ActivityScope;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.RushOrdersContract;
import me.zl.mvp.utils.RxLifecycleUtils;
import okhttp3.RequestBody;


@ActivityScope
public class RushOrdersPresenter extends BasePresenter<RushOrdersContract.Model, RushOrdersContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    int requirementId;
    List<RushOrderLawyerEntity> lawyers;
    private int mTotalProgress = 120;
    private Thread countdownThread;//倒计时线程

    private int mCurrentProgress = 120;
    private boolean isCountdownStop;//倒计时

    private boolean isGetStatusStop;//获取订单状态停止
    private Thread getStatusThread;

    private int orderStatus = 1;

    @Inject
    public RushOrdersPresenter(RushOrdersContract.Model model, RushOrdersContract.View rootView) {
        super(model, rootView);
    }

    public void onCreate(int requirementId){
        this.requirementId = requirementId;
        getRushLawyers();

        mRootView.initTextBanner();
        mRootView.setTotalProgress(mTotalProgress);

        countdownThread = new Thread(new CountdownRunable());
        getStatusThread = new Thread(new GetStatusRunable());

        getStatusThread.start();

        setOrderStatus(0);
    }

    class GetStatusRunable implements Runnable {
        @Override
        public void run() {
            while (true) {
                if (isGetStatusStop)
                    return;
                getRushStatus();
                try {
                    Thread.sleep(5000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class CountdownRunable implements Runnable {
        @Override
        public void run() {

            while (mCurrentProgress > 0) {
                if (isCountdownStop)
                    return;
                mCurrentProgress -= 1;
                mRootView.setCountdown(mCurrentProgress);
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            mRootView.getActivity().runOnUiThread(() ->{
                isCountdownStop = true;
                isGetStatusStop = true;
                setOrderStatus(3);
            });
        }
    }

    //设置进度
    private void setOrderStatus(int i) {
        isCountdownStop = true;
        mRootView.stopFlipping();
        switch (i){
            case 0://初始
                mRootView.setOrderStatus(0);
                mRootView.setRushOrdersView(1);
                orderStatus = 1;
                break;
            case 1://倒计时
                //倒计时开始，律师轮播
                addNotice();
                mCurrentProgress = 120;
                isCountdownStop = false;
                countdownThread.start();
                mRootView.setOrderStatus(1);
                mRootView.setRushOrdersView(1);
                orderStatus = 1;
                break;
            case 2://匹配到律师
                mRootView.setOrderStatus(2);
                mRootView.setRushOrdersView(2);
                orderStatus = 2;
                break;
            case 3://失败
                mRootView.setOrderStatus(3);
                mRootView.setRushOrdersView(1);
                orderStatus = 1;
                break;
        }
    }

    public int getOrderStatus(){
        return orderStatus;
    }

    /**
     * 公告
     */
    public void addNotice() {
        int size = lawyers.size();
        mRootView.removeAllViews();
        if (size == 0) {
            mRootView.stopFlipping();
            return;
        }

        if (size == 1) {
            View view = View.inflate(mRootView.getActivity(), R.layout.view_flipper_item_layout, null);
            ((TextView) view.findViewById(R.id.tv_name)).setText(lawyers.get(0).getMember_name());
            ((TextView) view.findViewById(R.id.tv_content)).setText(lawyers.get(0).getInstitution_name());
            mRootView.addFlippingView(view);
            mRootView.stopFlipping();
            return;
        }

        mRootView.startFlipping();
        for (int i = 0; i < size; i++) {
            View view = View.inflate(mRootView.getActivity(), R.layout.view_flipper_item_layout, null);
            ((TextView) view.findViewById(R.id.tv_name)).setText(lawyers.get(i).getMember_name());
            ((TextView) view.findViewById(R.id.tv_content)).setText(lawyers.get(i).getInstitution_name());
            mRootView.addFlippingView(view);
        }
    }

    public void setCountdownStop(boolean isStop){
        this.isCountdownStop = isStop;
    }

    public void setGetStatusStop(boolean isStop){
        this.isGetStatusStop = isStop;
    }

    public void getRushLawyers(){
        Map<String, Object> map = new HashMap<>();
        map.put("requirementId", requirementId);
        mModel.requirementGrablawyers(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 2))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<RushOrderLawyerEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<RushOrderLawyerEntity>> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            lawyers = baseResponse.getData();
                            setOrderStatus(1);
                        }
                    }
                });
    }

    public void getRushStatus(){
        Map<String, Object> map = new HashMap<>();
        map.put("requirementId", requirementId);
        mModel.requirementStatusCheck(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 2))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<RushOrderStatusEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<RushOrderStatusEntity> baseResponse) {
                        if(isGetStatusStop)
                            return;

                        if (baseResponse.isSuccess()) {
                            if(baseResponse.getData().getStatus() == 1){//1，抢单成功，0，未抢单
                                setOrderStatus(2);
                                isGetStatusStop = true;
                                mRootView.setStatusSuccess(baseResponse.getData().getLawyer());
                                mRootView.setOrderInfo(baseResponse.getData().getOrder());
                            }
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }


}
