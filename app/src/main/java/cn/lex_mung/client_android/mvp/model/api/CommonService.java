package cn.lex_mung.client_android.mvp.model.api;

import java.util.List;

import cn.lex_mung.client_android.mvp.model.entity.AboutEntity;
import cn.lex_mung.client_android.mvp.model.entity.AgreementEntity;
import cn.lex_mung.client_android.mvp.model.entity.AmountBalanceEntity;
import cn.lex_mung.client_android.mvp.model.entity.BalanceEntity;
import cn.lex_mung.client_android.mvp.model.entity.BannerEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.BusinessEntity;
import cn.lex_mung.client_android.mvp.model.entity.BusinessTypeEntity;
import cn.lex_mung.client_android.mvp.model.entity.CaseListEntity;
import cn.lex_mung.client_android.mvp.model.entity.CouponsEntity;
import cn.lex_mung.client_android.mvp.model.entity.DemandMessageEntity;
import cn.lex_mung.client_android.mvp.model.entity.EquitiesDetailsEntity;
import cn.lex_mung.client_android.mvp.model.entity.EquitiesListEntity;
import cn.lex_mung.client_android.mvp.model.entity.EquitiesMainListEntity;
import cn.lex_mung.client_android.mvp.model.entity.ExpertCallEntity;
import cn.lex_mung.client_android.mvp.model.entity.entrust.EntrustDetailEntity;
import cn.lex_mung.client_android.mvp.model.entity.expert.ExpertPriceEntity;
import cn.lex_mung.client_android.mvp.model.entity.FeedbackTypeEntity;
import cn.lex_mung.client_android.mvp.model.entity.FreeConsultEntity;
import cn.lex_mung.client_android.mvp.model.entity.FreeConsultReplyListEntity;
import cn.lex_mung.client_android.mvp.model.entity.GeneralEntity;
import cn.lex_mung.client_android.mvp.model.entity.IndustryEntity;
import cn.lex_mung.client_android.mvp.model.entity.InstitutionEntity;
import cn.lex_mung.client_android.mvp.model.entity.LawsHomePagerBaseEntity;
import cn.lex_mung.client_android.mvp.model.entity.LawyerEntity2;
import cn.lex_mung.client_android.mvp.model.entity.LawyerListScreenEntity;
import cn.lex_mung.client_android.mvp.model.entity.MessageEntity;
import cn.lex_mung.client_android.mvp.model.entity.MyLikeEntity;
import cn.lex_mung.client_android.mvp.model.entity.OrderDetailsEntity;
import cn.lex_mung.client_android.mvp.model.entity.OrderEntity;
import cn.lex_mung.client_android.mvp.model.entity.OrderStatusEntity;
import cn.lex_mung.client_android.mvp.model.entity.OrgAmountEntity;
import cn.lex_mung.client_android.mvp.model.entity.PayEntity;
import cn.lex_mung.client_android.mvp.model.entity.RegionEntity;
import cn.lex_mung.client_android.mvp.model.entity.ReleaseDemandOrgMoneyEntity;
import cn.lex_mung.client_android.mvp.model.entity.ReleaseDemandOrgMoneyEntity2;
import cn.lex_mung.client_android.mvp.model.entity.RemainEntity;
import cn.lex_mung.client_android.mvp.model.entity.RequirementStatusEntity;
import cn.lex_mung.client_android.mvp.model.entity.SolutionListEntity;
import cn.lex_mung.client_android.mvp.model.entity.SolutionTypeEntity;
import cn.lex_mung.client_android.mvp.model.entity.TradingListEntity;
import cn.lex_mung.client_android.mvp.model.entity.UnreadMessageCountEntity;
import cn.lex_mung.client_android.mvp.model.entity.UploadImageEntity;
import cn.lex_mung.client_android.mvp.model.entity.UserInfoDetailsEntity;
import cn.lex_mung.client_android.mvp.model.entity.VersionEntity;
import cn.lex_mung.client_android.mvp.model.entity.expert.ExpertReserveEntity;
import cn.lex_mung.client_android.mvp.model.entity.free.CommonFreeTextEntity;
import cn.lex_mung.client_android.mvp.model.entity.free.FreeTextBizinfoEntity;
import cn.lex_mung.client_android.mvp.model.entity.help.HelpStepEntity;
import cn.lex_mung.client_android.mvp.model.entity.help.HelpStepLawyerEntity;
import cn.lex_mung.client_android.mvp.model.entity.help.HirstoryDemandEntity;
import cn.lex_mung.client_android.mvp.model.entity.home.CommonMarkEntity;
import cn.lex_mung.client_android.mvp.model.entity.home.CommonPageContractsEntity;
import cn.lex_mung.client_android.mvp.model.entity.home.CommonSolutionEntity;
import cn.lex_mung.client_android.mvp.model.entity.home.HomeEntity;
import cn.lex_mung.client_android.mvp.model.entity.home.OnlineUrlEntity;
import cn.lex_mung.client_android.mvp.model.entity.home.PagesSecondEntity;
import cn.lex_mung.client_android.mvp.model.entity.home.RequirementTypeV3Entity;
import cn.lex_mung.client_android.mvp.model.entity.mine.RechargeCouponEntity;
import cn.lex_mung.client_android.mvp.model.entity.mine.RechargeEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.DocGetEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.DocUploadEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.OrderCouponEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.QuickPayEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.RequirementCreateEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.RequirementDetailEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.RushOrderLawyerEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.RushOrderStatusEntity;
import cn.lex_mung.client_android.mvp.model.entity.other.ActivityEntity;
import cn.lex_mung.client_android.mvp.model.entity.other.LaunchLocationEntity;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CommonService {

    /**
     * 检查版本
     *
     * @return BaseResponse
     */
    @GET("version/update")
    Observable<BaseResponse<VersionEntity>> checkVersion();

    /**
     * 获取个人展示页面的案件列表
     *
     * @return BaseResponse
     */
    @POST("case/list")
    Observable<BaseResponse<BaseListEntity<CaseListEntity>>> getCaseList(@Body RequestBody body);

    /**
     * 首页banner
     *
     * @return BaseResponse
     */
    @GET("banner")
    Observable<BaseResponse<BaseListEntity<BannerEntity>>> getBanner();

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
    Observable<BaseResponse<BaseListEntity<SolutionListEntity>>> getSolutionList(@Body RequestBody body);

    /**
     * 获取律师筛选条件
     *
     * @return BaseResponse
     */
    @GET("common/lawyer/search/page/v4")
    Observable<BaseResponse<List<LawyerListScreenEntity>>> getPeerSearchList();

    /**
     * 获取律师列表
     *
     * @return BaseResponse
     */
    @POST("common/lawyer/search/{pageNum}")
    Observable<BaseResponse<BaseListEntity<LawyerEntity2>>> getLawyerList(@Path("pageNum") int pageNum, @Body RequestBody body);

    /**
     * 获取律师列表
     *
     * @return BaseResponse
     */
    @POST("lawyer/search/{pageNum}")
    Observable<BaseResponse<BaseListEntity<LawyerEntity2>>> getLawyerList1(@Path("pageNum") int pageNum, @Body RequestBody body);

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
    @GET("common/member/homepage/base/v4/{targetMemberId}")
    Observable<BaseResponse<LawsHomePagerBaseEntity>> getLawsHomePagerBase(@Path("targetMemberId") int id);

    /**
     * 获取律师展示页面数据
     *
     * @return UserAllInfoEntity
     */
    @GET("lawyer/member/homepage/base/v4/{targetMemberId}")
    Observable<BaseResponse<LawsHomePagerBaseEntity>> getLawsHomePagerBase1(@Path("targetMemberId") int id);

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
    Observable<BaseResponse<BaseListEntity<FreeConsultReplyListEntity>>> getConsultReplyList(@Path("consultationId") int consultationId, @Path("pageNum") int pageNum);

    ///common/freeText/v2/{consultationId}
    //GET
    //文字咨询详情v2
    @GET("common/freeText/v2/{consultationId}")
    Observable<BaseResponse<FreeConsultEntity>> commonFreeText(@Path("consultationId") int id);

    ///lawyer/freeText/v2/{consultationId}
    @GET("lawyer/freeText/v2/{consultationId}")
    Observable<BaseResponse<FreeConsultEntity>> lawyerFreeText(@Path("consultationId") int id);

    ///lawyer/freeText/v2/{consultationId}/reply/{pageNum}/{pageSize}
    //GET
    //文字咨询回复列表v2
    @GET("common/freeText/v2/{consultationId}/reply/{pageNum}/{pageSize}")
    Observable<BaseResponse<BaseListEntity<FreeConsultReplyListEntity>>> lawyerFreeText(@Path("consultationId") int consultationId, @Path("pageNum") int pageNum, @Path("pageSize") int pageSize);


    /**
     * 获取咨询详情回复详情列表
     *
     * @return BaseResponse
     */
    @GET("lawyer/freeText/{consultationId}/{lawyerId}/reply/detail/{pageNum}")
    Observable<BaseResponse<BaseListEntity<FreeConsultReplyListEntity>>> getFreeConsultReplyList(@Path("consultationId") int consultationId,
                                                                                                 @Path("lawyerId") int lawyerId,
                                                                                                 @Path("pageNum") int pageNum);

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
    @POST("client/member/order/v2")
    Observable<BaseResponse<BaseListEntity<OrderEntity>>> getOrderList(@Body RequestBody body);

    /**
     * 我的订单详情
     *
     * @return BaseResponse
     */
    @POST("client/member/order/detail/v2")
    Observable<BaseResponse<BaseListEntity<OrderDetailsEntity>>> getOrderDetail(@Body RequestBody body);

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
     * 权益用户订单列表
     *
     * @return
     */
    @POST("client/member/order/org")
    Observable<BaseResponse<BaseListEntity<OrderEntity>>> getTradingList(@Body RequestBody body);

    /**
     * 获取用户交易明细
     *
     * @return BaseResponse
     */
    @POST("client/amount/detail")
    Observable<BaseResponse<BaseListEntity<TradingListEntity>>> amountDetail(@Body RequestBody body);

    /**
     * 获取关注律师列表
     *
     * @return BaseResponse
     */
    @POST("lawyer/member/myfollow")
    Observable<BaseResponse<BaseListEntity<MyLikeEntity>>> getMyLikeList(@Body RequestBody body);

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
    @GET("common/requirement/types/v3")
    Observable<BaseResponse<RequirementTypeV3Entity>> getHomepageRequirementType();

    /**
     * 未登录获取全部权益列表
     *
     * @return BaseResponse
     */
    @GET("common/rights/orglist/v2")
//    Observable<BaseResponse<List<EquitiesListEntity>>> getEquitiesList(@Query("regionId") int regionId);
    Observable<BaseResponse<EquitiesMainListEntity>> getEquitiesList(@Query("regionId") int regionId);

    /**
     * 登录获取全部权益列表
     *
     * @return BaseResponse
     */
    @GET("client/rights/orglist/v2")
//    Observable<BaseResponse<List<EquitiesListEntity>>> getEquitiesList_1(@Query("regionId") int regionId);
    Observable<BaseResponse<EquitiesMainListEntity>> getEquitiesList_1(@Query("regionId") int regionId);

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
    @GET("client/rights/{orgId}/{level}")
    Observable<BaseResponse<EquitiesDetailsEntity>> getEquitiesDetails1(@Path("orgId") int orgId, @Path("level") int levelId);

    /**
     * 权益组织详情
     *
     * @return BaseResponse
     */
    @GET("common/rights/{orgId}/{level}")
    Observable<BaseResponse<EquitiesDetailsEntity>> getEquitiesDetails(@Path("orgId") int orgId, @Path("level") int levelId);

    /**
     * 加入组织
     *
     * @return BaseResponse
     */
    @POST("lawyer/organization/insert")
    Observable<BaseResponse> joinEquitiesOrg(@Body RequestBody body);

    /**
     * 获取律师的电话咨询单价和自己的余额
     *
     * @return BaseResponse
     */
    @GET("/client/expert/price/{id}/v3")
    Observable<BaseResponse<ExpertPriceEntity>> expertPrice(@Path("id") int id);

    /**
     * 发送需求服务类型列表
     *
     * @return BaseResponse
     */
    @POST("client/require")
    Observable<BaseResponse<List<BusinessEntity>>> releaseDemandList(@Body RequestBody body);

    /**
     * 用户与律师共同加入的最优惠组织等级+实付金额+优惠金额
     *
     * @return BaseResponse
     */
    @POST("client/optimal/v2")
    Observable<BaseResponse<ReleaseDemandOrgMoneyEntity>> getReleaseDemandOrgMoney(@Body RequestBody body);

    /**
     * 用户与律师共同加入的组织
     *
     * @return BaseResponse
     */
    @POST("client/optimal/list")
    Observable<BaseResponse<List<ReleaseDemandOrgMoneyEntity>>> getOrgList(@Body RequestBody body);

    /**
     * 用户与律师共同加入的组织
     *
     * @return BaseResponse
     */
    @POST("client/optimal/list/v2")
    Observable<BaseResponse<ReleaseDemandOrgMoneyEntity2>> getOrgList2(@Body RequestBody body);

    /**
     * 带支付的发需求
     *
     * @return BaseResponse
     */
    @POST("client/requirement/post/v2")
    Observable<BaseResponse<GeneralEntity>> releaseRequirement(@Body RequestBody body);

    /**
     * 带支付的发需求
     *
     * @return BaseResponse
     */
    @POST("client/requirement/post")
    Observable<BaseResponse<GeneralEntity>> releaseRequirement2(@Body RequestBody body);

    /**
     * 获取未读消息数量
     *
     * @return BaseResponse
     */
    @GET("client/message/unread/count")
    Observable<BaseResponse<UnreadMessageCountEntity>> getUnreadCount();


    /**
     * 全部设为已读
     *
     * @return BaseResponse
     */
    @GET("client/message/unread/update/{type}")
    Observable<BaseResponse> allSetRead(@Path("type") int type);

    /**
     * 设置消息为已读
     *
     * @return BaseResponse
     */
    @GET("client/message/unread/update/single/{id}")
    Observable<BaseResponse> setRead(@Path("id") int id);

    /**
     * 获取订单消息列表
     *
     * @return BaseResponse
     */
    @GET("client/message/order")
    Observable<BaseResponse<BaseListEntity<MessageEntity>>> getOrderMessageList(@Query("pageNum") int pageNum);

    /**
     * 获取系统消息列表
     *
     * @return BaseResponse
     */
    @GET("client/message/system")
    Observable<BaseResponse<BaseListEntity<MessageEntity>>> getSystemMessageList(@Query("pageNum") int pageNum);

    /**
     * 获取需求消息列表
     *
     * @return BaseResponse
     */
    @GET("client/message/requirement")
    Observable<BaseResponse<DemandMessageEntity>> getDemandMessageList();

    /**
     * 获取我的优惠券列表
     *
     * @return BaseResponse
     */
    @GET("client/rights/couponlist/v2")
    Observable<BaseResponse<BaseListEntity<CouponsEntity>>> getCouponsList(@Query("pageNum") int pageNum);

    /**
     * 获取快速咨询订单用户号码
     *
     * @return BaseResponse
     */
    @GET("lawyer/order/quick/userphone/{orderNo}")
    Observable<BaseResponse<RemainEntity>> getUserPhone(@Path("orderNo") String orderNo);

    /**
     * 发送快速咨询（需要先支付才可以调用）
     *
     * @return BaseResponse
     */
    @POST("client/quick/post")
    Observable<BaseResponse<OrderStatusEntity>> releaseFastConsult(@Body RequestBody body);

    /**
     * 获取用户与某个律师的最新需求状态
     *
     * @return BaseResponse
     */
    @GET("client/requirement/between/{lawyerId}")
    Observable<BaseResponse<List<RequirementStatusEntity>>> getRequirementStatus(@Path("lawyerId") int id);

    /**
     * 获取交易流程URL
     *
     * @return BaseResponse
     */
    @GET("tariffExplanationUrl")
    Observable<BaseResponse<AgreementEntity>> tariffExplanationUrl();

    /**
     * 专家咨询-发起通话
     *
     * @return BaseResponse
     */
    @GET("client/expert/call/{id}/v2")
    Observable<BaseResponse<ExpertCallEntity>> sendCall(@Path("id") int id);

    /**
     * 用户上传订单文档
     */
    @Multipart
    @POST("client/order/doc/upload")
    Observable<BaseResponse<DocUploadEntity>> docUpload(@Part("order_no") RequestBody order_no,
                                                        @Part MultipartBody.Part file);

    ///client/order/doc/get/{orderNo}/{pageNum}/{pageSize}
    //GET
    //用户获取订单文档
    @GET("client/order/doc/get/{orderNo}/{pageNum}/{pageSize}")
    Observable<BaseResponse<DocGetEntity>> docGet(@Path("orderNo") String orderNo, @Path("pageNum") int pageNum, @Path("pageSize") int pageSize);

    //发抢单类型商品需求
    @POST("client/requirement/create")
    Observable<BaseResponse<RequirementCreateEntity>> requirementCreate(@Body RequestBody body);

    ///client/requirement/grablawyers
    //POST
    //获取当前需求可抢单的律师列表(5个)
    @POST("client/requirement/grablawyers")
    Observable<BaseResponse<List<RushOrderLawyerEntity>>> requirementGrablawyers(@Body RequestBody body);

    ///client/requirement/status/check
    //POST
    //需求抢单状态查询
    @POST("client/requirement/status/check")
    Observable<BaseResponse<RushOrderStatusEntity>> requirementStatusCheck(@Body RequestBody body);

    ///lawyer/order/requirement/detail/{requirementId}
    //GET
    //付费需求详细信息
    @GET("lawyer/order/requirement/detail/{requirementId}/{orderNo}")
    Observable<BaseResponse<List<RequirementDetailEntity>>> requirementDetail(@Path("requirementId") int requirementId, @Path("orderNo") String orderNo);

    ///lawyer/order/requirement/detail/{requirementId}
    //GET
    //付费需求详细信息
    @GET("lawyer/order/requirement/detail/{requirementId}")
    Observable<BaseResponse<List<RequirementDetailEntity>>> requirementDetail(@Path("requirementId") int requirementId);

    ///client/order/doc/read/{repositoryId}
    //GET
    //用户端更新文件已读状态
    @GET("client/order/doc/read/{repositoryId}")
    Observable<BaseResponse> docRead(@Path("repositoryId") String repositoryId);

    ///freeText/bizinfo
    //GET
    //获取免费咨询运营日数据
    @GET("freeText/bizinfo")
    Observable<BaseResponse<FreeTextBizinfoEntity>> freeTextBizinfo();

    ///common/freeText/v2
    //POST
    //文字咨询列表v2
    @POST("common/freeText/v2")
    Observable<BaseResponse<BaseListEntity<CommonFreeTextEntity>>> commonFreeText(@Body RequestBody body);

    ///lawyer/freeText/v2
    @POST("lawyer/freeText/v2")
    Observable<BaseResponse<BaseListEntity<CommonFreeTextEntity>>> lawyerFreeText(@Body RequestBody body);

    ///lawyer/freeText/v2/{consultationId}/{lawyerId}/reply/detail/{pageNum}/{pageSize}
    //GET
    //文字咨询回复详情v2
    @GET("common/freeText/v2/{consultationId}/{lawyerId}/reply/detail/{pageNum}/{pageSize}")
    Observable<BaseResponse<BaseListEntity<FreeConsultReplyListEntity>>> replyDetail(@Path("consultationId") int consultationId,
                                                                                     @Path("lawyerId") int lawyerId,
                                                                                     @Path("pageNum") int pageNum,
                                                                                     @Path("pageSize") int pageSize);

    ///client/require/coupon
    //POST
    //用户优惠券列表
    @POST("client/require/coupon")
    Observable<BaseResponse<BaseListEntity<OrderCouponEntity>>> requireCoupon(@Body RequestBody body);

    ///assistant/filters
    //GET
    //获取服务助手步骤中筛选条件
    @GET("assistant/filters")
    Observable<BaseResponse<HelpStepEntity>> assistantFilters();

    ///assistant/recommendLawyers
    //POST
    //获取推荐律师
    @POST("assistant/recommendLawyers")
    Observable<BaseResponse<HelpStepLawyerEntity>> assistantRecommendLawyers(@Body RequestBody body);

    ///assistant/recommendLawyers/other
    //POST
    //获取其他推荐律师
    @POST("assistant/recommendLawyers/other")
    Observable<BaseResponse<HelpStepLawyerEntity>> assistantRecommendLawyersOther(@Body RequestBody body);

    ///client/requirement/one
    //GET
    //用户最近一次完成的可协商类型需求
    @POST("client/requirement/one")
    Observable<BaseResponse<BaseListEntity<HirstoryDemandEntity>>> clientRequirementOne(@Body RequestBody body);

    ////common/online/url
    //GET
    //获取在线咨询的H5链接(百度商桥地址)
    @GET("common/online/url")
    Observable<BaseResponse<OnlineUrlEntity>> clientOnlineUrl();

    //https://cpu.lex-mung.com/client/org/amount
    ///client/org/amount
    //GET
    //获取用户集团卡余额
    @GET("client/org/amount")
    Observable<BaseResponse<List<OrgAmountEntity>>> clientOrgAmount();

    /**
     * 获取用户余额
     *
     * @return BaseResponse
     */
    @GET("client/amount/balance/{id}")
    Observable<BaseResponse<BalanceEntity>> getUserBalance(@Path("id") int id);

    ///client/amount/balance/v2
    //POST
    //获取用户余额、会员卡、集团卡
    @POST("client/amount/balance/v2")
    Observable<BaseResponse<AmountBalanceEntity>> amountBalance(@Body RequestBody body);

    ///client/amount/balance/v2/{organizationLevId}
    //POST
    //获取用户余额、会员卡、集团卡
    @POST("client/amount/balance/v2/{organizationLevId}")
    Observable<BaseResponse<AmountBalanceEntity>> amountBalance2(@Path("organizationLevId") int organizationLevId, @Body RequestBody body);

    ///client/quick/coupon
    //POST
    //用户快速电话咨询优惠券列表
    @POST("client/quick/coupon")
    Observable<BaseResponse<BaseListEntity<OrderCouponEntity>>> quickCoupon(@Body RequestBody body);

    ///client/quick/pay
    //POST
    //用户快速电话咨询实付价格
    @POST("client/quick/pay")
    Observable<BaseResponse<QuickPayEntity>> quickPay(@Body RequestBody body);

    ///client/optimal/require/list
    //POST
    //用户热门需求优惠券列表
    @POST("client/optimal/require/list")
    Observable<BaseResponse<BaseListEntity<OrderCouponEntity>>> optimalRequireList(@Body RequestBody body);

    ///client/optimal/require
    //POST
    //用户热门商品实付价格
    @POST("client/optimal/require")
    Observable<BaseResponse<QuickPayEntity>> optimalRequire(@Body RequestBody body);

    ///common/client/home
    //GET
    //获取首页模板
    @GET("common/client/home")
    Observable<BaseResponse<BaseListEntity<HomeEntity>>> clientHome();

    //http://pv.sohu.com/cityjson?ie=utf-8
    //GET
    //ip定位
    @GET("https://restapi.amap.com/v3/ip?key=f55325bc244062399ce0579b6b9d56a5")
    Observable<LaunchLocationEntity> cityjson();

    ///random
    //GET
    //移动web消息
    @GET("random")
    Observable<BaseResponse<List<String>>> random();

//    ///common/random/v2
//    //GET
//    //app首页顶端消息
//    @GET("common/random/v2")
//    Observable<BaseResponse<List<String>>> random();

    ///client/recharge/list
    //POST
    //充值赠送列表
    @POST("client/recharge/list")
    Observable<BaseResponse<List<RechargeEntity>>> rechargeList(@Body RequestBody body);

    ///client/voucher/{voucherPackId}
    //GET
    //券包关联优惠券列表
    @GET("client/voucher/{voucherPackId}")
    Observable<BaseResponse<List<RechargeCouponEntity>>> rechargeCouponList(@Path("voucherPackId") int voucherPackId);

    ///common/solution/list
    //GET
    //获取解决方案全部类型
    @GET("common/solution/list")
    Observable<BaseResponse<BaseListEntity<CommonSolutionEntity>>> commonSolutionList();

    ///common/lawyer/homepage/{pageNum}/{pageSize}
    //POST
    //首页律师列表（返回与/common/lawyer/search/一致）
    @POST("common/lawyer/homepage/{pageNum}/{pageSize}")
    Observable<BaseResponse<BaseListEntity<LawyerEntity2>>> getLawyerHomeList(@Path("pageNum") int pageNum,
                                                                              @Path("pageSize") int pageSize,
                                                                              @Body RequestBody body);

    ///common/marks/{solutionId}
    //GET
    //根据解决方案id获取标签（最多返回8个）
    @GET("common/marks/{solutionId}")
    Observable<BaseResponse<List<CommonMarkEntity>>> commonMarks(@Path("solutionId") int solutionId);

    //common/page/contracts/{solutionTypeId}
    //GET
    //起草审查合同文案
    @GET("common/page/contracts/{solutionTypeId}")
    Observable<BaseResponse<List<CommonPageContractsEntity>>> commonPageContracts(@Path("solutionTypeId") int solutionTypeId);

    ///client/coupon/count
    //POST
    //用户可用优惠个数
    @POST("client/coupon/count")
    Observable<BaseResponse<Integer>> couponCount(@Body RequestBody body);

    ///client/withdraw/verify
    //POST
    //查看用户是否有正在审核中的提现申请
    @GET("client/withdraw/verify")
    Observable<BaseResponse<Boolean>> withdrawVerify();

    ///client/expert/reserve
    //POST
    //预约专家咨询
    @POST("client/expert/reserve")
    Observable<BaseResponse<ExpertReserveEntity>> expertReserve(@Body RequestBody body);

    ///client/expert/cancel
    //POST
    //用户取消专家咨询订单
    @POST("client/expert/cancel")
    Observable<BaseResponse> expertCancel(@Body RequestBody body);

    ///client/expert/finish
    //POST
    //用户结束专家咨询订单
    @POST("client/expert/finish")
    Observable<BaseResponse> expertFinish(@Body RequestBody body);

    ///client/receive/{voucherPackId}
    //GET
    //用户领取券包
    @GET("client/receive/{voucherPackId}")
    Observable<BaseResponse> clientReceive(@Path("voucherPackId") int voucherPackId);

    ///client/coupon/gain
    //POST
    //用户领取优惠券
    @POST("client/coupon/gain")
    Observable<BaseResponse> clientCouponGain(@Body RequestBody body);

    ///client/call/order/insert
    //POST
    //公益电话订单新增
    @POST("client/call/order/insert")
    Observable<BaseResponse> callOrderInsert(@Body RequestBody body);

    ///common/popup/list
    //POST
    //客户端弹窗
    @POST("common/popup/list")
    Observable<BaseResponse<BaseListEntity<ActivityEntity>>> popupList(@Body RequestBody body);

    ///lawyer/caseorder/detail/v2/{id}
    //GET
    //律师端案源订单详情
    @GET("lawyer/caseorder/detail/v2/{id}")
    Observable<BaseResponse<EntrustDetailEntity>> caseorderDetail(@Path("id") int id);

    ///common/pages/second
    //GET
    //二级页面获取h5地址
    @GET("common/pages/second")
    Observable<BaseResponse<PagesSecondEntity>> pagesSecond();
}
