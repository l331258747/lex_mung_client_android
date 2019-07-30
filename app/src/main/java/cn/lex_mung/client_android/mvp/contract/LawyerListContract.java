package cn.lex_mung.client_android.mvp.contract;

import android.app.Activity;

import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.BusinessTypeEntity;

import java.util.List;

import cn.lex_mung.client_android.mvp.model.entity.LawyerEntity2;
import cn.lex_mung.client_android.mvp.ui.adapter.LawyerListAdapter;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.RequestBody;

public interface LawyerListContract {
    interface View extends IView {
        void setScreenColor(int color);

        void initRecyclerView(LawyerListAdapter adapter);

        void setEmptyView(LawyerListAdapter adapter);

        Activity getActivity();

        void setField(int id,String name);
    }

    interface Model extends IModel {
        Observable<BaseResponse<List<BusinessTypeEntity>>> getBusinessType();

        Observable<BaseResponse<BaseListEntity<LawyerEntity2>>> getLawyerList(int pageNum, RequestBody body);

        Observable<BaseResponse<BaseListEntity<LawyerEntity2>>> getLawyerList1(int pageNum, RequestBody body);
    }
}
