package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import me.zl.mvp.integration.AppManager;
import me.zl.mvp.di.scope.FragmentScope;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.HelpStep2Contract;


@FragmentScope
public class HelpStep2Presenter extends BasePresenter<HelpStep2Contract.Model, HelpStep2Contract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    List<String> datas = new ArrayList<>();

    @Inject
    public HelpStep2Presenter(HelpStep2Contract.Model model, HelpStep2Contract.View rootView) {
        super(model, rootView);
    }

    public void getList(int i) {
        datas = new ArrayList<>();

        datas.add("" + i);
        datas.add("" + i);
        datas.add("" + i);
        datas.add("" + i);
        datas.add("" + i);
        datas.add("" + i);
        datas.add("" + i);

        mRootView.setAdapter(datas);
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
