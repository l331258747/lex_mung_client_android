package cn.lex_mung.client_android.mvp.contract;

import android.content.Context;
import android.support.v4.app.Fragment;

import cn.lex_mung.client_android.mvp.model.entity.AgreementEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.ExpertPriceEntity;
import cn.lex_mung.client_android.mvp.model.entity.LawsHomePagerBaseEntity;
import cn.lex_mung.client_android.mvp.model.entity.LawyerTagsEntity;

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

        void initViewPager(List<Fragment> fragments);

        void hideLikeLayout();

        void setLikeLayout(int icon, int color, String string);

        void setPositionName(String memberPositionName);

        void setInstitutionNameAndPractice(String format);

        void setSocialPosition(String toString);

        void hideSocialPosition();

        void setCreditCertification(String tagName);

        void hideCreditCertificationLayout();

        void showMoreSocialPositionLayout();

        void hideFieldLayout();

        void showFieldDialog(LawsHomePagerBaseEntity.ChildBean bean);

        void hideFieldLayout_1();

        Context getActivity();

        void addSimpleFlowLayout(android.view.View itemView, int i);

        void removeViews();

        //------电话
        void showToPayDialog(String s);
        void showToErrorDialog(String s);

        void showDialDialog(ExpertPriceEntity entity);

        void showDial1Dialog(String s);
    }

    interface Model extends IModel {
        Observable<BaseResponse<LawsHomePagerBaseEntity>> getLawsHomePagerBase(int id);

        Observable<BaseResponse<LawsHomePagerBaseEntity>> getLawsHomePagerBase1( int id);

        Observable<BaseResponse> follow(int id);

        Observable<BaseResponse> unFollow(int id);

        //-----电话
        Observable<BaseResponse<ExpertPriceEntity>> expertPrice(int id);

        Observable<BaseResponse<AgreementEntity>> sendCall(int id);
    }
}
