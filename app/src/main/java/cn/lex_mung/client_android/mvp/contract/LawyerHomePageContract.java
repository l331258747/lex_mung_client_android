package cn.lex_mung.client_android.mvp.contract;

import android.content.Context;
import android.support.v4.app.Fragment;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.ExpertCallEntity;
import cn.lex_mung.client_android.mvp.model.entity.expert.ExpertPriceEntity;
import cn.lex_mung.client_android.mvp.model.entity.LawsHomePagerBaseEntity;

import java.util.List;

import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.RequestBody;

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

        void setCreditCertification(String tagName,String imgUrl);

        void hideCreditCertificationLayout();

        void showMoreSocialPositionLayout();

        void hideFieldLayout();

        void showFieldDialog(LawsHomePagerBaseEntity.ChildBean bean);

        void hideFieldLayout_1();

        Context getActivity();

        void addSimpleFlowLayout(android.view.View itemView, int i);

        void removeViews();
        void showCall(boolean isHide);

        void callPublickPhone(String phone);

        //------电话
//        void showToErrorDialog(String s);
//        void showBalanceNoDialog(ExpertPriceEntity entity);
//        void showBalanceYesDialog(ExpertPriceEntity entity);
//        void GoCall(String str);

        void showExpertPrice(ExpertPriceEntity entity);
    }

    interface Model extends IModel {
        Observable<BaseResponse<LawsHomePagerBaseEntity>> getLawsHomePagerBase(int id);

        Observable<BaseResponse<LawsHomePagerBaseEntity>> getLawsHomePagerBase1( int id);

        Observable<BaseResponse> follow(int id);

        Observable<BaseResponse> unFollow(int id);

        //-----电话
        Observable<BaseResponse<ExpertPriceEntity>> expertPrice(int id);

        Observable<BaseResponse<ExpertCallEntity>> sendCall(int id);

        Observable<BaseResponse> callOrderInsert(RequestBody body);
    }
}
