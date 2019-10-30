package cn.lex_mung.client_android.mvp.contract;

import android.app.Activity;
import android.support.v4.app.Fragment;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.RemainEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.DocGetEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.DocUploadEntity;
import cn.lex_mung.client_android.mvp.ui.adapter.TabOrderContractAdapter;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public interface OrderContractContract {
    interface View extends IView {
        Activity getActivity();

        void initRecyclerView(TabOrderContractAdapter adapter);

        void call(String phone);
    }

    interface Model extends IModel {
        Observable<BaseResponse<DocUploadEntity>> docUpload(int type,RequestBody order_no, MultipartBody.Part file);
        Observable<BaseResponse<DocGetEntity>> docGet(int type,String order_no, int pageNum);
        Observable<BaseResponse> docRead(int type,String repositoryId);

        Observable<BaseResponse<RemainEntity>> legalAdviserOrderUserPhone(String orderNo);
    }
}
