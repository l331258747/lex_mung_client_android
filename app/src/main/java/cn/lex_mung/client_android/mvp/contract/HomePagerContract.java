package cn.lex_mung.client_android.mvp.contract;

import cn.lex_mung.client_android.mvp.model.entity.RequirementTypeEntity;
import cn.lex_mung.client_android.mvp.model.entity.SolutionTypeEntity;
import cn.lex_mung.client_android.mvp.model.entity.BannerEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.UnreadMessageCountEntity;

import java.util.List;

import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.RequestBody;

public interface HomePagerContract {
    interface View extends IView {
        void setBannerAdapter(List<BannerEntity.ListBean> list);

        void setSolutionType(List<SolutionTypeEntity> list);

        void setRequirementTypeAdapter(List<RequirementTypeEntity> data);

        void setUnreadMessageCount(String count);

        void hideUnreadMessageCount();
    }

    interface Model extends IModel {
        Observable<BaseResponse<BannerEntity>> getBanner();

        Observable<BaseResponse<List<SolutionTypeEntity>>> getSolutionType(RequestBody body);

        Observable<BaseResponse<List<RequirementTypeEntity>>> getHomepageRequirementType();

        Observable<BaseResponse<UnreadMessageCountEntity>> getUnreadCount();
    }
}
