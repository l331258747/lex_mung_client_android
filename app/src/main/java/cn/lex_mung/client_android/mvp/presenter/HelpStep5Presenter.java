package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import cn.lex_mung.client_android.mvp.model.entity.help.IndustryEntity;
import cn.lex_mung.client_android.mvp.model.entity.help.RequirementInvolveAmountBean;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.di.scope.FragmentScope;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.HelpStep5Contract;


@FragmentScope
public class HelpStep5Presenter extends BasePresenter<HelpStep5Contract.Model, HelpStep5Contract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;


    private List<IndustryEntity> industryList = new ArrayList<>();
    private List<String> industryStrList = new ArrayList<>();
    private String industry;
    private int industryId;

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public int getIndustryId() {
        return industryId;
    }

    public void setIndustryId(int industryId) {
        this.industryId = industryId;
    }

    public List<IndustryEntity> getMoneyList() {
        return industryList;
    }

    public List<String> getMoneyStrList() {
        return industryStrList;
    }

    @Inject
    public HelpStep5Presenter(HelpStep5Contract.Model model, HelpStep5Contract.View rootView) {
        super(model, rootView);
    }


    public void onCreate(List<IndustryEntity> datas){
        for (IndustryEntity item : datas){
            industryStrList.add(item.getText());
        }
        industryList = datas;
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
