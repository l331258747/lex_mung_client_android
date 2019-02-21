package com.lex_mung.client_android.mvp.model.api.service;

import com.lex_mung.client_android.mvp.model.entity.BaseResponse;
import com.lex_mung.client_android.mvp.model.entity.CodeEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 登录相关api
 */
public interface LoginService {

    /**
     * 测试服务器获取验证码
     *
     * @return BaseResponse
     */
    @GET("https://api-test.lex-mung.com/code/{phone}")
    Observable<BaseResponse<CodeEntity>> getCode(@Path("phone") String phone);
}
