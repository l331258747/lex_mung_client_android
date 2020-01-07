package cn.lex_mung.client_android.mvp.contract;

import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.EquitiesDetailsEntity;
import cn.lex_mung.client_android.mvp.model.entity.EquitiesListEntity;

import java.util.List;

import cn.lex_mung.client_android.mvp.model.entity.EquitiesMainListEntity;
import cn.lex_mung.client_android.mvp.model.entity.LawyerEntity2;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.RequestBody;

public interface EquitiesContract {
    interface View extends IView {
        void hideCurrentEquitiesLayout();

        void showCurrentEquitiesLayout();

        void setEquitiesAdapter1(List<EquitiesListEntity> data);

        void setEquitiesAdapter2(List<EquitiesListEntity> data);

        void showAllEquitiesLayout();

        void showEquitiesDetails();

        void setMemberTotal(String memberCount);

        void setLawyerTotal(String lawyerCount);

        void setEquitiesExplain(String rightsInterpret);

        void setOpenQualification(String openingQualification);

        void setExclusiveEquities(String exclusiveRights);

        void setEquitiesBg(String image);

        void setLawyerAdapter(List<LawyerEntity2> list);

        void setBalance(List<EquitiesDetailsEntity.CouponsChildEntity> list);

        void showEmptyView();
    }

    interface Model extends IModel {
        Observable<BaseResponse<EquitiesMainListEntity>> getEquitiesList();

        Observable<BaseResponse<EquitiesMainListEntity>> getEquitiesList_1();

        Observable<BaseResponse<EquitiesDetailsEntity>> getEquitiesDetails(int orgId, int levelId);

        Observable<BaseResponse<EquitiesDetailsEntity>> getEquitiesDetails1(int orgId, int levelId);

        Observable<BaseResponse<BaseListEntity<LawyerEntity2>>> getLawyerList(int pageNum, RequestBody body);
    }
}
