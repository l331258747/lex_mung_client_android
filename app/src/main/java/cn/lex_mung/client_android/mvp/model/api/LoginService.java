package cn.lex_mung.client_android.mvp.model.api;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.TokenEntity;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
    Observable<BaseResponse<TokenEntity>> getCode(@Path("phone") String phone);

    /**
     * 获取验证码
     *
     * @param body RequestBody
     * @return BaseResponse
     */
    @POST("passport/verifycode")
    Observable<BaseResponse> getCode(@Body RequestBody body);

    /**
     * 登录
     *
     * @param body RequestBody
     * @return BaseResponse
     */
    @POST("passport/app/login")
    Observable<BaseResponse<TokenEntity>> login(@Body RequestBody body);

    /**
     * 登出
     *
     * @return BaseResponse
     */
    @GET("passport/logout")
    Observable<BaseResponse> logout();
}
