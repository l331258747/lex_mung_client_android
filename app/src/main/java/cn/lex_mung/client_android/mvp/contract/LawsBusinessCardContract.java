package cn.lex_mung.client_android.mvp.contract;

import java.util.List;

import cn.lex_mung.client_android.mvp.model.entity.OrgTagsEntity;
import cn.lex_mung.client_android.mvp.ui.adapter.PersonalHomePageEducationAdapter;
import cn.lex_mung.client_android.mvp.ui.adapter.PersonalHomePageWorkAdapter;

import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;

public interface LawsBusinessCardContract {
    interface View extends IView {
        void initRecyclerView(PersonalHomePageWorkAdapter workAdapter, PersonalHomePageEducationAdapter educationAdapter);

        void setDescription(String memberDescription);

        void hideDescriptionLayout();

        void setPersonalHonor(String toString);

        void hidePersonalHonorLayout();

//        void setJoinLawyerTeam(String toString);

        void setJoinLawyerTeam(List<OrgTagsEntity> orgTagsEntities);

        void hideJoinLawyerTeamLayout();

        void hideEducationLayout();

        void hideWorkLayout();

        void showNoDataLayout();
    }

    interface Model extends IModel {

    }
}
