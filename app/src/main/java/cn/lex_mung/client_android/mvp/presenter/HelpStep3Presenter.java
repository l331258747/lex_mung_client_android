package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import cn.lex_mung.client_android.mvp.contract.HelpStep3Contract;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.di.scope.FragmentScope;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;


@FragmentScope
public class HelpStep3Presenter extends BasePresenter<HelpStep3Contract.Model, HelpStep3Contract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private List<String> moneyList = new ArrayList<>();
    private String money;

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public List<String> getMoneyList() {
        return moneyList;
    }

    @Inject
    public HelpStep3Presenter(HelpStep3Contract.Model model, HelpStep3Contract.View rootView) {
        super(model, rootView);
    }

    public void onCreate(){
        moneyList.add("1万");
        moneyList.add("2万");
        moneyList.add("3万");
        moneyList.add("4万");
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
