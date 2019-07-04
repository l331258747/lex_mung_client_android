package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private List<SolutionTypeBean> list;
    private List<String> allProv = new ArrayList<>();//所有的省
    private Map<String, List<String>> cityMap = new HashMap<>();//key:省p---value:市n  value是一个集合
    private String province = "";
    private String city = "";
    private String region = "";
    private int typeId;

    @Inject
    public HelpStep2Presenter(HelpStep2Contract.Model model, HelpStep2Contract.View rootView) {
        super(model, rootView);
    }

    public List<SolutionTypeBean> getList() {
        return list;
    }

    public List<String> getAllProv() {
        return allProv;
    }

    public Map<String, List<String>> getCityMap() {
        return cityMap;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getRegion() {
        return region = province + "-" + city;
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
        list = datas;
        for (SolutionTypeBean entity : list == null ? new ArrayList<SolutionTypeBean>() : list) {
            allProv.add(entity.getSolutionTypeName());//封装所有的省
            //所有的市
            List<String> allCity = new ArrayList<>();//所有市的长度
            for (SolutionTypeChildBean entity1 : entity.getChild() == null ? new ArrayList<SolutionTypeChildBean>() : entity.getChild()) {
                allCity.add(entity1.getSolutionTypeName());//封装市集合
            }
            cityMap.put(entity.getSolutionTypeName(), allCity);//某个省取出所有的市,
        }
    }
}
