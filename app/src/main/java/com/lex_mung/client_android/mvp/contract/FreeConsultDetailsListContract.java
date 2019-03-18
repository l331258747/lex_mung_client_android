package com.lex_mung.client_android.mvp.contract;

import com.lex_mung.client_android.mvp.model.entity.BaseResponse;
import com.lex_mung.client_android.mvp.model.entity.FreeConsultReplyEntity;
import com.lex_mung.client_android.mvp.model.entity.FreeConsultReplyListEntity;
import com.lex_mung.client_android.mvp.ui.adapter.FreeConsultDetailsListAdapter;

import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.RequestBody;


public interface FreeConsultDetailsListContract {
    interface View extends IView {

        void initRecyclerView(FreeConsultDetailsListAdapter adapter);

        void setEmptyView(FreeConsultDetailsListAdapter adapter);

        void setTitle(String format);

        void clearInput();
    }

    interface Model extends IModel {
        Observable<BaseResponse<FreeConsultReplyEntity>> getFreeConsultReplyDetailList(int consultationId, int lawyerId, int pageNum);

        Observable<BaseResponse<FreeConsultReplyListEntity>> lawyerReply(int consultationId, RequestBody body);

        Observable<BaseResponse> deleteReply(int consultationReplyId);
    }
}
