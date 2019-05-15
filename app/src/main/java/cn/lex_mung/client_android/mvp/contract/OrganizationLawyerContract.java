package cn.lex_mung.client_android.mvp.contract;

import cn.lex_mung.client_android.mvp.model.entity.LawyerEntity;
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
        Observable<LawyerEntity> getLawyerList(int pageNum, RequestBody body);

        Observable<LawyerEntity> getLawyerList1(int pageNum, RequestBody body);
    }
}
