package com.lex_mung.client_android.mvp.contract;

import com.lex_mung.client_android.mvp.model.entity.BaseResponse;
import com.lex_mung.client_android.mvp.model.entity.LawsHomePagerBaseEntity;
import com.lex_mung.client_android.mvp.model.entity.LawyerEvaluationEntity;
import com.lex_mung.client_android.mvp.model.entity.LawyerTagsEntity;

import java.util.List;

import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;


public interface LawyerHomePageContract {
    interface View extends IView {

        void setName(String memberName);

        void setAvatar(String iconImage);

        void setAvatar(int icon);

        void setTopBg(String backgroundImage);

        void setTopBg(int icon);

        void setRegionAndInstitutionName(String text);

        void initViewPager(LawsHomePagerBaseEntity entity);

        void setField(String field);

        void hideFieldLayout();

        void setTagsAdapter(List<LawyerTagsEntity> lawyerTags);

        void hideTagsAdapter();

        void setAge(String age);

        void hideAgeLayout();

        void setSex(int bg, int color, int icon);

        void hideLikeLayout();

        void setLikeLayout(int icon, int color, String string);

        void setPositionNameAndPractice(String format);

        void hideSexIcon();

        void showSexIcon();
    }

    interface Model extends IModel {
        Observable<BaseResponse<LawsHomePagerBaseEntity>> getLawsHomePagerBase(int id);

        Observable<BaseResponse> follow(int id);

        Observable<BaseResponse> unFollow(int id);
    }
}
