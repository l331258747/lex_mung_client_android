package cn.lex_mung.client_android.mvp.contract;

import android.app.Activity;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.BusinessTypeEntity;
import cn.lex_mung.client_android.mvp.model.entity.LawyerEntity;

import java.util.List;

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
    }

    interface Model extends IModel {
        Observable<BaseResponse<List<BusinessTypeEntity>>> getBusinessType();

        Observable<LawyerEntity> getLawyerList(int pageNum, RequestBody body);

        Observable<LawyerEntity> getLawyerList1(int pageNum, RequestBody body);
    }
}
