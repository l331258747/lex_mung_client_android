package cn.lex_mung.client_android.mvp.contract;

import android.app.Activity;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.free.CommonFreeTextEntity;
import cn.lex_mung.client_android.mvp.model.entity.free.FreeTextBizinfoEntity;
import cn.lex_mung.client_android.mvp.ui.adapter.FreeConsultMainAdapter;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IModel;
import me.zl.mvp.mvp.IView;
import okhttp3.RequestBody;


public interface FreeConsultMainContract {
    interface View extends IView {
        void initRecyclerView(FreeConsultMainAdapter adapter);

        void setEmptyView(FreeConsultMainAdapter adapter);

        Activity getActivity();

    }

    interface Model extends IModel {
        Observable<BaseResponse<FreeTextBizinfoEntity>> freeTextBizinfo();

        Observable<BaseResponse<CommonFreeTextEntity>> commonFreeText(RequestBody body);
    }
}
