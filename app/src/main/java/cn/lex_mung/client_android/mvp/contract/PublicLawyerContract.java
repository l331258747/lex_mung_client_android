package cn.lex_mung.client_android.mvp.contract;

import java.util.List;

import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.EquitiesDetailsEntity;
import cn.lex_mung.client_android.mvp.model.entity.LawyerEntity2;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.RequestBody;


public interface PublicLawyerContract {
    interface View extends IView {
        void setEquitiesDetail(EquitiesDetailsEntity equitiesDetailsEntity);
        void setLawyerAdapter(List<LawyerEntity2> lists,boolean isAdd);
        void hideLawyerLayout();
        void setLawyerTitle(String str);
        void callPublickPhone(String phone);
    }

    interface Model extends IModel {

        Observable<BaseResponse<EquitiesDetailsEntity>> getEquitiesDetails(int orgId, int levelId);

        Observable<BaseResponse<EquitiesDetailsEntity>> getEquitiesDetails1(int orgId, int levelId);

        Observable<BaseResponse<BaseListEntity<LawyerEntity2>>> getLawyerList(int pageNum, RequestBody body);

        Observable<BaseResponse> callOrderInsert(RequestBody body);
    }
}
