package cn.lex_mung.client_android.mvp.contract;

import android.app.Activity;

import cn.lex_mung.client_android.mvp.model.entity.AgreementEntity;
import cn.lex_mung.client_android.mvp.model.entity.BalanceEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.BusinessEntity;
import cn.lex_mung.client_android.mvp.model.entity.GeneralEntity;
import cn.lex_mung.client_android.mvp.model.entity.PayEntity;
import cn.lex_mung.client_android.mvp.model.entity.ReleaseDemandOrgMoneyEntity;
import cn.lex_mung.client_android.mvp.ui.adapter.ReleaseDemandServiceTypeAdapter;

import java.util.List;

import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.RequestBody;

public interface ReleaseDemandContract {
    interface View extends IView {

        void setRegion(String region);

        void showPayLayout();

        void showProblemDescriptionLayout();

        void initRecyclerView(ReleaseDemandServiceTypeAdapter adapter);

        void hideLoadingLayout();

        void setOrderMoney(String money);

        void setDiscountMoney(String money);

        void hideDiscountMoney();

        void setBalance(String balance);

        void setDiscountWay(String organizationName);

        void setClubCardBalance(String money);

        void hideClubCardBalance();

        void setTip(String string);

        void showToAppInfoDialog();

        void showLackOfBalanceDialog();

        void hideFieldLayout();

        Activity getActivity();
    }

    interface Model extends IModel {
        Observable<BaseResponse<List<BusinessEntity>>> releaseDemandList(RequestBody body);

        Observable<BaseResponse<ReleaseDemandOrgMoneyEntity>> getReleaseDemandOrgMoney(RequestBody body);

        Observable<BaseResponse<BalanceEntity>> getUserBalance(int id);

        Observable<BaseResponse<GeneralEntity>> releaseRequirement(RequestBody body);

        Observable<BaseResponse<PayEntity>> pay(RequestBody body);

        Observable<BaseResponse<AgreementEntity>> tariffExplanationUrl();
    }
}
