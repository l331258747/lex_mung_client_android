package cn.lex_mung.client_android.mvp.contract;

import android.app.Activity;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.FreeConsultEntity;
import cn.lex_mung.client_android.mvp.model.entity.FreeConsultReplyEntity;
import cn.lex_mung.client_android.mvp.model.entity.LawyerEntity;
import cn.lex_mung.client_android.mvp.ui.adapter.FreeConsultDetail1Adapter;
import cn.lex_mung.client_android.mvp.ui.adapter.FreeConsultMainAdapter;
import cn.lex_mung.client_android.mvp.ui.adapter.LawyerListAdapter;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IModel;
import me.zl.mvp.mvp.IView;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Path;


public interface FreeConsultDetail1Contract {
    interface View extends IView {
        void initRecyclerView(FreeConsultDetail1Adapter adapter);

        void setEmptyView(boolean isShow);

        Activity getActivity();

        void setData(FreeConsultEntity entity);

        void setLawyerList(LawyerListAdapter lawyerListAdapter);
    }

    interface Model extends IModel {
        Observable<BaseResponse<FreeConsultEntity>> commonFreeText(int id,boolean isLogin);

        Observable<BaseResponse<FreeConsultReplyEntity>> lawyerFreeText(int consultationId, int pageNum, int pageSize);

        Observable<LawyerEntity> getLawyerList(int pageNum, RequestBody body);
    }
}