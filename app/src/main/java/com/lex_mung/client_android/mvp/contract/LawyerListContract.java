package com.lex_mung.client_android.mvp.contract;

import com.lex_mung.client_android.mvp.model.entity.BaseResponse;
import com.lex_mung.client_android.mvp.model.entity.BusinessTypeEntity;
import com.lex_mung.client_android.mvp.model.entity.LawyerEntity;

import java.util.List;

import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.RequestBody;

public interface LawyerListContract {
    interface View extends IView {
        void setAdapter(List<LawyerEntity.LawyerBean.ListBean> list, boolean isAdd);

        void setScreenColor(int color);
    }

    interface Model extends IModel {
        Observable<BaseResponse<List<BusinessTypeEntity>>> getBusinessType();

        Observable<LawyerEntity> getLawyerList(int pageNum, RequestBody body);
    }
}
