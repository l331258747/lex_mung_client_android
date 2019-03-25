package cn.lex_mung.client_android.mvp.contract;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.BusinessTypeEntity;
import cn.lex_mung.client_android.mvp.model.entity.LawyerEntity;

import java.util.List;

import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.RequestBody;

public interface FindLawyerContract {
    interface View extends IView {
        void setAdapter(List<LawyerEntity.LawyerBean.ListBean> list, boolean isAdd);

        void setScreenColor(int color);
    }

    interface Model extends IModel {
        Observable<BaseResponse<List<BusinessTypeEntity>>> getBusinessType();

        Observable<LawyerEntity> getLawyerList(int pageNum, RequestBody body);

        Observable<LawyerEntity> getLawyerList1(int pageNum, RequestBody body);
    }
}
