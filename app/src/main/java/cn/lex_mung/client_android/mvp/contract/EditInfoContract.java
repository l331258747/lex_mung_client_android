package cn.lex_mung.client_android.mvp.contract;

import android.app.Activity;
import android.content.Intent;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.IndustryEntity;
import cn.lex_mung.client_android.mvp.model.entity.UploadImageEntity;
import cn.lex_mung.client_android.mvp.model.entity.UserInfoDetailsEntity;

import java.util.List;

import io.reactivex.Observable;
import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface EditInfoContract {
    interface View extends IView {

        Activity getActivity();

        void showSelectPhotoDialog();

        void showToAppInfoDialog();

        void launchActivity(Intent intent, int code);

        void setAvatar(String path);

        void setUserName(String memberName);

        void setUserSex(String s);

        void setBirthday(String birthday);

        void setRegion(String region);

        void setIndustryName(String industry);

        void setInstitutionName(String institutionName);

        void setPositionName(String memberPositionName);

        void setEmail(String email);
    }

    interface Model extends IModel {
        Observable<BaseResponse<UploadImageEntity>> uploadImage(RequestBody body, MultipartBody.Part file);

        Observable<BaseResponse<List<IndustryEntity>>> getIndustryList(RequestBody body);

        Observable<BaseResponse> updateUserInfo(RequestBody body);

        Observable<BaseResponse<UserInfoDetailsEntity>> getUserInfoDetail();
    }
}
