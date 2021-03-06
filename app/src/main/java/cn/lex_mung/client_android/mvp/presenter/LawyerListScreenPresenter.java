package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;

import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.mvp.model.entity.help.SolutionTypeBean;
import cn.lex_mung.client_android.mvp.model.entity.help.SolutionTypeChildBean;
import cn.lex_mung.client_android.mvp.ui.activity.BusinessTypeSelectActivity;
import cn.lex_mung.client_android.mvp.ui.activity.SelectListItemActivity;
import cn.lex_mung.client_android.mvp.ui.activity.SelectResortInstitutionActivity;
import cn.lex_mung.client_android.mvp.ui.adapter.LawyerListScreenAdapter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.di.scope.ActivityScope;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.RxLifecycleUtils;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.LawyerListScreenContract;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.InstitutionListEntity;
import cn.lex_mung.client_android.mvp.model.entity.LawyerListScreenEntity;

import org.simple.eventbus.Subscriber;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static cn.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO;
import static cn.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO_1;
import static cn.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO_BUSINESS;
import static cn.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO_INSTITUTIONS;
import static cn.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO_LIST;
import static cn.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO_LIST_1;
import static cn.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO_TYPE;

@ActivityScope
public class LawyerListScreenPresenter extends BasePresenter<LawyerListScreenContract.Model, LawyerListScreenContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private LawyerListScreenAdapter adapter;
    private List<LawyerListScreenEntity> list = new ArrayList<>();

    private int pos;
    private boolean flag;
    private int regionId1;
    private int regionId2;
    private int requireTypeId;

    private String businessName;
    private int businessId;

    @Inject
    public LawyerListScreenPresenter(LawyerListScreenContract.Model model, LawyerListScreenContract.View rootView) {
        super(model, rootView);
    }

    public void setLawyerListScreenEntityList(List<LawyerListScreenEntity> list) {
        initAdapter();
        this.list.addAll(list);
        if (list.size() <= 0) {
            getPeerSearchList();
        } else {
            adapter.setNewData(list);
            setBusinessLayout();
        }
    }

    public void setBusinessLayout(){
        List<LawyerListScreenEntity> list = adapter.getData();
        for (int i=0;i<list.size();i++){
            LawyerListScreenEntity entity = list.get(i);
            if(entity.getPropKey().equals("businessTypeId")){
                if(businessId <=0){
                    entity.setContent("");
                    entity.setId(0);
                    adapter.notifyItemChanged(i, entity);
                    return;
                }
                entity.setContent(businessName);
                entity.setId(businessId);
                adapter.notifyItemChanged(i, entity);
                return;
            }
        }
    }

    public void setBusinessName(String businessName) {
        if(businessId <=0){
            this.businessName = "";
            return;
        }
        this.businessName = businessName;
    }

    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void setRegionId1(int regionId1) {
        this.regionId1 = regionId1;
    }

    public void setRegionId2(int regionId2) {
        this.regionId2 = regionId2;
    }

    public void setRequireTypeId(int requireTypeId) {
        this.requireTypeId = requireTypeId;
    }

    public boolean isFlag() {
        return flag;
    }

    private void initAdapter() {
        adapter = new LawyerListScreenAdapter(flag);
        adapter.setActivity(mRootView.getActivity());
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            if (isFastClick()) return;
            LawyerListScreenEntity entity = adapter.getItem(position);
            if (entity == null) {
                return;
            }
            Bundle bundle = new Bundle();
            if("businessTypeId".equals(entity.getPropKey())){
                pos = position;
                bundle.clear();
                bundle.putSerializable(BundleTags.LIST, (Serializable) entity.getItems());
                bundle.putBoolean(BundleTags.FLAG, isFlag());
                bundle.putInt(BundleTags.ID, entity.getId());
                mRootView.launchActivity(new Intent(mApplication, BusinessTypeSelectActivity.class), bundle);
                return;
            }

            if ("courtId".equals(entity.getPropKey())
                    || "procuratorateId".equals(entity.getPropKey())) {
                pos = position;
                bundle.clear();
                bundle.putString(BundleTags.TYPE, "courtId".equals(entity.getPropKey()) ? "court" : "procuratorate");
                bundle.putInt(BundleTags.REGION_ID_1, regionId1);
                bundle.putInt(BundleTags.REGION_ID_2, regionId2);
                bundle.putInt(BundleTags.ID, entity.getId());
                bundle.putBoolean(BundleTags.FLAG, isFlag());
                mRootView.launchActivity(new Intent(mApplication, SelectResortInstitutionActivity.class), bundle);
                return;
            }
            if (entity.getIsTile() == 0) {
                pos = position;
                bundle.clear();
                bundle.putSerializable(BundleTags.LIST, (Serializable) entity.getItems());
                bundle.putInt(BundleTags.TYPE, 5);
                bundle.putString(BundleTags.TITLE, entity.getText());
                bundle.putInt(BundleTags.ID, entity.getId());
                bundle.putBoolean(BundleTags.FLAG, isFlag());
                mRootView.launchActivity(new Intent(mApplication, SelectListItemActivity.class), bundle);
            }
        });
        mRootView.initRecyclerView(adapter);
    }

    public void reset() {
        for (LawyerListScreenEntity entity : list) {
            entity.setContent("");
            entity.setId(0);
            entity.setPos(0);
        }
        adapter.setNewData(list);
        if (isFlag()) {
            AppUtils.post(LAWYER_LIST_SCREEN_INFO_1, LAWYER_LIST_SCREEN_INFO_LIST_1, list);
        } else {
            AppUtils.post(LAWYER_LIST_SCREEN_INFO, LAWYER_LIST_SCREEN_INFO_LIST_1, list);
        }
    }

    public void confirm() {
        if (isFlag()) {
            AppUtils.post(LAWYER_LIST_SCREEN_INFO_1, LAWYER_LIST_SCREEN_INFO_LIST, list);
        } else {
            AppUtils.post(LAWYER_LIST_SCREEN_INFO, LAWYER_LIST_SCREEN_INFO_LIST, list);
        }
        mRootView.killMyself();
    }

    private void getPeerSearchList() {
        mModel.getPeerSearchList()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<LawyerListScreenEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<LawyerListScreenEntity>> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            list.addAll(baseResponse.getData());
                            setSelectRequireTypeId(list);
                            adapter.setNewData(list);
                            setBusinessLayout();
                        }
                    }
                });
    }

    public void setSelectRequireTypeId(List<LawyerListScreenEntity> list) {
        if (requireTypeId == -1 || list == null || list.size() == 0)
            return;
        for (int i = 0; i < list.size(); i++) {
            LawyerListScreenEntity item = list.get(i);
            if (TextUtils.equals(item.getPropKey(), "requireTypeId")) {
                List<SolutionTypeBean> item2 = item.getItems();
                for (int j = 0; j < item2.size(); j++) {
                    if (item2.get(j).getId() == requireTypeId) {
                        item.setPos(j);
                        item.setId(requireTypeId);
                        item.setContent(item2.get(j).getText());
                    }
                }
            }
        }
    }

    /**
     * 更新信息
     *
     * @param message message
     */
    @Subscriber(tag = LAWYER_LIST_SCREEN_INFO)
    private void refresh(Message message) {
        LawyerListScreenEntity entity;
        switch (message.what) {
            case LAWYER_LIST_SCREEN_INFO_TYPE:
                SolutionTypeBean bean = (SolutionTypeBean) message.obj;
                entity = list.get(pos);
                entity.setContent(bean.getText());
                entity.setId(bean.getId());
                adapter.notifyItemChanged(pos, entity);
                break;
            case LAWYER_LIST_SCREEN_INFO_INSTITUTIONS:
                InstitutionListEntity institutionListEntity = (InstitutionListEntity) message.obj;
                entity = list.get(pos);
                entity.setContent(institutionListEntity.getInstitutionName());
                entity.setId(institutionListEntity.getInstitutionId());
                adapter.notifyItemChanged(pos, entity);
                break;
            case LAWYER_LIST_SCREEN_INFO_BUSINESS:
                SolutionTypeChildBean bean1 = (SolutionTypeChildBean) message.obj;
                if(bean1 == null) return;
                entity = list.get(pos);
                entity.setContent(bean1.getSolutionTypeName());
                entity.setId(bean1.getSolutionTypeId());
                adapter.notifyItemChanged(pos, entity);
                break;
        }
    }

    /**
     * 更新信息
     *
     * @param message message
     */
    @Subscriber(tag = LAWYER_LIST_SCREEN_INFO_1)
    private void refresh_1(Message message) {
        LawyerListScreenEntity entity;
        switch (message.what) {
            case LAWYER_LIST_SCREEN_INFO_TYPE:
                SolutionTypeBean bean = (SolutionTypeBean) message.obj;
                entity = list.get(pos);
                entity.setContent(bean.getText());
                entity.setId(bean.getId());
                adapter.notifyItemChanged(pos, entity);
                break;
            case LAWYER_LIST_SCREEN_INFO_INSTITUTIONS:
                InstitutionListEntity institutionListEntity = (InstitutionListEntity) message.obj;
                entity = list.get(pos);
                entity.setContent(institutionListEntity.getInstitutionName());
                entity.setId(institutionListEntity.getInstitutionId());
                adapter.notifyItemChanged(pos, entity);
                break;
            case LAWYER_LIST_SCREEN_INFO_BUSINESS:
                SolutionTypeChildBean bean1 = (SolutionTypeChildBean) message.obj;
                if(bean1 == null) return;
                entity = list.get(pos);
                entity.setContent(bean1.getSolutionTypeName());
                entity.setId(bean1.getSolutionTypeId());
                adapter.notifyItemChanged(pos, entity);
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
}
