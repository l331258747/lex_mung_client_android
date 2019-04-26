package cn.lex_mung.client_android.mvp.contract;

import android.support.v4.app.Fragment;

import java.util.List;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.order.DocGetEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.DocUploadEntity;
import cn.lex_mung.client_android.mvp.ui.adapter.TabOrderContractAdapter;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public interface TabOrderContractContract {
    interface View extends IView {
        Fragment getFragment();

        void initRecyclerView(TabOrderContractAdapter adapter);

    }

    interface Model extends IModel {
        Observable<BaseResponse<DocUploadEntity>> docUpload(RequestBody order_no, MultipartBody.Part file);
        Observable<BaseResponse<DocGetEntity>> docGet(String order_no,int pageNum);
    }
}
