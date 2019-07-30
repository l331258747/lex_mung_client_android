package cn.lex_mung.client_android.mvp.contract;

import android.app.Activity;

import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.LawyerEntity2;
import cn.lex_mung.client_android.mvp.model.entity.SolutionTypeEntity;
import cn.lex_mung.client_android.mvp.model.entity.BannerEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.UnreadMessageCountEntity;

import java.util.List;

import cn.lex_mung.client_android.mvp.model.entity.home.HomeEntity;
import cn.lex_mung.client_android.mvp.model.entity.home.NormalBean;
import cn.lex_mung.client_android.mvp.model.entity.home.OnlineUrlEntity;
import cn.lex_mung.client_android.mvp.model.entity.home.RequirementTypeV3Entity;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Path;

public interface HomePagerContract {
    interface View extends IView {
//        void setBannerAdapter(List<BannerEntity> list);
//        void setSolutionType(List<SolutionTypeEntity> list);
//        void setRequirementTypeAdapter(List<NormalBean> data);
//        void setHotContract(List<NormalBean> datas);
//        void setMoreContract(List<NormalBean> datas);

        void setHomeAdapter(List<HomeEntity> datas);
        void addHomeLawyer(HomeEntity homeEntity);

        void setUnreadMessageCount(String count);

        void hideUnreadMessageCount();


        void showFlipView(boolean isShow);

        void addNotice(List<String> datas);

        Activity getHomeActivity();

    }

    interface Model extends IModel {
//        Observable<BaseResponse<BaseListEntity<BannerEntity>>> getBanner();
//        Observable<BaseResponse<List<SolutionTypeEntity>>> getSolutionType(RequestBody body);
//        Observable<BaseResponse<RequirementTypeV3Entity>> getHomepageRequirementType();
//        Observable<BaseResponse<OnlineUrlEntity>> clientOnlineUrl();

        Observable<BaseResponse<UnreadMessageCountEntity>> getUnreadCount();

        Observable<BaseResponse<BaseListEntity<HomeEntity>>> clientHome();

        Observable<BaseResponse<List<String>>> random();

        Observable<BaseResponse<BaseListEntity<LawyerEntity2>>> getLawyerHomeList(RequestBody body);
    }
}
