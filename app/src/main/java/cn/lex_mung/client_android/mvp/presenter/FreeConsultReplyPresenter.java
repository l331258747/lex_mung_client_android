package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.contract.FreeConsultReplyContract;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.FreeConsultReplyListEntity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import me.zl.mvp.di.scope.ActivityScope;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.RxLifecycleUtils;
import okhttp3.RequestBody;

import static cn.lex_mung.client_android.app.EventBusTags.CONSULT_INFO.EDIT_CONSULT_DETAILS_REPLY_COUNT;
import static cn.lex_mung.client_android.app.EventBusTags.CONSULT_INFO.EDIT_REPLY_COUNT;


@ActivityScope
public class FreeConsultReplyPresenter extends BasePresenter<FreeConsultReplyContract.Model, FreeConsultReplyContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private int consultationId;
    private int lawyerId;

    public void setConsultationId(int consultationId) {
        this.consultationId = consultationId;
    }

    public void setLawyerId(int lawyerId) {
        this.lawyerId = lawyerId;
    }

    @Inject
    public FreeConsultReplyPresenter(FreeConsultReplyContract.Model model, FreeConsultReplyContract.View rootView) {
        super(model, rootView);
    }

    public void reply(String content){
        if (TextUtils.isEmpty(content)) {
            mRootView.showMessage(mApplication.getString(R.string.text_please_input_content));
            return;
        }
        if (content.length() < 5) {
            mRootView.showMessage(mApplication.getString(R.string.text_no_less_5));
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("type", 2);
        map.put("content", content);
        map.put("consultationId", consultationId);
        map.put("lawyerId", lawyerId);
        mModel.lawyerReply(consultationId, RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<FreeConsultReplyListEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<FreeConsultReplyListEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.killMyself();
                            AppUtils.post(EDIT_REPLY_COUNT, EDIT_CONSULT_DETAILS_REPLY_COUNT);
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
