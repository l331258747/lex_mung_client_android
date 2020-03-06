package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.PracticeExperienceContract;
import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.CaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.LawsHomePagerBaseEntity;
import cn.lex_mung.client_android.mvp.model.entity.QualificationEntity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import me.zl.mvp.di.scope.FragmentScope;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.utils.RxLifecycleUtils;
import okhttp3.RequestBody;


@FragmentScope
public class PracticeExperiencePresenter extends BasePresenter<PracticeExperienceContract.Model, PracticeExperienceContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private LawsHomePagerBaseEntity entity;

    private int pageNum = 1;
    private int totalNum;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getTotalNum() {
        return totalNum;
    }

    @Inject
    public PracticeExperiencePresenter(PracticeExperienceContract.Model model, PracticeExperienceContract.View rootView) {
        super(model, rootView);
    }

    public void setEntity(LawsHomePagerBaseEntity entity) {
        if (entity == null
                || entity.getPracticeInfo() == null) return;
        this.entity = entity;
        getCaseList(false);
        try {
            int num = 0;
            int num1 = 0;
            if (entity.getPracticeInfo().getServed() != null
                    && entity.getPracticeInfo().getServed().size() > 0) {
                StringBuilder text = new StringBuilder();
                for (String s : entity.getPracticeInfo().getServed()) {
                    text.append(s).append("\n");
                }
                text.delete(text.length() - 1, text.length());
                mRootView.setServiceFirm(text.toString());
            } else {
                num++;
                mRootView.hideServiceFirmLayout();
            }
            if (entity.getPracticeInfo().getBaseSkill() != null
                    && entity.getPracticeInfo().getBaseSkill().size() > 0) {
                StringBuilder text = new StringBuilder();
                for (String s : entity.getPracticeInfo().getBaseSkill()) {
                    text.append(s).append("  ·  ");
                }
                text.delete(text.length() - 3, text.length());
                mRootView.setProfessionalSkills(text.toString());
            } else {
                num++;
                num1++;
                mRootView.hideProfessionalSkillsLayout();
            }
            if (entity.getPracticeInfo().getIndustry() != null
                    && entity.getPracticeInfo().getIndustry().size() > 0) {
                StringBuilder text = new StringBuilder();
                for (String s : entity.getPracticeInfo().getIndustry()) {
                    text.append(s).append("  ·  ");
                }
                text.delete(text.length() - 3, text.length());
                mRootView.setIndustry(text.toString());
            } else {
                num++;
                num1++;
                mRootView.hideIndustryLayout();
            }
            if (entity.getPracticeInfo().getLangSkill() != null
                    && entity.getPracticeInfo().getLangSkill().size() > 0) {
                StringBuilder text = new StringBuilder();
                for (String s : entity.getPracticeInfo().getLangSkill()) {
                    text.append(s).append("  ·  ");
                }
                text.delete(text.length() - 3, text.length());
                mRootView.setLanguage(text.toString());
            } else {
                num++;
                num1++;
                mRootView.hideLanguageLayout();
            }

            if (entity.getQualifications() != null
                    && entity.getQualifications().size() > 0) {
                StringBuilder text = new StringBuilder();
                for (QualificationEntity s : entity.getQualifications()) {
                    text.append(s.getTypeName()).append("  ·  ");
                }
                text.delete(text.length() - 3, text.length());
                mRootView.setCertificate(text.toString());
            } else {
                num++;
                num1++;
                mRootView.hideCertificateLayout();
            }

            if (entity.getPracticeInfo().getOtherSkill() != null
                    && entity.getPracticeInfo().getOtherSkill().size() > 0) {
                StringBuilder text = new StringBuilder();
                for (String s : entity.getPracticeInfo().getOtherSkill()) {
                    text.append(s).append("  ·  ");
                }
                text.delete(text.length() - 3, text.length());
                mRootView.setOther(text.toString());
            } else {
                num++;
                num1++;
                mRootView.hideOtherLayout();
            }
            if (entity.getPracticeInfo().getCourt() != null
                    && entity.getPracticeInfo().getCourt().size() > 0) {
                StringBuilder text = new StringBuilder();
                for (String s : entity.getPracticeInfo().getCourt()) {
                    text.append(s).append("\n");
                }
                text.delete(text.length() - 1, text.length());
                mRootView.setCourt(text.toString());
            } else {
                num++;
                mRootView.hideCourtLayout();
            }
            if (entity.getPracticeInfo().getProcuratorate() != null
                    && entity.getPracticeInfo().getProcuratorate().size() > 0) {
                StringBuilder text = new StringBuilder();
                for (String s : entity.getPracticeInfo().getProcuratorate()) {
                    text.append(s).append("\n");
                }
                text.delete(text.length() - 1, text.length());
                mRootView.setP(text.toString());
            } else {
                num++;
                mRootView.hidePLayout();
            }
            if (num1 == 5) {
                mRootView.showNoDataLayout1();
            }
            if (num == 8) {
                mRootView.showNoDataLayout();
            }
        } catch (Exception ignored) {
        }
    }


    public void getCaseList(boolean isAdd) {
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", pageNum);
        map.put("memberId", entity.getMemberId());
        map.put("lawyerFirm", entity.getInstitutionName());
        map.put("pageSize", 10);
        mModel.getCaseList(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<BaseListEntity<CaseListEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<BaseListEntity<CaseListEntity>> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            totalNum = baseResponse.getData().getPages();
                            pageNum = baseResponse.getData().getPageNum();
                            mRootView.setAdapter(isAdd, baseResponse.getData().getList());
                        }
                    }
                });
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
