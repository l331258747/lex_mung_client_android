package cn.lex_mung.client_android.mvp.contract;

import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.LawyerEntity2;
import cn.lex_mung.client_android.mvp.ui.adapter.LawyerListAdapter;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.RequestBody;


public interface OrganizationLawyerContract {
    interface View extends IView {
        void initRecyclerView(LawyerListAdapter adapter);

        void setEmptyView(LawyerListAdapter adapter);
    }

    interface Model extends IModel {
        Observable<BaseResponse<BaseListEntity<LawyerEntity2>>> getLawyerList(int pageNum, RequestBody body);

        Observable<BaseResponse<BaseListEntity<LawyerEntity2>>> getLawyerList1(int pageNum, RequestBody body);
    }
}
