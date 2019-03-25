package cn.lex_mung.client_android.mvp.contract;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.EquitiesDetailsEntity;

import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.RequestBody;
import retrofit2.http.Body;

public interface JoinEquitiesOrgContract {
    interface View extends IView {
        void setMemberTotal(String memberCount);

        void setLawyerTotal(String lawyerCount);

        void setEquitiesExplain(String rightsInterpret);

        void setOpenQualification(String openingQualification);

        void setExclusiveEquities(String exclusiveRights);

        void setEquitiesBg(String image);

        void setOrgName(String organizationName);
    }

    interface Model extends IModel {
        Observable<BaseResponse<EquitiesDetailsEntity>> getEquitiesDetails(int orgId, int levelId);

        Observable<BaseResponse<EquitiesDetailsEntity>> getEquitiesDetails1(int orgId, int levelId);

        Observable<BaseResponse> joinEquitiesOrg(@Body RequestBody body);
    }
}
