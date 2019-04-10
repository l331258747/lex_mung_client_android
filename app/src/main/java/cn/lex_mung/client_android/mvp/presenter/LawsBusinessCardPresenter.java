package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;

import cn.lex_mung.client_android.mvp.model.entity.OrgTagsEntity;
import cn.lex_mung.client_android.mvp.ui.adapter.PersonalHomePageEducationAdapter;
import cn.lex_mung.client_android.mvp.ui.adapter.PersonalHomePageWorkAdapter;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.di.scope.FragmentScope;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.LawsBusinessCardContract;
import cn.lex_mung.client_android.mvp.model.entity.LawsHomePagerBaseEntity;

@FragmentScope
public class LawsBusinessCardPresenter extends BasePresenter<LawsBusinessCardContract.Model, LawsBusinessCardContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private PersonalHomePageWorkAdapter workAdapter;
    private PersonalHomePageEducationAdapter educationAdapter;

    @Inject
    public LawsBusinessCardPresenter(LawsBusinessCardContract.Model model, LawsBusinessCardContract.View rootView) {
        super(model, rootView);
    }

    private void initAdapter() {
        workAdapter = new PersonalHomePageWorkAdapter();
        educationAdapter = new PersonalHomePageEducationAdapter();
        mRootView.initRecyclerView(workAdapter, educationAdapter);
    }

    public void setEntity(LawsHomePagerBaseEntity entity) {
        initAdapter();
        try {
            if (entity == null
                    || entity.getBaseInfo() == null) return;
            int num = 0;
            if (!TextUtils.isEmpty(entity.getBaseInfo().getMemberDescription())) {
                mRootView.setDescription(entity.getBaseInfo().getMemberDescription());
            } else {
                num++;
                mRootView.hideDescriptionLayout();
            }
            if (entity.getBaseInfo().getHonor() != null
                    && entity.getBaseInfo().getHonor().size() > 0) {
                StringBuilder text = new StringBuilder();
                for (String s : entity.getBaseInfo().getHonor()) {
                    text.append(s).append("\n");
                }
                text.delete(text.length() - 1, text.length());
                mRootView.setPersonalHonor(text.toString());
            } else {
                num++;
                mRootView.hidePersonalHonorLayout();
            }
            if (entity.getBaseInfo().getOrgTags() != null
                    && entity.getBaseInfo().getOrgTags().size() > 0) {
                StringBuilder text = new StringBuilder();
                for (OrgTagsEntity orgTagsEntity : entity.getBaseInfo().getOrgTags()) {
                    text.append(orgTagsEntity.getTagName()).append("\n");
                }
                text.delete(text.length() - 1, text.length());
                mRootView.setJoinLawyerTeam(text.toString());
            } else {
                num++;
                mRootView.hideJoinLawyerTeamLayout();
            }
            if (entity.getBaseInfo().getWorkExp() != null
                    && entity.getBaseInfo().getWorkExp().size() > 0) {
                workAdapter.setNewData(entity.getBaseInfo().getWorkExp());
            } else {
                num++;
                mRootView.hideWorkLayout();
            }
            if (entity.getBaseInfo().getEducation() != null
                    && entity.getBaseInfo().getEducation().size() > 0) {
                educationAdapter.setNewData(entity.getBaseInfo().getEducation());
            } else {
                num++;
                mRootView.hideEducationLayout();
            }
            if (num == 5) {
                mRootView.showNoDataLayout();
            }
        } catch (Exception ignored) {
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
