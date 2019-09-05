package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.HelpStep6Contract;
import cn.lex_mung.client_android.mvp.model.entity.help.IndustryEntity;
import cn.lex_mung.client_android.mvp.model.entity.help.PayMoneyEntity;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.zl.mvp.di.scope.FragmentScope;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.mvp.BasePresenter;


@FragmentScope
public class HelpStep6Presenter extends BasePresenter<HelpStep6Contract.Model, HelpStep6Contract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;


    private List<PayMoneyEntity> payMoneyList = new ArrayList<>();
    private List<String> payMoneyStrList = new ArrayList<>();
    private String payMoney;
    private int payMoneyId;

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }

    public int getPayMoneyId() {
        return payMoneyId;
    }

    public void setPayMoneyId(int payMoneyId) {
        this.payMoneyId = payMoneyId;
    }

    public List<PayMoneyEntity> getMoneyList() {
        return payMoneyList;
    }

    public List<String> getMoneyStrList() {
        return payMoneyStrList;
    }


    public void onCreate(List<PayMoneyEntity> datas){
        for (PayMoneyEntity item : datas){
            payMoneyStrList.add(item.getText());
        }
        payMoneyList = datas;
    }

    @Inject
    public HelpStep6Presenter(HelpStep6Contract.Model model, HelpStep6Contract.View rootView) {
        super(model, rootView);
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
