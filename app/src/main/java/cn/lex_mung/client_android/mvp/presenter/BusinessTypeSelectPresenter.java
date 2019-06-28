package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import cn.lex_mung.client_android.mvp.model.entity.help.SolutionTypeBean;
import cn.lex_mung.client_android.mvp.model.entity.help.SolutionTypeChildBean;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.di.scope.ActivityScope;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.BusinessTypeSelectContract;


@ActivityScope
public class BusinessTypeSelectPresenter extends BasePresenter<BusinessTypeSelectContract.Model, BusinessTypeSelectContract.View> {
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
    public BusinessTypeSelectPresenter(BusinessTypeSelectContract.Model model, BusinessTypeSelectContract.View rootView) {
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


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
