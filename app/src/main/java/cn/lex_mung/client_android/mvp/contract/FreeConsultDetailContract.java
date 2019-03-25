package cn.lex_mung.client_android.mvp.contract;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.FreeConsultEntity;
import cn.lex_mung.client_android.mvp.model.entity.FreeConsultReplyEntity;
import cn.lex_mung.client_android.mvp.model.entity.FreeConsultReplyListEntity;

import java.util.List;

import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import retrofit2.http.Path;

public interface FreeConsultDetailContract {
    interface View extends IView {
        void setAdapter(List<FreeConsultReplyListEntity> list, boolean isAdd);

        void setName(String memberName);

        void setAvatar(String memberIconImage);

        void setRegion(String region);

        void setCategoryName(String categoryName);

        void setContent(String content);

        void setTime(String s);

        void setReplyCount(String count);

        void refreshReplyCount(int count);

        void setAvatar(int ic_avatar);
    }

    interface Model extends IModel {
        Observable<BaseResponse<FreeConsultEntity>> getFreeConsultDetail(@Path("consultationId") int id);

        Observable<BaseResponse<FreeConsultReplyEntity>> getFreeConsultReplyList(int consultationId, int pageNum);
    }
}
