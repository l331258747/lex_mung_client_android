package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.HelpStep2Contract;
import cn.lex_mung.client_android.mvp.model.entity.help.SolutionTypeBean;
import cn.lex_mung.client_android.mvp.model.entity.help.SolutionTypeChildBean;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.zl.mvp.di.scope.FragmentScope;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.mvp.BasePresenter;


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

    List<SolutionTypeChildBean> datas1, datas2, datas3;

    @Inject
    public HelpStep2Presenter(HelpStep2Contract.Model model, HelpStep2Contract.View rootView) {
        super(model, rootView);
    }

    public void getTabPosition(int i) {
        switch (i) {
            case 0:
                mRootView.setAdapter(datas1);
                break;
            case 1:
                mRootView.setAdapter(datas2);
                break;
            case 2:
                mRootView.setAdapter(datas3);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void setList(List<SolutionTypeBean> datas) {
        datas1 = new ArrayList<>();
        datas3 = new ArrayList<>();
        datas2 = new ArrayList<>();
        for (SolutionTypeBean item : datas) {

            if (item.getChild() == null || item.getChild().size() == 0) {
                continue;
            }

            if (TextUtils.equals(item.getSolutionTypeName(), "个人类")) {
                datas1 = item.getChild();
            } else if (TextUtils.equals(item.getSolutionTypeName(), "商事类")) {
                datas2 = item.getChild();
            } else if (TextUtils.equals(item.getSolutionTypeName(), "刑事类")) {
                datas3 = item.getChild();
            }
        }
    }
}
