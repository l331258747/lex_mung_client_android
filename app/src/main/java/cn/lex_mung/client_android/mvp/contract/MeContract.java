package cn.lex_mung.client_android.mvp.contract;

import java.util.List;

import cn.lex_mung.client_android.mvp.model.entity.AboutEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.UserInfoDetailsEntity;
import cn.lex_mung.client_android.mvp.model.entity.home.RightsVipEntity;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IModel;
import me.zl.mvp.mvp.IView;

public interface MeContract {
    interface View extends IView {

        void setAvatar(String iconImage);

        void setName(String memberName);

        void setRegion(String region);

        void setOrg(String organizationName);

        void setAge(String age);

        void hideAgeLayout();

        void setSex(int bg, int color, int icon);

        void hideSexIcon();

        void showSexIcon();

        void showLoginLayout();

        void hideLoginLayout();

        void hideOrgLayout();

        void changeVipData(List<RightsVipEntity> entities);
    }

    interface Model extends IModel {
        Observable<BaseResponse<UserInfoDetailsEntity>> getUserInfoDetail();

        Observable<BaseResponse<AboutEntity>> getAbout();

        Observable<BaseResponse<List<RightsVipEntity>>> rightsVip();
    }
}
