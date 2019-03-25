package cn.lex_mung.client_android.mvp.contract;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.EquitiesDetailsEntity;
import cn.lex_mung.client_android.mvp.model.entity.EquitiesListEntity;
import cn.lex_mung.client_android.mvp.model.entity.LawyerEntity;

import java.util.List;

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

        void setLawyerAdapter(List<LawyerEntity.LawyerBean.ListBean> list);
    }

    interface Model extends IModel {
        Observable<BaseResponse<List<EquitiesListEntity>>> getEquitiesList();

        Observable<BaseResponse<List<EquitiesListEntity>>> getEquitiesList_1();

        Observable<BaseResponse<EquitiesDetailsEntity>> getEquitiesDetails(int orgId, int levelId);

        Observable<LawyerEntity> getLawyerList(int pageNum, RequestBody body);
    }
}
