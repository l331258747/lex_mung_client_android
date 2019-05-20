package cn.lex_mung.client_android.mvp.contract;

import android.app.Activity;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.FreeConsultReplyEntity;
import cn.lex_mung.client_android.mvp.model.entity.FreeConsultReplyListEntity;
import cn.lex_mung.client_android.mvp.ui.adapter.FreeConsultDetail1Adapter;
import cn.lex_mung.client_android.mvp.ui.adapter.FreeConsultDetail1ListAdapter;
import cn.lex_mung.client_android.mvp.ui.adapter.FreeConsultMainAdapter;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IModel;
import me.zl.mvp.mvp.IView;


public interface FreeConsultDetail1ListContract {
    interface View extends IView {
        void initRecyclerView(FreeConsultDetail1ListAdapter adapter);

        void setEmptyView(FreeConsultDetail1ListAdapter adapter);

        Activity getActivity();

        void showDeleteDialog(int consultationReplyId, int position);

    }

    interface Model extends IModel {
        Observable<BaseResponse<FreeConsultReplyEntity>> replyDetail(int consultationId,
                                                                     int lawyerId,
                                                                     int pageNum);

        Observable<BaseResponse> deleteReply(int consultationReplyId);
    }
}
