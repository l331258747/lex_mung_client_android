package cn.lex_mung.client_android.mvp.contract;


import android.app.Activity;

import java.util.List;

import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.LawyerEntity2;
import cn.lex_mung.client_android.mvp.model.entity.SolutionListEntity;
import cn.lex_mung.client_android.mvp.model.entity.free.CommonFreeTextEntity;
import cn.lex_mung.client_android.mvp.model.entity.home.CommonMarkEntity;
import cn.lex_mung.client_android.mvp.model.entity.home.CommonPageContractsEntity;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IModel;
import me.zl.mvp.mvp.IView;
import okhttp3.RequestBody;


public interface SolutionDetailContract {
    interface View extends IView {
        Activity getActivity();
        void initTableLayout(List<CommonMarkEntity> marks);
        void hideTableLayout();
        void hideLawyerLayout();
        void hideLawyerAllView();
        void initLawyerAdapter(List<LawyerEntity2> datas);
        void setContractLayout(List<CommonPageContractsEntity> datas);
        void initFreeAdapter(List<CommonFreeTextEntity> datas);
        void hideFreeLayout();

        void initSolutionAdapter();
        void setSolutionData(List<SolutionListEntity> list, boolean isAdd);
        void hideSolutionLayout();
    }

    interface Model extends IModel {

        Observable<BaseResponse<BaseListEntity<LawyerEntity2>>> getLawyerHomeList(RequestBody body);

        Observable<BaseResponse<List<CommonMarkEntity>>> commonMarks(int solutionId);

        Observable<BaseResponse<BaseListEntity<CommonFreeTextEntity>>> commonFreeText(RequestBody body, boolean isLogin);

        Observable<BaseResponse<BaseListEntity<SolutionListEntity>>> getSolutionList(RequestBody body);

        Observable<BaseResponse<List<CommonPageContractsEntity>>> commonPageContracts();

    }
}
