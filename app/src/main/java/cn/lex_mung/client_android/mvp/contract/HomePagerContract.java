package cn.lex_mung.client_android.mvp.contract;

import android.app.Activity;

import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.LawyerEntity2;
import cn.lex_mung.client_android.mvp.model.entity.SolutionTypeEntity;
import cn.lex_mung.client_android.mvp.model.entity.BannerEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.UnreadMessageCountEntity;

import java.util.List;

import cn.lex_mung.client_android.mvp.model.entity.free.CommonFreeTextEntity;
import cn.lex_mung.client_android.mvp.model.entity.home.HomeChildEntity;
import cn.lex_mung.client_android.mvp.model.entity.home.HomeEntity;
import cn.lex_mung.client_android.mvp.model.entity.home.NormalBean;
import cn.lex_mung.client_android.mvp.model.entity.home.OnlineUrlEntity;
import cn.lex_mung.client_android.mvp.model.entity.home.PagesSecondEntity;
import cn.lex_mung.client_android.mvp.model.entity.home.RequirementTypeV3Entity;
import cn.lex_mung.client_android.mvp.model.entity.home.RightsVipEntity;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Path;

public interface HomePagerContract {
    interface View extends IView {
        void setHomeAdapter(List<HomeEntity> datas);
        void addHomeLawyer(HomeEntity homeEntity);
        void addHomeFree(HomeEntity homeEntity);

        void setUnreadMessageCount(int count);

        void hideUnreadMessageCount();


        void showFlipView(boolean isShow);

        void addNotice(List<String> datas);

        Activity getHomeActivity();

        void showEmptyView();

        void changeVipData(List<RightsVipEntity> entities);

    }

    interface Model extends IModel {
        Observable<BaseResponse<List<SolutionTypeEntity>>> getSolutionType(RequestBody body);

        Observable<BaseResponse<UnreadMessageCountEntity>> getUnreadCount();

        Observable<BaseResponse<BaseListEntity<HomeEntity>>> clientHome();

        Observable<BaseResponse<List<String>>> random();

        Observable<BaseResponse<BaseListEntity<LawyerEntity2>>> getLawyerHomeList(RequestBody body);

        Observable<BaseResponse<PagesSecondEntity>> pagesSecond();

        Observable<BaseResponse<BaseListEntity<CommonFreeTextEntity>>> commonFreeText(RequestBody body);

        Observable<BaseResponse<List<RightsVipEntity>>> rightsVip();
    }
}
