package cn.lex_mung.client_android.mvp.contract;

import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.BusinessTypeEntity;

import java.util.List;

import cn.lex_mung.client_android.mvp.model.entity.LawyerEntity2;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.RequestBody;

public interface FindLawyerContract {
    interface View extends IView {
        void setAdapter(List<LawyerEntity2> list, boolean isAdd);

        void setScreenColor(int color);

        void setField(int id,String name);

        void showEmptyView(boolean isAdd);
    }

    interface Model extends IModel {
        Observable<BaseResponse<List<BusinessTypeEntity>>> getBusinessType();

        Observable<BaseResponse<BaseListEntity<LawyerEntity2>>> getLawyerList(int pageNum, RequestBody body);

        Observable<BaseResponse<BaseListEntity<LawyerEntity2>>> getLawyerList1(int pageNum, RequestBody body);
    }
}
