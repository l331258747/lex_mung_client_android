package cn.lex_mung.client_android.mvp.contract;

import android.app.Activity;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.free.CommonFreeTextEntity;
import cn.lex_mung.client_android.mvp.ui.adapter.FreeConsultListAdapter;
import cn.lex_mung.client_android.mvp.ui.adapter.FreeConsultMainAdapter;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.RequestBody;


public interface FreeConsultListContract {
    interface View extends IView {
        void initRecyclerView(FreeConsultListAdapter adapter);

        void setEmptyView(boolean isShow);

        Activity getActivity();
    }

    interface Model extends IModel {
        Observable<BaseResponse<CommonFreeTextEntity>> commonFreeText(RequestBody body);
    }
}
