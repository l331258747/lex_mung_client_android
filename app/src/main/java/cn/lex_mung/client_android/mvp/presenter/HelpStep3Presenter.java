package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.HelpStep3Contract;
import cn.lex_mung.client_android.mvp.model.entity.help.PayMoneyEntity;
import cn.lex_mung.client_android.mvp.model.entity.help.RequirementInvolveAmountBean;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.zl.mvp.di.scope.FragmentScope;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.mvp.BasePresenter;


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

    private List<RequirementInvolveAmountBean> moneyList = new ArrayList<>();
    private List<String> moneyStrList = new ArrayList<>();
    private String money;
    private int amountId;

    public int getAmountId() {
        return amountId;
    }

    public void setAmountId(int amountId) {
        this.amountId = amountId;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public List<RequirementInvolveAmountBean> getMoneyList() {
        return moneyList;
    }

    public List<String> getMoneyStrList() {
        return moneyStrList;
    }



    public void onCreate(List<RequirementInvolveAmountBean> datas){
        for (RequirementInvolveAmountBean item : datas){
            moneyStrList.add(item.getAmountName());
        }
        moneyList = datas;
    }

    private List<PayMoneyEntity> payLawyerMoneyList = new ArrayList<>();
    private List<String> payLawyerMoneyStrList = new ArrayList<>();
    private String payLawyerMoney;
    private int payLawyerMoneyId;

    public int getPayLawyerMoneyId() {
        return payLawyerMoneyId;
    }

    public void setPayLawyerMoneyId(int payLawyerMoneyId) {
        this.payLawyerMoneyId = payLawyerMoneyId;
    }

    public String getPayLawyerMoney() {
        return payLawyerMoney;
    }

    public void setPayLawyerMoney(String payLawyerMoney) {
        this.payLawyerMoney = payLawyerMoney;
    }

    public List<PayMoneyEntity> getPayLawyerMoneyList() {
        return payLawyerMoneyList;
    }

    public List<String> getPayLawyerMoneyStrList() {
        return payLawyerMoneyStrList;
    }


    public void onCreate2(List<PayMoneyEntity> datas){
        for (PayMoneyEntity item : datas){
            payLawyerMoneyStrList.add(item.getText());
        }
        payLawyerMoneyList = datas;
    }

    @Inject
    public HelpStep3Presenter(HelpStep3Contract.Model model, HelpStep3Contract.View rootView) {
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
