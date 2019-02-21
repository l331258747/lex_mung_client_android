package com.lex_mung.client_android.mvp.model.api.service;

import com.lex_mung.client_android.mvp.model.entity.BusinessTypeEntity;
import com.lex_mung.client_android.mvp.model.entity.InstitutionEntity;
import com.lex_mung.client_android.mvp.model.entity.LawyerEntity;
import com.lex_mung.client_android.mvp.model.entity.PeerScreenEntity;
import com.lex_mung.client_android.mvp.model.entity.RegionEntity;
import com.lex_mung.client_android.mvp.model.entity.SolutionListEntity;
import com.lex_mung.client_android.mvp.model.entity.SolutionTypeEntity;
import com.lex_mung.client_android.mvp.model.entity.BannerEntity;
import com.lex_mung.client_android.mvp.model.entity.BaseResponse;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CommonService {

    /**
     * 首页banner
     *
     * @return BaseResponse
     */
    @GET("banner")
    Observable<BaseResponse<BannerEntity>> getBanner();

    /**
     * 首页解决方案类型
     *
     * @return BaseResponse
     */
    @POST("solution/type")
    Observable<BaseResponse<List<SolutionTypeEntity>>> getSolutionType(@Body RequestBody body);

    /**
     * 首页解决方案列表
     *
     * @return BaseResponse
     */
    @POST("solution/list")
    Observable<BaseResponse<SolutionListEntity>> getSolutionList(@Body RequestBody body);

    /**
     * 获取律师筛选条件
     *
     * @return BaseResponse
     */
    @GET("common/lawyer/search/page")
    Observable<BaseResponse<List<PeerScreenEntity>>> getPeerSearchList();

    /**
     * 获取律师列表
     *
     * @return BaseResponse
     */
    @POST("common/lawyer/search/{pageNum}")
    Observable<LawyerEntity> getLawyerList(@Path("pageNum") int pageNum, @Body RequestBody body);

    /**
     * 获取专业领域
     *
     * @return BaseResponse
     */
    @GET("common/businessType/v2")
    Observable<BaseResponse<List<BusinessTypeEntity>>> getBusinessType();

    /**
     * 获取法院
     *
     * @return BaseResponse
     */
    @POST("common/court")
    Observable<BaseResponse<InstitutionEntity>> getCourt(@Body RequestBody body);

    /**
     * 获取检察院
     *
     * @return BaseResponse
     */
    @POST("common/procuratorate")
    Observable<BaseResponse<InstitutionEntity>> getProcuratorate(@Body RequestBody body);

    /**
     * 获取地区
     *
     * @return BaseResponse
     */
    @POST("common/region")
    Observable<BaseResponse<List<RegionEntity>>> getRegion(@Body RequestBody body);




}
