package com.lex_mung.client_android.mvp.model.api;

import com.lex_mung.client_android.mvp.model.entity.AboutEntity;
import com.lex_mung.client_android.mvp.model.entity.AgreementEntity;
import com.lex_mung.client_android.mvp.model.entity.BalanceEntity;
import com.lex_mung.client_android.mvp.model.entity.BusinessTypeEntity;
import com.lex_mung.client_android.mvp.model.entity.EquitiesDetailsEntity;
import com.lex_mung.client_android.mvp.model.entity.EquitiesListEntity;
import com.lex_mung.client_android.mvp.model.entity.FeedbackTypeEntity;
import com.lex_mung.client_android.mvp.model.entity.FreeConsultEntity;
import com.lex_mung.client_android.mvp.model.entity.FreeConsultReplyEntity;
import com.lex_mung.client_android.mvp.model.entity.FreeConsultReplyListEntity;
import com.lex_mung.client_android.mvp.model.entity.IndustryEntity;
import com.lex_mung.client_android.mvp.model.entity.InstitutionEntity;
import com.lex_mung.client_android.mvp.model.entity.LawsHomePagerBaseEntity;
import com.lex_mung.client_android.mvp.model.entity.LawyerEntity;
import com.lex_mung.client_android.mvp.model.entity.MyLikeEntity;
import com.lex_mung.client_android.mvp.model.entity.OrderEntity;
import com.lex_mung.client_android.mvp.model.entity.OrderStatusEntity;
import com.lex_mung.client_android.mvp.model.entity.PayEntity;
import com.lex_mung.client_android.mvp.model.entity.LawyerListScreenEntity;
import com.lex_mung.client_android.mvp.model.entity.RegionEntity;
import com.lex_mung.client_android.mvp.model.entity.RequirementTypeEntity;
import com.lex_mung.client_android.mvp.model.entity.SolutionListEntity;
import com.lex_mung.client_android.mvp.model.entity.SolutionTypeEntity;
import com.lex_mung.client_android.mvp.model.entity.BannerEntity;
import com.lex_mung.client_android.mvp.model.entity.BaseResponse;
import com.lex_mung.client_android.mvp.model.entity.TradingListEntity;
import com.lex_mung.client_android.mvp.model.entity.UploadImageEntity;
import com.lex_mung.client_android.mvp.model.entity.UserInfoDetailsEntity;
import com.lex_mung.client_android.mvp.model.entity.VersionEntity;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface CommonService {

    /**
     * 检查版本
     *
     * @return BaseResponse
     */
    @GET("version/update")
    Observable<BaseResponse<VersionEntity>> checkVersion();

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
    Observable<BaseResponse<List<LawyerListScreenEntity>>> getPeerSearchList();

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


    /**
     * 获取律师展示页面数据
     *
     * @return UserAllInfoEntity
     */
    @GET("common/member/homepage/base/v3/{targetMemberId}")
    Observable<BaseResponse<LawsHomePagerBaseEntity>> getLawsHomePagerBase(@Path("targetMemberId") int id);

    /**
     * 获取律师展示页面>关注律师
     *
     * @return UserAllInfoEntity
     */
    @GET("lawyer/member/follow/{targetMemberId}")
    Observable<BaseResponse> follow(@Path("targetMemberId") int id);

    /**
     * 获取律师展示页面>取消关注律师
     *
     * @return UserAllInfoEntity
     */
    @GET("lawyer/member/unfollow/{targetMemberId}")
    Observable<BaseResponse> unFollow(@Path("targetMemberId") int id);

    /**
     * 获取用户信息详情
     *
     * @return BaseResponse
     */
    @POST("client/member/detail/v2")
    Observable<BaseResponse<UserInfoDetailsEntity>> getUserInfoDetail();

    /**
     * 律师协议
     *
     * @return BaseResponse
     */
    @GET("userRegisterAgreenmentUrl")
    Observable<BaseResponse<AgreementEntity>> userRegisterAgreenmentUrl();

    /**
     * app日活
     *
     * @return BaseResponse
     */
    @GET("applog/startup")
    Observable<BaseResponse> appStartUp();

    /**
     * 上传图片
     *
     * @param body 类型
     * @param file 图片文件
     */
    @Multipart
    @POST("upload/file")
    Observable<BaseResponse<UploadImageEntity>> uploadImage(@Part("type") RequestBody body, @Part MultipartBody.Part file);

    /**
     * 更新用户信息
     *
     * @return BaseResponse
     */
    @POST("client/member/update")
    Observable<BaseResponse> updateUserInfo(@Body RequestBody body);

    /**
     * 获取行业列表
     *
     * @return BaseResponse
     */
    @POST("common/industry")
    Observable<BaseResponse<List<IndustryEntity>>> getIndustryList(@Body RequestBody body);

    /**
     * 发布免费咨询
     *
     * @return BaseResponse
     */
    @POST("client/freeText/post")
    Observable<BaseResponse<FreeConsultEntity>> releaseFreeConsult(@Body RequestBody body);

    /**
     * 获取咨询详情
     *
     * @return BaseResponse
     */
    @GET("lawyer/freeText/{consultationId}")
    Observable<BaseResponse<FreeConsultEntity>> getFreeConsultDetail(@Path("consultationId") int id);

    /**
     * 获取咨询详情回复列表
     *
     * @return BaseResponse
     */
    @GET("lawyer/freeText/{consultationId}/reply/{pageNum}")
    Observable<BaseResponse<FreeConsultReplyEntity>> getConsultReplyList(@Path("consultationId") int consultationId, @Path("pageNum") int pageNum);

    /**
     * 获取咨询详情回复详情列表
     *
     * @return BaseResponse
     */
    @GET("lawyer/freeText/{consultationId}/{lawyerId}/reply/detail/{pageNum}")
    Observable<BaseResponse<FreeConsultReplyEntity>> getFreeConsultReplyList(@Path("consultationId") int consultationId, @Path("lawyerId") int lawyerId, @Path("pageNum") int pageNum);

    /**
     * 用户回复
     *
     * @return BaseResponse
     */
    @POST("lawyer/freeText/{consultationId}/reply/post")
    Observable<BaseResponse<FreeConsultReplyListEntity>> userReplyFreeConsult(@Path("consultationId") int consultationId, @Body RequestBody body);

    /**
     * 删除回复
     *
     * @return BaseResponse
     */
    @GET("lawyer/freeText/reply/{consultationReplyId}/delete")
    Observable<BaseResponse> deleteReply(@Path("consultationReplyId") int consultationReplyId);

    /**
     * 我的订单列表
     *
     * @return BaseResponse
     */
    @POST("client/member/order")
    Observable<BaseResponse<List<OrderEntity>>> getOrderList(@Body RequestBody body);

    /**
     * 获取用户余额
     *
     * @return BaseResponse
     */
    @GET("client/amount/balance/{id}")
    Observable<BaseResponse<BalanceEntity>> getUserBalance(@Path("id") int id);

    /**
     * 充值
     *
     * @return BaseResponse
     */
    @POST("pay/recharge")
    Observable<BaseResponse<PayEntity>> pay(@Body RequestBody body);

    /**
     * 提现
     *
     * @return BaseResponse
     */
    @POST("pay/withdraw")
    Observable<BaseResponse> withdraw(@Body RequestBody body);

    /**
     * 获取用户交易明细
     *
     * @return BaseResponse
     */
    @POST("client/amount/detail")
    Observable<BaseResponse<TradingListEntity>> getTradingList(@Body RequestBody body);

    /**
     * 获取关注律师列表
     *
     * @return BaseResponse
     */
    @POST("lawyer/member/myfollow")
    Observable<BaseResponse<MyLikeEntity>> getMyLikeList(@Body RequestBody body);

    /**
     * 查询订单支付状态
     *
     * @return BaseResponse
     */
    @POST("pay/order/check")
    Observable<BaseResponse<OrderStatusEntity>> checkOrderStatus(@Body RequestBody body);

    /**
     * 关于接口返回
     *
     * @return BaseResponse
     */
    @GET("aboutus")
    Observable<BaseResponse<AboutEntity>> getAbout();

    /**
     * 意见反馈类型接口
     *
     * @return BaseResponse
     */
    @GET("lawyer/feedback/types")
    Observable<BaseResponse<List<FeedbackTypeEntity>>> getFeedbackType();

    /**
     * 上传意见反馈
     *
     * @return BaseResponse
     */
    @POST("lawyer/feedback/post")
    Observable<BaseResponse> uploadFeedback(@Body RequestBody body);

    /**
     * 首页获取发需求的服务分类和事务分类(不需要登陆)
     *
     * @return BaseResponse
     */
    @GET("common/requirement/types/v2")
    Observable<BaseResponse<List<RequirementTypeEntity>>> getHomepageRequirementType();

    /**
     * 未登录获取全部权益列表
     *
     * @return BaseResponse
     */
    @GET("common/rights/orglist")
    Observable<BaseResponse<List<EquitiesListEntity>>> getEquitiesList();

    /**
     * 登录获取全部权益列表
     *
     * @return BaseResponse
     */
    @GET("client/rights/orglist")
    Observable<BaseResponse<List<EquitiesListEntity>>> getEquitiesList_1();

    /**
     * 添加权益组织
     *
     * @return BaseResponse
     */
    @POST("insert/feedback")
    Observable<BaseResponse> addEquitiesOrg(@Body RequestBody body);

    /**
     * 权益组织详情
     *
     * @return BaseResponse
     */
    @GET("client/rights/{orgId}/{levelId}")
    Observable<BaseResponse<EquitiesDetailsEntity>> getEquitiesDetails(@Path("orgId") int orgId, @Path("levelId") int levelId);

    /**
     * 加入组织
     *
     * @return BaseResponse
     */
    @POST("lawyer/organization/insert")
    Observable<BaseResponse> joinEquitiesOrg(@Body RequestBody body);

}
