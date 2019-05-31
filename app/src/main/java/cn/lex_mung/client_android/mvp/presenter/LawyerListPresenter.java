package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.mvp.model.entity.RequireEntity;
import cn.lex_mung.client_android.mvp.ui.activity.LawyerHomePageActivity;
import cn.lex_mung.client_android.mvp.ui.adapter.LawyerListAdapter;
import cn.lex_mung.client_android.mvp.ui.dialog.SingleTextDialog;
import cn.lex_mung.client_android.utils.BuryingPointHelp;
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
import me.zl.mvp.utils.DataHelper;
import me.zl.mvp.utils.LogUtils;
import me.zl.mvp.utils.RxLifecycleUtils;
import okhttp3.RequestBody;

import javax.inject.Inject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.contract.LawyerListContract;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.BusinessTypeEntity;
import cn.lex_mung.client_android.mvp.model.entity.LawyerEntity;
import cn.lex_mung.client_android.mvp.model.entity.LawyerListScreenEntity;
import cn.lex_mung.client_android.mvp.model.entity.RegionEntity;

import org.simple.eventbus.Subscriber;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO_1;
import static cn.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO_INSTITUTIONS;
import static cn.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO_LIST;
import static cn.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO_LIST_1;
import static cn.lex_mung.client_android.app.EventBusTags.LAWYER_LIST_SCREEN_INFO.LAWYER_LIST_SCREEN_INFO_TYPE;

