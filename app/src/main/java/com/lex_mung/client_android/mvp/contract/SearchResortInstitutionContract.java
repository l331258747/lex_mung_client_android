package com.lex_mung.client_android.mvp.contract;

import com.lex_mung.client_android.mvp.model.entity.BaseResponse;
import com.lex_mung.client_android.mvp.model.entity.InstitutionEntity;
import com.lex_mung.client_android.mvp.model.entity.InstitutionListEntity;

import java.util.List;

import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.RequestBody;

public interface SearchResortInstitutionContract {
    interface View extends IView {

        void setAdapter(List<InstitutionListEntity> list, boolean isAdd);
    }

    interface Model extends IModel {
        Observable<BaseResponse<InstitutionEntity>> getCourt(RequestBody body);

        Observable<BaseResponse<InstitutionEntity>> getProcuratorate(RequestBody body);
    }
}
