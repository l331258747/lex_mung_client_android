package com.lex_mung.client_android.mvp.contract;

import com.lex_mung.client_android.mvp.model.entity.EducationBackgroundEntity;
import com.lex_mung.client_android.mvp.model.entity.JobSkillsEntity;
import com.lex_mung.client_android.mvp.model.entity.OrgTagsEntity;
import com.lex_mung.client_android.mvp.model.entity.WorkexpEntity;
import com.lex_mung.client_android.mvp.ui.widget.RadarView;

import java.util.List;

import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;


public interface LawsBusinessCardContract {
    interface View extends IView {

        void setOrgAdapter(List<OrgTagsEntity> depositTags);

        void hideOrgLayout();

        void setIndustryAdapter(String industry);

        void setLanguage(String skillName, StringBuilder language);

        void setOther(String skillName, StringBuilder other);

        void setQualification(StringBuilder qualification);

        void setSkillAdapter(String skillName, List<JobSkillsEntity> children);

        void setFieldData(List<RadarView.RadarData> list, List<RadarView.RadarData> meanList);

        void setDescription(String memberDescription);

        void setWorkAdapter(List<WorkexpEntity> workExp);

        void setEducationAdapter(List<EducationBackgroundEntity> education);

        void hideFieldLayout();

        void hideWorkLayout();

        void hideEducationLayout();

        void hideSkillLayout();

        void hideOtherLayout();

        void hideLanguageLayout();

        void hideIndustryLayout();

        void hideQualificationLayout();

        void hideDescriptionLayout();

        void showNoDataLayout();

        void showNoDataLayout1();
    }

    interface Model extends IModel {

    }
}
