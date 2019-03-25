package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;

import me.zl.mvp.integration.AppManager;
import me.zl.mvp.di.scope.FragmentScope;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.LawsBusinessCardContract;
import cn.lex_mung.client_android.mvp.model.entity.JobSkillsEntity;
import cn.lex_mung.client_android.mvp.model.entity.LawsHomePagerBaseEntity;
import cn.lex_mung.client_android.mvp.ui.widget.RadarView;

import java.util.ArrayList;
import java.util.List;

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

    @Inject
    public LawsBusinessCardPresenter(LawsBusinessCardContract.Model model, LawsBusinessCardContract.View rootView) {
        super(model, rootView);
    }

    public void setEntity(LawsHomePagerBaseEntity entity) {
        try {
            if (entity == null
                    || entity.getBaseInfo() == null) return;
            int num = 0;
            int num1 = 0;
            if (entity.getBaseInfo().getBusinessRadar() != null
                    && entity.getBaseInfo().getBusinessRadar().size() > 0) {
                List<RadarView.RadarData> list = new ArrayList<>();
                List<RadarView.RadarData> meanList = new ArrayList<>();
                for (LawsHomePagerBaseEntity.BaseInfoBean.BusinessRadarBean bean : entity.getBaseInfo().getBusinessRadar()) {
                    list.add(new RadarView.RadarData(bean.getBusinessTypeName(), bean.getScore() * 100));
                    meanList.add(new RadarView.RadarData("", bean.getAvgScore() * 100));
                }
                mRootView.setFieldData(list, meanList);
            } else {
                num++;
                mRootView.hideFieldLayout();
            }
            if (entity.getBaseInfo().getSkill() != null
                    && entity.getBaseInfo().getSkill().size() > 2) {
                if (entity.getBaseInfo().getSkill().get(0).getChildren() != null
                        && entity.getBaseInfo().getSkill().get(0).getChildren().size() > 0) {
                    mRootView.setSkillAdapter(entity.getBaseInfo().getSkill().get(0).getSkillName(), entity.getBaseInfo().getSkill().get(0).getChildren());
                } else {
                    num++;
                    num1++;
                    mRootView.hideSkillLayout();
                }
                if (entity.getBaseInfo().getSkill().get(1).getChildren() != null
                        && entity.getBaseInfo().getSkill().get(1).getChildren().size() > 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (JobSkillsEntity jobSkillsEntity : entity.getBaseInfo().getSkill().get(1).getChildren()) {
                        stringBuilder.append(jobSkillsEntity.getSkillName()).append(" ");
                    }
                    mRootView.setOther(entity.getBaseInfo().getSkill().get(1).getSkillName(), stringBuilder);
                } else {
                    num++;
                    num1++;
                    mRootView.hideOtherLayout();
                }
                if (entity.getBaseInfo().getSkill().get(2).getChildren() != null
                        && entity.getBaseInfo().getSkill().get(2).getChildren().size() > 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (JobSkillsEntity jobSkillsEntity : entity.getBaseInfo().getSkill().get(2).getChildren()) {
                        stringBuilder.append(jobSkillsEntity.getSkillName()).append(" ");
                    }
                    mRootView.setLanguage(entity.getBaseInfo().getSkill().get(2).getSkillName(), stringBuilder);
                } else {
                    num++;
                    num1++;
                    mRootView.hideLanguageLayout();
                }
            }

            if (entity.getBaseInfo().getIndustry() != null
                    && entity.getBaseInfo().getIndustry().size() > 0) {
                StringBuilder content = new StringBuilder();
                for (String s : entity.getBaseInfo().getIndustry()) {
                    content.append(s).append("，");
                }
                if (content.length() > 0) {
                    content.delete(content.length() - 1, content.length());
                }
                mRootView.setIndustryAdapter(content.toString());
            } else {
                num++;
                num1++;
                mRootView.hideIndustryLayout();
            }

            if (entity.getBaseInfo().getQualifi() != null
                    && entity.getBaseInfo().getQualifi().size() > 0) {
                StringBuilder qualification = new StringBuilder();
                for (String s : entity.getBaseInfo().getQualifi()) {
                    qualification.append(s).append(" ");
                }
                mRootView.setQualification(qualification);
            } else {
                num++;
                num1++;
                mRootView.hideQualificationLayout();
            }
            if (!TextUtils.isEmpty(entity.getBaseInfo().getMemberDescription())) {
                mRootView.setDescription(entity.getBaseInfo().getMemberDescription());
            } else {
                num++;
                mRootView.hideDescriptionLayout();
            }
            if (entity.getReliabilityInfo() == null) return;
            if (entity.getReliabilityInfo().getWorkExp() != null
                    && entity.getReliabilityInfo().getWorkExp().size() > 0) {
                mRootView.setWorkAdapter(entity.getReliabilityInfo().getWorkExp());
            } else {
                num++;
                mRootView.hideWorkLayout();
            }
            if (entity.getReliabilityInfo().getEducation() != null
                    && entity.getReliabilityInfo().getEducation().size() > 0) {
                mRootView.setEducationAdapter(entity.getReliabilityInfo().getEducation());
            } else {
                num++;
                mRootView.hideEducationLayout();
            }
            if (entity.getReliabilityInfo().getDepositTags() != null
                    && entity.getReliabilityInfo().getDepositTags().size() > 0) {
                mRootView.setOrgAdapter(entity.getReliabilityInfo().getDepositTags());
            } else {
                num++;
                mRootView.hideOrgLayout();
            }
            if (num1 == 5) {
                mRootView.showNoDataLayout1();
            }
            if (num == 10) {
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
