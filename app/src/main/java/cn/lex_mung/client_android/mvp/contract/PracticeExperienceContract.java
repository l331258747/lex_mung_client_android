package cn.lex_mung.client_android.mvp.contract;

import java.util.List;

import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.CaseListEntity;
import io.reactivex.Observable;
import me.zl.mvp.mvp.IModel;
import me.zl.mvp.mvp.IView;
import okhttp3.RequestBody;

public interface PracticeExperienceContract {
    interface View extends IView {

        void setServiceFirm(String toString);

        void hideServiceFirmLayout();

        void setProfessionalSkills(String toString);

        void hideProfessionalSkillsLayout();

        void setIndustry(String toString);

        void hideIndustryLayout();

        void setLanguage(String toString);

        void hideLanguageLayout();

        void setOther(String toString);

        void hideOtherLayout();

        void setCourt(String toString);

        void hideCourtLayout();

        void setP(String toString);

        void hidePLayout();

        void showNoDataLayout1();

        void showNoDataLayout();

        void showCaseLayout();

        void hideCaseLayout();

        void setAdapter(boolean isAdd, List<CaseListEntity> entities);
    }

    interface Model extends IModel {
        Observable<BaseResponse<BaseListEntity<CaseListEntity>>> getCaseList(RequestBody body);
    }
}