@ActivityScope
public class LawyerListPresenter extends BasePresenter<LawyerListContract.Model, LawyerListContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private int pageNum = 1;
    private int totalNum;

    private int sort;
    private int fieldId;
    private int regionId1;
    private int regionId2;
    private String lawyerName;
    private int orgId;
    private int orgLevId;

    private List<BusinessTypeEntity> fieldList = new ArrayList<>();
    private List<RegionEntity> regionList = new ArrayList<>();
    private List<LawyerListScreenEntity> list = new ArrayList<>();
    private Map<String, Object> screenMap = new HashMap<>();

    private LawyerListAdapter adapter;
    private SmartRefreshLayout smartRefreshLayout;

    int requireTypeId;
    String requireTypeName;
    boolean isShow;


    @Inject
    public LawyerListPresenter(LawyerListContract.Model model, LawyerListContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 开始执行
     *
     * @param smartRefreshLayout SmartRefreshLayout
     */
    public void onCreate(SmartRefreshLayout smartRefreshLayout) {
        this.smartRefreshLayout = smartRefreshLayout;
        initAdapter();
        new Thread(this::initJsonData).start();
        getBusinessType();
        getConsultList(false, false);

        if (requireTypeId != -1) {
            isShow = true;
        }
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public void setOrgLevId(int orgLevId) {
        this.orgLevId = orgLevId;
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public List<BusinessTypeEntity> getFieldList() {
        return fieldList;
    }

    public List<RegionEntity> getRegionList() {
        return regionList;
    }

    public List<LawyerListScreenEntity> getList() {
        return list;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

    public int getFieldId() {
        return fieldId;
    }

    public void setRegionId(int regionId1, int regionId2) {
        this.regionId1 = regionId1;
        this.regionId2 = regionId2;
    }

    public int getRegionId1() {
        return regionId1;
    }

    public int getRegionId2() {
        return regionId2;
    }

    public void setLawyerName(String name) {
        lawyerName = name;
        pageNum = 1;
        getConsultList(false, true);
    }

    private void initAdapter() {
        adapter = new LawyerListAdapter(mImageLoader);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            if (isFastClick()) return;

            switch (requireTypeId) {
                case 2:
                    BuryingPointHelp.getInstance().onEvent(mRootView.getActivity(), "litigation_arbitration_detail","litigation_arbitration_lawyer_detail_click");
                    break;
                case 9:
                    BuryingPointHelp.getInstance().onEvent(mRootView.getActivity(), "meeting_detail","meeting_assistant_lawyer_detail_click");
                    break;
                case 6:
                    BuryingPointHelp.getInstance().onEvent(mRootView.getActivity(), "enterprise_detail","enterprise_detail_assistant_lawyer_detail_click");
                    break;
                default:
                    BuryingPointHelp.getInstance().onEvent(mRootView.getActivity(), "expert_consulation_detail","expert_consulation_lawyer_detail_click");
                    break;
            }
            LawyerEntity.LawyerBean.ListBean bean = adapter.getItem(position);
            if (bean == null) return;
            Bundle bundle = new Bundle();
            bundle.clear();
            bundle.putInt(BundleTags.ID, bean.getMemberId());
            mRootView.launchActivity(new Intent(mApplication, LawyerHomePageActivity.class), bundle);
        });
        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (pageNum < totalNum) {
                    pageNum = pageNum + 1;
                    getConsultList(true, false);
                } else {
                    smartRefreshLayout.finishLoadMoreWithNoMoreData();
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = 1;
                getConsultList(false, false);
            }
        });
        mRootView.initRecyclerView(adapter);
    }

    public void getConsultList(boolean isAdd, boolean isShowLoading) {
        Map<String, Object> map = new HashMap<>();
        map.put("sort", sort);
        map.put("regionId", regionId2);
        map.put("businessTypeId", fieldId);
        if (!TextUtils.isEmpty(lawyerName)) {
            map.put("lawyerName", lawyerName);
        }
        if (screenMap != null && screenMap.size() > 0) {
            map.putAll(screenMap);
        }
        if (orgId > 0 && orgLevId > 0) {
            map.put("orgId", orgId);
            map.put("orgLevId", orgLevId);
        }
        if (DataHelper.getBooleanSF(mApplication, DataHelperTags.IS_LOGIN_SUCCESS)) {
            mModel.getLawyerList1(pageNum, RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                    .subscribeOn(Schedulers.io())
                    .retryWhen(new RetryWithDelay(0, 0))
                    .doOnSubscribe(disposable -> {
                        if (isShowLoading) {
                            mRootView.showLoading("");
                        }
                    })
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally(() -> mRootView.hideLoading())
                    .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                    .subscribe(new ErrorHandleSubscriber<LawyerEntity>(mErrorHandler) {
                        @Override
                        public void onNext(LawyerEntity baseResponse) {
                            if (baseResponse.isSuccess()) {
                                totalNum = baseResponse.getData().getPages();
                                pageNum = baseResponse.getData().getPageNum();
                                if (isAdd) {
                                    adapter.addData(baseResponse.getData().getList());
                                    smartRefreshLayout.finishLoadMore();
                                } else {
                                    mRootView.setEmptyView(adapter);
                                    smartRefreshLayout.finishRefresh();
                                    adapter.setNewData(baseResponse.getData().getList());
                                    if (totalNum == pageNum) {
                                        smartRefreshLayout.finishLoadMoreWithNoMoreData();
                                    }
                                }
                                showDialog(baseResponse.getData().getTotal());
                            }
                        }
                    });
        } else {
            mModel.getLawyerList(pageNum, RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                    .subscribeOn(Schedulers.io())
                    .retryWhen(new RetryWithDelay(0, 0))
                    .doOnSubscribe(disposable -> {
                        if (isShowLoading) {
                            mRootView.showLoading("");
                        }
                    })
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally(() -> mRootView.hideLoading())
                    .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                    .subscribe(new ErrorHandleSubscriber<LawyerEntity>(mErrorHandler) {
                        @Override
                        public void onNext(LawyerEntity baseResponse) {
                            if (baseResponse.isSuccess()) {
                                totalNum = baseResponse.getData().getPages();
                                pageNum = baseResponse.getData().getPageNum();
                                if (isAdd) {
                                    adapter.addData(baseResponse.getData().getList());
                                    smartRefreshLayout.finishLoadMore();
                                } else {
                                    mRootView.setEmptyView(adapter);
                                    smartRefreshLayout.finishRefresh();
                                    adapter.setNewData(baseResponse.getData().getList());
                                    if (totalNum == pageNum) {
                                        smartRefreshLayout.finishLoadMoreWithNoMoreData();
                                    }
                                }

                                showDialog(baseResponse.getData().getTotal());
                            }
                        }
                    });
        }
    }

    private void showDialog(int num) {
        if (isShow) {
            new SingleTextDialog(mRootView.getActivity())
                    .setContent("平台已为您优选" + num + "名提供" + requireTypeName + "服务的律师,平台根据执业经验、社会资源、专业知识进行综合排序，您可以在筛选中对您的选择进行修改")
                    .setSubmitStr("我知道了！").show();
            isShow = false;
        }
    }

    private void getBusinessType() {
        mModel.getBusinessType()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<BusinessTypeEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<BusinessTypeEntity>> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            fieldList.add(new BusinessTypeEntity(0, "全部领域"));
                            fieldList.addAll(baseResponse.getData());
                        }
                    }
                });
    }

    /**
     * 更新信息
     *
     * @param message message
     */
    @Subscriber(tag = LAWYER_LIST_SCREEN_INFO_1)
    private void refresh(Message message) {
        switch (message.what) {
            case LAWYER_LIST_SCREEN_INFO_LIST:
                list.clear();
                screenMap.clear();
                list.addAll((Collection<? extends LawyerListScreenEntity>) message.obj);
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getId() > 0) {
                        if ("requireTypeId".equals(list.get(i).getPropKey())) {
                            if (i + 1 < list.size()) {
                                screenMap.put("require", new RequireEntity(list.get(i).getId(), list.get(i + 1).getMinPrice(), list.get(i + 1).getMaxPrice()));
                            } else {
                                screenMap.put("require", new RequireEntity(list.get(i).getId(), 0, 0));
                            }
                        } else {
                            screenMap.put(list.get(i).getPropKey(), list.get(i).getId());
                        }
                    }
                }
                pageNum = 1;
                getConsultList(false, false);
                break;
            case LAWYER_LIST_SCREEN_INFO_LIST_1:
                list.clear();
                list.addAll((Collection<? extends LawyerListScreenEntity>) message.obj);
                screenMap.clear();
                pageNum = 1;
                getConsultList(false, false);
                mRootView.setScreenColor(AppUtils.getColor(mApplication, R.color.c_b5b5b5));
                break;
            case LAWYER_LIST_SCREEN_INFO_TYPE:
            case LAWYER_LIST_SCREEN_INFO_INSTITUTIONS:
                mRootView.setScreenColor(AppUtils.getColor(mApplication, R.color.c_06a66a));
                break;
        }
    }

    /**
     * 从assert文件夹中获取json数据
     */
    private void initJsonData() {
        try {
            StringBuilder sb = new StringBuilder();
            InputStream is = mApplication.getAssets().open("city.json");//打开json数据
            byte[] by = new byte[is.available()];//转字节
            int len = -1;
            while ((len = is.read(by)) != -1) {
                sb.append(new String(by, 0, len, "utf-8"));//根据字节长度设置编码
            }
            is.close();//关闭流
            regionList.add(new RegionEntity(0, "全部地区"));
            regionList.addAll(new Gson().fromJson(sb.toString(), new TypeToken<List<RegionEntity>>() {
            }.getType()));
        } catch (Exception e) {
            LogUtils.debugInfo(e.getMessage());
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

    public void setRequireTypeId(int requireTypeId, String requireTypeName) {
        this.requireTypeId = requireTypeId;
        this.requireTypeName = requireTypeName;
        if (requireTypeId != -1) {
            mRootView.setScreenColor(AppUtils.getColor(mApplication, R.color.c_06a66a));
            list.clear();
            screenMap.clear();
            screenMap.put("require", new RequireEntity(requireTypeId, 0, 0));
            pageNum = 1;
        }
    }
}
