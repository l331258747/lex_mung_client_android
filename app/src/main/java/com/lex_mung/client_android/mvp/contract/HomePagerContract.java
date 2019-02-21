package com.lex_mung.client_android.mvp.contract;

import com.lex_mung.client_android.mvp.model.entity.SolutionTypeEntity;
import com.lex_mung.client_android.mvp.model.entity.BannerEntity;
import com.lex_mung.client_android.mvp.model.entity.BaseResponse;

import java.util.List;

import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.RequestBody;

public interface HomePagerContract {
    interface View extends IView {

        void setBannerAdapter(List<BannerEntity.ListBean> list);

        void setSolutionType(List<SolutionTypeEntity> list);
    }

    interface Model extends IModel {
        Observable<BaseResponse<BannerEntity>> getBanner();

        Observable<BaseResponse<List<SolutionTypeEntity>>> getSolutionType(RequestBody body);
    }
}
